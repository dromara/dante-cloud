/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dante.gateway.filter;

import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.assistant.core.enums.ResultErrorCodes;
import cn.herodotus.engine.assistant.core.utils.SqlInjectionUtils;
import cn.herodotus.dante.gateway.utils.WebFluxUtils;
import io.netty.buffer.ByteBufAllocator;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.core5.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>Description: SQL 注入拦截过滤器 </p>
 * <p>
 * 对xss字符集的转换会导致会第三方平台接入的接口出现一些列问题，尤其是需要参数签名验签的接口，因为参数的变化导致验签不成功
 * 对于第三方平台（尤其时强势的第三方），我们往往无法要求第三方按照我们的参数规则传递参数，这类的接口会包含sql注入的关键字
 * 在请求重构过程，可能会改变参数的结构，会导致验签失败
 * 对post请求，虽然目前前后端大多交互都是通过Json，但如有特殊请求参数可能是非Json格式参数，需要多改类型参数进行兼容
 *
 * @author : gengwei.zheng
 * @date : 2021/9/1 8:47
 */
@Component
public class GlobalSqlInjectionFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(GlobalSqlInjectionFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.debug("[Herodotus] |- Global SQL Injection Filter in use!");

        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        HttpMethod method = serverHttpRequest.getMethod();
        String contentType = serverHttpRequest.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);

        URI uri = exchange.getRequest().getURI();

        if (isGetRequest(method)) {
            String rawQuery = uri.getRawQuery();
            if (StringUtils.isBlank(rawQuery)) {
                return chain.filter(exchange);
            }

            log.debug("[Herodotus] |- The original request parameter is [{}]", rawQuery);
            // 执行XSS清理
            boolean isSQLInjection = SqlInjectionUtils.checkForGet(rawQuery);

            // 如果存在sql注入,直接拦截请求
            if (isSQLInjection) {
                return sqlInjectionResponse(exchange, uri);
            }
            // 不对参数做任何处理
            return chain.filter(exchange);
        }

        //post请求时，如果是文件上传之类的请求，不修改请求消息体
        if (isPostRequest(method, contentType)) {
            // 参考的是 org.springframework.cloud.gateway.filter.factory.AddRequestParameterGatewayFilterFactory

            //从请求里获取Post请求体
            String bodyString = resolveBodyFromRequest(serverHttpRequest);

            if (StringUtils.isNotBlank(bodyString)) {

                boolean isSQLInjection;
                if (WebFluxUtils.isJsonMediaType(contentType)) {
                    //如果MediaType是json才执行json方式验证
                    isSQLInjection = SqlInjectionUtils.checkForPost(bodyString);
                } else {
                    //form表单方式，需要走get请求
                    isSQLInjection = SqlInjectionUtils.checkForGet(bodyString);
                }

                //  如果存在sql注入,直接拦截请求
                if (isSQLInjection) {
                    return sqlInjectionResponse(exchange, uri);
                }

                ServerHttpRequest request = serverHttpRequest.mutate().uri(uri).build();
                // 重新构造body
                byte[] newBytes = bodyString.getBytes(StandardCharsets.UTF_8);
                DataBuffer bodyDataBuffer = toDataBuffer(newBytes);
                Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);

                HttpHeaders headers = new HttpHeaders();
                headers.putAll(exchange.getRequest().getHeaders());


                // 由于修改了传递参数，需要重新设置CONTENT_LENGTH，长度是字节长度，不是字符串长度
                int length = bodyString.getBytes().length;
                headers.remove(HttpHeaders.CONTENT_LENGTH);
                headers.setContentLength(length);

                // 设置CONTENT_TYPE
                if (StringUtils.isNotBlank(contentType)) {
                    headers.set(HttpHeaders.CONTENT_TYPE, contentType);
                }

                // 由于post的body只能订阅一次，由于上面代码中已经订阅过一次body。所以要再次封装请求到request才行，不然会报错请求已经订阅过
                request = new ServerHttpRequestDecorator(request) {
                    @Override
                    public HttpHeaders getHeaders() {
                        return headers;
                    }

                    @Override
                    public Flux<DataBuffer> getBody() {
                        return bodyFlux;
                    }
                };

                //封装request，传给下一级
                return chain.filter(exchange.mutate().request(request).build());
            }
        }

        return chain.filter(exchange);
    }

    /**
     * 从Flux<DataBuffer>中获取字符串的方法
     * @return 请求体
     */
    private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest) {
        //获取请求体
        Flux<DataBuffer> body = serverHttpRequest.getBody();
        AtomicReference<String> bodyRef = new AtomicReference<>();
        body.subscribe(buffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
            DataBufferUtils.release(buffer);
            bodyRef.set(charBuffer.toString());
        });
        //获取request body
        return bodyRef.get();
    }

    /**
     * 字节数组转DataBuffer
     *
     * @param bytes 字节数组
     * @return DataBuffer
     */
    private DataBuffer toDataBuffer(byte[] bytes) {
        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }

    private Mono<Void> sqlInjectionResponse(ServerWebExchange exchange, URI uri) {
        log.error("[Herodotus] |- Paramters of Request [" + uri.getRawPath() + uri.getRawQuery() + "] contain illegal SQL keyword!");
        return WebFluxUtils.writeJsonResponse(exchange.getResponse(), new Result<String>().type(ResultErrorCodes.SQL_INJECTION_REQUEST).status(HttpStatus.SC_FORBIDDEN));
    }

    private boolean isGetRequest(HttpMethod method) {
        return method == HttpMethod.GET;
    }

    private Boolean isPostRequest(HttpMethod method, String contentType) {
        return (method == HttpMethod.POST || method == HttpMethod.PUT) && (MediaType.APPLICATION_FORM_URLENCODED_VALUE.equalsIgnoreCase(contentType) || MediaType.APPLICATION_JSON_VALUE.equals(contentType));
    }

    /**
     * 自定义过滤器执行的顺序，数值越大越靠后执行，越小就越先执行
     *
     * @return
     */
    @Override
    public int getOrder() {
        return FilterOrder.GLOBAL_SQL_INJECTION_FILTER_ORDER;
    }
}

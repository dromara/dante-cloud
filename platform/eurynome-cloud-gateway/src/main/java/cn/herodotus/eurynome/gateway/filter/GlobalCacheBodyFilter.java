/*
 * Copyright (c) 2019-2021 Gengwei Zheng (herodotus@aliyun.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-gateway
 * File Name: GlobalCacheBodyFilter.java
 * Author: gengwei.zheng
 * Date: 2021/09/01 17:34:01
 */

package cn.herodotus.eurynome.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <p>Description: Request Body 缓存过滤器 </p>
 * <p>
 * 这个过滤器解决body不能重复读的问题，为后续的GlobalFilter重写post|put请求的body做准备
 *
 * @author : gengwei.zheng
 * @date : 2021/9/1 16:07
 */
@Component
public class GlobalCacheBodyFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpMethod method = exchange.getRequest().getMethod();
        String contentType = exchange.getRequest().getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
        if (method == HttpMethod.POST || method == HttpMethod.PUT) {
            if (MediaType.APPLICATION_FORM_URLENCODED_VALUE.equalsIgnoreCase(contentType)
                    || MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(contentType)
                    || MediaType.APPLICATION_JSON_UTF8_VALUE.equals(contentType)) {
                return DataBufferUtils.join(exchange.getRequest().getBody())
                        .flatMap(dataBuffer -> {
                            DataBufferUtils.retain(dataBuffer);
                            Flux<DataBuffer> cachedFlux = Flux.defer(() -> Flux.just(dataBuffer.slice(0, dataBuffer.readableByteCount())));
                            ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                                @Override
                                public Flux<DataBuffer> getBody() {
                                    return cachedFlux;
                                }
                            };
                            return chain.filter(exchange.mutate().request(mutatedRequest).build());
                        });
            }

        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return FilterOrder.GLOBAL_CACHE_BODY_FILTER_ORDER;
    }
}

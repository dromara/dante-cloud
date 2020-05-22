package cn.herodotus.eurynome.gateway.filter;

import com.google.common.base.Joiner;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR;

/**
 * <p>Description: 统一返回的响应数据格式 </p>
 *
 * {@link :https://www.cnblogs.com/commissar-Xia/p/11651196.html}
 * @author : gengwei.zheng
 * @date : 2020/3/4 18:37
 */
@Slf4j
@Component
public class GlobalResponseFilter implements GlobalFilter, Ordered {

    private static Joiner joiner = Joiner.on("");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.debug("[Herodotus] |- Gateway Global Response Filter in use!");

        //获取response的 返回数据
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        ServerHttpResponseDecorator responseDecorator = new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                // 获取ContentType，判断是否返回JSON格式数据
                String originalResponseContentType = exchange.getAttribute(ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);
                if (StringUtils.isNotBlank(originalResponseContentType) && originalResponseContentType.contains(MediaType.APPLICATION_JSON.toString())) {

                    Flux<? extends DataBuffer> fluxBody = Flux.from(body);

                    return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                        //解决返回体分段传输
                        List<String> list = Lists.newArrayList();
                        dataBuffers.forEach(dataBuffer -> {
                            try {
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);
                                list.add(new String(content, StandardCharsets.UTF_8));
                            } catch (Exception e) {
                                log.error("[Herodotus] |- Load api encryption rules failed， reason ：{}", Throwables.getStackTraceAsString(e));
                            }
                        });

                        String responseData = joiner.join(list);
                        // 二次处理（加密/过滤等）如果不需要做二次处理可直接跳过下行
                        byte[] uppedContent = new String(responseData.getBytes(), StandardCharsets.UTF_8).getBytes();
                        originalResponse.getHeaders().setContentLength(uppedContent.length);
                        return bufferFactory.wrap(uppedContent);
                    }));
                }
                return super.writeWith(body);
            }

            @Override
            public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
                return writeWith(Flux.from(body).flatMapSequential(p -> p));
            }
        };
        return chain.filter(exchange.mutate().response(responseDecorator).build());
    }

    @Override
    public int getOrder() {
        return FilterOrder.GLOBAL_RESPONSE_FILTER_ORDER;
    }
}

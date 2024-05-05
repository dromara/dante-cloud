/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 *    Author: ZHENGGENGWEI<码匠君>
 *    Contact: <herodotus@aliyun.com>
 *    Blog and source code availability: <https://gitee.com/herodotus/herodotus-cloud>
 */

package cn.herodotus.cloud.gateway.filter;

import cn.herodotus.stirrup.web.core.reactive.utils.ExchangeUtils;
import cn.herodotus.stirrup.web.core.reactive.utils.HeaderUtils;
import cn.herodotus.stirrup.web.core.reactive.utils.RequestBodyUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <p>Description: 响应体处理 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/9/1 19:58
 */
@Component
public class GlobalCacheBodyFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (ExchangeUtils.isPostTypeRequest(exchange)) {
            return DataBufferUtils.join(exchange.getRequest().getBody())
                    .flatMap(dataBuffer -> {
                        byte[] bytes = RequestBodyUtils.toBytes(dataBuffer);
                        Flux<DataBuffer> cached = RequestBodyUtils.cached(exchange, bytes);
                        ServerWebExchange newExchange = ExchangeUtils.createNewExchange(exchange, HeaderUtils.getHeaders(exchange), cached);
                        return chain.filter(newExchange);
                    });
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return FilterOrder.GLOBAL_CACHE_BODY_FILTER_ORDER;
    }
}

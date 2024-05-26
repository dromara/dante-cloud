/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Herodotus Cloud.
 *
 * Herodotus Cloud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Herodotus Cloud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.vip>.
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

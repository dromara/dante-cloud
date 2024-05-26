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

package cn.herodotus.cloud.gateway.factory;

import cn.herodotus.stirrup.core.definition.constants.HerodotusHeaders;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import static org.springframework.cloud.gateway.support.GatewayToStringStyler.filterToStringCreator;

/**
 * <p>Description: Gateway Session 处理，方便统一前后端 Session</p>
 *
 * @author : gengwei.zheng
 * @date : 2022/12/2 18:14
 */
@Component
public class HerodotusSessionGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private static final Logger log = LoggerFactory.getLogger(HerodotusSessionGatewayFilterFactory.class);

    @Override
    public GatewayFilter apply(Object config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

                log.debug("[Herodotus] |- Herodotus Session Gateway Filter in use!");

                String herodotusSession = exchange.getRequest().getHeaders().getFirst(HerodotusHeaders.X_HERODOTUS_SESSION_ID);

                return exchange.getSession()
                        .doOnNext(webSession -> {
                            String sessionId = webSession.getId();
                            log.debug("[Herodotus] |- Gateway session id is : [{}]", sessionId);
                            if (StringUtils.isNotBlank(herodotusSession)) {
                                sessionId = herodotusSession;
                                log.debug("[Herodotus] |- Using herodotus session id: [{}]", herodotusSession);
                            }
                            exchange.getRequest()
                                    .mutate()
                                    .header("Cookie", "SESSION=" + Base64.encode(sessionId))
                                    .build();
                        })
                        .flatMap(WebSession::save)
                        .then(chain.filter(exchange));
            }

            @Override
            public String toString() {
                return filterToStringCreator(HerodotusSessionGatewayFilterFactory.this).toString();
            }
        };
    }
}
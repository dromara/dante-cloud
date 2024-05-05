/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 *    Author: ZHENGGENGWEI<码匠君>
 *    Contact: <herodotus@aliyun.com>
 *    Blog and source code availability: <https://gitee.com/herodotus/herodotus-cloud>
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
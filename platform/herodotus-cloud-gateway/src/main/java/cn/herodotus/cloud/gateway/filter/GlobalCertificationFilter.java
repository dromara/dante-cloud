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

import cn.herodotus.cloud.gateway.properties.GatewaySecurityProperties;
import cn.herodotus.stirrup.core.autoconfigure.oauth2.reactive.WebFluxPathUtils;
import cn.herodotus.stirrup.core.definition.constants.ErrorCodes;
import cn.herodotus.stirrup.core.foundation.utils.WellFormedUtils;
import cn.herodotus.stirrup.web.core.reactive.utils.HeaderUtils;
import cn.herodotus.stirrup.web.core.reactive.utils.ResponseUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.core5.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <p> Description : GlobalCertificationFilter </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/4 18:36
 */
@Component
public class GlobalCertificationFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(GlobalCertificationFilter.class);

    @Resource
    private GatewaySecurityProperties gatewaySecurityProperties;

    /**
     * 说明：目前所有请求均是通过Gateway进行访问。
     * /oauth/check_token，是比较特殊的地址，不是使用token的方式进行鉴权。
     * 虽然目前使用的是“permitAll”的方式，不够安全。但是不管什么情况，在Gateway这一端，不应该进行拦截。
     * 后续可以根据IP，以及OAuth2鉴权的方式进行安全控制。
     *
     * @param exchange exchange
     * @param chain    chain
     * @return 无
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.debug("[Herodotus] |- Gateway Global Certification Filter in use!");

        // 1.检查是否是免登陆连接
        String url = exchange.getRequest().getURI().getPath();
        List<String> whiteList = gatewaySecurityProperties.getWhiteList();


        if (WebFluxPathUtils.isPathMatch(whiteList, url)) {
            return chain.filter(exchange);
        }

        // 2.外部进入的请求，如果包含 X_HERODOTUS_FROM_IN 请求头，认为是非法请求，直接拦截。X_HERODOTUS_FROM_IN 只能用于内部 Feign 间忽略权限使用
        String fromIn = HeaderUtils.getHerodotusFromIn(exchange);
        if (ObjectUtils.isNotEmpty(fromIn)) {
            log.warn("[Herodotus] |- Illegal request to disable access!");
            return ResponseUtils.renderError(exchange, ErrorCodes.ACCESS_DENIED, HttpStatus.SC_FORBIDDEN);
        }

        String webSocketToken = HeaderUtils.getWebsocketProtocol(exchange);
        if (StringUtils.isNotBlank(webSocketToken) && StringUtils.endsWith(webSocketToken, ".stomp")) {
            return chain.filter(exchange);
        }

        // 3.非免登陆地址，获取token 检查token，如果为空，或者不是 Bearer XXX形式，则认为未授权。
        String token = HeaderUtils.getAuthorization(exchange);
        if (!WellFormedUtils.isToken(token)) {
            log.warn("[Herodotus] |- Token is not Well Formed!");
            return ResponseUtils.renderError(exchange, ErrorCodes.ACCESS_DENIED, HttpStatus.SC_UNAUTHORIZED);
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return FilterOrder.GLOBAL_CERTIFICATION_FILTER_ORDER;
    }
}

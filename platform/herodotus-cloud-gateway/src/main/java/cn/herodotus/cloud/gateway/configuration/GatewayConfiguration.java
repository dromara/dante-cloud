/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 *    Author: ZHENGGENGWEI<码匠君>
 *    Contact: <herodotus@aliyun.com>
 *    Blog and source code availability: <https://gitee.com/herodotus/herodotus-cloud>
 */

package cn.herodotus.cloud.gateway.configuration;

import cn.herodotus.cloud.gateway.handler.RefreshRoutesListener;
import cn.herodotus.cloud.gateway.properties.GatewaySecurityProperties;
import cn.herodotus.stirrup.web.core.annotation.ConditionalOnSwaggerEnabled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * <p>Description: Gateway 服务网关配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/11/3 11:53
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({GatewaySecurityProperties.class})
public class GatewayConfiguration {

    private static final Logger log = LoggerFactory.getLogger(GatewayConfiguration.class);

    private static final String MAX_AGE = "18000L";

    /**
     * Gateway 跨域处理
     *
     * @return WebFilter
     */
    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            if (CorsUtils.isCorsRequest(request)) {
                HttpHeaders requestHeaders = request.getHeaders();
                ServerHttpResponse response = ctx.getResponse();
                HttpMethod requestMethod = requestHeaders.getAccessControlRequestMethod();
                HttpHeaders headers = response.getHeaders();
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, requestHeaders.getOrigin());
                headers.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders.getAccessControlRequestHeaders());
                if (requestMethod != null) {
                    headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, requestMethod.name());
                }
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
                headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
                headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE);
                if (request.getMethod() == HttpMethod.OPTIONS) {
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }

            }

            return chain.filter(ctx);
        };
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnSwaggerEnabled
    static class GatewaySwaggerConfiguration {
        @Bean
        public RefreshRoutesListener refreshRoutesListener(RouteLocator routeLocator, SwaggerUiConfigParameters swaggerUiConfigParameters, SwaggerUiConfigProperties swaggerUiConfigProperties) {
            RefreshRoutesListener refreshRoutesListener = new RefreshRoutesListener(routeLocator, swaggerUiConfigParameters, swaggerUiConfigProperties);
            log.trace("[Herodotus] |- Bean [Refresh Routes Listener] Auto Configure.");
            return refreshRoutesListener;
        }
    }

}

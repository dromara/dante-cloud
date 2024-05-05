/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 *    Author: ZHENGGENGWEI<码匠君>
 *    Contact: <herodotus@aliyun.com>
 *    Blog and source code availability: <https://gitee.com/herodotus/herodotus-cloud>
 */

package cn.herodotus.cloud.gateway.handler;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.ApplicationListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>Description: 服务变更监听 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/9/10 22:38
 */
public class RefreshRoutesListener implements ApplicationListener<RefreshRoutesEvent> {

    private static final Logger log = LoggerFactory.getLogger(RefreshRoutesListener.class);

    public static final String API_URI = "/v3/api-docs";

    /**
     * 网关应用名称
     */
    @Value("${spring.application.name}")
    private String self;
    private final RouteLocator routeLocator;
    private final SwaggerUiConfigParameters swaggerUiConfigParameters;
    private final SwaggerUiConfigProperties swaggerUiConfigProperties;

    public RefreshRoutesListener(RouteLocator routeLocator, SwaggerUiConfigParameters swaggerUiConfigParameters, SwaggerUiConfigProperties swaggerUiConfigProperties) {
        this.routeLocator = routeLocator;
        this.swaggerUiConfigParameters = swaggerUiConfigParameters;
        this.swaggerUiConfigProperties = swaggerUiConfigProperties;
    }

    @Override
    public void onApplicationEvent(RefreshRoutesEvent refreshRoutesEvent) {

        List<String> routes = new ArrayList<>();
        routeLocator.getRoutes()
                .filter(route -> route.getUri().getHost() != null && Objects.equals(route.getUri().getScheme(), "lb") && !self.equalsIgnoreCase(route.getUri().getHost()))
                .subscribe(route -> routes.add(route.getUri().getHost()));

        Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> swaggerUrls = routes.stream().map(this::createSwaggerUrl).collect(Collectors.toSet());

        if (ObjectUtils.isNotEmpty(swaggerUiConfigParameters)) {
            log.trace("[Herodotus] |- Services is Changed, update Urls");
            swaggerUiConfigParameters.setUrls(swaggerUrls);
            swaggerUiConfigProperties.setUrls(swaggerUrls);
        }
    }

    private AbstractSwaggerUiConfigProperties.SwaggerUrl createSwaggerUrl(String service) {

        String data = API_URI;

        if (StringUtils.containsIgnoreCase(service, "bpmn")) {
            data = "/openapi.json";
        }

        String url = "/" + service.toLowerCase() + data;


        log.trace("[Herodotus] |- Create Swagger Url - Name: {}, Location {}.", service, url);

        AbstractSwaggerUiConfigProperties.SwaggerUrl swaggerUrl = new AbstractSwaggerUiConfigProperties.SwaggerUrl();
        swaggerUrl.setUrl(url);
        swaggerUrl.setName(service);
        return swaggerUrl;
    }
}

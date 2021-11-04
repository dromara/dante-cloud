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
 * File Name: ServiceRefreshRoutesListener.java
 * Author: gengwei.zheng
 * Date: 2021/09/11 10:14:11
 */

package cn.herodotus.eurynome.gateway.handler;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springdoc.core.SwaggerUiConfigProperties;
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
 * @date : 2021/9/11 10:14
 */
public class RefreshRoutesListener implements ApplicationListener<RefreshRoutesEvent> {

    private static final Logger log = LoggerFactory.getLogger(RefreshRoutesListener.class);

    public static final String API_URI = "/v3/api-docs";

    /**
     * 网关应用名称
     */
    @Value("${spring.application.name}")
    private String self;
    private RouteLocator routeLocator;
    private SwaggerUiConfigParameters swaggerUiConfigParameters;
    private SwaggerUiConfigProperties swaggerUiConfigProperties;

    public void setRouteLocator(RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }

    public void setSwaggerUiConfigParameters(SwaggerUiConfigParameters swaggerUiConfigParameters) {
        this.swaggerUiConfigParameters = swaggerUiConfigParameters;
    }

    public void setSwaggerUiConfigProperties(SwaggerUiConfigProperties swaggerUiConfigProperties) {
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
            log.debug("[Herodotus] |- Services is Changed, update Urls");
            swaggerUiConfigParameters.setUrls(swaggerUrls);
            swaggerUiConfigProperties.setUrls(swaggerUrls);
        }
    }

    private AbstractSwaggerUiConfigProperties.SwaggerUrl createSwaggerUrl(String service) {

        String data = API_URI;

        if (StringUtils.containsIgnoreCase(service, "bpmn")) {
            data =  "/openapi.json";
        }

        String url = "/" + service.toLowerCase() + data;

        log.debug("[Herodotus] |- Create Swagger Url - Name: {}, Location {}.", service, url);

        AbstractSwaggerUiConfigProperties.SwaggerUrl swaggerUrl = new AbstractSwaggerUiConfigProperties.SwaggerUrl();
        swaggerUrl.setUrl(url);
        swaggerUrl.setName(service);
        return swaggerUrl;
    }
}


/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dante.gateway.handler;

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
            data = "/openapi.json";
        }

        String url = "/" + service.toLowerCase() + data;

        log.debug("[Herodotus] |- Create Swagger Url - Name: {}, Location {}.", service, url);

        AbstractSwaggerUiConfigProperties.SwaggerUrl swaggerUrl = new AbstractSwaggerUiConfigProperties.SwaggerUrl();
        swaggerUrl.setUrl(url);
        swaggerUrl.setName(service);
        return swaggerUrl;
    }
}


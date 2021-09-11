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
 * Module Name: eurynome-cloud-rest
 * File Name: SwaggerConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/08/20 11:58:20
 */

package cn.herodotus.eurynome.rest.configuration;

import cn.herodotus.eurynome.assistant.annotation.conditional.ConditionalOnSwaggerEnabled;
import cn.herodotus.eurynome.common.constant.enums.Architecture;
import cn.herodotus.eurynome.common.constant.magic.SecurityConstants;
import cn.herodotus.eurynome.common.constant.magic.SymbolConstants;
import cn.herodotus.eurynome.rest.properties.PlatformProperties;
import cn.herodotus.eurynome.rest.properties.ServiceProperties;
import com.google.common.collect.ImmutableList;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.*;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * <p> Description : SwaggerConfiguration </p>
 * <p>
 * 原来的@EnableSwagger2去掉
 *
 * @author : gengwei.zheng
 * @date : 2020/3/31 11:54
 */
@Slf4j
@Configuration
@ConditionalOnSwaggerEnabled
@AutoConfigureAfter(ServiceConfiguration.class)
@SecuritySchemes({
        @SecurityScheme(name = SecurityConstants.OPEN_API_SECURITY_SCHEME_BEARER_NAME, type = SecuritySchemeType.OAUTH2, bearerFormat = "JWT", scheme = "bearer",
                flows = @OAuthFlows(
                        password = @OAuthFlow(authorizationUrl = "${herodotus.platform.endpoint.user-authorization-uri}", tokenUrl = "${herodotus.platform.endpoint.access-token-uri}", refreshUrl = "${herodotus.platform.endpoint.access-token-uri}", scopes = @OAuthScope(name = "all")),
                        authorizationCode = @OAuthFlow(authorizationUrl = "${herodotus.platform.endpoint.user-authorization-uri}", tokenUrl = "${herodotus.platform.endpoint.access-token-uri}", refreshUrl = "${herodotus.platform.endpoint.access-token-uri}", scopes = @OAuthScope(name = "all"))
                )),
})
public class OpenAPIConfiguration {

    @Autowired
    private PlatformProperties platformProperties;
    @Autowired
    private ServiceProperties serviceProperties;

    @PostConstruct
    public void postConstruct() {
        log.debug("[Eurynome] |- Plugin [Herodotus Swagger] Auto Configure.");
    }

    @Bean
    public OpenAPI createOpenAPI() {
        return new OpenAPI()
                .servers(getServers())
                .info(new Info().title("Eurynome Cloud")
                        .description("Eurynome Cloud Microservices Architecture")
                        .version("v2.5.5.0")
                        .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/")))
                .externalDocs(new ExternalDocumentation()
                        .description("Eurynome Cloud Documentation")
                        .url(" https://herodotus.gitee.io/eurynome-cloud"));
    }

    private String getServiceAddress() {
        if (platformProperties.getArchitecture().equals(Architecture.DISTRIBUTED)) {
            return platformProperties.getEndpoint().getGatewayAddress() + SymbolConstants.FORWARD_SLASH + serviceProperties.getApplicationName();
        } else {
            return serviceProperties.getAddress();
        }
    }

    private List<Server> getServers() {
        Server server = new Server();
        server.setUrl(getServiceAddress());
        return ImmutableList.of(server);
    }
}

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
import cn.herodotus.eurynome.common.constant.magic.SecurityConstants;
import cn.herodotus.eurynome.common.constant.enums.Architecture;
import cn.herodotus.eurynome.rest.properties.PlatformProperties;
import cn.herodotus.eurynome.rest.properties.ServiceProperties;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.Collections;

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
@EnableSwagger2
public class SwaggerConfiguration {

    /**
     * Knife4j的一个问题，只能设置"oauth2"，否则token配置界面不会显示
     */
    private static final String SCHEMA_OAUTH_NAME = "oauth2";

    @Autowired
    private PlatformProperties platformProperties;
    @Autowired
    private ServiceProperties serviceProperties;

    @PostConstruct
    public void postConstruct() {
        log.debug("[Eurynome] |- Plugin [Herodotus Swagger] Auto Configure.");
    }

    private String getTokenAddress() {
        if (platformProperties.getArchitecture().equals(Architecture.MONOCOQUE)) {
            return serviceProperties.getUrl() + SecurityConstants.ENDPOINT_OAUTH_TOKEN;
        } else {
            return platformProperties.getEndpoint().getAccessTokenUri();
        }
    }

    @Bean
    public Docket docket() {
        // 注意此处改动，需要将SWAGGER_2改成OAS_30
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                // 扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                // 支持的通讯协议集合
                .securitySchemes(Collections.singletonList(securityScheme()))
                .securityContexts(Collections.singletonList(securityContexts()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Herodotus Cloud 聚合文档")
                .description("Herodotus Cloud 微服务框架")
                .contact(new Contact("码匠君", "https://blog.csdn.net/Pointer_v", "herodotus@aliyun.com"))
                .version("1.0")
                .build();
    }

    /**
     * 这里是写允许认证的scope
     */
    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[]{
                new AuthorizationScope("all", "All scope is trusted!")
        };
    }

    /**
     * 这个类决定了你使用哪种认证方式，我这里使用密码模式
     */
    private SecurityScheme securityScheme() {
        GrantType grantType = new ResourceOwnerPasswordCredentialsGrant(this.getTokenAddress());

        return new OAuthBuilder()
                .name(SCHEMA_OAUTH_NAME)
                .grantTypes(Collections.singletonList(grantType))
                .build();
    }

    /**
     * 授权信息全局应用
     */
    private SecurityContext securityContexts() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(new SecurityReference(SCHEMA_OAUTH_NAME, scopes())))
                .forPaths(PathSelectors.any())
                .build();
    }
}

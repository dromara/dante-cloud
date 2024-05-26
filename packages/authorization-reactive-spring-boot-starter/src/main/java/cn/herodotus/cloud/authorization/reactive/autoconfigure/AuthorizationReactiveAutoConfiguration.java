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

package cn.herodotus.cloud.authorization.reactive.autoconfigure;

import cn.herodotus.stirrup.oauth2.authorization.reactive.OAuth2AuthorizeExchangeSpecCustomizer;
import cn.herodotus.stirrup.oauth2.authorization.reactive.OAuth2ResourceServerSpecCustomizer;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;

/**
 * <p>Description: Reactive 环境资源服务器配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/1/26 15:13
 */
@AutoConfiguration
@EnableWebFluxSecurity
public class AuthorizationReactiveAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationReactiveAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Starter [Herodotus Authorization Reactive] Auto Configure.");
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(
            ServerHttpSecurity httpSecurity,
            ServerSecurityContextRepository serverSecurityContextRepository,
            OAuth2AuthorizeExchangeSpecCustomizer oauth2AuthorizeExchangeSpecCustomizer,
            OAuth2ResourceServerSpecCustomizer oauth2ResourceServerSpecCustomizer) {

        log.debug("[Herodotus] |- Bean [Authorization Reactive Security Filter Chain] Auto Configure.");

        httpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable).cors(ServerHttpSecurity.CorsSpec::disable);

        httpSecurity
                .authorizeExchange(oauth2AuthorizeExchangeSpecCustomizer)
                .oauth2ResourceServer(oauth2ResourceServerSpecCustomizer)
                .securityContextRepository(serverSecurityContextRepository);

        SecurityWebFilterChain chain = httpSecurity.build();

        chain.getWebFilters().toStream()
                .filter(webFilter -> webFilter instanceof AuthenticationWebFilter)
                .forEach(item -> {
                    AuthenticationWebFilter filter = (AuthenticationWebFilter) item;
                    filter.setSecurityContextRepository(serverSecurityContextRepository);
                });

        return chain;
    }
}

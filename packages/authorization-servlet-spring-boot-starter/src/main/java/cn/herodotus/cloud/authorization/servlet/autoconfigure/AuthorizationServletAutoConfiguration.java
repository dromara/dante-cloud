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

package cn.herodotus.cloud.authorization.servlet.autoconfigure;

import cn.herodotus.stirrup.oauth2.authorization.servlet.OAuth2AuthorizeHttpRequestsConfigurerCustomer;
import cn.herodotus.stirrup.oauth2.authorization.servlet.OAuth2ResourceServerConfigurerCustomer;
import cn.herodotus.stirrup.oauth2.authorization.servlet.OAuth2SessionManagementConfigurerCustomer;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache;

/**
 * <p>Description: Servlet 环境资源服务器配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/21 23:56
 */
@AutoConfiguration
@EnableWebSecurity
public class AuthorizationServletAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationServletAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Starter [Herodotus Authorization Servlet] Auto Configure.");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity,
            OAuth2SessionManagementConfigurerCustomer oauth2SessionManagementConfigurerCustomer,
            OAuth2ResourceServerConfigurerCustomer oauth2ResourceServerConfigurerCustomer,
            OAuth2AuthorizeHttpRequestsConfigurerCustomer oauth2AuthorizeHttpRequestsConfigurerCustomer
    ) throws Exception {

        log.debug("[Herodotus] |- Bean [Resource Server Security Filter Chain] Auto Configure.");

        httpSecurity.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable);

        httpSecurity.authorizeHttpRequests(oauth2AuthorizeHttpRequestsConfigurerCustomer)
                .sessionManagement(oauth2SessionManagementConfigurerCustomer)
                .oauth2ResourceServer(oauth2ResourceServerConfigurerCustomer);

        return httpSecurity.build();
    }
}

/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 *    Author: ZHENGGENGWEI<码匠君>
 *    Contact: <herodotus@aliyun.com>
 *    Blog and source code availability: <https://gitee.com/herodotus/herodotus-cloud>
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

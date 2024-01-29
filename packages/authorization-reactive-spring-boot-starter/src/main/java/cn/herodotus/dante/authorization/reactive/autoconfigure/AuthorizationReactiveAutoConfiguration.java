/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 *    Author: ZHENGGENGWEI<码匠君>
 *    Contact: <herodotus@aliyun.com>
 *    Blog and source code availability: <https://gitee.com/herodotus/herodotus-cloud>
 */

package cn.herodotus.dante.authorization.reactive.autoconfigure;

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

/**
 * <p>Description: TODO </p>
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
            OAuth2AuthorizeExchangeSpecCustomizer oauth2AuthorizeExchangeSpecCustomizer,
            OAuth2ResourceServerSpecCustomizer oauth2ResourceServerSpecCustomizer) {

        log.debug("[Herodotus] |- Bean [Authorization Reactive Security Filter Chain] Auto Configure.");

        httpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable).cors(ServerHttpSecurity.CorsSpec::disable);

        httpSecurity
                .authorizeExchange(oauth2AuthorizeExchangeSpecCustomizer)
                .oauth2ResourceServer(oauth2ResourceServerSpecCustomizer);
        return httpSecurity.build();
    }
}

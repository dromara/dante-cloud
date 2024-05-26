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

package cn.herodotus.cloud.authentication.autoconfigure;

import cn.herodotus.stirrup.core.foundation.processor.captcha.CaptchaRendererFactory;
import cn.herodotus.stirrup.core.identity.strategy.StrategyUserDetailsService;
import cn.herodotus.stirrup.oauth2.authentication.configurer.OAuth2FormLoginSecureConfigurer;
import cn.herodotus.stirrup.oauth2.authentication.customizer.HerodotusUserDetailsService;
import cn.herodotus.stirrup.oauth2.authentication.response.DefaultOAuth2AuthenticationEventPublisher;
import cn.herodotus.stirrup.oauth2.authorization.servlet.*;
import cn.herodotus.stirrup.oauth2.core.properties.OAuth2AuthenticationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * <p>Description: 默认安全配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/12 20:53
 */
@AutoConfiguration
@EnableWebSecurity
public class DefaultSecurityAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(DefaultSecurityAutoConfiguration.class);

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(
            HttpSecurity httpSecurity,
            UserDetailsService userDetailsService,
            CaptchaRendererFactory captchaRendererFactory,
            OAuth2AuthenticationProperties oauth2AuthenticationProperties,
            OAuth2SessionManagementConfigurerCustomer oauth2SessionManagementConfigurerCustomer,
            OAuth2ResourceServerConfigurerCustomer oauth2ResourceServerConfigurerCustomer,
            OAuth2AuthorizeHttpRequestsConfigurerCustomer oauth2AuthorizeHttpRequestsConfigurerCustomer
    ) throws Exception {

        log.debug("[Herodotus] |- Bean [Default Security Filter Chain] Auto Configure.");
        // 禁用CSRF 开启跨域
        httpSecurity.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable);

        httpSecurity
                .authorizeHttpRequests(oauth2AuthorizeHttpRequestsConfigurerCustomer)
                .sessionManagement(oauth2SessionManagementConfigurerCustomer)
                .exceptionHandling(exceptions -> {
                    exceptions.authenticationEntryPoint(new HerodotusAuthenticationEntryPoint());
                    exceptions.accessDeniedHandler(new HerodotusAccessDeniedHandler());
                })
                .oauth2ResourceServer(oauth2ResourceServerConfigurerCustomer)
                .with(new OAuth2FormLoginSecureConfigurer<>(userDetailsService, oauth2AuthenticationProperties, captchaRendererFactory), (configurer) -> {
                });

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationEventPublisher authenticationEventPublisher(ApplicationContext applicationContext) {
        log.debug("[Herodotus] |- Bean [Authentication Event Publisher] Auto Configure.");
        return new DefaultOAuth2AuthenticationEventPublisher(applicationContext);
    }

    @Bean
    @ConditionalOnMissingBean
    public UserDetailsService userDetailsService(StrategyUserDetailsService strategyUserDetailsService) {
        HerodotusUserDetailsService herodotusUserDetailsService = new HerodotusUserDetailsService(strategyUserDetailsService);
        log.debug("[Herodotus] |- Bean [Herodotus User Details Service] Auto Configure.");
        return herodotusUserDetailsService;
    }
}

/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.eurynome.module.oauth2.configuration;

import cn.herodotus.engine.security.extend.processor.HerodotusSecurityConfigureHandler;
import cn.herodotus.engine.security.extend.response.HerodotusAccessDeniedHandler;
import cn.herodotus.engine.security.extend.response.HerodotusAuthenticationEntryPoint;
import cn.herodotus.eurynome.module.oauth2.service.HerodotusOauthUserDetailsService;
import cn.herodotus.eurynome.module.upms.logic.service.system.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.annotation.PostConstruct;

/**
 * <p>Description: 默认安全配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/12 20:53
 */
@EnableWebSecurity
public class DefaultSecurityConfiguration {

    private static final Logger log = LoggerFactory.getLogger(DefaultSecurityConfiguration.class);

    @Autowired
    private HerodotusSecurityConfigureHandler herodotusSecurityConfigureHandler;

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [OAuth2 Default Security] Auto Configure.");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        // @formatter:off
        http.authorizeRequests()
                .antMatchers(herodotusSecurityConfigureHandler.getPermitAllArray()).permitAll()
                .antMatchers(herodotusSecurityConfigureHandler.getStaticResourceArray()).permitAll()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HerodotusAuthenticationEntryPoint())
                .accessDeniedHandler(new HerodotusAccessDeniedHandler())
                .and()
                .csrf().disable();
        // @formatter:on
        return http.build();
    }

    @Bean
    @ConditionalOnBean(SysUserService.class)
    @ConditionalOnMissingBean
    UserDetailsService userDetailsService(SysUserService sysUserService) {
        HerodotusOauthUserDetailsService herodotusOauthUserDetailsService = new HerodotusOauthUserDetailsService(sysUserService);
        log.debug("[Herodotus] |- Bean [Herodotus Oauth User Details Service] Auto Configure.");
        return herodotusOauthUserDetailsService;
    }
}

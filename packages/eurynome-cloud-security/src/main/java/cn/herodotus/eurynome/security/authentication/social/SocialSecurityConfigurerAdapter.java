/*
 * Copyright (c) 2019-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-security
 * File Name: SocialSecurityConfigurerAdapter.java
 * Author: gengwei.zheng
 * Date: 2021/4/3 下午5:47
 * LastModified: 2021/4/3 下午5:47
 */

package cn.herodotus.eurynome.security.authentication.social;

import cn.herodotus.eurynome.security.authentication.handler.SocialAuthenticationSuccessHandler;
import cn.herodotus.eurynome.security.definition.service.HerodotusUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: SocialSecurityConfigurerAdapter </p>
 *
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/3 17:47
 */
public class SocialSecurityConfigurerAdapter extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private AuthenticationSuccessHandler authenticationSuccessHandler;

    private HerodotusUserDetailsService herodotusUserDetailsService;

    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    public void setHerodotusUserDetailsService(HerodotusUserDetailsService herodotusUserDetailsService) {
        this.herodotusUserDetailsService = herodotusUserDetailsService;
    }

    @Override
    public void configure(HttpSecurity http) {
        SocialAuthenticationProcessingFilter socialAuthenticationProcessingFilter = new SocialAuthenticationProcessingFilter("/oauth/social");
        socialAuthenticationProcessingFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        socialAuthenticationProcessingFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);

        SocialAuthenticationProvider socialAuthenticationProvider = new SocialAuthenticationProvider();
        socialAuthenticationProvider.setHerodotusUserDetailsService(herodotusUserDetailsService);

        http.authenticationProvider(socialAuthenticationProvider)
                .addFilterAfter(socialAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

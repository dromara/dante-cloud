/*
 * Copyright (c) 2019-2020 the original author or authors.
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
 * Module Name: eurynome-cloud-starter
 * File Name: ResourceServerAutoConfiguration.java
 * Author: gengwei.zheng
 * Date: 2020/5/29 下午7:58
 * LastModified: 2020/5/29 下午7:58
 */

package cn.herodotus.eurynome.autoconfigure;

import cn.herodotus.eurynome.localstorage.service.SecurityMetadataService;
import cn.herodotus.eurynome.security.access.HerodotusAccessDecisionManager;
import cn.herodotus.eurynome.security.web.HerodotusAuthenticationEntryPoint;
import cn.herodotus.eurynome.security.web.access.HerodotusAccessDeniedHandler;
import cn.herodotus.eurynome.security.web.access.intercept.HerodotusSecurityMetadataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: ResourceServerAutoConfiguration </p>
 *
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/29 19:58
 */
@Slf4j
@Configuration
@EnableResourceServer
public class ResourceServerAutoConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private SecurityMetadataService securityMetadataService;

    @Bean
    @ConditionalOnBean(SecurityMetadataService.class)
    public FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource() {
        HerodotusSecurityMetadataSource herodotusSecurityMetadataSource = new HerodotusSecurityMetadataSource();
        herodotusSecurityMetadataSource.setSecurityMetadataService(securityMetadataService);
        log.debug("[Herodotus] |- Bean [Security Metadata Source] Auto Configure.");
        return herodotusSecurityMetadataSource;
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        HerodotusAccessDecisionManager herodotusAccessDecisionManager = new HerodotusAccessDecisionManager();
        log.debug("[Herodotus] |- Bean [Access Decision Manager] Auto Configure.");
        return herodotusAccessDecisionManager;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        http.authorizeRequests()
//                .antMatchers(getWhiteList()).permitAll()
                .anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
                        fsi.setAccessDecisionManager(accessDecisionManager());
                        fsi.setSecurityMetadataSource(filterInvocationSecurityMetadataSource());
                        return fsi;
                    }
                })
                .and() // 认证鉴权错误处理,为了统一异常处理。每个资源服务器都应该加上。
                .exceptionHandling()
                .accessDeniedHandler(new HerodotusAccessDeniedHandler())
                .authenticationEntryPoint(new HerodotusAuthenticationEntryPoint());

        // 关闭csrf 跨站（域）攻击防控
        http.csrf().disable();

        log.info("[Herodotus] |- Bean [Resource Server Configuration] Auto Configure.");

    }
}

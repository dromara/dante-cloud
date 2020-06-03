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
 * Module Name: eurynome-cloud-security
 * File Name: ResourceServerAutoConfiguration.java
 * Author: gengwei.zheng
 * Date: 2020/6/3 下午3:40
 * LastModified: 2020/6/3 下午3:22
 */

package cn.herodotus.eurynome.security.configuration;

import cn.herodotus.eurynome.rest.properties.ApplicationProperties;
import cn.herodotus.eurynome.rest.properties.RestProperties;
import cn.herodotus.eurynome.security.access.HerodotusAccessDecisionManager;
import cn.herodotus.eurynome.security.authentication.SecurityMetadataLocalStorage;
import cn.herodotus.eurynome.security.metadata.RequestMappingScanner;
import cn.herodotus.eurynome.security.properties.SecurityProperties;
import cn.herodotus.eurynome.security.web.HerodotusAuthenticationEntryPoint;
import cn.herodotus.eurynome.security.web.access.HerodotusAccessDeniedHandler;
import cn.herodotus.eurynome.security.web.access.intercept.HerodotusSecurityMetadataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.util.List;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: ResourceServerAutoConfiguration </p>
 *
 * <p>Description: 通用的ResourceServerConfigurerAdapter </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/29 19:58
 */
@Slf4j
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private ApplicationProperties applicationProperties;
    @Autowired
    private RestProperties restProperties;
    @Autowired
    private SecurityMetadataLocalStorage securityMetadataLocalStorage;

    /**
     * 自定义注解扫描
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(RequestMappingScanner.class)
    @ConditionalOnBean(SecurityMetadataLocalStorage.class)
    public RequestMappingScanner requestMappingScanner() {
        RequestMappingScanner requestMappingScan = new RequestMappingScanner(restProperties, applicationProperties, securityMetadataLocalStorage, EnableResourceServer.class);
        log.debug("[Herodotus] |- Bean [Request Mapping Scan] Auto Configure.");
        return requestMappingScan;
    }

    @Bean
    @ConditionalOnBean(RequestMappingScanner.class)
    public HerodotusSecurityMetadataSource herodotusSecurityMetadataSource() {
        HerodotusSecurityMetadataSource herodotusSecurityMetadataSource = new HerodotusSecurityMetadataSource();
        herodotusSecurityMetadataSource.setSecurityMetadataLocalStorage(securityMetadataLocalStorage);
        log.debug("[Herodotus] |- Bean [Security Metadata Source] Auto Configure.");
        return herodotusSecurityMetadataSource;
    }

    @Bean
    public HerodotusAccessDecisionManager herodotusAccessDecisionManager() {
        HerodotusAccessDecisionManager herodotusAccessDecisionManager = new HerodotusAccessDecisionManager();
        log.debug("[Herodotus] |- Bean [Access Decision Manager] Auto Configure.");
        return herodotusAccessDecisionManager;
    }



    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        http.authorizeRequests()
                .antMatchers(getWhiteList()).permitAll()
                .anyRequest().authenticated();

        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
                        fsi.setAccessDecisionManager(herodotusAccessDecisionManager());
                        fsi.setSecurityMetadataSource(herodotusSecurityMetadataSource());
                        return fsi;
                    }
                })
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new HerodotusAccessDeniedHandler())
                .authenticationEntryPoint(new HerodotusAuthenticationEntryPoint());

        // 关闭csrf 跨站（域）攻击防控
        http.csrf().disable();

        log.info("[Herodotus] |- Bean [Resource Server ConfigurerAdapter] Auto Configure.");

    }

    private String[] getWhiteList() {
        if (securityProperties != null) {
            List<String> whitelist = securityProperties.getInterceptor().getWhitelist();
            if (CollectionUtils.isNotEmpty(whitelist)) {
                log.info("[Herodotus] |- OAuth2 Fetch The Resource White List.");
                return whitelist.toArray(new String[]{});
            }
        }

        log.warn("[Herodotus] |- OAuth2 Can not Fetch The Resource White List Configurations.");
        return new String[]{};
    }
}

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
 * Module Name: eurynome-cloud-security
 * File Name: WebMvcSecurityConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/10/10 21:54:10
 */

package cn.herodotus.eurynome.module.security.configuration;

import cn.herodotus.engine.rest.secure.interceptor.XssHttpServletFilter;
import cn.herodotus.engine.security.core.properties.SecurityProperties;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * <p>Description: Web 安全配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/10 21:54
 */
@Configuration(proxyBeanMethods = false)
@Order(101)
public class WebMvcSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(WebMvcSecurityConfiguration.class);

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private XssHttpServletFilter xssHttpServletFilter;

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Core [Web Security Configurer Adapter] Auto Configure.");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(xssHttpServletFilter, FilterSecurityInterceptor.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(getIgnoredStaticResources());
    }

    private String[] getIgnoredStaticResources() {
        List<String> defaultIgnored = Lists.newArrayList("/error",
                "/static/**",
                "/webjars/**",
                "/features/**",
                "/plugins/**",
                "/favicon.ico",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/openapi.json");

        List<String> customIgnored = securityProperties.getInterceptor().getStaticResource();

        if (CollectionUtils.isNotEmpty(customIgnored)) {
            defaultIgnored.addAll(customIgnored);
        }

        String[] result = new String[defaultIgnored.size()];
        return defaultIgnored.toArray(result);
    }
}

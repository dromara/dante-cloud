/*
 * Copyright (c) 2019-2021 Gengwei Zheng(herodotus@aliyun.com)
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
 * File Name: WebMvcAutoConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.security.autoconfigure;

import cn.herodotus.eurynome.data.stamp.AccessLimitedStampManager;
import cn.herodotus.eurynome.data.stamp.IdempotentStampManager;
import cn.herodotus.eurynome.rest.security.AccessLimitedInterceptor;
import cn.herodotus.eurynome.rest.security.IdempotentInterceptor;
import cn.herodotus.eurynome.rest.security.XssHttpServletFilter;
import cn.herodotus.eurynome.security.properties.SecurityProperties;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * <p>Description: WebMvcAutoConfiguration </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/4 11:00
 */
@Configuration
public class WebMvcAutoConfiguration implements WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(WebMvcAutoConfiguration.class);

    @Autowired
    private IdempotentStampManager idempotentStampManager;
    @Autowired
    private AccessLimitedStampManager accessLimitedStampManager;

    @PostConstruct
    public void postConstruct() {
        log.debug("[Eurynome] |- Core [Herodotus Web Mvc in component security] Auto Configure.");
    }

    @Bean
    public IdempotentInterceptor idempotentInterceptor() {
        IdempotentInterceptor idempotentInterceptor = new IdempotentInterceptor();
        idempotentInterceptor.setIdempotentStampManager(idempotentStampManager);
        log.trace("[Eurynome] |- Bean [Idempotent Interceptor] Auto Configure.");
        return idempotentInterceptor;
    }

    @Bean
    public AccessLimitedInterceptor accessLimitedInterceptor() {
        AccessLimitedInterceptor accessLimitedInterceptor = new AccessLimitedInterceptor();
        accessLimitedInterceptor.setAccessLimitedStampManager(accessLimitedStampManager);
        log.trace("[Eurynome] |- Bean [Access Limited Interceptor] Auto Configure.");
        return accessLimitedInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessLimitedInterceptor());
        registry.addInterceptor(idempotentInterceptor());
        WebMvcConfigurer.super.addInterceptors(registry);
    }


    /**
     * 多个WebSecurityConfigurerAdapter
     */
    @Configuration(proxyBeanMethods = false)
    @Order(101)
    public static class StaticResourceSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        private SecurityProperties securityProperties;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.addFilterBefore(new XssHttpServletFilter(), FilterSecurityInterceptor.class);
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers(getIgnoredStaticResources());
        }

        private String[] getIgnoredStaticResources() {
            List<String> defaultIgnored = Lists.newArrayList("/error",
                    "/static/**",
                    "/webjars/**",
                    "/resources/**",
                    "/swagger-ui.html",
                    "/swagger-resources/**",
                    "/v2/api-docs",
                    "/features/**",
                    "/plugins/**",
                    "/favicon.ico");

            List<String> customIgnored = securityProperties.getInterceptor().getStaticResource();

            if (CollectionUtils.isNotEmpty(customIgnored)) {
                defaultIgnored.addAll(customIgnored);
            }

            String[] result = new String[defaultIgnored.size()];
            return defaultIgnored.toArray(result);
        }
    }


    /**
     * 资源处理器
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}

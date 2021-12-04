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
 * Module Name: eurynome-cloud-kernel
 * File Name: SentinelWebAutoConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/12/04 15:38:04
 */

package com.alibaba.cloud.sentinel;

import cn.herodotus.eurynome.kernel.configuration.SentinelConfiguration;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.SentinelWebInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.Optional;

/**
 * <p>Description: 重新定义Sentinel配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/4 15:38
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass(SentinelConfiguration.SentinelWebConfiguration.class)
public class SentinelWebAutoConfiguration extends WebMvcConfigurationSupport {

    private static final Logger log = LoggerFactory
            .getLogger(SentinelWebAutoConfiguration.class);

    @Autowired
    private SentinelProperties properties;

    @Autowired
    private Optional<SentinelWebInterceptor> sentinelWebInterceptorOptional;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (!sentinelWebInterceptorOptional.isPresent()) {
            return;
        }
        SentinelProperties.Filter filterConfig = properties.getFilter();
        registry.addInterceptor(sentinelWebInterceptorOptional.get())
                .order(filterConfig.getOrder())
                .addPathPatterns(filterConfig.getUrlPatterns());
        log.info(
                "[Sentinel Starter] register SentinelWebInterceptor with urlPatterns: {}.",
                filterConfig.getUrlPatterns());
    }
}
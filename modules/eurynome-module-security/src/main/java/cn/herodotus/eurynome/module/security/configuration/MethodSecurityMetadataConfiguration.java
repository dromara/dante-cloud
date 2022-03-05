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
 * File Name: MethodSecurityConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/07/28 18:12:28
 */

package cn.herodotus.eurynome.module.security.configuration;

import cn.herodotus.engine.web.core.definition.RequestMappingScanManager;
import cn.herodotus.eurynome.module.security.processor.HerodotusRequestMappingScanManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * <p>Description: 全局方法级安全配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/28 18:11
 */
@Configuration(proxyBeanMethods = false)
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class MethodSecurityMetadataConfiguration extends GlobalMethodSecurityConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MethodSecurityMetadataConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    public RequestMappingScanManager requestMappingScanManager() {
        HerodotusRequestMappingScanManager herodotusRequestMappingScanManager = new HerodotusRequestMappingScanManager();
        log.trace("[Herodotus] |- Bean [Request Mapping Scan Manager] Auto Configure.");
        return herodotusRequestMappingScanManager;
    }
}

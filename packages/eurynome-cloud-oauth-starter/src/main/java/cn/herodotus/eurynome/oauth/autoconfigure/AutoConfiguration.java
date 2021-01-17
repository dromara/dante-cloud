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
 * Module Name: eurynome-cloud-oauth-starter
 * File Name: AutoConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/1/17 上午11:07
 * LastModified: 2021/1/17 上午11:07
 */

package cn.herodotus.eurynome.oauth.autoconfigure;

import cn.herodotus.eurynome.oauth.autoconfigure.logic.DataSourceSecurityMetadata;
import cn.herodotus.eurynome.oauth.autoconfigure.service.OauthClientDetailsService;
import cn.herodotus.eurynome.oauth.autoconfigure.service.OauthUserDetailsService;
import cn.herodotus.eurynome.oauth.configuration.OauthConfiguration;
import cn.herodotus.eurynome.security.annotation.EnableHerodotusSecurity;
import cn.herodotus.eurynome.security.definition.service.HerodotusClientDetailsService;
import cn.herodotus.eurynome.security.definition.service.HerodotusUserDetailsService;
import cn.herodotus.eurynome.security.definition.service.SecurityMetadataStorage;
import cn.herodotus.eurynome.upms.logic.annotation.EnableUpmsLogic;
import cn.herodotus.eurynome.upms.logic.service.system.SysAuthorityService;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: AutoConfiguration </p>
 *
 * <p>Description: OAuth Starter </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/1/17 11:07
 */
@Slf4j
@Configuration
@EnableHerodotusSecurity
@EnableUpmsLogic
@Import(OauthConfiguration.class)
@EnableMethodCache(basePackages = {
        "cn.herodotus.eurynome.oauth.autoconfigure.service",
})
public class AutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.info("[Eurynome] |- Starter [Herodotus OAuth Starter] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean(HerodotusUserDetailsService.class)
    public HerodotusUserDetailsService herodotusUserDetailsService() {
        OauthUserDetailsService oauthUserDetailsService = new OauthUserDetailsService();
        log.debug("[Eurynome] |- Bean [User Details Service] Auto Configure.");
        return oauthUserDetailsService;
    }

    @Bean
    @ConditionalOnMissingBean(HerodotusClientDetailsService.class)
    public HerodotusClientDetailsService herodotusClientDetailsService() {
        OauthClientDetailsService oauthClientDetailsService = new OauthClientDetailsService();
        log.debug("[Eurynome] |- Bean [Client Details Service] Auto Configure.");
        return oauthClientDetailsService;
    }

    @Bean
    @ConditionalOnMissingBean(SecurityMetadataStorage.class)
    public SecurityMetadataStorage securityMetadataStorage(SysAuthorityService sysAuthorityService) {
        DataSourceSecurityMetadata dataSourceSecurityMetadata = new DataSourceSecurityMetadata(sysAuthorityService);
        log.debug("[Eurynome] |- Bean [Data Source Security Metadata] Auto Configure.");
        return dataSourceSecurityMetadata;
    }
}

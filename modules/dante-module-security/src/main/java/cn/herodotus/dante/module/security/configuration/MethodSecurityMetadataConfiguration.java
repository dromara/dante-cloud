/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2019-2022 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.dante.module.security.configuration;

import cn.herodotus.dante.module.security.processor.HerodotusRequestMappingScanManager;
import cn.herodotus.dante.module.security.processor.HerodotusSecurityMetadataSource;
import cn.herodotus.engine.oauth2.core.processor.HerodotusSecurityConfigureHandler;
import cn.herodotus.engine.oauth2.core.properties.SecurityProperties;
import cn.herodotus.engine.oauth2.metadata.configuration.SecurityMetadataConfiguration;
import cn.herodotus.engine.oauth2.metadata.processor.SecurityMetadataAnalysisProcessor;
import cn.herodotus.engine.oauth2.metadata.storage.SecurityMetadataLocalStorage;
import cn.herodotus.engine.web.core.definition.RequestMappingScanManager;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <p>Description: Security 权限配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/23 17:00
 */
@Configuration(proxyBeanMethods = false)
@Import({
        SecurityMetadataConfiguration.class
})
public class MethodSecurityMetadataConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MethodSecurityMetadataConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Module [Method Security Metadata] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestMappingScanManager requestMappingScanManager(SecurityMetadataAnalysisProcessor securityMetadataAnalysisProcessor) {
        HerodotusRequestMappingScanManager herodotusRequestMappingScanManager = new HerodotusRequestMappingScanManager(securityMetadataAnalysisProcessor);
        log.trace("[Herodotus] |- Bean [Request Mapping Scan Manager] Auto Configure.");
        return herodotusRequestMappingScanManager;
    }

    @Bean
    public HerodotusSecurityConfigureHandler herodotusSecurityConfigureHandler(SecurityProperties securityProperties) {
        HerodotusSecurityConfigureHandler herodotusSecurityRequestMatcherHandler = new HerodotusSecurityConfigureHandler(securityProperties);
        log.trace("[Herodotus] |- Bean [Herodotus Security Configure Handler] Auto Configure.");
        return herodotusSecurityRequestMatcherHandler;
    }

    /**
     * 权限信息存储器
     */
    @Bean
    @ConditionalOnMissingBean(HerodotusSecurityMetadataSource.class)
    public HerodotusSecurityMetadataSource herodotusSecurityMetadataSource(HerodotusSecurityConfigureHandler herodotusSecurityConfigureHandler, SecurityMetadataLocalStorage securityMetadataLocalStorage) {
        HerodotusSecurityMetadataSource herodotusSecurityMetadataSource = new HerodotusSecurityMetadataSource();
        herodotusSecurityMetadataSource.setSecurityMetadataLocalStorage(securityMetadataLocalStorage);
        herodotusSecurityMetadataSource.setHerodotusSecurityConfigureHandler(herodotusSecurityConfigureHandler);
        log.trace("[Herodotus] |- Bean [Herodotus Security Metadata Source] Auto Configure.");
        return herodotusSecurityMetadataSource;
    }
}

/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.cloud.module.social.configuration;

import cn.herodotus.cloud.module.social.processor.DefaultSocialAuthenticationHandler;
import cn.herodotus.stirrup.access.all.config.AccessAllConfiguration;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <p>Description: UPMS 社交配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/2 17:05
 */
@Configuration(proxyBeanMethods = false)
@Import({AccessAllConfiguration.class})
public class SocialModuleConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SocialModuleConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- SDK [Social Module] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultSocialAuthenticationHandler defaultSocialAuthenticationHandler() {
        DefaultSocialAuthenticationHandler defaultSocialAuthenticationHandler = new DefaultSocialAuthenticationHandler();
        log.trace("[Herodotus] |- Bean [Default Social Authentication Handler] Auto Configure.");
        return defaultSocialAuthenticationHandler;
    }
}

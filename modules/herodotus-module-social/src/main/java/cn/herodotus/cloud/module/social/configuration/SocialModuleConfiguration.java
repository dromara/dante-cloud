/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Herodotus Cloud.
 *
 * Herodotus Cloud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Herodotus Cloud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.vip>.
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

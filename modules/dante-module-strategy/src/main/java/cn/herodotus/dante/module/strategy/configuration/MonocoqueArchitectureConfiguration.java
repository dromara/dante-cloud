/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Dante Cloud.
 *
 * Dante Cloud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dante Cloud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.cn>.
 */

package cn.herodotus.dante.module.strategy.configuration;

import cn.herodotus.dante.module.social.configuration.SocialModuleConfiguration;
import cn.herodotus.dante.module.strategy.service.HerodotusLocalPermissionDetailsService;
import cn.herodotus.dante.module.strategy.service.HerodotusLocalUserDetailsService;
import cn.herodotus.engine.oauth2.core.definition.handler.SocialAuthenticationHandler;
import cn.herodotus.engine.oauth2.core.definition.strategy.StrategyPermissionDetailsService;
import cn.herodotus.engine.oauth2.core.definition.strategy.StrategyUserDetailsService;
import cn.herodotus.engine.rest.condition.annotation.ConditionalOnMonocoqueArchitecture;
import cn.herodotus.engine.supplier.upms.logic.configuration.SupplierUpmsLogicConfiguration;
import cn.herodotus.engine.supplier.upms.logic.service.security.SysPermissionService;
import cn.herodotus.engine.supplier.upms.logic.service.security.SysUserService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <p>Description: 单体式架构配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/1 21:27
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnMonocoqueArchitecture
@Import({SupplierUpmsLogicConfiguration.class, SocialModuleConfiguration.class})
public class MonocoqueArchitectureConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MonocoqueArchitectureConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Module Monocoque Architecture] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public StrategyPermissionDetailsService herodotusLocalPermissionDetailsService(SysPermissionService sysPermissionService) {
        HerodotusLocalPermissionDetailsService herodotusLocalPermissionDetailsService = new HerodotusLocalPermissionDetailsService(sysPermissionService);
        log.debug("[Herodotus] |- Strategy [Local Permission Details Service] Auto Configure.");
        return herodotusLocalPermissionDetailsService;
    }

    @Bean
    @ConditionalOnMissingBean
    public StrategyUserDetailsService herodotusLocalUserDetailsService(SysUserService sysUserService, SocialAuthenticationHandler socialAuthenticationHandler) {
        log.debug("[Herodotus] |- Strategy [Local User Details Service] Auto Configure.");
        return new HerodotusLocalUserDetailsService(sysUserService, socialAuthenticationHandler);
    }
}

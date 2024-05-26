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

package cn.herodotus.cloud.rpc.client.uaa.autoconfigure;

import cn.herodotus.cloud.module.social.configuration.SocialModuleConfiguration;
import cn.herodotus.cloud.rpc.client.uaa.autoconfigure.local.LocalStrategyPermissionDetailsService;
import cn.herodotus.cloud.rpc.client.uaa.autoconfigure.local.LocalStrategyUserDetailsService;
import cn.herodotus.stirrup.core.identity.handler.SocialAuthenticationHandler;
import cn.herodotus.stirrup.core.identity.strategy.StrategyPermissionDetailsService;
import cn.herodotus.stirrup.core.identity.strategy.StrategyUserDetailsService;
import cn.herodotus.stirrup.logic.upms.config.LogicUpmsConfiguration;
import cn.herodotus.stirrup.logic.upms.service.security.SysPermissionService;
import cn.herodotus.stirrup.logic.upms.service.security.SysUserService;
import cn.herodotus.stirrup.web.core.annotation.ConditionalOnMonocoqueArchitecture;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * <p>Description: 单体式架构配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/1 21:27
 */
@AutoConfiguration
@ConditionalOnMonocoqueArchitecture
@Import({LogicUpmsConfiguration.class, SocialModuleConfiguration.class})
public class MonocoqueArchitectureAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MonocoqueArchitectureAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Starter [Monocoque Architecture] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public StrategyPermissionDetailsService herodotusLocalPermissionDetailsService(SysPermissionService sysPermissionService) {
        LocalStrategyPermissionDetailsService localStrategyPermissionDetailsService = new LocalStrategyPermissionDetailsService(sysPermissionService);
        log.debug("[Herodotus] |- Strategy [Local Permission Details Service] Auto Configure.");
        return localStrategyPermissionDetailsService;
    }

    @Bean
    @ConditionalOnMissingBean
    public StrategyUserDetailsService herodotusLocalUserDetailsService(SysUserService sysUserService, SocialAuthenticationHandler socialAuthenticationHandler) {
        log.debug("[Herodotus] |- Strategy [Local User Details Service] Auto Configure.");
        return new LocalStrategyUserDetailsService(sysUserService, socialAuthenticationHandler);
    }
}

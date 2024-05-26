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
import cn.herodotus.cloud.rpc.client.uaa.autoconfigure.feign.FeignStrategyPermissionDetailsService;
import cn.herodotus.cloud.rpc.client.uaa.autoconfigure.feign.FeignStrategyUserDetailsService;
import cn.herodotus.cloud.rpc.client.uaa.autoconfigure.feign.api.RemoteAuthorityDetailsService;
import cn.herodotus.cloud.rpc.client.uaa.autoconfigure.feign.api.RemoteSocialDetailsService;
import cn.herodotus.cloud.rpc.client.uaa.autoconfigure.feign.api.RemoteUserDetailsService;
import cn.herodotus.cloud.rpc.client.uaa.autoconfigure.grpc.GrpcStrategyPermissionService;
import cn.herodotus.cloud.rpc.client.uaa.autoconfigure.grpc.GrpcStrategyUserService;
import cn.herodotus.cloud.rpc.client.uaa.autoconfigure.local.LocalStrategyPermissionDetailsService;
import cn.herodotus.cloud.rpc.client.uaa.autoconfigure.local.LocalStrategyUserDetailsService;
import cn.herodotus.stirrup.core.identity.handler.SocialAuthenticationHandler;
import cn.herodotus.stirrup.core.identity.strategy.StrategyPermissionDetailsService;
import cn.herodotus.stirrup.core.identity.strategy.StrategyUserDetailsService;
import cn.herodotus.stirrup.logic.upms.config.LogicUpmsConfiguration;
import cn.herodotus.stirrup.logic.upms.service.security.SysPermissionService;
import cn.herodotus.stirrup.logic.upms.service.security.SysUserService;
import cn.herodotus.stirrup.web.core.annotation.ConditionalOnDistributedArchitecture;
import cn.herodotus.stirrup.web.core.annotation.ConditionalOnLocalDataAccess;
import cn.herodotus.stirrup.web.core.annotation.ConditionalOnRemoteDataAccess;
import jakarta.annotation.PostConstruct;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <p>Description: 分布式架构配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/1 21:26
 */
@AutoConfiguration
@ConditionalOnDistributedArchitecture
public class DistributedArchitectureAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(DistributedArchitectureAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Starter [Distributed Architecture] Auto Configure.");
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnLocalDataAccess
    @Import({LogicUpmsConfiguration.class, SocialModuleConfiguration.class})
    static class DataAccessStrategyLocalConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public StrategyUserDetailsService herodotusLocalUserDetailsService(SysUserService sysUserService, SocialAuthenticationHandler socialAuthenticationHandler) {
            log.debug("[Herodotus] |- Strategy [Local User Details Service] Auto Configure.");
            return new LocalStrategyUserDetailsService(sysUserService, socialAuthenticationHandler);
        }

        @Bean
        @ConditionalOnMissingBean
        public StrategyPermissionDetailsService herodotusLocalPermissionDetailsService(SysPermissionService sysPermissionService) {
            LocalStrategyPermissionDetailsService localStrategyPermissionDetailsService = new LocalStrategyPermissionDetailsService(sysPermissionService);
            log.debug("[Herodotus] |- Strategy [Local Permission Details Service] Auto Configure.");
            return localStrategyPermissionDetailsService;
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnRemoteDataAccess
    @ConditionalOnClass(FeignClient.class)
    @EnableFeignClients(basePackages = {"cn.herodotus.cloud.rpc.client.uaa.autoconfigure.feign.api"})
    static class DataAccessStrategyFeignConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public StrategyUserDetailsService feignStrategyUserDetailsService(RemoteUserDetailsService remoteUserDetailsService, RemoteSocialDetailsService remoteSocialDetailsService) {
            FeignStrategyUserDetailsService service = new FeignStrategyUserDetailsService(remoteUserDetailsService, remoteSocialDetailsService);
            log.debug("[Herodotus] |- Strategy [Feign User Details Service] Auto Configure.");
            return service;
        }

        @Bean
        @ConditionalOnMissingBean
        public StrategyPermissionDetailsService feignStrategyPermissionDetailsService(RemoteAuthorityDetailsService remoteAuthorityDetailsService) {
            FeignStrategyPermissionDetailsService service = new FeignStrategyPermissionDetailsService(remoteAuthorityDetailsService);
            log.debug("[Herodotus] |- Strategy [Feign Permission Details Service] Auto Configure.");
            return service;
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnRemoteDataAccess
    @ConditionalOnClass(GrpcClient.class)
    static class DataAccessStrategyGrpcConfiguration {
        @Bean
        @ConditionalOnMissingBean
        public StrategyUserDetailsService grpcStrategyUserDetailsService() {
            GrpcStrategyUserService service = new GrpcStrategyUserService();
            log.debug("[Herodotus] |- Strategy [GRPC User Details Service] Auto Configure.");
            return service;
        }

        @Bean
        @ConditionalOnMissingBean
        public StrategyPermissionDetailsService grpcStrategyPermissionDetailsService() {
            GrpcStrategyPermissionService service = new GrpcStrategyPermissionService();
            log.debug("[Herodotus] |- Strategy [GRPC Permission Details Service] Auto Configure.");
            return service;
        }
    }
}

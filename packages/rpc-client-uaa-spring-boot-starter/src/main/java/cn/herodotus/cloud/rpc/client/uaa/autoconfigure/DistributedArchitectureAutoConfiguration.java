/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.apache.org/licenses/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6.若您的项目无法满足以上几点，可申请商业授权
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

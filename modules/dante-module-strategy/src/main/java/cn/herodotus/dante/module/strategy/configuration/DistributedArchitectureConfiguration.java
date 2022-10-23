/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
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
 * Dante Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dante.module.strategy.configuration;

import cn.herodotus.dante.module.strategy.annotation.ConditionalOnDistributedArchitecture;
import cn.herodotus.dante.module.strategy.annotation.ConditionalOnLocalDataAccess;
import cn.herodotus.dante.module.strategy.annotation.ConditionalOnRemoteDataAccess;
import cn.herodotus.dante.module.strategy.feign.RemoteAuthorityDetailsService;
import cn.herodotus.dante.module.strategy.feign.RemoteSocialDetailsService;
import cn.herodotus.dante.module.strategy.feign.RemoteUserDetailsService;
import cn.herodotus.dante.module.strategy.service.HerodotusLocalAuthorityDetailsService;
import cn.herodotus.dante.module.strategy.service.HerodotusLocalUserDetailsService;
import cn.herodotus.dante.module.strategy.service.HerodotusRemoteAuthorityDetailsService;
import cn.herodotus.dante.module.strategy.service.HerodotusRemoteUserDetailsService;
import cn.herodotus.dante.module.upms.logic.configuration.UpmsLogicModuleConfiguration;
import cn.herodotus.dante.module.upms.logic.configuration.UpmsSocialConfiguration;
import cn.herodotus.dante.module.upms.logic.service.system.SysAuthorityService;
import cn.herodotus.dante.module.upms.logic.service.system.SysUserService;
import cn.herodotus.engine.oauth2.core.definition.handler.SocialAuthenticationHandler;
import cn.herodotus.engine.oauth2.core.definition.strategy.StrategyAuthorityDetailsService;
import cn.herodotus.engine.oauth2.core.definition.strategy.StrategyUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

/**
 * <p>Description: 分布式架构配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/1 21:26
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDistributedArchitecture
public class DistributedArchitectureConfiguration {

    private static final Logger log = LoggerFactory.getLogger(DistributedArchitectureConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Distributed Architecture] Auto Configure.");
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnLocalDataAccess
    @Import({UpmsLogicModuleConfiguration.class, UpmsSocialConfiguration.class})
    static class DataAccessStrategyLocalConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public StrategyUserDetailsService herodotusLocalUserDetailsService(SysUserService sysUserService, SocialAuthenticationHandler socialAuthenticationHandler) {
            log.debug("[Herodotus] |- Strategy [Local User Details Service] Auto Configure.");
            return new HerodotusLocalUserDetailsService(sysUserService, socialAuthenticationHandler);
        }

        @Bean
        @ConditionalOnMissingBean
        public StrategyAuthorityDetailsService HerodotusLocalAuthorityDetailsService(SysAuthorityService sysAuthorityService) {
            HerodotusLocalAuthorityDetailsService herodotusLocalAuthorityDetailsService = new HerodotusLocalAuthorityDetailsService(sysAuthorityService);
            log.debug("[Herodotus] |- Strategy [Local Authority Details Service] Auto Configure.");
            return herodotusLocalAuthorityDetailsService;
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnRemoteDataAccess
    @EnableFeignClients(basePackages = {"cn.herodotus.dante.module.strategy.feign"})
    static class DataAccessStrategyRemoteConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public StrategyUserDetailsService herodotusRemoteUserDetailsService(RemoteUserDetailsService remoteUserDetailsService, RemoteSocialDetailsService remoteSocialDetailsService) {
            log.debug("[Herodotus] |- Strategy [Remote User Details Service] Auto Configure.");
            return new HerodotusRemoteUserDetailsService(remoteUserDetailsService, remoteSocialDetailsService);
        }

        @Bean
        @ConditionalOnMissingBean
        public StrategyAuthorityDetailsService HerodotusRemoteAuthorityDetailsService(RemoteAuthorityDetailsService remoteAuthorityDetailsService) {
            HerodotusRemoteAuthorityDetailsService herodotusRemoteAuthorityDetailsService = new HerodotusRemoteAuthorityDetailsService(remoteAuthorityDetailsService);
            log.debug("[Herodotus] |- Strategy [Remote Authority Details Service] Auto Configure.");
            return herodotusRemoteAuthorityDetailsService;
        }
    }
}

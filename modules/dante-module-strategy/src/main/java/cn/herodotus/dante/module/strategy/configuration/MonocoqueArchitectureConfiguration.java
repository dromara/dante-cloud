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

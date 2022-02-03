/*
 * Copyright (c) 2019-2021 Gengwei Zheng (herodotus@aliyun.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-oauth
 * File Name: OauthAutoConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/09/01 12:31:01
 */

package cn.herodotus.eurynome.module.oauth.autoconfigure;

import cn.herodotus.engine.security.authorize.configuration.SecurityAuthorizeConfiguration;
import cn.herodotus.engine.security.core.definition.service.HerodotusClientDetailsService;
import cn.herodotus.engine.security.core.definition.service.HerodotusUserDetailsService;
import cn.herodotus.engine.security.log.configuration.SecurityLogConfiguration;
import cn.herodotus.eurynome.module.oauth.configuration.AuthorizationServerConfiguration;
import cn.herodotus.eurynome.module.oauth.service.HerodotusOauthClientDetailsService;
import cn.herodotus.eurynome.module.oauth.service.HerodotusOauthUserDetailsService;
import cn.herodotus.eurynome.upms.logic.configuration.UpmsLogicConfiguration;
import cn.herodotus.eurynome.upms.logic.service.oauth.OauthClientDetailsService;
import cn.herodotus.eurynome.upms.logic.service.system.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: OauthConfiguration </p>
 *
 * <p>Description: Oauth 包的启动配置 </p>
 * <p>
 * 注意：这里没有使用@Enable的形式，主要是考虑到启动顺序问题。OauthAutoConfiguration应该在SecurityAutoConfiguration之后配置
 *
 * @author : gengwei.zheng
 * @date : 2021/1/17 11:11
 */
@Configuration(proxyBeanMethods = false)
@Import({
        SecurityAuthorizeConfiguration.class,
        AuthorizationServerConfiguration.class,
        SecurityLogConfiguration.class,
        UpmsLogicConfiguration.class
})
public class OauthAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OauthAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Module [Herodotus OAuth] Auto Configure.");
    }

    @Bean
    @ConditionalOnBean(SysUserService.class)
    @ConditionalOnMissingBean
    public HerodotusUserDetailsService herodotusUserDetailsService(SysUserService sysUserService) {
        HerodotusOauthUserDetailsService herodotusOauthUserDetailsService = new HerodotusOauthUserDetailsService(sysUserService);
        log.trace("[Herodotus] |- Bean [Herodotus User Details Service] Auto Configure.");
        return herodotusOauthUserDetailsService;
    }

    @Bean
    @ConditionalOnBean(OauthClientDetailsService.class)
    @ConditionalOnMissingBean
    public HerodotusClientDetailsService herodotusClientDetailsService(OauthClientDetailsService oauthClientDetailsService) {
        HerodotusOauthClientDetailsService herodotusOauthClientDetailsService = new HerodotusOauthClientDetailsService(oauthClientDetailsService);
        log.trace("[Herodotus] |- Bean [Herodotus Client Details Service] Auto Configure.");
        return herodotusOauthClientDetailsService;
    }
}

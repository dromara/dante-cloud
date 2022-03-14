/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.eurynome.module.oauth2.autoconfigure;

import cn.herodotus.engine.oauth2.server.authorization.configuration.OAuth2AuthorizationServerConfiguration;
import cn.herodotus.eurynome.module.oauth2.configuration.AuthorizationServerConfiguration;
import cn.herodotus.eurynome.module.oauth2.configuration.DefaultSecurityConfiguration;
import cn.herodotus.eurynome.module.upms.logic.configuration.UpmsLogicModuleConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

/**
 * <p>Description: Oauth 包的启动配置 </p>
 * <p>
 * 注意：这里没有使用@Enable的形式，主要是考虑到启动顺序问题。OauthAutoConfiguration应该在SecurityAutoConfiguration之后配置
 *
 * @author : gengwei.zheng
 * @date : 2022/2/17 13:52
 */
@Configuration(proxyBeanMethods = false)
@Import({
        UpmsLogicModuleConfiguration.class,
        OAuth2AuthorizationServerConfiguration.class,
        DefaultSecurityConfiguration.class,
        AuthorizationServerConfiguration.class,
})
public class OAuth2ModuleConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OAuth2ModuleConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Module OAuth] Auto Configure.");
    }

}

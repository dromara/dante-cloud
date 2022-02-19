/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.eurynome.module.oauth.autoconfigure;

import cn.herodotus.eurynome.module.oauth.configuration.AuthorizationServerConfiguration;
import cn.herodotus.eurynome.module.oauth.configuration.DefaultSecurityConfiguration;
import cn.herodotus.eurynome.module.upms.configuration.UpmsModuleConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
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
@EntityScan(basePackages = {
        "cn.herodotus.eurynome.module.oauth.entity"
})
@Import({
        UpmsModuleConfiguration.class,
        DefaultSecurityConfiguration.class,
        AuthorizationServerConfiguration.class,
})
public class OauthModuleConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OauthModuleConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Module OAuth] Auto Configure.");
    }

}

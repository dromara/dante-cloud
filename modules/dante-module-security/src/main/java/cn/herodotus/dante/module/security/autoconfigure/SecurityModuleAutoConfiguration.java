/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.dante.module.security.autoconfigure;

import cn.herodotus.dante.module.security.configuration.MethodSecurityMetadataConfiguration;
import cn.herodotus.engine.oauth2.core.exception.SecurityGlobalExceptionHandler;
import cn.herodotus.engine.oauth2.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;

/**
 * <p>Description: 安全模块自动配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/21 18:02
 */
@Configuration(proxyBeanMethods = false)
@EnableAsync
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@EnableConfigurationProperties({
        SecurityProperties.class,
})
@ComponentScan(basePackageClasses = SecurityGlobalExceptionHandler.class)
@Import({
        MethodSecurityMetadataConfiguration.class
})
public class SecurityModuleAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SecurityModuleAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- SDK [Module Security] Auto Configure.");
    }


}

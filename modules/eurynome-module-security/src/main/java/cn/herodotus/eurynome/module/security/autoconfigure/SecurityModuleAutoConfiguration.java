/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.eurynome.module.security.autoconfigure;

import cn.herodotus.engine.security.core.exception.SecurityGlobalExceptionHandler;
import cn.herodotus.engine.security.core.properties.SecurityProperties;
import cn.herodotus.engine.web.core.definition.RequestMappingScanManager;
import cn.herodotus.eurynome.module.security.configuration.MethodSecurityMetadataConfiguration;
import cn.herodotus.eurynome.module.security.processor.HerodotusRequestMappingScanManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;

/**
 * <p>Description: 安全模块自动配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/21 18:02
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({
        SecurityProperties.class,
})
@ComponentScan(basePackageClasses = SecurityGlobalExceptionHandler.class)
@Import({
        MethodSecurityMetadataConfiguration.class
})
@EnableAsync
@EnableAspectJAutoProxy(exposeProxy = true)
public class SecurityModuleAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SecurityModuleAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- SDK [Module Security] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestMappingScanManager requestMappingScanManager() {
        HerodotusRequestMappingScanManager herodotusRequestMappingScanManager = new HerodotusRequestMappingScanManager();
        log.debug("[Herodotus] |- Bean [Request Mapping Scan Manager] Auto Configure.");
        return herodotusRequestMappingScanManager;
    }
}

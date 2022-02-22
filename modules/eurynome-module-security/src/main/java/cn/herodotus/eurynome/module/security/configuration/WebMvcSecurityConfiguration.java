/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.eurynome.module.security.configuration;

import cn.herodotus.engine.security.core.properties.SecurityProperties;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

import java.util.List;

/**
 * <p>Description: Web MVC 安全配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/21 17:52
 */
@Configuration(proxyBeanMethods = false)
public class WebMvcSecurityConfiguration implements WebSecurityCustomizer {

    private final SecurityProperties securityProperties;

    public WebMvcSecurityConfiguration(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public void customize(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers(getIgnoredStaticResources());
    }

    private String[] getIgnoredStaticResources() {
        List<String> defaultIgnored = Lists.newArrayList("/error/**",
                "/favicon.ico",
                "/static/**");

        List<String> customIgnored = securityProperties.getInterceptor().getStaticResource();

        if (CollectionUtils.isNotEmpty(customIgnored)) {
            defaultIgnored.addAll(customIgnored);
        }

        String[] result = new String[defaultIgnored.size()];
        return defaultIgnored.toArray(result);
    }

}

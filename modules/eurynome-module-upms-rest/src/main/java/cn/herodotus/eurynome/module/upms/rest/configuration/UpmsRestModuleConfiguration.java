/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.eurynome.module.upms.rest.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * <p>Description: UpmsRest配置类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/1/5 11:58
 */
@Configuration
@ComponentScan(basePackages = {
        "cn.herodotus.eurynome.module.upms.rest.controller.hr",
        "cn.herodotus.eurynome.module.upms.rest.controller.system",
        "cn.herodotus.eurynome.module.upms.rest.controller.assistant",
})
public class UpmsRestModuleConfiguration {

    private static final Logger log = LoggerFactory.getLogger(UpmsRestModuleConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- SDK [Module Upms Rest] Auto Configure.");
    }


}

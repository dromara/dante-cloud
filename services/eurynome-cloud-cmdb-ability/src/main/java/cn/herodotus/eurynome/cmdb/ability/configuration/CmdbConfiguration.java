/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.eurynome.cmdb.ability.configuration;

import cn.herodotus.eurynome.cmdb.logic.configuration.CmdbLogicConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

/**
 * <p>Description: Cmdb 服务默认配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/21 22:49
 */
@Configuration(proxyBeanMethods = false)
@Import({
        CmdbLogicConfiguration.class
})
public class CmdbConfiguration {

    private static final Logger log = LoggerFactory.getLogger(CmdbConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Service [Cmdb Ability] Auto Configure.");
    }
}

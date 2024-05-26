/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Herodotus Cloud.
 *
 * Herodotus Cloud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Herodotus Cloud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.vip>.
 */

package cn.herodotus.cloud.rpc.server.upms.autoconfigure;

import cn.herodotus.cloud.module.metadata.configuration.MetadataModuleConfiguration;
import cn.herodotus.cloud.module.social.configuration.SocialModuleConfiguration;
import cn.herodotus.stirrup.logic.upms.config.LogicUpmsConfiguration;
import jakarta.annotation.PostConstruct;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <p>Description: Rpc Server Upms 自动配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/4/24 23:12
 */
@AutoConfiguration
@Import({
        LogicUpmsConfiguration.class, SocialModuleConfiguration.class, MetadataModuleConfiguration.class
})
public class RpcServerUpmsAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RpcServerUpmsAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Starter [Rpc Server Upms] Auto Configure.");
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnMissingClass({
            "net.devh.boot.grpc.server.service.GrpcService"
    })
    @ComponentScan(basePackages = {
            "cn.herodotus.cloud.rpc.server.upms.autoconfigure.feign"
    })
    static class FeignConfiguration {

    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(GrpcService.class)
    @ComponentScan(basePackages = {
            "cn.herodotus.cloud.rpc.server.upms.autoconfigure.grpc.service"
    })
    static class GrpcConfiguration {

    }
}

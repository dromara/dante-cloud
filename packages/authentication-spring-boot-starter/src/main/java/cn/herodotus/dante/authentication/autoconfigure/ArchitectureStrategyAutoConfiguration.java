/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Dante Cloud.
 *
 * Dante Cloud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dante Cloud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.cn>.
 */

package cn.herodotus.dante.authentication.autoconfigure;

import cn.herodotus.dante.module.strategy.configuration.DistributedArchitectureConfiguration;
import cn.herodotus.dante.module.strategy.configuration.MonocoqueArchitectureConfiguration;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * <p>Description: 策略模块配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/1 17:58
 */
@AutoConfiguration
@Import({
        DistributedArchitectureConfiguration.class,
        MonocoqueArchitectureConfiguration.class
})
public class ArchitectureStrategyAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ArchitectureStrategyAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Strategy Module] Auto Configure.");
    }
}

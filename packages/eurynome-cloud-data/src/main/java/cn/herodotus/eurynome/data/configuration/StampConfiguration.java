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
 * Module Name: eurynome-cloud-data
 * File Name: StampConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/08/26 18:56:26
 */

package cn.herodotus.eurynome.data.configuration;

import cn.herodotus.eurynome.data.properties.StampProperties;
import cn.herodotus.eurynome.data.stamp.AccessLimitedStampManager;
import cn.herodotus.eurynome.data.stamp.IdempotentStampManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * <p>Description: 跟踪标记配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/26 18:56
 */
@Configuration
@EnableConfigurationProperties({StampProperties.class})
public class StampConfiguration {

    private final Logger log = LoggerFactory.getLogger(StampConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Eurynome] |- Plugin [Herodotus Stamp] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public IdempotentStampManager idempotentStampManager(StampProperties stampProperties) {
        IdempotentStampManager idempotentStampManager = new IdempotentStampManager();
        idempotentStampManager.setStampProperties(stampProperties);
        log.trace("[Eurynome] |- Bean [Idempotent Stamp Manager] Auto Configure.");
        return idempotentStampManager;
    }

    @Bean
    @ConditionalOnMissingBean
    public AccessLimitedStampManager accessLimitedStampManager(StampProperties stampProperties) {
        AccessLimitedStampManager accessLimitedStampManager = new AccessLimitedStampManager();
        accessLimitedStampManager.setStampProperties(stampProperties);
        log.trace("[Eurynome] |- Bean [Access Limited Stamp Manager] Auto Configure.");
        return accessLimitedStampManager;
    }
}

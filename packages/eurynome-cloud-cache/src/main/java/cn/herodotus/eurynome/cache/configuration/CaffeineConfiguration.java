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
 * Module Name: eurynome-cloud-cache
 * File Name: CaffeineConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/10/28 10:12:28
 */

package cn.herodotus.eurynome.cache.configuration;

import cn.herodotus.eurynome.cache.enhance.layer.HerodotusCaffeineCacheManager;
import cn.herodotus.eurynome.cache.properties.CacheProperties;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * <p>Description: Caffeine配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/14 11:10
 */
@Configuration
public class CaffeineConfiguration {

    private static final Logger log = LoggerFactory.getLogger(CaffeineConfiguration.class);

    @Autowired
    private CacheProperties cacheProperties;

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Plugin [Herodotus Caffeine] Auto Configure.");
    }

    @Bean
    public Caffeine<Object, Object> caffeine() {
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                .expireAfterWrite(cacheProperties.getDuration(), cacheProperties.getUnit());

        log.trace("[Herodotus] |- Bean [Caffeine] Auto Configure.");

        return caffeine;
    }

    @Bean
    @ConditionalOnMissingBean(CaffeineCacheManager.class)
    public CaffeineCacheManager caffeineCacheManager() {
        HerodotusCaffeineCacheManager herodotusCaffeineCacheManager = new HerodotusCaffeineCacheManager(cacheProperties);
        herodotusCaffeineCacheManager.setCaffeine(caffeine());
        log.trace("[Herodotus] |- Bean [Caffeine Cache Manager] Auto Configure.");
        return herodotusCaffeineCacheManager;
    }
}

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
 * File Name: CacheConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/10/24 21:04:24
 */

package cn.herodotus.eurynome.data.configuration;

import cn.herodotus.eurynome.data.cache.jetcache.JetCacheBuilder;
import cn.herodotus.eurynome.data.cache.layer.HerodotusCacheManager;
import cn.herodotus.eurynome.data.properties.CacheProperties;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.support.SpringConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;

import javax.annotation.PostConstruct;

/**
 * <p>Description: Cache 相关 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/24 21:04
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CacheProperties.class)
@EnableCreateCacheAnnotation
@Import({CaffeineConfiguration.class, RedisConfiguration.class, RedissonConfiguration.class})
public class CacheConfiguration {

    private static final Logger log = LoggerFactory.getLogger(CacheConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Plugin [Herodotus Cache] Auto Configure.");
    }

    @Bean
    @Primary
    public HerodotusCacheManager herodotusCacheManager(CaffeineCacheManager caffeineCacheManager, RedisCacheManager redisCacheManager, CacheProperties cacheProperties) {
        HerodotusCacheManager herodotusCacheManager = new HerodotusCacheManager();
        herodotusCacheManager.setCaffeineCacheManager(caffeineCacheManager);
        herodotusCacheManager.setRedisCacheManager(redisCacheManager);
        herodotusCacheManager.setDesensitization(cacheProperties.getDesensitization());
        herodotusCacheManager.setClearRemoteOnExit(cacheProperties.getClearRemoteOnExit());
        herodotusCacheManager.setAllowNullValues(cacheProperties.getAllowNullValues());

        log.trace("[Herodotus] |- Bean [Herodotus Cache Manager] Auto Configure.");

        return herodotusCacheManager;
    }

    @Bean
    public JetCacheBuilder jetCacheBuilder(SpringConfigProvider springConfigProvider) {
        JetCacheBuilder jetCacheBuilder = new JetCacheBuilder(springConfigProvider);
        log.trace("[Herodotus] |- Bean [Jet Cache Builder] Auto Configure.");
        return jetCacheBuilder;
    }
}
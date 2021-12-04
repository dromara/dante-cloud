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
 * File Name: JetCacheConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/12/04 13:38:04
 */

package cn.herodotus.eurynome.cache.configuration;

import cn.herodotus.eurynome.cache.enhance.jetcache.JetCacheBuilder;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.support.ConfigProvider;
import com.alicp.jetcache.anno.support.GlobalCacheConfig;
import com.alicp.jetcache.anno.support.SpringConfigProvider;
import com.alicp.jetcache.autoconfigure.AutoConfigureBeans;
import com.alicp.jetcache.autoconfigure.JetCacheProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * <p>Description: JetCacheConfiguration </p>
 *
 * 新增JetCache配置，解决JetCache依赖循环问题
 *
 * @author : gengwei.zheng
 * @date : 2021/12/4 13:38
 */
@Configuration
@EnableConfigurationProperties(JetCacheProperties.class)
@EnableCreateCacheAnnotation
public class JetCacheConfiguration {

    private static final Logger log = LoggerFactory.getLogger(JetCacheConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Plugin [Herodotus JetCache] Auto Configure.");
    }

    @Bean
    public AutoConfigureBeans autoConfigureBeans() {
        AutoConfigureBeans autoConfigureBeans = new AutoConfigureBeans();
        log.trace("[Herodotus] |- Bean [Auto Configure Beans] Auto Configure.");
        return autoConfigureBeans;
    }

    @Bean
    public GlobalCacheConfig globalCacheConfig(AutoConfigureBeans autoConfigureBeans, JetCacheProperties jetCacheProperties) {
        GlobalCacheConfig globalCacheConfig = new GlobalCacheConfig();
        globalCacheConfig.setHiddenPackages(jetCacheProperties.getHiddenPackages());
        globalCacheConfig.setStatIntervalMinutes(jetCacheProperties.getStatIntervalMinutes());
        globalCacheConfig.setAreaInCacheName(jetCacheProperties.isAreaInCacheName());
        globalCacheConfig.setPenetrationProtect(jetCacheProperties.isPenetrationProtect());
        globalCacheConfig.setEnableMethodCache(jetCacheProperties.isEnableMethodCache());
        globalCacheConfig.setLocalCacheBuilders(autoConfigureBeans.getLocalCacheBuilders());
        globalCacheConfig.setRemoteCacheBuilders(autoConfigureBeans.getRemoteCacheBuilders());
        log.trace("[Herodotus] |- Bean [Global Cache Config] Auto Configure.");
        return globalCacheConfig;
    }

    @Bean
    @ConditionalOnBean(GlobalCacheConfig.class)
    public ConfigProvider configProvider() {
        SpringConfigProvider springConfigProvider = new SpringConfigProvider();
        log.trace("[Herodotus] |- Bean [Spring Config Provider] Auto Configure.");
        return springConfigProvider;
    }

    @Bean
    @ConditionalOnBean(SpringConfigProvider.class)
    public JetCacheBuilder jetCacheBuilder(SpringConfigProvider springConfigProvider) {
        JetCacheBuilder jetCacheBuilder = new JetCacheBuilder(springConfigProvider);
        log.trace("[Herodotus] |- Bean [Jet Cache Builder] Auto Configure.");
        return jetCacheBuilder;
    }
}
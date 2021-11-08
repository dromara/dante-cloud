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
 * Module Name: eurynome-integration-oss
 * File Name: MinioConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/11/08 21:00:08
 */

package cn.herodotus.eurynome.integration.oss.configuration;

import cn.herodotus.eurynome.assistant.annotation.conditional.ConditionalOnMinioEnabled;
import cn.herodotus.eurynome.integration.oss.core.MinioClientPool;
import cn.herodotus.eurynome.integration.oss.core.MinioManager;
import cn.herodotus.eurynome.integration.oss.core.MinioTemplate;
import cn.herodotus.eurynome.integration.oss.properties.MinioProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * <p>Description: Minio配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/11/8 11:30
 */
@Configuration
@ConditionalOnMinioEnabled
@EnableConfigurationProperties({MinioProperties.class})
public class MinioConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MinioConfiguration.class);

    @PostConstruct
    public void init() {
        log.info("[Herodotus] |- Plugin [Herodotus Minio] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public MinioClientPool minioClientPool(MinioProperties minioProperties) {
        MinioClientPool minioClientPool = new MinioClientPool(minioProperties);
        log.trace("[Herodotus] |- Bean [Minio Client Pool] Auto Configure.");
        return minioClientPool;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(MinioClientPool.class)
    public MinioTemplate minioTemplate(MinioClientPool minioClientPool) {
        MinioTemplate minioTemplate = new MinioTemplate(minioClientPool);
        log.trace("[Herodotus] |- Bean [Minio Template] Auto Configure.");
        return minioTemplate;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(MinioTemplate.class)
    public MinioManager minioManager(MinioTemplate minioTemplate, MinioProperties minioProperties) {
        MinioManager minioManager = new MinioManager(minioTemplate, minioProperties);
        log.trace("[Herodotus] |- Bean [Minio Manager] Auto Configure.");
        return minioManager;
    }
}

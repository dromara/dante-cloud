/*
 * Copyright (c) 2019-2021 Gengwei Zheng(herodotus@aliyun.com)
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
 * Module Name: eurynome-cloud-starter
 * File Name: AutoConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/05/13 11:04:13
 */

package cn.herodotus.eurynome.autoconfigure;

import cn.herodotus.eurynome.autoconfigure.logic.LocalCacheSecurityMetadata;
import cn.herodotus.eurynome.autoconfigure.logic.SecurityMetadataProducer;
import cn.herodotus.eurynome.crud.annotation.EnableHerodotusCrud;
import cn.herodotus.eurynome.kernel.annotation.EnableHerodotusKernel;
import cn.herodotus.eurynome.message.queue.KafkaProducer;
import cn.herodotus.eurynome.security.definition.service.SecurityMetadataStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.PostConstruct;

/**
 * @author gengwei.zheng
 */
@Slf4j
@Configuration
@EnableHerodotusCrud
@EnableHerodotusKernel
public class AutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.info("[Eurynome] |- Starter [Herodotus Starter] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean(KafkaProducer.class)
    public KafkaProducer kafkaProducer(KafkaTemplate kafkaTemplate) {
        KafkaProducer kafkaProducer = new KafkaProducer(kafkaTemplate);
        log.trace("[Eurynome] |- Bean [Kafka Producer] Auto Configure.");
        return kafkaProducer;
    }

    /**
     * 服务自身权限验证所需的Security Metadata存储配置
     *
     * 服务权限验证逻辑：
     * 1、配置服务本地Security Metadata存储
     */
    @Bean
    @ConditionalOnMissingBean(SecurityMetadataStorage.class)
    public SecurityMetadataStorage securityMetadataStorage() {
        LocalCacheSecurityMetadata localCacheSecurityMetadata = new LocalCacheSecurityMetadata();
        log.trace("[Eurynome] |- Bean [Local Cache Security Metadata] Auto Configure.");
        return localCacheSecurityMetadata;
    }

    /**
     * 权限信息发送器
     *
     * 服务权限验证逻辑：
     * 3、将服务本地存储的Security Metadata，发送到统一认证中心。
     * 4、通过客户端，在统一认证中心配置用户权限
     */
    @Bean
    @ConditionalOnMissingBean(SecurityMetadataProducer.class)
    @DependsOn(value = "requestMappingScanner")
    public SecurityMetadataProducer securityMetadataProducer(KafkaProducer kafkaProducer, SecurityMetadataStorage securityMetadataStorage) {
        SecurityMetadataProducer securityMetadataProducer = new SecurityMetadataProducer();
        securityMetadataProducer.setKafkaProducer(kafkaProducer);
        securityMetadataProducer.setSecurityMetadataStorage(securityMetadataStorage);
        log.trace("[Eurynome] |- Bean [Security Metadata Producer] Auto Configure.");
        return securityMetadataProducer;
    }
}

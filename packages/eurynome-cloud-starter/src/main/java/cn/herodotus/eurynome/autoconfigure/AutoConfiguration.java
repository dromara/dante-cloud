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

import cn.herodotus.eurynome.crud.annotation.EnableHerodotusCrud;
import cn.herodotus.eurynome.kernel.annotation.EnableHerodotusKernel;
import cn.herodotus.eurynome.message.queue.KafkaProducer;
import cn.herodotus.eurynome.security.definition.service.StrategyRequestMappingGatherService;
import cn.herodotus.eurynome.security.service.RemoteRequestMappingGatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    @Bean
    @ConditionalOnBean(KafkaProducer.class)
    public StrategyRequestMappingGatherService remoteSecurityMetadataStorageService(KafkaProducer kafkaProducer) {
        log.trace("[Eurynome] |- Bean [Remote Security Metadata Storage Service] Auto Configure.");
        return new RemoteRequestMappingGatherService(kafkaProducer);
    }
}

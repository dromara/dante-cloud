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
 * File Name: KafkaConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/08/08 10:41:08
 */

package cn.herodotus.eurynome.data.configuration;

import cn.herodotus.eurynome.assistant.annotation.conditional.ConditionalOnKafkaEnabled;
import cn.herodotus.eurynome.data.properties.QueueProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

import javax.annotation.PostConstruct;

/**
 * <p>Description: kafka配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/8 10:41
 */
@Configuration
@ConditionalOnKafkaEnabled
public class KafkaConfiguration {

    private static final Logger log = LoggerFactory.getLogger(KafkaConfiguration.class);

    @Autowired
    private QueueProperties queueProperties;
    @Autowired
    private ConsumerFactory<String, String> consumerFactory;

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Plugin [Herodotus Kafka] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean(ConcurrentKafkaListenerContainerFactory.class)
    public ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory);
        concurrentKafkaListenerContainerFactory.setAutoStartup(queueProperties.getKafka().getEnabled());
        log.trace("[Herodotus] |- Bean [Concurrent Kafka Listener ContainerFactory] Auto Configure.");
        return concurrentKafkaListenerContainerFactory;
    }
}

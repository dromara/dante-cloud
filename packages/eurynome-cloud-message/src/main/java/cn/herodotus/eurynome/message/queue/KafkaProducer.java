/*
 * Copyright (c) 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-message
 * File Name: KafkaProducer.java
 * Author: gengwei.zheng
 * Date: 2020/5/28 下午4:16
 * LastModified: 2020/5/23 上午9:32
 */

package cn.herodotus.eurynome.message.queue;

import cn.herodotus.eurynome.common.definition.MessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * <p>Description: 传统Kafka发送Message </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/21 12:01
 */
@Slf4j
public class KafkaProducer implements MessageProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String data) {
        log.debug("[Eurynome] |- Kafka Send Message Start!");

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, data);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("[Eurynome] |- Kafka Send Message Error! => ex = {}, topic = {}, data = {}", ex, topic, data);
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.debug("[Eurynome] |- Kafka Send Message Success! => topic = {}, data = {}", topic, data);
            }
        });

        log.debug("[Eurynome] |- Kafka Send Message Finished!");
    }
}

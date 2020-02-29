/*
 * Copyright 2019-2019 the original author or authors.
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
 * Project Name: luban-cloud
 * Module Name: luban-cloud-component-sdk
 * File Name: KafkaResourceStore.java
 * Author: gengwei.zheng
 * Date: 2019/11/21 上午11:53
 * LastModified: 2019/11/21 上午11:53
 */

package cn.herodotus.eurynome.component.sdk.annotation;

import cn.herodotus.eurynome.component.common.domain.RequestMappingResource;
import cn.herodotus.eurynome.component.common.definition.RequestMappingStore;
import cn.herodotus.eurynome.component.sdk.configuration.kafka.KafkaProducer;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/21 11:53
 */
@Slf4j
public class KafkaRequestMappingStore implements RequestMappingStore {

    private KafkaProducer kafkaProducer;

    public KafkaRequestMappingStore(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public boolean storeRequestMappings(List<RequestMappingResource> requestMappingResources) {

        String result = JSON.toJSONString(requestMappingResources);

        log.debug("[Luban] |- Platform Prepared the Resources [{}]", result);

        kafkaProducer.sendMessage("AUTHORITY_AUTO_SCAN", result);

        return true;
    }
}

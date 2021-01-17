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
 * Module Name: eurynome-cloud-starter
 * File Name: SecurityMetadataProducer.java
 * Author: gengwei.zheng
 * Date: 2020/6/4 上午6:59
 * LastModified: 2020/6/4 上午6:58
 */

package cn.herodotus.eurynome.autoconfigure.logic;

import cn.herodotus.eurynome.message.queue.KafkaProducer;
import cn.herodotus.eurynome.security.definition.RequestMapping;
import cn.herodotus.eurynome.security.definition.service.SecurityMetadataStorage;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: SecurityMetadataProducer </p>
 *
 * <p>Description: 扫描到的RequestMapping持久化存储 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/6/3 17:04
 */
public class SecurityMetadataProducer implements InitializingBean {

    private SecurityMetadataStorage securityMetadataStorage;

    private KafkaProducer kafkaProducer;

    public void setSecurityMetadataStorage(SecurityMetadataStorage securityMetadataStorage) {
        this.securityMetadataStorage = securityMetadataStorage;
    }

    public void setKafkaProducer(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<RequestMapping> requestMappings = securityMetadataStorage.findAll();
        if (CollectionUtils.isNotEmpty(requestMappings)) {
            String message = JSON.toJSONString(requestMappings);
            kafkaProducer.sendMessage("security.metadata", message);
        }
    }
}

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
 * File Name: SecurityMetadataPersistence.java
 * Author: gengwei.zheng
 * Date: 2020/5/29 下午8:56
 * LastModified: 2020/5/29 下午8:35
 */

package cn.herodotus.eurynome.autoconfigure;

import cn.herodotus.eurynome.localstorage.entity.SecurityMetadata;
import cn.herodotus.eurynome.localstorage.service.SecurityMetadataService;
import cn.herodotus.eurynome.message.stream.service.SecurityMetadataMessageProducer;
import cn.herodotus.eurynome.rest.definition.RequestMappingPersistence;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: SecurityMetadataPersistence </p>
 *
 * <p>Description: SecurityMetadataPersistence </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/29 10:04
 */
@Slf4j
public class SecurityMetadataPersistence implements RequestMappingPersistence<SecurityMetadata> {

    private final SecurityMetadataService securityMetadataService;
    private final SecurityMetadataMessageProducer securityMetadataMessageProducer;

    public SecurityMetadataPersistence(SecurityMetadataService securityMetadataService, SecurityMetadataMessageProducer securityMetadataMessageProducer) {
        this.securityMetadataService = securityMetadataService;
        this.securityMetadataMessageProducer = securityMetadataMessageProducer;
    }

    public SecurityMetadataService getSecurityMetadataService() {
        return securityMetadataService;
    }

    @Override
    public void store(List<SecurityMetadata> collection) {
        if (CollectionUtils.isNotEmpty(collection)) {
            List<SecurityMetadata> metadata = securityMetadataService.saveAll(collection);
            if (CollectionUtils.isNotEmpty(metadata)) {
                log.debug("[Herodotus] |- SecurityMetadata store to local storage SUCCESS!");
                String message = JSON.toJSONString(metadata);
                securityMetadataMessageProducer.sendMessage(message);
            } else {
                log.error("[Herodotus] |- SecurityMetadata store to local storage FAILED!");
            }
        }
    }
}

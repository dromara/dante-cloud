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
 * Module Name: eurynome-cloud-rest
 * File Name: SecurityMetadataPersistence.java
 * Author: gengwei.zheng
 * Date: 2020/5/29 上午10:04
 * LastModified: 2020/5/29 上午10:04
 */

package cn.herodotus.eurynome.rest.metadata;

import cn.herodotus.eurynome.localstorage.entity.SecurityMetadata;
import cn.herodotus.eurynome.localstorage.service.SecurityMetadataService;
import cn.herodotus.eurynome.message.stream.service.SecurityMetadataMessage;
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
public class SecurityMetadataPersistence {

    private final SecurityMetadataService securityMetadataService;
    private final SecurityMetadataMessage securityMetadataMessage;

    public SecurityMetadataPersistence(SecurityMetadataService securityMetadataService, SecurityMetadataMessage securityMetadataMessage) {
        this.securityMetadataService = securityMetadataService;
        this.securityMetadataMessage = securityMetadataMessage;
    }

    public void store(List<SecurityMetadata> securityMetadataCollection) {
        if (CollectionUtils.isNotEmpty(securityMetadataCollection)) {
            List<SecurityMetadata> metadata = securityMetadataService.saveAll(securityMetadataCollection);
            if (CollectionUtils.isNotEmpty(metadata)) {
                log.debug("[Herodotus] |- SecurityMetadata store to local storage SUCCESS!");
                String message = JSON.toJSONString(metadata);
                securityMetadataMessage.sendMessage(message);
            } else {
                log.error("[Herodotus] |- SecurityMetadata store to local storage FAILED!");
            }
        }
    }
}

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
 * File Name: RequestMappingMessageService.java
 * Author: gengwei.zheng
 * Date: 2020/5/28 下午5:00
 * LastModified: 2020/5/28 下午4:18
 */

package cn.herodotus.eurynome.message.stream.service;

import cn.herodotus.eurynome.message.stream.channel.SecurityMetadataProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.messaging.support.MessageBuilder;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: SecurityMetadataMessage </p>
 *
 * <p>Description: SecurityMetadataMessage </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/28 15:50
 */
@Slf4j
public class SecurityMetadataMessage {

    private final SecurityMetadataProcessor securityMetadataProcessor;

    public SecurityMetadataMessage(SecurityMetadataProcessor securityMetadataProcessor) {
        this.securityMetadataProcessor = securityMetadataProcessor;
    }

    public boolean sendMessage(String message) {
        boolean status = false;
        if (StringUtils.isNotEmpty(message)) {
            log.debug("[Herodotus] |- Prepared the Security Metadata [{}]", message);
            status = securityMetadataProcessor.output().send(MessageBuilder.withPayload(message).build());
        }

        if (status) {
            log.debug("[Herodotus] |- Sent [RequestMappingScan] Message SUCCESS!");
        } else {
            log.error("[Herodotus] |- Sent [RequestMappingScan] Message FAILED!");
        }

        return status;
    }
}

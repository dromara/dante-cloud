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
 * Module Name: eurynome-cloud-upms-ability
 * File Name: SecurityMetadataConsumer.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.upms.ability.service;

import cn.herodotus.eurynome.upms.ability.processor.SecurityMetadataAsyncStorage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * <p>Description: 获取其它服务发送过来的RequestMapping信息, 后期如果有消息服务可以考虑移除 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/21 13:58
 */
@Slf4j
@Service
public class SecurityMetadataConsumer {

    @Autowired
    private SecurityMetadataAsyncStorage securityMetadataAsyncStorage;

    @KafkaListener(topics = {"security.metadata"}, groupId = "herodotus.upms")
    public void serviceResourceMessage(String message) {
        if (StringUtils.isNotBlank(message)) {
            securityMetadataAsyncStorage.store(message);
        } else {
            log.error("[Eurynome] |- Message from Security Metadata Producer is null!");
        };
    }
}

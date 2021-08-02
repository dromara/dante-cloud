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
 * Module Name: eurynome-cloud-security
 * File Name: RemoteSecurityMetadataStorageService.java
 * Author: gengwei.zheng
 * Date: 2021/07/28 18:21:28
 */

package cn.herodotus.eurynome.security.service;

import cn.herodotus.eurynome.common.definition.MessageProducer;
import cn.herodotus.eurynome.security.definition.domain.RequestMapping;
import cn.herodotus.eurynome.security.definition.service.StrategyRequestMappingGatherService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * <p>Description: SecurityMetadata远程发送服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/28 18:21
 */
public class RemoteRequestMappingGatherService implements StrategyRequestMappingGatherService {

    private final MessageProducer messageProducer;

    public RemoteRequestMappingGatherService(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @Override
    public void store(List<RequestMapping> requestMappings) {
        if (CollectionUtils.isNotEmpty(requestMappings)) {
            String message = JSON.toJSONString(requestMappings);
            messageProducer.sendMessage("security.metadata", message);
        }
    }
}

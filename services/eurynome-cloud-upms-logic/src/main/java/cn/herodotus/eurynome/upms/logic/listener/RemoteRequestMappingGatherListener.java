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
 * Module Name: eurynome-cloud-upms-logic
 * File Name: RemoteRequestMappingGatherListener.java
 * Author: gengwei.zheng
 * Date: 2021/08/11 20:49:11
 */

package cn.herodotus.eurynome.upms.logic.listener;

import cn.herodotus.eurynome.security.definition.domain.RequestMapping;
import cn.herodotus.eurynome.security.event.remote.RemoteRequestMappingGatherEvent;
import cn.herodotus.eurynome.upms.logic.processor.RequestMappingStoreProcessor;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>Description: SecurityMetadata远程变更事件监听 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/7 20:49
 */
@Component
public class RemoteRequestMappingGatherListener implements ApplicationListener<RemoteRequestMappingGatherEvent> {

    private static final Logger log = LoggerFactory.getLogger(RemoteRequestMappingGatherListener.class);

    private final RequestMappingStoreProcessor requestMappingStoreProcessor;

    @Autowired
    public RemoteRequestMappingGatherListener(RequestMappingStoreProcessor requestMappingStoreProcessor) {
        this.requestMappingStoreProcessor = requestMappingStoreProcessor;
    }

    @Override
    public void onApplicationEvent(RemoteRequestMappingGatherEvent event) {
        log.info("[Herodotus] |- Request Mapping Gather REMOTE Listener, response event!");

        String requestMapping = event.getRequestMapping();
        if (ObjectUtils.isNotEmpty(requestMapping)) {
            List<RequestMapping> requestMappings = JSON.parseArray(requestMapping, RequestMapping.class);
            if (CollectionUtils.isNotEmpty(requestMappings)) {
                requestMappingStoreProcessor.postProcess(requestMappings);
            }
        }
    }
}

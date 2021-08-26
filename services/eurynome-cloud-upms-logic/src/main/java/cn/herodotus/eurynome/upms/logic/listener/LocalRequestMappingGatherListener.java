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
 * File Name: LocalRequestMappingGatherListener.java
 * Author: gengwei.zheng
 * Date: 2021/08/23 18:55:23
 */

package cn.herodotus.eurynome.upms.logic.listener;

import cn.herodotus.eurynome.security.definition.domain.RequestMapping;
import cn.herodotus.eurynome.security.event.LocalRequestMappingGatherEvent;
import cn.herodotus.eurynome.upms.logic.processor.RequestMappingStoreProcessor;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>Description: 本地RequestMapping收集监听 </p>
 * <p>
 * 主要在单体式架构，以及 UUA 服务自身使用
 *
 * @author : gengwei.zheng
 * @date : 2021/8/23 18:55
 */
@Component
public class LocalRequestMappingGatherListener implements ApplicationListener<LocalRequestMappingGatherEvent> {

    private static final Logger log = LoggerFactory.getLogger(LocalRequestMappingGatherListener.class);

    private final RequestMappingStoreProcessor requestMappingStoreProcessor;

    @Autowired
    public LocalRequestMappingGatherListener(RequestMappingStoreProcessor requestMappingStoreProcessor) {
        this.requestMappingStoreProcessor = requestMappingStoreProcessor;
    }

    @Override
    public void onApplicationEvent(LocalRequestMappingGatherEvent event) {

        log.info("[Eurynome] |- Request mapping gather LOCAL listener, response event!");

        List<RequestMapping> requestMappings = event.getRequestMappings();
        if (CollectionUtils.isNotEmpty(requestMappings)) {
            requestMappingStoreProcessor.postProcess(requestMappings);
        }
    }
}

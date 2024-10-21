/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Dante Cloud.
 *
 * Dante Cloud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dante Cloud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.cn>.
 */

package org.dromara.dante.module.metadata.listener;

import org.dromara.dante.module.metadata.processor.RequestMappingStoreProcessor;
import cn.herodotus.engine.assistant.core.json.jackson2.utils.Jackson2Utils;
import cn.herodotus.engine.message.core.logic.domain.RequestMapping;
import cn.herodotus.engine.oauth2.resource.autoconfigure.bus.RemoteRequestMappingGatherEvent;
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
 * @date : 2021/8/5 16:16
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

        log.info("[Herodotus] |- Request mapping gather REMOTE listener, response event!");

        String requestMapping = event.getData();
        log.debug("[Herodotus] |- Fetch data [{}]", requestMapping);
        if (ObjectUtils.isNotEmpty(requestMapping)) {
            List<RequestMapping> requestMappings = Jackson2Utils.toList(requestMapping, RequestMapping.class);
            if (CollectionUtils.isNotEmpty(requestMappings)) {
                requestMappingStoreProcessor.postProcess(requestMappings);
            }
        }
    }
}

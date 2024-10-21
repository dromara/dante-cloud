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

package org.dromara.dante.module.metadata.processor;

import cn.herodotus.engine.message.core.logic.domain.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>Description: RequestMapping存储服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/7 14:15
 */
@Component
public class RequestMappingStoreProcessor {

    private static final Logger log = LoggerFactory.getLogger(RequestMappingStoreProcessor.class);

    private final SecurityMetadataDistributeProcessor securityMetadataDistributeProcessor;

    @Autowired
    public RequestMappingStoreProcessor(SecurityMetadataDistributeProcessor securityMetadataDistributeProcessor) {
        this.securityMetadataDistributeProcessor = securityMetadataDistributeProcessor;
    }

    @Async
    public void postProcess(List<RequestMapping> requestMappings) {
        log.debug("[Herodotus] |- [4] Async store request mapping process BEGIN!");
        securityMetadataDistributeProcessor.postRequestMappings(requestMappings);
    }
}

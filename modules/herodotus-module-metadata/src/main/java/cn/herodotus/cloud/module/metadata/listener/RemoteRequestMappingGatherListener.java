/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Herodotus Cloud.
 *
 * Herodotus Cloud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Herodotus Cloud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.vip>.
 */

package cn.herodotus.cloud.module.metadata.listener;

import cn.herodotus.cloud.module.metadata.processor.AttributeTransmitterDistributeProcessor;
import cn.herodotus.stirrup.core.foundation.json.jackson2.utils.Jackson2Utils;
import cn.herodotus.stirrup.message.ability.domain.RequestMapping;
import cn.herodotus.stirrup.oauth2.authorization.autoconfigure.bus.RemoteRequestMappingGatherEvent;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>Description: SecurityMetadata远程变更事件监听 </p>
 * <p>
 * 所有服务启动完成，扫描 Rest 到所有接口之后，会通过发送 {@link RemoteRequestMappingGatherEvent} 事件，将扫描到的接口数据发送到 UPMS
 * <p>
 * {@link RemoteRequestMappingGatherListener} 监听到 {@link RemoteRequestMappingGatherEvent} 事件后，进行后续的处理。
 *
 * @author : gengwei.zheng
 * @date : 2021/8/5 16:16
 */
@Component
public class RemoteRequestMappingGatherListener implements ApplicationListener<RemoteRequestMappingGatherEvent> {

    private static final Logger log = LoggerFactory.getLogger(RemoteRequestMappingGatherListener.class);

    private final AttributeTransmitterDistributeProcessor attributeTransmitterDistributeProcessor;

    public RemoteRequestMappingGatherListener(AttributeTransmitterDistributeProcessor attributeTransmitterDistributeProcessor) {
        this.attributeTransmitterDistributeProcessor = attributeTransmitterDistributeProcessor;
    }

    @Override
    public void onApplicationEvent(RemoteRequestMappingGatherEvent event) {

        log.info("[Herodotus] |- Request mapping gather REMOTE listener, response event!");

        String requestMapping = event.getData();
        log.debug("[Herodotus] |- Fetch data [{}]", requestMapping);
        if (ObjectUtils.isNotEmpty(requestMapping)) {
            List<RequestMapping> requestMappings = Jackson2Utils.toList(requestMapping, RequestMapping.class);
            if (CollectionUtils.isNotEmpty(requestMappings)) {
                log.debug("[Herodotus] |- [4] Request mapping process BEGIN!");
                attributeTransmitterDistributeProcessor.postRequestMappings(requestMappings);
            }
        }
    }
}

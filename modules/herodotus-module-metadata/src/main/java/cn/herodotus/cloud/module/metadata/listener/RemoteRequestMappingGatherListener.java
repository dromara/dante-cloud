/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 *    Author: ZHENGGENGWEI<码匠君>
 *    Contact: <herodotus@aliyun.com>
 *    Blog and source code availability: <https://gitee.com/herodotus/herodotus-cloud>
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

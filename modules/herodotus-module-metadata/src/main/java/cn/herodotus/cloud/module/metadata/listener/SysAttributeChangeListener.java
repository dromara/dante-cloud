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
import cn.herodotus.stirrup.logic.upms.domain.event.SysAttributeChangeEvent;
import cn.herodotus.stirrup.logic.upms.entity.security.SysAttribute;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * <p>Description: SysSecurityAttribute变更事件监听 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/4 22:18
 */
@Component
public class SysAttributeChangeListener implements ApplicationListener<SysAttributeChangeEvent> {

    private static final Logger log = LoggerFactory.getLogger(SysAttributeChangeListener.class);

    private final AttributeTransmitterDistributeProcessor attributeTransmitterDistributeProcessor;

    public SysAttributeChangeListener(AttributeTransmitterDistributeProcessor attributeTransmitterDistributeProcessor) {
        this.attributeTransmitterDistributeProcessor = attributeTransmitterDistributeProcessor;
    }

    @Override
    public void onApplicationEvent(SysAttributeChangeEvent event) {

        log.debug("[Herodotus] |- SysAttribute Change Listener, response event!");

        SysAttribute sysAttribute = event.getData();
        if (ObjectUtils.isNotEmpty(sysAttribute)) {
            log.debug("[Herodotus] |- Got SysAttribute, start to process SysAttribute change.");
            attributeTransmitterDistributeProcessor.distributeChangedSecurityAttribute(sysAttribute);
        }
    }
}

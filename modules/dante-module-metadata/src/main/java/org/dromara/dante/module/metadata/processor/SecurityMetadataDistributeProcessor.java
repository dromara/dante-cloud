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

import org.dromara.dante.module.metadata.converter.SysAttributeToSecurityAttributeConverter;
import org.dromara.dante.module.metadata.converter.SysInterfacesToSysAttributesConverter;
import cn.herodotus.engine.assistant.core.exception.transaction.TransactionalRollbackException;
import cn.herodotus.engine.message.core.definition.strategy.StrategyEventManager;
import cn.herodotus.engine.message.core.logic.domain.RequestMapping;
import cn.herodotus.engine.oauth2.authorization.processor.SecurityMetadataSourceAnalyzer;
import cn.herodotus.engine.oauth2.core.definition.domain.SecurityAttribute;
import cn.herodotus.engine.oauth2.resource.autoconfigure.bus.RemoteSecurityMetadataSyncEvent;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysAttribute;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysInterface;
import cn.herodotus.engine.supplier.upms.logic.service.security.SysAttributeService;
import cn.herodotus.engine.supplier.upms.logic.service.security.SysInterfaceService;
import com.google.common.collect.ImmutableList;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>Description: SecurityMetadata数据处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/8 14:00
 */
@Component
public class SecurityMetadataDistributeProcessor implements StrategyEventManager<List<SecurityAttribute>> {

    private static final Logger log = LoggerFactory.getLogger(SecurityMetadataDistributeProcessor.class);

    private final Converter<List<SysInterface>, List<SysAttribute>> toSysAttributes;
    private final Converter<SysAttribute, SecurityAttribute> toSecurityAttribute;

    private final SysAttributeService sysAttributeService;
    private final SysInterfaceService sysInterfaceService;
    private final SecurityMetadataSourceAnalyzer securityMetadataSourceAnalyzer;

    public SecurityMetadataDistributeProcessor(SysAttributeService sysAttributeService, SysInterfaceService sysInterfaceService, SecurityMetadataSourceAnalyzer securityMetadataSourceAnalyzer) {
        this.sysAttributeService = sysAttributeService;
        this.sysInterfaceService = sysInterfaceService;
        this.securityMetadataSourceAnalyzer = securityMetadataSourceAnalyzer;
        this.toSysAttributes = new SysInterfacesToSysAttributesConverter();
        this.toSecurityAttribute = new SysAttributeToSecurityAttributeConverter();
    }

    @Override
    public void postLocalProcess(List<SecurityAttribute> data) {
        securityMetadataSourceAnalyzer.processSecurityAttribute(data);
    }

    @Override
    public void postRemoteProcess(String data, String originService, String destinationService) {
        publishEvent(new RemoteSecurityMetadataSyncEvent(data, originService, destinationService));
    }

    /**
     * 将SysAuthority表中存在，但是SysSecurityAttribute中不存在的数据同步至SysSecurityAttribute，保证两侧数据一致
     */
    @Transactional(rollbackFor = TransactionalRollbackException.class)
    public void postRequestMappings(List<RequestMapping> requestMappings) {
        List<SysInterface> storedInterfaces = sysInterfaceService.storeRequestMappings(requestMappings);
        if (CollectionUtils.isNotEmpty(storedInterfaces)) {
            log.debug("[Herodotus] |- [5] Request mapping store success, start to merge security metadata!");

            List<SysInterface> sysInterfaces = sysInterfaceService.findAllocatable();

            if (CollectionUtils.isNotEmpty(sysInterfaces)) {
                List<SysAttribute> elements = toSysAttributes.convert(sysInterfaces);
                List<SysAttribute> result = sysAttributeService.saveAllAndFlush(elements);
                if (CollectionUtils.isNotEmpty(result)) {
                    log.debug("[Herodotus] |- Merge security attribute SUCCESS and FINISHED!");
                } else {
                    log.error("[Herodotus] |- Merge Security attribute failed!, Please Check!");
                }
            } else {
                log.debug("[Herodotus] |- No security attribute requires merge, SKIP!");
            }

            distributeServiceSecurityAttributes(storedInterfaces);
        }
    }

    private void distributeServiceSecurityAttributes(List<SysInterface> storedInterfaces) {
        storedInterfaces.stream().findAny().ifPresent(item -> {
            String serviceId = item.getServiceId();
            List<SysAttribute> sysAttributes = sysAttributeService.findAllByServiceId(item.getServiceId());
            if (CollectionUtils.isNotEmpty(sysAttributes)) {
                List<SecurityAttribute> securityAttributes = sysAttributes.stream().map(toSecurityAttribute::convert).toList();
                log.debug("[Herodotus] |- [6] Synchronization permissions to service [{}]", serviceId);
                this.postProcess(serviceId, securityAttributes);
            }
        });
    }

    public void distributeChangedSecurityAttribute(SysAttribute sysAttribute) {
        SecurityAttribute securityAttribute = toSecurityAttribute.convert(sysAttribute);
        postProcess(securityAttribute.getServiceId(), ImmutableList.of(securityAttribute));
    }
}

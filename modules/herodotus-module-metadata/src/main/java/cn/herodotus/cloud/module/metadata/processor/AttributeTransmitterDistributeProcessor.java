/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 *    Author: ZHENGGENGWEI<码匠君>
 *    Contact: <herodotus@aliyun.com>
 *    Blog and source code availability: <https://gitee.com/herodotus/herodotus-cloud>
 */

package cn.herodotus.cloud.module.metadata.processor;

import cn.herodotus.cloud.module.metadata.converter.SysAttributeToAttributeTransmitterConverter;
import cn.herodotus.cloud.module.metadata.converter.SysInterfacesToSysAttributesConverter;
import cn.herodotus.stirrup.core.event.definition.strategy.StrategyEventManager;
import cn.herodotus.stirrup.core.identity.domain.AttributeTransmitter;
import cn.herodotus.stirrup.logic.upms.entity.security.SysAttribute;
import cn.herodotus.stirrup.logic.upms.entity.security.SysInterface;
import cn.herodotus.stirrup.logic.upms.service.security.SysAttributeService;
import cn.herodotus.stirrup.logic.upms.service.security.SysInterfaceService;
import cn.herodotus.stirrup.message.ability.domain.RequestMapping;
import cn.herodotus.stirrup.oauth2.authorization.autoconfigure.bus.RemoteAttributeTransmitterSyncEvent;
import cn.herodotus.stirrup.oauth2.authorization.processor.SecurityAttributeAnalyzer;
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
public class AttributeTransmitterDistributeProcessor implements StrategyEventManager<List<AttributeTransmitter>> {

    private static final Logger log = LoggerFactory.getLogger(AttributeTransmitterDistributeProcessor.class);

    private final Converter<List<SysInterface>, List<SysAttribute>> toSysAttributes;
    private final Converter<SysAttribute, AttributeTransmitter> toAttributeTransmitter;

    private final SysAttributeService sysAttributeService;
    private final SysInterfaceService sysInterfaceService;
    private final SecurityAttributeAnalyzer securityAttributeAnalyzer;

    public AttributeTransmitterDistributeProcessor(SysAttributeService sysAttributeService, SysInterfaceService sysInterfaceService, SecurityAttributeAnalyzer securityAttributeAnalyzer) {
        this.sysAttributeService = sysAttributeService;
        this.sysInterfaceService = sysInterfaceService;
        this.securityAttributeAnalyzer = securityAttributeAnalyzer;
        this.toSysAttributes = new SysInterfacesToSysAttributesConverter();
        this.toAttributeTransmitter = new SysAttributeToAttributeTransmitterConverter();
    }

    /**
     * UPMS 服务既要处理各个服务权限数据的分发，也要处理自身服务权限数据
     *
     * @param data 事件携带数据
     */
    @Override
    public void postLocalProcess(List<AttributeTransmitter> data) {
        securityAttributeAnalyzer.processAttributeTransmitters(data);
    }

    @Override
    public void postRemoteProcess(String data, String originService, String destinationService) {
        publishEvent(new RemoteAttributeTransmitterSyncEvent(data, originService, destinationService));
    }

    /**
     * 将SysAuthority表中存在，但是SysSecurityAttribute中不存在的数据同步至SysSecurityAttribute，保证两侧数据一致
     */
    @Transactional(rollbackFor = Exception.class)
    public void postRequestMappings(List<RequestMapping> requestMappings) {

        // 将各个服务发送回来的 requestMappings 存储到 SysInterface 中
        List<SysInterface> storedInterfaces = sysInterfaceService.storeRequestMappings(requestMappings);

        if (CollectionUtils.isNotEmpty(storedInterfaces)) {
            log.debug("[Herodotus] |- [5] Request mapping store success, start to merge security metadata!");

            // 查询将新增的 SysInterface，将其转存到 SysAttribute 中
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

            // 执行权限数据分发
            distributeServiceSecurityAttributes(storedInterfaces);
//
//            List<SysAttribute> sysAttributes = sysAttributeService.findAll();
//            this.postGroupProcess(sysAttributes);
        }
    }

    private void distributeServiceSecurityAttributes(List<SysInterface> storedInterfaces) {
        storedInterfaces.stream().findAny().ifPresent(item -> {
            String serviceId = item.getServiceId();
            List<SysAttribute> sysAttributes = sysAttributeService.findAllByServiceId(item.getServiceId());
            if (CollectionUtils.isNotEmpty(sysAttributes)) {
                List<AttributeTransmitter> attributeTransmitters = sysAttributes.stream().map(toAttributeTransmitter::convert).toList();
                log.debug("[Herodotus] |- [6] Synchronization permissions to service [{}]", serviceId);
                this.postProcess(serviceId, attributeTransmitters);
            }
        });
    }

    public void distributeChangedSecurityAttribute(SysAttribute sysAttribute) {
        AttributeTransmitter attributeTransmitter = toAttributeTransmitter.convert(sysAttribute);
        postProcess(attributeTransmitter.getServiceId(), ImmutableList.of(attributeTransmitter));
    }
}

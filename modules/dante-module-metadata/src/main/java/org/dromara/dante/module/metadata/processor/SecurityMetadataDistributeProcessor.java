/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Cloud 采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package org.dromara.dante.module.metadata.processor;

import cn.herodotus.engine.core.foundation.exception.transaction.TransactionalRollbackException;
import cn.herodotus.engine.logic.upms.entity.security.SysAttribute;
import cn.herodotus.engine.logic.upms.entity.security.SysInterface;
import cn.herodotus.engine.logic.upms.service.security.SysAttributeService;
import cn.herodotus.engine.logic.upms.service.security.SysInterfaceService;
import cn.herodotus.engine.message.core.definition.strategy.StrategyEventManager;
import cn.herodotus.engine.message.core.logic.domain.RequestMapping;
import cn.herodotus.engine.oauth2.authorization.processor.SecurityMetadataSourceAnalyzer;
import cn.herodotus.engine.oauth2.core.definition.domain.SecurityAttribute;
import cn.herodotus.engine.oauth2.resource.autoconfigure.bus.RemoteSecurityMetadataSyncEvent;
import com.google.common.collect.ImmutableList;
import org.apache.commons.collections4.CollectionUtils;
import org.dromara.dante.module.metadata.converter.SysAttributeToSecurityAttributeConverter;
import org.dromara.dante.module.metadata.converter.SysInterfacesToSysAttributesConverter;
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

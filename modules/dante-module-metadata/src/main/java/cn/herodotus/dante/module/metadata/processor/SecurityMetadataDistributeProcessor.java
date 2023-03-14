/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dante.module.metadata.processor;

import cn.herodotus.engine.assistant.core.exception.transaction.TransactionalRollbackException;
import cn.herodotus.engine.message.security.event.RemoteSecurityMetadataSyncEvent;
import cn.herodotus.engine.oauth2.authorization.processor.SecurityMetadataSourceAnalyzer;
import cn.herodotus.engine.oauth2.core.definition.domain.SecurityAttribute;
import cn.herodotus.engine.rest.core.context.ServiceContext;
import cn.herodotus.engine.rest.core.definition.event.ApplicationStrategyEvent;
import cn.herodotus.engine.rest.core.domain.RequestMapping;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysAttribute;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysInterface;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysPermission;
import cn.herodotus.engine.supplier.upms.logic.service.security.SysAttributeService;
import cn.herodotus.engine.supplier.upms.logic.service.security.SysInterfaceService;
import com.google.common.collect.ImmutableList;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Description: SecurityMetadata数据处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/8 14:00
 */
@Component
public class SecurityMetadataDistributeProcessor implements ApplicationStrategyEvent<List<SecurityAttribute>> {

    private static final Logger log = LoggerFactory.getLogger(SecurityMetadataDistributeProcessor.class);

    private final SysAttributeService sysAttributeService;
    private final SysInterfaceService sysInterfaceService;
    private final SecurityMetadataSourceAnalyzer securityMetadataSourceAnalyzer;

    public SecurityMetadataDistributeProcessor(SysAttributeService sysAttributeService, SysInterfaceService sysInterfaceService, SecurityMetadataSourceAnalyzer securityMetadataSourceAnalyzer) {
        this.sysAttributeService = sysAttributeService;
        this.sysInterfaceService = sysInterfaceService;
        this.securityMetadataSourceAnalyzer = securityMetadataSourceAnalyzer;
    }

    @Override
    public void postLocalProcess(List<SecurityAttribute> data) {
        securityMetadataSourceAnalyzer.processSecurityAttribute(data);
    }

    @Override
    public void postRemoteProcess(String data, String originService, String destinationService) {
        ServiceContext.getInstance().publishEvent(new RemoteSecurityMetadataSyncEvent(data, originService, destinationService));
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
                List<SysAttribute> elements = this.convertSysInterfacesToSysAttributes(sysInterfaces);
                List<SysAttribute> result = sysAttributeService.batchSaveOrUpdate(elements);
                if (CollectionUtils.isNotEmpty(result)) {
                    log.debug("[Herodotus] |- Merge security attribute SUCCESS and FINISHED!");
                } else {
                    log.error("[Herodotus] |- Merge Security attribute failed!, Please Check!");
                }
            } else {
                log.debug("[Herodotus] |- No security attribute requires merge, SKIP!");
            }

            log.debug("[Herodotus] |- [6] Synchronization current permissions to every service!");

            List<SysAttribute> sysAttributes = sysAttributeService.findAll();
            this.postGroupProcess(sysAttributes);
        }
    }

    private void postGroupProcess(List<SysAttribute> sysAttributes) {
        if (CollectionUtils.isNotEmpty(sysAttributes)) {
            Map<String, List<SecurityAttribute>> grouped = sysAttributes.stream().map(this::convertSysAttributeToSecurityAttribute).collect(Collectors.groupingBy(SecurityAttribute::getServiceId));
            log.debug("[Herodotus] |- Grouping SysInterface and distribute to every server.");
            grouped.forEach(this::postProcess);
        }
    }

    public void distributeChangedSecurityAttribute(SysAttribute sysAttribute) {
        SecurityAttribute securityAttribute = convertSysAttributeToSecurityAttribute(sysAttribute);
        postProcess(securityAttribute.getServiceId(), ImmutableList.of(securityAttribute));
    }

    private List<SysAttribute> convertSysInterfacesToSysAttributes(Collection<SysInterface> sysInterfaces) {
        if (CollectionUtils.isNotEmpty(sysInterfaces)) {
            return sysInterfaces.stream().map(this::convertSysInterfaceToSysAttribute).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private SysAttribute convertSysInterfaceToSysAttribute(SysInterface sysInterface) {
        SysAttribute sysAttribute = new SysAttribute();
        sysAttribute.setAttributeId(sysInterface.getInterfaceId());
        sysAttribute.setAttributeCode(sysInterface.getInterfaceCode());
        sysAttribute.setRequestMethod(sysInterface.getRequestMethod());
        sysAttribute.setServiceId(sysInterface.getServiceId());
        sysAttribute.setClassName(sysInterface.getClassName());
        sysAttribute.setMethodName(sysInterface.getMethodName());
        sysAttribute.setUrl(sysInterface.getUrl());
        sysAttribute.setStatus(sysInterface.getStatus());
        sysAttribute.setReserved(sysInterface.getReserved());
        sysAttribute.setDescription(sysInterface.getDescription());
        sysAttribute.setReversion(sysInterface.getReversion());
        sysAttribute.setCreateTime(sysInterface.getCreateTime());
        sysAttribute.setUpdateTime(sysInterface.getUpdateTime());
        sysAttribute.setRanking(sysInterface.getRanking());
        return sysAttribute;
    }

    private SecurityAttribute convertSysAttributeToSecurityAttribute(SysAttribute sysAttribute) {
        SecurityAttribute securityAttribute = new SecurityAttribute();
        securityAttribute.setAttributeId(sysAttribute.getAttributeId());
        securityAttribute.setAttributeCode(sysAttribute.getAttributeCode());
        securityAttribute.setWebExpression(sysAttribute.getWebExpression());
        securityAttribute.setPermissions(convertPermissionToCommaDelimitedString(sysAttribute.getPermissions()));
        securityAttribute.setUrl(sysAttribute.getUrl());
        securityAttribute.setRequestMethod(sysAttribute.getRequestMethod());
        securityAttribute.setServiceId(sysAttribute.getServiceId());
        securityAttribute.setAttributeName(sysAttribute.getDescription());
        return securityAttribute;
    }

    private String convertPermissionToCommaDelimitedString(Set<SysPermission> sysAuthorities) {
        if (CollectionUtils.isNotEmpty(sysAuthorities)) {
            List<String> codes = sysAuthorities.stream().map(SysPermission::getPermissionCode).toList();
            return StringUtils.collectionToCommaDelimitedString(codes);
        } else {
            return "";
        }
    }
}

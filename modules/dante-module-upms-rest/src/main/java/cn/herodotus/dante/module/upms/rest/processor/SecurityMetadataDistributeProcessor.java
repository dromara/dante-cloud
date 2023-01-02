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

package cn.herodotus.dante.module.upms.rest.processor;

import cn.herodotus.dante.module.upms.logic.entity.system.SysAuthority;
import cn.herodotus.dante.module.upms.logic.entity.system.SysRole;
import cn.herodotus.dante.module.upms.logic.entity.system.SysSecurityAttribute;
import cn.herodotus.dante.module.upms.logic.service.system.SysAuthorityService;
import cn.herodotus.dante.module.upms.logic.service.system.SysSecurityAttributeService;
import cn.herodotus.engine.assistant.core.exception.transaction.TransactionalRollbackException;
import cn.herodotus.engine.event.security.remote.RemoteSecurityMetadataSyncEvent;
import cn.herodotus.engine.oauth2.authorization.processor.SecurityMetadataSourceAnalyzer;
import cn.herodotus.engine.oauth2.core.definition.domain.HerodotusGrantedAuthority;
import cn.herodotus.engine.oauth2.core.definition.domain.SecurityAttribute;
import cn.herodotus.engine.web.core.context.ServiceContext;
import cn.herodotus.engine.web.core.definition.ApplicationStrategyEvent;
import cn.herodotus.engine.web.core.domain.RequestMapping;
import com.google.common.collect.ImmutableList;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    private final SysSecurityAttributeService sysSecurityAttributeService;
    private final SysAuthorityService sysAuthorityService;
    private final SecurityMetadataSourceAnalyzer securityMetadataSourceAnalyzer;

    @Autowired
    public SecurityMetadataDistributeProcessor(SysSecurityAttributeService sysSecurityAttributeService, SysAuthorityService sysAuthorityService, SecurityMetadataSourceAnalyzer securityMetadataSourceAnalyzer) {
        this.sysSecurityAttributeService = sysSecurityAttributeService;
        this.sysAuthorityService = sysAuthorityService;
        this.securityMetadataSourceAnalyzer = securityMetadataSourceAnalyzer;
    }

    @Override
    public void postLocalProcess(List<SecurityAttribute> data) {
        securityMetadataSourceAnalyzer.processSecurityMetadata(data);
    }

    @Override
    public void postRemoteProcess(String data, String originService, String destinationService) {
        ServiceContext.getInstance().publishEvent(new RemoteSecurityMetadataSyncEvent(data, originService, destinationService));
    }

    private void postGroupProcess(List<SysSecurityAttribute> sysSecurityAttributes) {
        if (CollectionUtils.isNotEmpty(sysSecurityAttributes)) {
            Map<String, List<SecurityAttribute>> grouped = sysSecurityAttributes.stream().map(this::convertSysSecurityAttributeToSecurityAttribute).collect(Collectors.groupingBy(SecurityAttribute::getServiceId));
            log.debug("[Herodotus] |- Grouping SysSecurityAttribute and distribute to every server.");
            grouped.forEach(this::postProcess);
        }
    }

    public void distributeChangedSecurityAttribute(SysSecurityAttribute sysSecurityAttribute) {
        SecurityAttribute securityAttribute = convertSysSecurityAttributeToSecurityAttribute(sysSecurityAttribute);
        postProcess(securityAttribute.getServiceId(), ImmutableList.of(securityAttribute));
    }

    public void distributeRelationChangedSecurityAttribute(List<String> sysAuthorities) {
        List<SysSecurityAttribute> sysSecurityAttributes = sysSecurityAttributeService.findByAttributeIdIn(sysAuthorities);
        this.postGroupProcess(sysSecurityAttributes);
    }

    /**
     * 将SysAuthority表中存在，但是SysSecurityAttribute中不存在的数据同步至SysSecurityAttribute，保证两侧数据一致
     */
    @Transactional(rollbackFor = TransactionalRollbackException.class)
    public void postMetadataProcess(List<RequestMapping> requestMappings) {
        List<SysAuthority> storeRequestMappings = sysAuthorityService.storeRequestMappings(requestMappings);
        if (CollectionUtils.isNotEmpty(storeRequestMappings)) {
            log.debug("[Herodotus] |- [5] Request mapping store success, start to merge security metadata!");

            List<SysAuthority> sysAuthorities = sysAuthorityService.findAllocatable();
            if (CollectionUtils.isNotEmpty(sysAuthorities)) {
                List<SysSecurityAttribute> sysMetadata = this.convertSysAuthoritiesToSysSecurityAttributes(sysAuthorities);
                List<SysSecurityAttribute> result = sysSecurityAttributeService.batchSaveOrUpdate(sysMetadata);
                if (CollectionUtils.isNotEmpty(result)) {
                    log.debug("[Herodotus] |- [6] Merge security metadata SUCCESS and FINISHED!");
                } else {
                    log.error("[Herodotus] |- [6] Merge Security Metadata failed!, Please Check!");
                }
            } else {
                log.debug("[Herodotus] |- No security metadata requires merge, SKIP!");
            }

            log.debug("[Herodotus] |- [7] Synchronization current authorities to every service!");
            List<SysSecurityAttribute> sysSecurityAttributes = sysSecurityAttributeService.findAll();
            this.postGroupProcess(sysSecurityAttributes);
        }
    }

    private List<SysSecurityAttribute> convertSysAuthoritiesToSysSecurityAttributes(Collection<SysAuthority> sysAuthorities) {
        if (CollectionUtils.isNotEmpty(sysAuthorities)) {
            return sysAuthorities.stream().map(this::convertSysAuthorityToSysSecurityAttribute).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private SysSecurityAttribute convertSysAuthorityToSysSecurityAttribute(SysAuthority sysAuthority) {
        SysSecurityAttribute sysSecurityAttribute = new SysSecurityAttribute();
        sysSecurityAttribute.setAttributeId(sysAuthority.getAuthorityId());
        sysSecurityAttribute.setAttributeCode(sysAuthority.getAuthorityCode());
        sysSecurityAttribute.setUrl(sysAuthority.getUrl());
        sysSecurityAttribute.setRequestMethod(sysAuthority.getRequestMethod());
        sysSecurityAttribute.setServiceId(sysAuthority.getServiceId());
        sysSecurityAttribute.setDescription(sysAuthority.getAuthorityName());
        return sysSecurityAttribute;
    }

    private SecurityAttribute convertSysSecurityAttributeToSecurityAttribute(SysSecurityAttribute sysSecurityAttribute) {
        SecurityAttribute securityAttribute = new SecurityAttribute();
        securityAttribute.setAttributeId(sysSecurityAttribute.getAttributeId());
        securityAttribute.setAttributeCode(sysSecurityAttribute.getAttributeCode());
        securityAttribute.setExpression(sysSecurityAttribute.getExpression());
        securityAttribute.setManualSetting(sysSecurityAttribute.getManualSetting());
        securityAttribute.setIpAddress(sysSecurityAttribute.getIpAddress());
        securityAttribute.setUrl(sysSecurityAttribute.getUrl());
        securityAttribute.setRequestMethod(sysSecurityAttribute.getRequestMethod());
        securityAttribute.setServiceId(sysSecurityAttribute.getServiceId());
        securityAttribute.setAttributeName(sysSecurityAttribute.getDescription());
        if (CollectionUtils.isNotEmpty(sysSecurityAttribute.getRoles())) {
            securityAttribute.setRoles(convertSysRolesToHerodotusGrantedAuthorities(sysSecurityAttribute.getRoles()));
        }
        return securityAttribute;
    }

    private Set<HerodotusGrantedAuthority> convertSysRolesToHerodotusGrantedAuthorities(Set<SysRole> sysRoles) {
        return sysRoles.stream().map(this::convertSysRoleToHerodotusGrantedAuthority).collect(Collectors.toSet());
    }

    private HerodotusGrantedAuthority convertSysRoleToHerodotusGrantedAuthority(SysRole sysRole) {
        return new HerodotusGrantedAuthority(sysRole.getRoleCode());
    }
}

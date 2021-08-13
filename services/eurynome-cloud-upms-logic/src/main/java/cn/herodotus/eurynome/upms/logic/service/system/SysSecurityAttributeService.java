/*
 * Copyright (c) 2019-2021 Gengwei Zheng (herodotus@aliyun.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-upms-logic
 * File Name: SysSecurityAttributeService.java
 * Author: gengwei.zheng
 * Date: 2021/08/05 17:50:05
 */

package cn.herodotus.eurynome.upms.logic.service.system;

import cn.herodotus.eurynome.crud.service.BaseLayeredService;
import cn.herodotus.eurynome.data.base.repository.BaseRepository;
import cn.herodotus.eurynome.upms.api.entity.system.SysSecurityAttribute;
import cn.herodotus.eurynome.upms.api.entity.system.SysRole;
import cn.herodotus.eurynome.upms.logic.repository.system.SysSecurityAttributeRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.SetJoin;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: SysSecurityAttributeService </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/5 17:50
 */
@Service
public class SysSecurityAttributeService extends BaseLayeredService<SysSecurityAttribute, String> {

    private static final Logger log = LoggerFactory.getLogger(SysSecurityAttributeService.class);

    private final SysSecurityAttributeRepository sysSecurityAttributeRepository;

    @Autowired
    public SysSecurityAttributeService(SysSecurityAttributeRepository sysSecurityAttributeRepository) {
        this.sysSecurityAttributeRepository = sysSecurityAttributeRepository;
    }

    @Override
    public BaseRepository<SysSecurityAttribute, String> getRepository() {
        return this.sysSecurityAttributeRepository;
    }

    public List<SysSecurityAttribute> batchSaveOrUpdate(List<SysSecurityAttribute> sysMetadata) {
        log.debug("[Eurynome] |- SysSecurityAttribute Service batchSaveOrUpdate.");
        return sysSecurityAttributeRepository.saveAllAndFlush(sysMetadata);
    }

    public List<SysSecurityAttribute> findByAttributeIdIn(List<String> ids) {
        log.debug("[Eurynome] |- SysSecurityAttribute Service findByAttributeIdIn.");
        return sysSecurityAttributeRepository.findByAttributeIdIn(ids);
    }

    public List<SysSecurityAttribute> findByRoleId(String roleId) {
        return this.findByCondition(roleId, null, null);
    }

    public List<SysSecurityAttribute> findByScopeId(String scopeId) {
        return this.findByCondition(null, scopeId, null);
    }

    public List<SysSecurityAttribute> findByIpAddressId(String ipAddressId) {
        return this.findByCondition(null, null, ipAddressId);
    }

    public List<SysSecurityAttribute> findByCondition(String roleId, String scopeId, String ipAddressId) {
        Specification<SysSecurityAttribute> specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(roleId)) {
                SetJoin<SysSecurityAttribute, SysRole> join = root.joinSet("roles", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(join.get("roleId").as(String.class), roleId));
            }

            if (StringUtils.isNotBlank(scopeId)) {
                SetJoin<SysSecurityAttribute, SysRole> join = root.joinSet("scopes", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(join.get("scopeId").as(String.class), scopeId));
            }

            if (StringUtils.isNotBlank(ipAddressId)) {
                SetJoin<SysSecurityAttribute, SysRole> join = root.joinSet("ipAddress", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(join.get("ipId").as(String.class), ipAddressId));
            }

            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(predicateArray)));
            return criteriaQuery.getRestriction();
        };

        log.debug("[Eurynome] |- SysSecurityAttribute Service findByCondition.");
        return this.sysSecurityAttributeRepository.findAll(specification);
    }
}

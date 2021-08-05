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
 * File Name: SysMetadataService.java
 * Author: gengwei.zheng
 * Date: 2021/08/05 17:50:05
 */

package cn.herodotus.eurynome.upms.logic.service.system;

import cn.herodotus.eurynome.crud.service.BaseLayeredService;
import cn.herodotus.eurynome.data.base.repository.BaseRepository;
import cn.herodotus.eurynome.upms.api.entity.system.SysMetadata;
import cn.herodotus.eurynome.upms.api.entity.system.SysRole;
import cn.herodotus.eurynome.upms.logic.repository.system.SysMetadataRepository;
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
 * <p>Description: SysMetadataService </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/5 17:50
 */
@Service
public class SysMetadataService extends BaseLayeredService<SysMetadata, String> {

    private static final Logger log = LoggerFactory.getLogger(SysMetadataService.class);

    private final SysMetadataRepository sysMetadataRepository;

    @Autowired
    public SysMetadataService(SysMetadataRepository sysMetadataRepository) {
        this.sysMetadataRepository = sysMetadataRepository;
    }

    @Override
    public BaseRepository<SysMetadata, String> getRepository() {
        return this.sysMetadataRepository;
    }

    public List<SysMetadata> batchSaveOrUpdate(List<SysMetadata> sysMetadata) {
        log.debug("[Herodotus] |- SysMetadata Service batchSaveOrUpdate.");
        return sysMetadataRepository.saveAllAndFlush(sysMetadata);
    }

    public List<SysMetadata> findByMetadataIn(List<String> ids) {
        log.debug("[Herodotus] |- SysMetadata Service findByMetadataIn.");
        return sysMetadataRepository.findByMetadataIdIn(ids);
    }

    public List<SysMetadata> findByRoleId(String roleId) {
        return this.findByCondition(roleId, null, null);
    }

    public List<SysMetadata> findByScopeId(String scopeId) {
        return this.findByCondition(null, scopeId, null);
    }

    public List<SysMetadata> findByIpAddressId(String ipAddressId) {
        return this.findByCondition(null, null, ipAddressId);
    }

    public List<SysMetadata> findByCondition(String roleId, String scopeId, String ipAddressId) {
        Specification<SysMetadata> specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(roleId)) {
                SetJoin<SysMetadata, SysRole> join = root.joinSet("roles", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(join.get("roleId").as(String.class), roleId));
            }

            if (StringUtils.isNotBlank(scopeId)) {
                SetJoin<SysMetadata, SysRole> join = root.joinSet("scopes", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(join.get("scopeId").as(String.class), scopeId));
            }

            if (StringUtils.isNotBlank(ipAddressId)) {
                SetJoin<SysMetadata, SysRole> join = root.joinSet("ipAddress", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(join.get("ipId").as(String.class), ipAddressId));
            }

            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(predicateArray)));
            return criteriaQuery.getRestriction();
        };

        log.debug("[Herodotus] |- SysMetadata Service findByCondition.");
        return this.sysMetadataRepository.findAll(specification);
    }
}

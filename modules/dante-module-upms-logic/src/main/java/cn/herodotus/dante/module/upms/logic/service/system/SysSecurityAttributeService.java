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

package cn.herodotus.dante.module.upms.logic.service.system;

import cn.herodotus.dante.module.upms.logic.entity.system.SysRole;
import cn.herodotus.dante.module.upms.logic.entity.system.SysSecurityAttribute;
import cn.herodotus.dante.module.upms.logic.repository.system.SysSecurityAttributeRepository;
import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.engine.data.core.service.BaseLayeredService;
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
 * @date : 2021/8/4 6:48
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
        List<SysSecurityAttribute> sysSecurityAttributes = sysSecurityAttributeRepository.saveAllAndFlush(sysMetadata);
        log.debug("[Herodotus] |- SysSecurityAttribute Service batchSaveOrUpdate.");
        return sysSecurityAttributes;
    }

    public List<SysSecurityAttribute> findByAttributeIdIn(List<String> ids) {
        log.debug("[Herodotus] |- SysSecurityAttribute Service findByAttributeIdIn.");
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

            if (StringUtils.isNotBlank(ipAddressId)) {
                SetJoin<SysSecurityAttribute, SysRole> join = root.joinSet("ipAddress", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(join.get("ipId").as(String.class), ipAddressId));
            }

            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(predicateArray)));
            return criteriaQuery.getRestriction();
        };

        log.debug("[Herodotus] |- SysSecurityAttribute Service findByCondition.");
        return this.sysSecurityAttributeRepository.findAll(specification);
    }
}

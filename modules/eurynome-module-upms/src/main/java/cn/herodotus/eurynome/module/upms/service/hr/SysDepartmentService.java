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
 * File Name: SysDepartmentService.java
 * Author: gengwei.zheng
 * Date: 2021/09/25 10:46:25
 */

package cn.herodotus.eurynome.module.upms.service.hr;

import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.engine.data.core.service.BaseLayeredService;
import cn.herodotus.eurynome.module.upms.entity.hr.SysDepartment;
import cn.herodotus.eurynome.module.upms.repository.hr.SysDepartmentRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: 部门管理服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/20 11:50
 */
@Service
public class SysDepartmentService extends BaseLayeredService<SysDepartment, String> {

    private static final Logger log = LoggerFactory.getLogger(SysDepartmentService.class);

    private final SysDepartmentRepository sysDepartmentRepository;
    private final SysOwnershipService sysOwnershipService;

    @Autowired
    public SysDepartmentService(SysDepartmentRepository sysDepartmentRepository, SysOwnershipService sysOwnershipService) {
        this.sysDepartmentRepository = sysDepartmentRepository;
        this.sysOwnershipService = sysOwnershipService;
    }

    @Override
    public BaseRepository<SysDepartment, String> getRepository() {
        return sysDepartmentRepository;
    }

    public Page<SysDepartment> findByCondition(int pageNumber, int pageSize, String organizationId) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Specification<SysDepartment> specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (ObjectUtils.isNotEmpty(organizationId)) {
                predicates.add(criteriaBuilder.equal(root.get("organizationId"), organizationId));
            }

            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(predicateArray)));
            return criteriaQuery.getRestriction();
        };

        log.debug("[Herodotus] |- SysDepartment Service findByCondition.");
        return this.findByPage(specification, pageable);
    }

    public List<SysDepartment> findAll(String organizationId) {
        if (ObjectUtils.isNotEmpty(organizationId)) {
            return sysDepartmentRepository.findByOrganizationId(organizationId);
        } else {
            return sysDepartmentRepository.findAll();
        }
    }

    @Override
    public void deleteById(String departmentId) {
        sysOwnershipService.deleteByDepartmentId(departmentId);
        super.deleteById(departmentId);
        log.debug("[Herodotus] |- SysDepartment Service deleteById.");
    }
}

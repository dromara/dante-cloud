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
 * File Name: SysOwnershipViewService.java
 * Author: gengwei.zheng
 * Date: 2021/09/23 15:07:23
 */

package cn.herodotus.eurynome.upms.logic.service.hr;

import cn.herodotus.eurynome.data.base.repository.BaseRepository;
import cn.herodotus.eurynome.rest.base.service.ReadableService;
import cn.herodotus.eurynome.upms.api.entity.hr.SysOwnershipView;
import cn.herodotus.eurynome.upms.logic.repository.hr.SysOwnershipViewRepository;
import org.apache.commons.lang3.StringUtils;
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
 * <p>Description: 人事归属服务视图 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/15 16:33
 */
@Service
public class SysOwnershipViewService implements ReadableService<SysOwnershipView, String> {

    private static final Logger log = LoggerFactory.getLogger(SysOwnershipViewService.class);

    @Autowired
    private SysOwnershipViewRepository sysOwnershipViewRepository;

    @Override
    public BaseRepository<SysOwnershipView, String> getRepository() {
        return this.sysOwnershipViewRepository;
    }

    public Page<SysOwnershipView> findByCondition(int pageNumber, int pageSize, String organizationId, String departmentId) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Specification<SysOwnershipView> specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(organizationId)) {
                predicates.add(criteriaBuilder.equal(root.get("organizationId"), organizationId));
            }

            if (StringUtils.isNotBlank(departmentId)) {
                predicates.add(criteriaBuilder.equal(root.get("departmentId"), departmentId));
            }

            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(predicateArray)));
            return criteriaQuery.getRestriction();
        };

        Page<SysOwnershipView> pages = this.findByPage(specification, pageable);
        log.debug("[Herodotus] |- SysOwnershipView Service findByCondition.");
        return pages;
    }
}

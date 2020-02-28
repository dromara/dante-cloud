/*
 * Copyright 2019-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
 *
 * Project Name: luban-cloud
 * Module Name: luban-cloud-upms-logic
 * File Name: SysDepartmentService.java
 * Author: gengwei.zheng
 * Date: 2019/11/19 上午11:03
 * LastModified: 2019/11/19 上午10:59
 */

package cn.herodotus.eurynome.upms.logic.service.hr;

import cn.herodotus.eurynome.component.data.service.BaseCrudService;
import cn.herodotus.eurynome.upms.api.entity.hr.SysDepartment;
import cn.herodotus.eurynome.upms.logic.repository.hr.SysDepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Description: 部门管理服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/20 11:50
 */
@Slf4j
@Service
public class SysDepartmentService extends BaseCrudService<SysDepartment, String> {

    private final SysDepartmentRepository sysDepartmentRepository;

    @Autowired
    public SysDepartmentService(SysDepartmentRepository sysDepartmentRepository) {
        this.sysDepartmentRepository = sysDepartmentRepository;
    }

    @Override
    public SysDepartment findById(String departmentId) {
        log.debug("[Luban UPMS] |- SysDepartment Service findById.");
        return sysDepartmentRepository.findByDepartmentId(departmentId);
    }

    @Override
    public void deleteById(String departmentId) {
        log.debug("[Luban UPMS] |- SysDepartment Service deleteById.");
        sysDepartmentRepository.deleteByDepartmentId(departmentId);
    }

    @Override
    public Page<SysDepartment> findByPage(int pageNumber, int pageSize) {
        log.debug("[Luban UPMS] |- SysDepartment Service findByPage.");
        return sysDepartmentRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, "ranking"));
    }

    @Override
    public SysDepartment saveOrUpdate(SysDepartment sysDepartment) {
        log.debug("[Luban UPMS] |- SysDepartment Service saveOrUpdate.");
        return sysDepartmentRepository.saveAndFlush(sysDepartment);
    }

    public List<SysDepartment> findAllByOrganizationId(String organizationId) {
        log.debug("[Luban UPMS] |- SysDepartment Service findAllByOrganizationId.");
        return sysDepartmentRepository.findAllByOrganizationId(organizationId);
    }

    public Page<SysDepartment> findByPage(String organizationId, int pageNumber, int pageSize) {
        log.debug("[Luban UPMS] |- SysDepartment Service findByPage.");
        return sysDepartmentRepository.findAllByOrganizationId(organizationId, PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, "ranking"));
    }

}

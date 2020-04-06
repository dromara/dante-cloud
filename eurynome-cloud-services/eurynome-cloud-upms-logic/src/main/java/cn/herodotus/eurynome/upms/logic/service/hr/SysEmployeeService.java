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
 * File Name: SysEmployeeService.java
 * Author: gengwei.zheng
 * Date: 2019/11/19 上午11:03
 * LastModified: 2019/11/19 上午10:59
 */

package cn.herodotus.eurynome.upms.logic.service.hr;

import cn.herodotus.eurynome.component.data.base.service.BaseCrudService;
import cn.herodotus.eurynome.upms.api.entity.hr.SysEmployee;
import cn.herodotus.eurynome.upms.logic.repository.hr.SysEmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * <p>Description: 人员 服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/20 11:54
 */
@Slf4j
@Service
public class SysEmployeeService extends BaseCrudService<SysEmployee, String> {

    private final SysEmployeeRepository sysEmployeeRepository;

    @Autowired
    public SysEmployeeService(SysEmployeeRepository sysEmployeeRepository) {
        this.sysEmployeeRepository = sysEmployeeRepository;
    }

    @Override
    public SysEmployee findById(String employeeId) {
        log.debug("[Herodotus] |- SysEmployee Service findById.");
        return sysEmployeeRepository.findByEmployeeId(employeeId);
    }

    @Override
    public void deleteById(String employeeId) {
        log.debug("[Herodotus] |- SysEmployee Service deleteById.");
        sysEmployeeRepository.deleteByEmployeeId(employeeId);
    }

    @Override
    public Page<SysEmployee> findByPage(int pageNumber, int pageSize) {
        log.debug("[Herodotus] |- SysEmployee Service findByPage.");
        return sysEmployeeRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, "ranking"));
    }

    public Page<SysEmployee> findByPage(String departmentId, int pageNumber, int pageSize) {
        log.debug("[Herodotus] |- SysEmployee Service findByPage.");
        return sysEmployeeRepository.findAllByDepartmentId(departmentId, PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, "ranking"));
    }

    @Override
    public SysEmployee saveOrUpdate(SysEmployee sysEmployee) {
        log.debug("[Herodotus] |- SysEmployee Service saveOrUpdate.");
        return sysEmployeeRepository.saveAndFlush(sysEmployee);
    }
}

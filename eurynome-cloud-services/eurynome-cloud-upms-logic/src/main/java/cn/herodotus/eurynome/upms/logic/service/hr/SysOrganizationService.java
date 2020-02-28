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
 * File Name: SysOrganizationService.java
 * Author: gengwei.zheng
 * Date: 2019/11/19 上午11:03
 * LastModified: 2019/11/19 上午10:59
 */

package cn.herodotus.eurynome.upms.logic.service.hr;

import cn.herodotus.eurynome.component.data.service.BaseCrudService;
import cn.herodotus.eurynome.upms.api.entity.hr.SysOrganization;
import cn.herodotus.eurynome.upms.logic.repository.hr.SysOrganizationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: 单位管理服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/20 11:39
 */
@Slf4j
@Service
public class SysOrganizationService extends BaseCrudService<SysOrganization, String> {

    private final SysOrganizationRepository sysOrganizationRepository;

    @Autowired
    public SysOrganizationService(SysOrganizationRepository sysOrganizationRepository) {
        this.sysOrganizationRepository = sysOrganizationRepository;
    }

    @Override
    public SysOrganization findById(String organizationId) {
        return sysOrganizationRepository.findByOrganizationId(organizationId);
    }

    @Override
    public void deleteById(String organizationId) {
        sysOrganizationRepository.deleteByOrganizationId(organizationId);
    }

    @Override
    public Page<SysOrganization> findByPage(int pageNumber, int pageSize) {
        return sysOrganizationRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, "ranking"));
    }

    @Override
    public SysOrganization saveOrUpdate(SysOrganization sysOrganization) {
        log.debug("[Luban UPMS] |- SysOrganization Service saveOrUpdate.");
        return sysOrganizationRepository.saveAndFlush(sysOrganization);
    }

    public List<SysOrganization> findAll() {
        return sysOrganizationRepository.findAll(Sort.by(Sort.Direction.ASC, "ranking"));
    }
}

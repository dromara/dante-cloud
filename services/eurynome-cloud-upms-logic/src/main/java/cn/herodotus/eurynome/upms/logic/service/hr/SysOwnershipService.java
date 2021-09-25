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
 * File Name: SysOwnershipService.java
 * Author: gengwei.zheng
 * Date: 2021/09/18 16:16:18
 */

package cn.herodotus.eurynome.upms.logic.service.hr;

import cn.herodotus.eurynome.data.base.repository.BaseRepository;
import cn.herodotus.eurynome.rest.base.service.BaseLayeredService;
import cn.herodotus.eurynome.upms.api.entity.hr.SysOwnership;
import cn.herodotus.eurynome.upms.logic.repository.hr.SysOwnershipRepository;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Description: 人事归属服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/15 16:30
 */
@Service
public class SysOwnershipService extends BaseLayeredService<SysOwnership, String> {

    private static final Logger log = LoggerFactory.getLogger(SysOwnershipService.class);

    private final SysOwnershipRepository sysOwnershipRepository;

    @Autowired
    public SysOwnershipService(SysOwnershipRepository sysOwnershipRepository) {
        this.sysOwnershipRepository = sysOwnershipRepository;
    }

    @Override
    public BaseRepository<SysOwnership, String> getRepository() {
        return this.sysOwnershipRepository;
    }

    /**
     * 根据单位ID删除人事归属
     * <p>
     * 从操作的完整性上应该包含该操作，但是这个操作风险很大，会删除较多内容
     *
     * @param organizationId 单位ID
     */
    public void deleteByOrganizationId(String organizationId) {
        sysOwnershipRepository.deleteByOrganizationId(organizationId);
        log.debug("[Herodotus] |- SysOwnershipService Service deleteByOrganizationId.");
    }

    /**
     * 根据单位ID删除人事归属
     * <p>
     * 从操作的完整性上应该包含该操作，但是这个操作风险很大，会删除较多内容
     *
     * @param departmentId 部门ID
     */
    public void deleteByDepartmentId(String departmentId) {
        sysOwnershipRepository.deleteByDepartmentId(departmentId);
        log.debug("[Herodotus] |- SysOwnershipService Service deleteByDepartmentId.");
    }

    /**
     * 根据单位ID删除人事归属
     * <p>
     * 从操作的完整性上应该包含该操作，但是这个操作风险很大，会删除较多内容
     *
     * @param employeeId 人员ID
     */
    public void deleteByEmployeeId(String employeeId) {
        sysOwnershipRepository.deleteByEmployeeId(employeeId);
        log.debug("[Herodotus] |- SysOwnershipService Service deleteByEmployeeId.");
    }

    /**
     * 批量设置人事归属关系
     * <p>
     * 如果是通过{@link SysEmployeeService#findAllocatable(int, int, String, String)}获取未设置人事归属关系的人员列表。
     * 那么本方法逻辑上不会出现重复数据的设置。
     *
     * @param organizationId 单位ID
     * @param departmentId   部门ID
     * @param employeeIds    人员IDs
     * @return 已设置的人事归属列表，如果未设置成功，列表size为 0
     */
    public List<SysOwnership> assign(String organizationId, String departmentId, String[] employeeIds) {
        List<SysOwnership> result = new ArrayList<>();

        if (ArrayUtils.isNotEmpty(employeeIds)) {
            List<SysOwnership> iterable = Arrays.stream(employeeIds).map(employeeId -> {
                SysOwnership sysOwnership = new SysOwnership();
                sysOwnership.setOrganizationId(organizationId);
                sysOwnership.setDepartmentId(departmentId);
                sysOwnership.setEmployeeId(employeeId);
                return sysOwnership;
            }).collect(Collectors.toList());

            result = sysOwnershipRepository.saveAllAndFlush(iterable);
        }

        log.debug("[Herodotus] |- SysOwnershipService Service assign.");
        return result;
    }
}

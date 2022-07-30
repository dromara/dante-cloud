/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Cloud Licensed under the Apache License, Version 2.0 (the "License");
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

package cn.herodotus.dante.module.upms.logic.dto;

import cn.herodotus.dante.module.upms.logic.entity.hr.SysDepartment;
import cn.herodotus.engine.assistant.core.definition.domain.AbstractDto;
import cn.herodotus.dante.module.upms.logic.entity.hr.SysEmployee;
import cn.herodotus.dante.module.upms.logic.entity.hr.SysOwnership;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.collections4.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>Description: 增加人员归属参数BO对象 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/9/25 10:24
 */
@Schema(description = "增加人员归属参数BO对象")
public class AllocatableDeploy extends AbstractDto {

    @NotNull(message = "单位ID不能为空")
    @Schema(description = "单位ID")
    private String organizationId;

    @NotNull(message = "部门ID不能为空")
    @Schema(description = "部门ID")
    private String departmentId;

    @Schema(description = "配置的人员列表")
    private List<SysEmployee> employees;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public List<SysEmployee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<SysEmployee> employees) {
        this.employees = employees;
    }

    public List<SysEmployee> getAllocatable() {
        if (CollectionUtils.isNotEmpty(this.employees)) {
            return employees.stream().peek(employee -> {
                SysDepartment sysDepartment = new SysDepartment();
                sysDepartment.setDepartmentId(this.departmentId);
                Set<SysDepartment> sysDepartments = employee.getDepartments();
                if (CollectionUtils.isEmpty(sysDepartments)) {
                    sysDepartments = new HashSet<>();
                }
                sysDepartments.add(sysDepartment);
                employee.setDepartments(sysDepartments);
            }).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    public List<SysOwnership> getOwnerships() {
        if (CollectionUtils.isNotEmpty(this.employees)) {
            return this.employees.stream().map(employee -> {
                SysOwnership sysOwnership = new SysOwnership();
                sysOwnership.setEmployeeId(employee.getEmployeeId());
                sysOwnership.setDepartmentId(this.departmentId);
                sysOwnership.setOrganizationId(this.organizationId);
                return sysOwnership;
            }).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("organizationId", organizationId)
                .add("departmentId", departmentId)
                .toString();
    }
}

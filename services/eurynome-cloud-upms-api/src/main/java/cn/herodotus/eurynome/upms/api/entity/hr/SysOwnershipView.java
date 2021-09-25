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
 * Module Name: eurynome-cloud-upms-api
 * File Name: SysOwnershipView.java
 * Author: gengwei.zheng
 * Date: 2021/09/25 10:37:25
 */

package cn.herodotus.eurynome.upms.api.entity.hr;

import cn.herodotus.eurynome.data.base.entity.BaseEntity;
import cn.herodotus.eurynome.upms.api.constants.UpmsConstants;
import cn.herodotus.eurynome.common.constant.enums.Identity;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

/**
 * <p>Description: 人事归属视图实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/15 14:08
 */
@Schema(title = "人事归属视图实体")
@Entity
@Immutable
@Table(name = "v_sys_ownership")
public class SysOwnershipView extends BaseEntity {

    @Schema(title = "归属ID")
    @Id
    @Column(name = "ownership_id")
    private String ownershipId;

    @Schema(title = "所属单位ID")
    @Column(name = "organization_id")
    private String organizationId;

    @Schema(title = "所属单位名称")
    @Column(name = "organization_name")
    private String organizationName;

    @Schema(title = "所属单位上级代码")
    @Column(name = "organization_parent_id")
    private String organizationParentId;

    @Schema(title = "所属部门ID")
    @Column(name = "department_id")
    private String departmentId;

    @Schema(title = "所属部门名称")
    @Column(name = "department_name")
    private String departmentName;

    @Schema(title = "所属部门上级代码")
    @Column(name = "department_parent_id")
    private String departmentParentId;

    @Schema(title = "人员ID")
    @Column(name = "employee_id")
    private String employeeId;

    @Schema(title = "人员姓名")
    @Column(name = "employee_name")
    private String employeeName;

    @Schema(title = "电子邮件")
    @Column(name = "email")
    private String email;

    @Schema(title = "PKI电子邮件")
    @Column(name = "pki_email")
    private String pkiEmail;

    @Schema(title = "手机号码")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Schema(title = "身份")
    @Column(name = "identity")
    @Enumerated(EnumType.ORDINAL)
    private Identity identity = Identity.STAFF;

    @Override
    public String getLinkedProperty() {
        return this.getDepartmentId();
    }

    @Override
    public String getId() {
        return this.getOwnershipId();
    }

    public String getOwnershipId() {
        return ownershipId;
    }

    public void setOwnershipId(String ownershipId) {
        this.ownershipId = ownershipId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationParentId() {
        return organizationParentId;
    }

    public void setOrganizationParentId(String organizationParentId) {
        this.organizationParentId = organizationParentId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentParentId() {
        return departmentParentId;
    }

    public void setDepartmentParentId(String departmentParentId) {
        this.departmentParentId = departmentParentId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPkiEmail() {
        return pkiEmail;
    }

    public void setPkiEmail(String pkiEmail) {
        this.pkiEmail = pkiEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("ownershipId", ownershipId)
                .add("organizationId", organizationId)
                .add("organizationName", organizationName)
                .add("organizationParentId", organizationParentId)
                .add("departmentId", departmentId)
                .add("departmentName", departmentName)
                .add("departmentParentId", departmentParentId)
                .add("employeeId", employeeId)
                .add("employeeName", employeeName)
                .add("email", email)
                .add("pkiEmail", pkiEmail)
                .add("phoneNumber", phoneNumber)
                .add("identity", identity)
                .toString();
    }
}

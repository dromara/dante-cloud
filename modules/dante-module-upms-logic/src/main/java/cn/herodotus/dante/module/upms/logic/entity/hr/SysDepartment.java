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

package cn.herodotus.dante.module.upms.logic.entity.hr;

import cn.herodotus.dante.module.upms.logic.constants.UpmsConstants;
import cn.herodotus.engine.data.core.entity.BaseSysEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <p>Description: 部门信息 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/19 16:40
 */
@Schema(title = "部门")
@Entity
@Table(name = "sys_department", indexes = {@Index(name = "sys_department_id_idx", columnList = "department_id")})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "departmentId")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_SYS_DEPARTMENT)
public class SysDepartment extends BaseSysEntity {

    @Schema(title = "部门ID")
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "department_id", length = 64)
    private String departmentId;

    @Schema(title = "部门名称")
    @Column(name = "department_name", length = 200)
    private String departmentName;

    @Schema(title = "4A标准部门ID")
    @Column(name = "a4_biz_dept_id", length = 64)
    private String a4BizDeptId;

    @Schema(title = "标准部门代码")
    @Column(name = "biz_dept_code", length = 60)
    private String bizDeptCode;

    @Schema(title = "标准部门说明")
    @Column(name = "biz_dept_desc", length = 256)
    private String bizDeptDesc;

    @Schema(title = "标准部门ID")
    @Column(name = "biz_dept_id", length = 64)
    private String bizDeptId;

    @Schema(title = "标准部门名称")
    @Column(name = "biz_dept_name", length = 200)
    private String bizDeptName;

    @Schema(title = "标准部门类型")
    @Column(name = "biz_dept_type", length = 30)
    private String bizDeptType;

    @Schema(title = "分区代码")
    @Column(name = "partition_code", length = 256)
    private String partitionCode;

    @Schema(title = "部门简称")
    @Column(name = "short_name", length = 200)
    private String shortName;

    @Schema(title = "上级部门ID")
    @Column(name = "parent_id", length = 64)
    private String parentId;

    @Schema(title = "所属单位ID")
    @Column(name = "organization_id", length = 64)
    private String organizationId;

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

    public String getA4BizDeptId() {
        return a4BizDeptId;
    }

    public void setA4BizDeptId(String a4BizDeptId) {
        this.a4BizDeptId = a4BizDeptId;
    }

    public String getBizDeptCode() {
        return bizDeptCode;
    }

    public void setBizDeptCode(String bizDeptCode) {
        this.bizDeptCode = bizDeptCode;
    }

    public String getBizDeptDesc() {
        return bizDeptDesc;
    }

    public void setBizDeptDesc(String bizDeptDesc) {
        this.bizDeptDesc = bizDeptDesc;
    }

    public String getBizDeptId() {
        return bizDeptId;
    }

    public void setBizDeptId(String bizDeptId) {
        this.bizDeptId = bizDeptId;
    }

    public String getBizDeptName() {
        return bizDeptName;
    }

    public void setBizDeptName(String bizDeptName) {
        this.bizDeptName = bizDeptName;
    }

    public String getBizDeptType() {
        return bizDeptType;
    }

    public void setBizDeptType(String bizDeptType) {
        this.bizDeptType = bizDeptType;
    }

    public String getPartitionCode() {
        return partitionCode;
    }

    public void setPartitionCode(String partitionCode) {
        this.partitionCode = partitionCode;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysDepartment that = (SysDepartment) o;
        return Objects.equal(departmentId, that.departmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(departmentId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("departmentId", departmentId)
                .add("departmentName", departmentName)
                .add("a4BizDeptId", a4BizDeptId)
                .add("bizDeptCode", bizDeptCode)
                .add("bizDeptDesc", bizDeptDesc)
                .add("bizDeptId", bizDeptId)
                .add("bizDeptName", bizDeptName)
                .add("bizDeptType", bizDeptType)
                .add("partitionCode", partitionCode)
                .add("shortName", shortName)
                .add("parentId", parentId)
                .add("organizationId", organizationId)
                .toString();
    }
}

/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
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
 * Eurynome Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Eurynome Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/eurynome-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/eurynome-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.eurynome.cmdb.logic.entity.asset;

import cn.herodotus.eurynome.cmdb.logic.base.BaseCmdbEntity;
import cn.herodotus.eurynome.cmdb.logic.constants.CmdbConstants;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: 应用系统 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/21 14:47
 */
@Schema(title = "服务器")
@Entity
@Table(name = "asset_application", uniqueConstraints = {@UniqueConstraint(columnNames = {"application_id"})},
        indexes = {@Index(name = "asset_application_id_idx", columnList = "application_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CmdbConstants.REGION_ASSET_APPLICATION)
public class AssetApplication extends BaseCmdbEntity {

    @Schema(name = "应用系统ID")
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "application_id", length = 64)
    private String applicationId;

    @Schema(name = "应用系统名称")
    @Column(name = "application_name", length = 200)
    private String applicationName;

    @Schema(name = "管理部门")
    @Column(name = "department", length = 200)
    private String department;

    @Schema(name = "管理负责人")
    @Column(name = "employee", length = 20)
    private String employee;

    @Schema(name = "等保定级")
    @Column(name = "protection_grade", length = 20)
    private String protectionGrade;

    @Schema(name = "等保备案编号")
    @Column(name = "protection_filing_no", length = 100)
    private String protectionFilingNo;

    @Schema(name = "建设单位")
    @Column(name = "construction_unit", length = 100)
    private String constructionUnit;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CmdbConstants.REGION_ASSET_SERVER)
    @Schema(title = "使用的服务器")
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "asset_application_server",
            joinColumns = {@JoinColumn(name = "application_id")},
            inverseJoinColumns = {@JoinColumn(name = "server_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"application_id", "server_id"})},
            indexes = {@Index(name = "asset_application_server_aid_idx", columnList = "application_id"), @Index(name = "asset_application_server_sid_idx", columnList = "server_id")})
    private Set<AssetServer> servers = new HashSet<>();

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getProtectionGrade() {
        return protectionGrade;
    }

    public void setProtectionGrade(String protectionGrade) {
        this.protectionGrade = protectionGrade;
    }

    public String getProtectionFilingNo() {
        return protectionFilingNo;
    }

    public void setProtectionFilingNo(String protectionFilingNo) {
        this.protectionFilingNo = protectionFilingNo;
    }

    public String getConstructionUnit() {
        return constructionUnit;
    }

    public void setConstructionUnit(String constructionUnit) {
        this.constructionUnit = constructionUnit;
    }

    public Set<AssetServer> getServers() {
        return servers;
    }

    public void setServers(Set<AssetServer> servers) {
        this.servers = servers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AssetApplication that = (AssetApplication) o;
        return Objects.equal(applicationId, that.applicationId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(applicationId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("applicationId", applicationId)
                .add("applicationName", applicationName)
                .add("department", department)
                .add("employee", employee)
                .add("protectionGrade", protectionGrade)
                .add("protectionFilingNo", protectionFilingNo)
                .add("constructionUnit", constructionUnit)
                .toString();
    }
}

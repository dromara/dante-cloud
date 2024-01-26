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

package cn.herodotus.dante.bpmn.logic.entity;

import cn.herodotus.dante.bpmn.logic.domain.base.BaseEntity;
import cn.herodotus.dante.bpmn.logic.generator.ActIdTenantMemberUuidGenerator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

/**
 * <p>Description: Camunda 租户成员</p>
 * <p>
 * 集成进入微服务中，目前考虑该表与SysOwnership对应。使用Debezium进行数据同步。
 *
 * @author : gengwei.zheng
 * @date : 2021/7/20 13:08
 */
@Schema(name = "Camunda 人员归属关系")
@Entity
@Table(name = "act_id_tenant_member", indexes = {
        @Index(name = "act_id_tenant_member_id_idx", columnList = "id_"),
        @Index(name = "act_id_tenant_member_tid_idx", columnList = "tenant_id_"),
        @Index(name = "act_id_tenant_member_gid_idx", columnList = "group_id_"),
        @Index(name = "act_id_tenant_member_eid_idx", columnList = "user_id_")
})
public class ActIdTenantMember extends BaseEntity {

    @JsonProperty("ownership_id")
    @Schema(title =  "人员ID")
    @Id
    @ActIdTenantMemberUuidGenerator
    @Column(name = "id_", length = 64)
    private String id;

    @JsonProperty("organization_id")
    @Column(name = "tenant_id_", length = 64)
    private String tenantId;

    @JsonProperty("department_id")
    @Column(name = "group_id_", length = 64)
    private String groupId;

    @JsonProperty("employee_id")
    @Column(name = "user_id_", length = 64)
    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("tenantId", tenantId)
                .add("groupId", groupId)
                .add("userId", userId)
                .toString();
    }
}

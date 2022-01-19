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
 * Module Name: eurynome-cloud-bpmn-rest
 * File Name: ActIdTenantMember.java
 * Author: gengwei.zheng
 * Date: 2021/07/20 19:12:20
 */

package cn.herodotus.eurynome.bpmn.rest.entity;

import cn.herodotus.eurynome.bpmn.rest.domain.base.BaseEntity;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

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

    @JSONField(name = "ownership_id")
    @JsonProperty("ownership_id")
    @Schema(title =  "人员ID")
    @Id
    @GeneratedValue(generator = "act-tenant-member-uuid")
    @GenericGenerator(name = "act-tenant-member-uuid", strategy = "cn.herodotus.eurynome.bpmn.rest.generator.ActIdTenantMemberUUIDGenerator")
    @Column(name = "id_", length = 64)
    private String id;

    @JSONField(name = "organization_id")
    @JsonProperty("organization_id")
    @Column(name = "tenant_id_", length = 64)
    private String tenantId;

    @JSONField(name = "department_id")
    @JsonProperty("department_id")
    @Column(name = "group_id_", length = 64)
    private String groupId;

    @JSONField(name = "employee_id")
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

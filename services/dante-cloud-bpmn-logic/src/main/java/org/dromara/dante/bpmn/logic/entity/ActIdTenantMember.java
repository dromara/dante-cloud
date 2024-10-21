/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Dante Cloud.
 *
 * Dante Cloud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dante Cloud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.cn>.
 */

package org.dromara.dante.bpmn.logic.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.dromara.dante.bpmn.logic.domain.base.BaseEntity;
import org.dromara.dante.bpmn.logic.generator.ActIdTenantMemberUuidGenerator;

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
    @Schema(title = "人员ID")
    @Id
    @ActIdTenantMemberUuidGenerator
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

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
import com.google.common.base.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.dromara.dante.bpmn.logic.domain.base.BaseEntity;

/**
 * <p>Description: 工作流人员和组关系 </p>
 * <p>
 * 集成进入微服务中，目前考虑该表与SysEmployeeDepartment对应。使用Debezium进行数据同步。
 *
 * @author : gengwei.zheng
 * @date : 2021/7/20 13:04
 */
@Schema(name = "Camunda 人员和组关系")
@Entity
@Table(name = "act_id_membership", indexes = {
        @Index(name = "act_id_membership_uid_idx", columnList = "user_id_"),
        @Index(name = "act_id_membership_gid_idx", columnList = "group_id_")
})
public class ActIdMembership extends BaseEntity {

    @Id
    @JSONField(name = "employee_id")
    @JsonProperty("employee_id")
    @Column(name = "user_id_")
    private String userId;

    @Id
    @JSONField(name = "department_id")
    @JsonProperty("department_id")
    @Column(name = "group_id_")
    private String groupId;

    public ActIdMembership() {
    }

    public ActIdMembership(String userId, String groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getId() {
        return this.getUserId() + this.getGroupId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ActIdMembership that = (ActIdMembership) o;
        return Objects.equal(userId, that.userId) && Objects.equal(groupId, that.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId, groupId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("groupId", groupId)
                .toString();
    }
}

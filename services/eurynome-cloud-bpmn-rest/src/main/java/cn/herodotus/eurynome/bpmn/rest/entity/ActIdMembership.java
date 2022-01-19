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
 * File Name: ActIdMembership.java
 * Author: gengwei.zheng
 * Date: 2021/07/20 19:07:20
 */

package cn.herodotus.eurynome.bpmn.rest.entity;

import cn.herodotus.eurynome.bpmn.rest.domain.base.BaseEntity;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

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

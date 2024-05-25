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

package cn.herodotus.dante.bpmn.logic.entity;

import cn.herodotus.dante.bpmn.logic.domain.base.BaseEntity;
import cn.herodotus.dante.bpmn.logic.generator.ActIdGroupUuidGenerator;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

/**
 * <p>Description: 工作流组表 </p>
 * <p>
 * 集成进入微服务中，目前考虑该表与SysDepartment对应。使用Debezium进行数据同步。
 *
 * @author : gengwei.zheng
 * @date : 2021/7/20 12:55
 */
@Schema(name = "Camunda 组")
@Entity
@Table(name = "act_id_group", indexes = {@Index(name = "act_id_group_id_idx", columnList = "id_")})
public class ActIdGroup extends BaseEntity {

    @JSONField(name = "department_id")
    @JsonProperty("department_id")
    @Schema(title =  "部门ID")
    @Id
    @ActIdGroupUuidGenerator
    @Column(name = "id_", length = 64)
    private String id;

    @Schema(title =  "修订")
    @JSONField(name = "revision")
    @JsonProperty("revision")
    @Column(name = "rev_")
    private Integer revision;

    @Schema(title =  "名称")
    @JSONField(name = "department_name")
    @JsonProperty("department_name")
    @Column(name = "name_")
    private String name;

    @Schema(title =  "类型")
    @JSONField(name = "organization_id")
    @JsonProperty("organization_id")
    @Column(name = "type_")
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("revision", revision)
                .add("name", name)
                .add("type", type)
                .toString();
    }
}

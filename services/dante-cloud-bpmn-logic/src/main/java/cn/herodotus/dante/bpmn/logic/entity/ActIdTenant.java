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
import cn.herodotus.dante.bpmn.logic.generator.ActIdTenantUuidGenerator;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

/**
 * <p>Description: Camunda租户 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/20 13:00
 */
@Schema(name = "Camunda租户")
@Entity
@Table(name = "act_id_tenant", indexes = {@Index(name = "act_id_tenant_id_idx", columnList = "id_")})
public class ActIdTenant extends BaseEntity {

    @JSONField(name = "organization_id")
    @JsonProperty("organization_id")
    @Schema(title =  "租户ID")
    @Id
    @ActIdTenantUuidGenerator
    @Column(name = "id_", length = 64)
    private String id;

    @JSONField(name = "revision")
    @JsonProperty("revision")
    @Column(name = "rev_")
    private Integer revision;

    @JSONField(name = "organization_name")
    @JsonProperty("organization_name")
    @Column(name = "name_")
    private String name;

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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("revision", revision)
                .add("name", name)
                .toString();
    }
}

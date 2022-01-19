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
 * File Name: ActIdTenant.java
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
    @GeneratedValue(generator = "act-tenant-uuid")
    @GenericGenerator(name = "act-tenant-uuid", strategy = "cn.herodotus.eurynome.bpmn.rest.generator.ActIdTenantUUIDGenerator")
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

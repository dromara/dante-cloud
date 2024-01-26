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
import cn.herodotus.dante.bpmn.logic.generator.ActIdTenantUuidGenerator;
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

    @JsonProperty("organization_id")
    @Schema(title =  "租户ID")
    @Id
    @ActIdTenantUuidGenerator
    @Column(name = "id_", length = 64)
    private String id;

    @JsonProperty("revision")
    @Column(name = "rev_")
    private Integer revision;

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

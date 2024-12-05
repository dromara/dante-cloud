/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Cloud 采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package org.dromara.dante.bpmn.logic.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.dromara.dante.bpmn.logic.domain.base.BaseEntity;
import org.dromara.dante.bpmn.logic.generator.ActIdGroupIdGenerator;

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
    @Schema(title = "部门ID")
    @Id
    @ActIdGroupIdGenerator
    @Column(name = "id_", length = 64)
    private String id;

    @Schema(title = "修订")
    @JSONField(name = "revision")
    @JsonProperty("revision")
    @Column(name = "rev_")
    private Integer revision;

    @Schema(title = "名称")
    @JSONField(name = "department_name")
    @JsonProperty("department_name")
    @Column(name = "name_")
    private String name;

    @Schema(title = "类型")
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

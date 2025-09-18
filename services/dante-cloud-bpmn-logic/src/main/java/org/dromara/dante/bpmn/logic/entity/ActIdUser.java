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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.dromara.dante.bpmn.logic.domain.base.BaseBpmnEntity;
import org.dromara.dante.bpmn.logic.generator.ActIdUserIdGenerator;

import java.util.Date;

/**
 * <p>Description: 工作流用户表 </p>
 * <p>
 * 集成进入微服务中，目前考虑该表与SysEmployee对应。使用Debezium进行数据同步。
 *
 * @author : gengwei.zheng
 * @date : 2021/7/20 12:49
 */
@Schema(name = "Camunda用户")
@Entity
@Table(name = "act_id_user", indexes = {@Index(name = "act_id_user_id_idx", columnList = "id_")})
public class ActIdUser extends BaseBpmnEntity {

    @JsonProperty("employee_id")
    @Schema(title = "人员ID")
    @Id
    @ActIdUserIdGenerator
    @Column(name = "id_", length = 64)
    private String id;

    @Schema(title = "修订")
    @JsonProperty("reversion")
    @Column(name = "rev_")
    private Integer revision = 0;

    @Schema(title = "姓")
    @JsonProperty("employee_name")
    @Column(name = "first_")
    private String firstName;

    @Schema(title = "名")
    @JsonProperty("mobile_phone_number")
    @Column(name = "last_")
    private String lastName;

    @Schema(title = "电子邮箱")
    @JsonProperty("email")
    @Column(name = "email_")
    private String email;

    @Schema(title = "密码")
    @JsonProperty("password")
    @Column(name = "pwd_")
    private String password;

    @Schema(title = "密码盐")
    @JsonProperty("salt")
    @Column(name = "salt_")
    private String salt;

    @Schema(title = "锁定过期时间")
    @JsonProperty("lock_expiration_time")
    @Column(name = "lock_exp_time_")
    private Date LockExpirationTime;

    @JsonProperty("picture_id")
    @Column(name = "picture_id_")
    private String pictureId;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getLockExpirationTime() {
        return LockExpirationTime;
    }

    public void setLockExpirationTime(Date lockExpirationTime) {
        LockExpirationTime = lockExpirationTime;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("revision", revision)
                .add("firstName", firstName)
                .add("lastName", lastName)
                .add("email", email)
                .add("password", password)
                .add("salt", salt)
                .add("LockExpirationTime", LockExpirationTime)
                .add("pictureId", pictureId)
                .toString();
    }
}

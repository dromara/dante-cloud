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
import cn.herodotus.dante.bpmn.logic.generator.ActIdUserUuidGenerator;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

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
public class ActIdUser extends BaseEntity {

    @JSONField(name = "employee_id")
    @JsonProperty("employee_id")
    @Schema(title =  "人员ID")
    @Id
    @ActIdUserUuidGenerator
    @Column(name = "id_", length = 64)
    private String id;

    @Schema(title =  "修订")
    @JSONField(name = "reversion")
    @JsonProperty("reversion")
    @Column(name = "rev_")
    private Integer revision = 0;

    @Schema(title =  "姓")
    @JSONField(name = "employee_name")
    @JsonProperty("employee_name")
    @Column(name = "first_")
    private String firstName;

    @Schema(title =  "名")
    @JSONField(name = "mobile_phone_number")
    @JsonProperty("mobile_phone_number")
    @Column(name = "last_")
    private String lastName;

    @Schema(title =  "电子邮箱")
    @JSONField(name = "email")
    @JsonProperty("email")
    @Column(name = "email_")
    private String email;

    @Schema(title =  "密码")
    @JSONField(name = "password")
    @JsonProperty("password")
    @Column(name = "pwd_")
    private String password;

    @Schema(title =  "密码盐")
    @JSONField(name = "salt")
    @JsonProperty("salt")
    @Column(name = "salt_")
    private String salt;

    @Schema(title =  "锁定过期时间")
    @JSONField(name = "lock_expiration_time")
    @JsonProperty("lock_expiration_time")
    @Column(name = "lock_exp_time_")
    private Date LockExpirationTime;

    @JSONField(name = "picture_id")
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

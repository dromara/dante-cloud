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
 * Module Name: eurynome-cloud-upms-logic
 * File Name: OauthApproval.java
 * Author: gengwei.zheng
 * Date: 2021/09/26 19:23:26
 */

package cn.herodotus.eurynome.upms.logic.entity.oauth;

import com.google.common.base.MoreObjects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

/**
 * <p>Description: OauthApproval </p>
 * <p>
 * 该实体只用于初始化 OAuth2 相关业务表使用，具体数据库操作由OAuth2 使用内置的原生SQL完成。
 * <p>
 * 实际业务中一般用不到，所以使用最小化配置。如果实际业务中要使用该实体需要完善相关配置，并测试验证OAuth2是否可以正常运行。
 *
 * @author : gengwei.zheng
 * @date : 2021/9/19 13:58
 */
@Entity
@Table(name = "oauth_approvals")
public class OauthApproval {

    /**
     * 添加一个空的id标识，因为jpa在映射实体是需要一个id，这个必须。原始的OAuth2脚本中，该表是无主键的表
     */
    @Id

    @Column(name = "userId", length = 256)
    private String userId;

    @Column(name = "clientId", length = 256)
    private String clientId;

    @Column(name = "scope", length = 256)
    private String scope;

    @Column(name = "status", length = 10)
    private String status;

    @Column(name = "expiresAt")
    private Instant expiresAt;

    @Column(name = "lastModifiedAt")
    private Instant lastModifiedAt;

    public Instant getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(Instant lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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
                .add("userId", userId)
                .add("clientId", clientId)
                .add("scope", scope)
                .add("status", status)
                .add("expiresAt", expiresAt)
                .add("lastModifiedAt", lastModifiedAt)
                .toString();
    }
}
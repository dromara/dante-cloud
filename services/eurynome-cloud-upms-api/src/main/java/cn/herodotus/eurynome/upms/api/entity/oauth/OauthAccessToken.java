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
 * Module Name: eurynome-cloud-upms-api
 * File Name: OauthAccessToken.java
 * Author: gengwei.zheng
 * Date: 2021/09/19 13:43:19
 */

package cn.herodotus.eurynome.upms.api.entity.oauth;

import com.google.common.base.MoreObjects;

import javax.persistence.*;

/**
 * <p>Description: OauthAccessToken </p>
 * <p>
 * 该实体只用于初始化 OAuth2 相关业务表使用，具体数据库操作由OAuth2 使用内置的原生SQL完成。
 * <p>
 * 实际业务中一般用不到，所以使用最小化配置。如果实际业务中要使用该实体需要完善相关配置，并测试验证OAuth2是否可以正常运行。
 *
 * @author : gengwei.zheng
 * @date : 2021/9/19 13:37
 */
@Entity
@Table(name = "oauth_access_token", indexes = {@Index(name = "oauth_access_token_id_idx", columnList = "authentication_id")})
public class OauthAccessToken {

    @Id
    @Column(name = "authentication_id", nullable = false, length = 256)
    private String id;

    @Column(name = "token_id", nullable = false, length = 256)
    private String tokenId;

    @Column(name = "token")
    private byte[] token;

    @Column(name = "user_name", length = 256)
    private String userName;

    @Column(name = "client_id", length = 256)
    private String clientId;

    @Column(name = "authentication")
    private byte[] authentication;

    @Column(name = "refresh_token", length = 256)
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public byte[] getToken() {
        return token;
    }

    public void setToken(byte[] token) {
        this.token = token;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("tokenId", tokenId)
                .add("userName", userName)
                .add("clientId", clientId)
                .add("refreshToken", refreshToken)
                .toString();
    }
}
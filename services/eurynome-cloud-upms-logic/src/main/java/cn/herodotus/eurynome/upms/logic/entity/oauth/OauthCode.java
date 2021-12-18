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
 * File Name: OauthCode.java
 * Author: gengwei.zheng
 * Date: 2021/09/26 19:23:26
 */

package cn.herodotus.eurynome.upms.logic.entity.oauth;

import com.google.common.base.MoreObjects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>Description: OauthCode </p>
 * <p>
 * 该实体只用于初始化 OAuth2 相关业务表使用，具体数据库操作由OAuth2 使用内置的原生SQL完成。
 * <p>
 * 实际业务中一般用不到，所以使用最小化配置。如果实际业务中要使用该实体需要完善相关配置，并测试验证OAuth2是否可以正常运行。
 *
 * @author : gengwei.zheng
 * @date : 2021/9/19 14:01
 */
@Entity
@Table(name = "oauth_code")
public class OauthCode {

    /**
     * 添加一个空的id标识，因为jpa在映射实体是需要一个id，这个必须。原始的OAuth2脚本中，该表是无主键的表
     */
    @Id

    @Column(name = "code", length = 256)
    private String code;

    @Column(name = "authentication")
    private byte[] authentication;

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("code", code)
                .toString();
    }
}
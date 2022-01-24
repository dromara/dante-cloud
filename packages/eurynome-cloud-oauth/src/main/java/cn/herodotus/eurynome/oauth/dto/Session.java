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
 * Module Name: eurynome-cloud-oauth
 * File Name: Session.java
 * Author: gengwei.zheng
 * Date: 2021/10/08 19:29:08
 */

package cn.herodotus.eurynome.oauth.dto;

import cn.herodotus.engine.assistant.core.domain.dto.BaseDto;
import com.google.common.base.MoreObjects;

/**
 * <p>Description: Session响应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/8 19:29
 */
public class Session extends BaseDto {

    /**
     * 前端未登录时，唯一身份标识。如果由前端生成，则直接返回；如果由后端生成，则返回后端生成值
     */
    private String sessionId;

    /**
     * 后台RSA公钥
     */
    private String publicKey;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("sessionId", sessionId)
                .add("publicKey", publicKey)
                .toString();
    }
}

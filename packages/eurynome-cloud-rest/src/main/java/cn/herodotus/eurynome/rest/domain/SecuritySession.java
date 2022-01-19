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
 * Module Name: eurynome-cloud-data
 * File Name: SecuritySession.java
 * Author: gengwei.zheng
 * Date: 2021/10/08 19:09:08
 */

package cn.herodotus.eurynome.rest.domain;

import cn.herodotus.eurynome.assistant.definition.dto.BaseDTO;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>Description: 前后台加密响应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/8 19:09
 */
@Schema(description = "前后台加密响应实体")
public class SecuritySession extends BaseDTO {

    /**
     * 在未登录情况下，前端可辨识的唯一标识
     */
    private String sessionKey;

    /**
     * 后台生成的RSA秘钥
     */
    private String passport;

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("sessionKey", sessionKey)
                .add("passport", passport)
                .toString();
    }
}

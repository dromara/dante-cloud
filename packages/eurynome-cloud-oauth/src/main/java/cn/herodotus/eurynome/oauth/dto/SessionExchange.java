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
 * File Name: SessionExchange.java
 * Author: gengwei.zheng
 * Date: 2021/10/08 19:31:08
 */

package cn.herodotus.eurynome.oauth.dto;

import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

/**
 * <p>Description: 机要传递实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/8 19:31
 */
@Schema(title = "机要传递实体")
public class SessionExchange {

    @NotBlank(message = "confidential参数不能为空")
    @Schema(title = "用后端RSA PublicKey加密的前端RSA PublicKey")
    private String confidential;

    @NotBlank(message = "Session Key不能为空")
    @Schema(title = "未登录前端身份标识")
    private String sessionId;

    public String getConfidential() {
        return confidential;
    }

    public void setConfidential(String confidential) {
        this.confidential = confidential;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("confidential", confidential)
                .add("sessionId", sessionId)
                .toString();
    }
}

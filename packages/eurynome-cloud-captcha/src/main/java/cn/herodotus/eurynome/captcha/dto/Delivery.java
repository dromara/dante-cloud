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
 * Module Name: eurynome-cloud-captcha
 * File Name: Delivery.java
 * Author: gengwei.zheng
 * Date: 2021/12/17 21:32:17
 */

package cn.herodotus.eurynome.captcha.dto;

import cn.herodotus.eurynome.assistant.definition.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>Description: 验证码数据传输 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/17 21:32
 */
public abstract class Delivery extends BaseDTO {

    @Schema(title = "验证码身份")
    private String identity;

    @Schema(title = "验证码类别")
    private String category;

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

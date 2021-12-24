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
 * File Name: Renderer.java
 * Author: gengwei.zheng
 * Date: 2021/12/24 09:14:24
 */

package cn.herodotus.eurynome.captcha.definition;

import cn.herodotus.eurynome.captcha.definition.domain.Metadata;
import cn.herodotus.eurynome.captcha.dto.Captcha;
import cn.herodotus.eurynome.captcha.dto.Verification;

/**
 * <p>Description: 基础绘制器定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/24 9:14
 */
public interface Renderer {

    /**
     * 验证码绘制
     *
     * @return 绘制的验证码和校验信息 {@link Metadata}
     */
    Metadata draw();

    /**
     * 创建验证码
     *
     * @param key 验证码标识，用于标记在缓存中的存储
     * @return 验证码数据 {@link Captcha}
     */
    Captcha getCapcha(String key);

    /**
     * 验证码校验
     *
     * @param verification 前端传入的验证值
     * @return true 验证成功，返回错误信息
     */
    boolean verify(Verification verification);

    /**
     * 获取验证码类别
     *
     * @return 验证码类别
     */
    String getCategory();
}

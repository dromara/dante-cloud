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
 * File Name: CaptchaResource.java
 * Author: gengwei.zheng
 * Date: 2021/12/17 21:36:17
 */

package cn.herodotus.eurynome.captcha.enums;

/**
 * <p>Description: 验证码资源 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/17 21:36
 */
public enum CaptchaResource {

    /**
     * 验证码资源类型
     */
    JIGSAW_ORIGINAL("Jigsaw original image","滑动拼图底图"),
    JIGSAW_TEMPLATE("Jigsaw template image","滑动拼图滑块底图"),
    WORD_CLICK("Word click image","文字点选底图");

    private final String content;
    private final String description;

    CaptchaResource(String type, String description) {
        this.content = type;
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public String getDescription() {
        return description;
    }
}

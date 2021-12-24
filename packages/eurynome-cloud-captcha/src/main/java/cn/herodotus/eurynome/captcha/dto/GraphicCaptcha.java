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
 * File Name: GraphicCaptcha.java
 * Author: gengwei.zheng
 * Date: 2021/12/24 21:09:24
 */

package cn.herodotus.eurynome.captcha.dto;

/**
 * <p>Description: 图形验证码 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/24 21:09
 */
public class GraphicCaptcha extends Captcha {

    /**
     * 图形验证码成的图。
     */
    private String graphicImageBase64;


    public String getGraphicImageBase64() {
        return graphicImageBase64;
    }

    public void setGraphicImageBase64(String graphicImageBase64) {
        this.graphicImageBase64 = graphicImageBase64;
    }
}

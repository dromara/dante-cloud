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
 * File Name: GifCaptchaRenderer.java
 * Author: gengwei.zheng
 * Date: 2021/12/24 21:08:24
 */

package cn.herodotus.eurynome.captcha.renderer.hutool;

import cn.herodotus.eurynome.captcha.definition.AbstractGraphicRenderer;
import cn.herodotus.eurynome.captcha.definition.domain.Metadata;
import cn.herodotus.eurynome.captcha.definition.enums.CaptchaCategory;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.GifCaptcha;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/24 21:08
 */
public class GifCaptchaRenderer extends AbstractGraphicRenderer {

    @Override
    public Metadata draw() {
        GifCaptcha gifCaptcha = CaptchaUtil.createGifCaptcha(this.getWidth(), this.getHeight(), this.getLength());
        gifCaptcha.setFont(this.getFont());

        Metadata metadata = new Metadata();
        metadata.setGraphicImageBase64(gifCaptcha.getImageBase64Data());
        metadata.setCharacters(gifCaptcha.getCode());
        return metadata;
    }

    @Override
    public String getCategory() {
        return CaptchaCategory.HUTOOL_GIF.getConstant();
    }
}

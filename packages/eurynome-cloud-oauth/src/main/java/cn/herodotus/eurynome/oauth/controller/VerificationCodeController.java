/*
 * Copyright (c) 2019-2021 Gengwei Zheng(herodotus@aliyun.com)
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
 * File Name: VerificationCodeController.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.oauth.controller;

import cn.herodotus.eurynome.constant.enums.captcha.CaptchaType;
import cn.herodotus.eurynome.security.properties.SecurityProperties;
import com.wf.captcha.*;
import com.wf.captcha.base.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/29 11:34
 */
@Controller
public class VerificationCodeController {

    @Autowired
    private SecurityProperties securityProperties;

    @RequestMapping("/defaultKaptcha")
    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception{

        // 设置请求头为输出图片类型
        httpServletResponse.setContentType("image/gif");
        httpServletResponse.setHeader("Pragma", "No-cache");
        httpServletResponse.setHeader("Cache-Control", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);

        Captcha captcha = createCaptcha();
        // 验证码存入session
        httpServletRequest.getSession().setAttribute(securityProperties.getVerificationCode().getSessionAttribute(), captcha.text().toLowerCase());
        // 输出图片流
        captcha.out(httpServletResponse.getOutputStream());
    }

    private Captcha createCaptcha() throws IOException, FontFormatException {
        Captcha captcha;

        CaptchaType captchaType = securityProperties.getVerificationCode().getCaptchaType();
        switch (captchaType) {
            case LETTERS_GIF:
                captcha = new GifCaptcha(securityProperties.getVerificationCode().getWidth(), securityProperties.getVerificationCode().getHeight(), securityProperties.getVerificationCode().getLength());
                captcha.setCharType(securityProperties.getVerificationCode().getCaptchaLetterType().getType());
                break;
            case CHINESE:
                captcha = new ChineseCaptcha(securityProperties.getVerificationCode().getWidth(), securityProperties.getVerificationCode().getHeight(), securityProperties.getVerificationCode().getLength());
                break;
            case CHINESE_GIF:
                captcha = new ChineseGifCaptcha(securityProperties.getVerificationCode().getWidth(), securityProperties.getVerificationCode().getHeight(), securityProperties.getVerificationCode().getLength());
                break;
            case ARITHMETIC:
                captcha = new ArithmeticCaptcha(securityProperties.getVerificationCode().getWidth(), securityProperties.getVerificationCode().getHeight(), securityProperties.getVerificationCode().getLength());
                break;
            default:
                captcha = new SpecCaptcha(securityProperties.getVerificationCode().getWidth(), securityProperties.getVerificationCode().getHeight(), securityProperties.getVerificationCode().getLength());
                captcha.setCharType(securityProperties.getVerificationCode().getCaptchaLetterType().getType());
                break;
        }

        captcha.setFont(securityProperties.getVerificationCode().getCaptchaFont().getIndex());
        return captcha;
    }
}

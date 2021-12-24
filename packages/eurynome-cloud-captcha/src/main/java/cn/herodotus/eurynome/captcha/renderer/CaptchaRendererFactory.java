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
 * File Name: CaptchaRendererFactory.java
 * Author: gengwei.zheng
 * Date: 2021/12/17 21:43:17
 */

package cn.herodotus.eurynome.captcha.renderer;

import cn.herodotus.eurynome.captcha.dto.Captcha;
import cn.herodotus.eurynome.captcha.dto.Verification;
import cn.herodotus.eurynome.captcha.definition.enums.CaptchaCategory;
import cn.herodotus.eurynome.captcha.exception.CaptchaCategoryIsIncorrectException;
import cn.herodotus.eurynome.captcha.exception.CaptchaHandlerNotExistException;
import cn.herodotus.eurynome.captcha.handler.CaptchaHandler;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Description: Captcha 工厂 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/17 21:43
 */
@Component
public class CaptchaRendererFactory {

    @Autowired
    private final Map<String, CaptchaHandler> handlers = new ConcurrentHashMap<>(8);

    public CaptchaHandler getCaptchaHandler(String category) {
        CaptchaCategory captchaCategory = CaptchaCategory.getCaptchaCategory(category);

        if (ObjectUtils.isEmpty(captchaCategory)) {
            throw new CaptchaCategoryIsIncorrectException("Captcha category is incorrect.");
        }

        CaptchaHandler captchaHandler = handlers.get(captchaCategory.getConstant());
        if (ObjectUtils.isEmpty(captchaHandler)) {
            throw new CaptchaHandlerNotExistException();
        }

        return captchaHandler;
    }

    public Captcha getCaptcha(String identity, String category) {
        CaptchaHandler captchaHandler = getCaptchaHandler(category);
        return captchaHandler.getCapcha(identity);
    }

    public boolean verify(Verification verification) {
        CaptchaHandler captchaHandler = getCaptchaHandler(verification.getCategory());
        return captchaHandler.verify(verification);
    }
}

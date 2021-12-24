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
 * File Name: AbstractGraphicRenderer.java
 * Author: gengwei.zheng
 * Date: 2021/12/24 21:09:24
 */

package cn.herodotus.eurynome.captcha.definition;

import cn.herodotus.eurynome.cache.constant.CacheConstants;
import cn.herodotus.eurynome.captcha.definition.domain.Metadata;
import cn.herodotus.eurynome.captcha.dto.Captcha;
import cn.herodotus.eurynome.captcha.dto.GraphicCaptcha;
import cn.herodotus.eurynome.captcha.dto.Verification;
import cn.herodotus.eurynome.captcha.exception.CaptchaHasExpiredException;
import cn.herodotus.eurynome.captcha.exception.CaptchaIsEmptyException;
import cn.herodotus.eurynome.captcha.exception.CaptchaMismatchException;
import cn.herodotus.eurynome.captcha.exception.CaptchaParameterIllegalException;
import cn.hutool.core.util.IdUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;

/**
 * <p>Description: 抽象的图形验证码 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/24 21:09
 */
public abstract class AbstractGraphicRenderer extends AbstractRenderer<String, String> {

    @CreateCache(name = CacheConstants.CACHE_NAME_CAPTCHA_GRAPHIC, cacheType = CacheType.BOTH)
    protected Cache<String, String> cache;

    @Override
    protected Cache<String, String> getCache() {
        return this.cache;
    }

    private GraphicCaptcha graphicCaptcha;

    protected Font getFont() {
        return this.getResourceProvider().getGraphicFont();
    }

    protected int getWidth() {
        return this.getCaptchaProperties().getGraphics().getWidth();
    }

    protected int getHeight() {
        return this.getCaptchaProperties().getGraphics().getHeight();
    }

    protected int getLength() {
        return this.getCaptchaProperties().getGraphics().getLength();
    }

    @Override
    public Captcha getCapcha(String key) {
        String identity = key;
        if (StringUtils.isBlank(identity)) {
            identity = IdUtil.fastUUID();
        }

        this.create(identity);
        return getGraphicCaptcha();
    }

    @Override
    public boolean verify(Verification verification) {

        if (ObjectUtils.isEmpty(verification) || StringUtils.isEmpty(verification.getIdentity())) {
            throw new CaptchaParameterIllegalException("Parameter value is illegal");
        }

        if (StringUtils.isEmpty(verification.getCharacters())) {
            throw new CaptchaIsEmptyException("Captcha is empty");
        }

        String store = this.get(verification.getIdentity());
        if (StringUtils.isEmpty(store)) {
            throw new CaptchaHasExpiredException("Stamp is invalid!");
        }

        this.delete(verification.getIdentity());

        String real = verification.getCharacters();

        if (!StringUtils.equalsIgnoreCase(store, real)) {
            throw new CaptchaMismatchException();
        }

        return true;
    }

    private GraphicCaptcha getGraphicCaptcha() {
        return graphicCaptcha;
    }

    protected void setGraphicCaptcha(GraphicCaptcha graphicCaptcha) {
        this.graphicCaptcha = graphicCaptcha;
    }

    @Override
    public String nextStamp(String key) {
        Metadata metadata = draw();

        GraphicCaptcha graphicCaptcha = new GraphicCaptcha();
        graphicCaptcha.setIdentity(key);
        graphicCaptcha.setGraphicImageBase64(metadata.getGraphicImageBase64());
        graphicCaptcha.setCategory(getCategory());
        this.setGraphicCaptcha(graphicCaptcha);

        return metadata.getCharacters();
    }
}

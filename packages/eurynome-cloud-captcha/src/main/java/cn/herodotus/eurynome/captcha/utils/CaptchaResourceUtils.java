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
 * File Name: CaptchaResourceUtils.java
 * Author: gengwei.zheng
 * Date: 2021/12/17 21:19:17
 */

package cn.herodotus.eurynome.captcha.utils;

import cn.herodotus.eurynome.assistant.constant.SymbolConstants;
import cn.herodotus.eurynome.assistant.utils.ResourceUtils;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.img.FontUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.IdUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Description: 验证码资源工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/17 21:19
 */
public class CaptchaResourceUtils {

    private static final Logger log = LoggerFactory.getLogger(CaptchaResourceUtils.class);

    public static String getBase64Image(Resource resource) {
        try {
            InputStream inputStream = resource.getInputStream();
            byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
            return Base64.encode(bytes);
        } catch (IOException e) {
            log.error("[Herodotus] |- Captcha get image catch io error!", e);
        }
        return null;
    }

    public static Map<String, String> getImages(String location) {
        if (ResourceUtils.isClasspathAllUrl(location)) {
            try {
                Resource[] resources = ResourceUtils.getResources(location);
                Map<String, String> images = new ConcurrentHashMap<>();
                if (ArrayUtils.isNotEmpty(resources)) {
                    Arrays.stream(resources).forEach(resource -> {
                        String data = getBase64Image(resource);
                        if (StringUtils.isNotBlank(data)) {
                            images.put(IdUtil.fastSimpleUUID(), data);
                        }
                    });
                }
                return images;
            } catch (IOException e) {
                log.error("[Herodotus] |- Analysis the  location [{}] catch io error!", location, e);
            }
        }

        return new ConcurrentHashMap<>(8);
    }

    private static boolean isTrueTypeFont(String fontName) {
        return StringUtils.endsWithIgnoreCase(fontName, ".ttf") || StringUtils.endsWithIgnoreCase(fontName, ".ttc") || StringUtils.endsWithIgnoreCase(fontName, ".otf");
    }

    public static Font getFont(String fontName, int fontSize, int fontStyle, String fontPath) {
        String fontResouce = getFontResource(fontPath, fontName);
        if (ResourceUtils.isClasspathAllUrl(fontResouce)) {
            try {
                Resource[] resources = ResourceUtils.getResources(fontResouce);
                if (ArrayUtils.isNotEmpty(resources) && isTrueTypeFont(fontName)) {
                    return FontUtil.createFont(resources[0].getInputStream()).deriveFont(fontStyle, Integer.valueOf(fontSize).floatValue());
                }
            } catch (IOException e) {
                log.error("[Herodotus] |- Get water mark font under the  location [{}] catch io error!", fontResouce, e);
            }
        }

        return new Font(fontName, fontStyle, fontSize);
    }

    public static BufferedImage base64ToImage(String base64String) {
        return ImgUtil.toImage(base64String);
    }

    public static String imageToBase64(BufferedImage bufferedImage) {
        return ImgUtil.toBase64(bufferedImage, ImgUtil.IMAGE_TYPE_PNG);
    }

    private static String getFontResource(String fontPath, String fontName) {
        if (StringUtils.endsWith(fontPath, SymbolConstants.FORWARD_SLASH)) {
            return fontPath + fontName;
        } else {
            return fontPath + SymbolConstants.FORWARD_SLASH + fontName;
        }
    }
}

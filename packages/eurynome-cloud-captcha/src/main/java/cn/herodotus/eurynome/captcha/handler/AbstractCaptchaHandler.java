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
 * File Name: AbstractCaptchaHandler.java
 * Author: gengwei.zheng
 * Date: 2021/12/17 21:41:17
 */

package cn.herodotus.eurynome.captcha.handler;

import cn.herodotus.eurynome.cache.definition.AbstractStampManager;
import cn.herodotus.eurynome.captcha.enums.CaptchaResource;
import cn.herodotus.eurynome.captcha.properties.CaptchaProperties;
import cn.herodotus.eurynome.captcha.utils.CaptchaRandomUtils;
import cn.herodotus.eurynome.captcha.utils.CaptchaResourceUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Description: 验证码通用基础类 </p>
 *
 * @param <K> 验证码缓存对应Key值的类型。
 * @param <V> 验证码缓存存储数据的值的类型
 * @author : gengwei.zheng
 * @date : 2021/12/17 21:41
 */
public abstract class AbstractCaptchaHandler<K, V> extends AbstractStampManager<K, V> implements CaptchaHandler {

    private static final Logger log = LoggerFactory.getLogger(AbstractCaptchaHandler.class);

    private final Map<String, String[]> indexes = new ConcurrentHashMap<>();
    private Font waterMarkFont;
    private CaptchaProperties captchaProperties;

    public void setCaptchaProperties(CaptchaProperties captchaProperties) {
        this.captchaProperties = captchaProperties;
    }

    protected CaptchaProperties getCaptchaProperties() {
        return captchaProperties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.loadWaterMarkFont();
    }

    protected void loadImages(Map<String, String> container, String location, CaptchaResource captchaResource) {
        container.putAll(CaptchaResourceUtils.getImages(location));
        log.debug("[Herodotus] |- {} load complete, total number is [{}]", captchaResource.getContent(), container.size());

        indexes.put(captchaResource.name(), container.keySet().toArray(new String[0]));
    }

    protected String getRandomBase64Image(Map<String, String> container, CaptchaResource captchaResource) {
        String[] data = indexes.get(captchaResource.name());
        if (ArrayUtils.isNotEmpty(data)) {
            int randomInt = CaptchaRandomUtils.randomInt(0, data.length);
            return container.get(data[randomInt]);
        }
        return null;
    }

    protected BufferedImage getRandomImage(Map<String, String> container, CaptchaResource captchaResource) {
        String data = getRandomBase64Image(container, captchaResource);
        if (StringUtils.isNotBlank(data)) {
            return CaptchaResourceUtils.base64ToImage(data);
        }

        return null;
    }

    private int getWatermarkFontSize() {
        return captchaProperties.getWatermark().getFontSize();
    }

    private int getHalfWatermarkFontSize() {
        return getWatermarkFontSize() / 2;
    }

    /**
     * 部署在linux中，如果没有安装中文字段，水印和点选文字，中文无法显示，
     * 通过加载resources下的font字体解决，无需在linux中安装字体
     */
    private void loadWaterMarkFont() {
        if (ObjectUtils.isEmpty(this.waterMarkFont)) {
            int fontSize = getHalfWatermarkFontSize();
            String fontName = captchaProperties.getWatermark().getFontName();
            String fontPath = captchaProperties.getWatermark().getFontPath();
            Integer fontStyle = captchaProperties.getWatermark().getFontStyle();
            this.waterMarkFont = CaptchaResourceUtils.getFont(fontName, fontSize, fontStyle, fontPath);
        }
    }

    protected int getEnOrZhLength(String s) {
        int enCount = 0;
        int zhCount = 0;
        for (int i = 0; i < s.length(); i++) {
            int length = String.valueOf(s.charAt(i)).getBytes(StandardCharsets.UTF_8).length;
            if (length > 1) {
                zhCount++;
            } else {
                enCount++;
            }
        }
        int zhOffset = getHalfWatermarkFontSize() * zhCount + 5;
        int enOffset = enCount * 8;
        return zhOffset + enOffset;
    }

    protected void addWatermark(Graphics graphics, int width, int height) {
        graphics.setFont(this.waterMarkFont);
        graphics.setColor(Color.white);
        String content = captchaProperties.getWatermark().getContent();
        graphics.drawString(content, width - getEnOrZhLength(content), height - getHalfWatermarkFontSize() + 7);
    }

    protected boolean isUnderOffset(int actualValue, int standardValue, int threshold) {
        return actualValue < standardValue - threshold;
    }

    protected boolean isOverOffset(int actualValue, int standardValue, int threshold) {
        return actualValue > standardValue + threshold;
    }

    protected boolean isDeflected(int actualValue, int standardValue, int threshold) {
        return isUnderOffset(actualValue, standardValue, threshold) || isOverOffset(actualValue, standardValue, threshold);
    }
}

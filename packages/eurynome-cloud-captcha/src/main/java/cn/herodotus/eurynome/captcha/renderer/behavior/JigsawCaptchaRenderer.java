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
 * File Name: JigsawCaptchaHandler.java
 * Author: gengwei.zheng
 * Date: 2021/12/24 09:11:24
 */

package cn.herodotus.eurynome.captcha.renderer.behavior;

import cn.herodotus.eurynome.cache.constant.CacheConstants;
import cn.herodotus.eurynome.captcha.algorithm.GaussianBlur;
import cn.herodotus.eurynome.captcha.definition.AbstractBehaviorRenderer;
import cn.herodotus.eurynome.captcha.definition.domain.Metadata;
import cn.herodotus.eurynome.captcha.definition.domain.Coordinate;
import cn.herodotus.eurynome.captcha.definition.enums.CaptchaCategory;
import cn.herodotus.eurynome.captcha.dto.Captcha;
import cn.herodotus.eurynome.captcha.dto.JigsawCaptcha;
import cn.herodotus.eurynome.captcha.dto.Verification;
import cn.herodotus.eurynome.captcha.definition.enums.CaptchaResource;
import cn.herodotus.eurynome.captcha.exception.CaptchaHasExpiredException;
import cn.herodotus.eurynome.captcha.exception.CaptchaMismatchException;
import cn.herodotus.eurynome.captcha.exception.CaptchaParameterIllegalException;
import cn.herodotus.eurynome.captcha.definition.AbstractRenderer;
import cn.herodotus.eurynome.captcha.provider.RandomProvider;
import cn.herodotus.eurynome.captcha.provider.ResourceProvider;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.IdUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Description: 拼图滑块验证码处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/17 21:44
 */
@Component
public class JigsawCaptchaRenderer extends AbstractBehaviorRenderer<String, Coordinate> {

    private static final Logger log = LoggerFactory.getLogger(JigsawCaptchaRenderer.class);

    private static final int AREA_SIZE = 3;
    private static final int AREA_ARRAY_SIZE = AREA_SIZE * AREA_SIZE;
    private static final int BOLD = 5;
    private static final int OFFSET = 100;

    private final Map<String, String> jigsawOriginalImages = new ConcurrentHashMap<>();
    private final Map<String, String> jigsawTemplateImages = new ConcurrentHashMap<>();

    @CreateCache(name = CacheConstants.CACHE_NAME_CAPTCHA_JIGSAW, cacheType = CacheType.BOTH)
    protected Cache<String, Coordinate> cache;

    @Override
    protected Cache<String, Coordinate> getCache() {
        return this.cache;
    }

    @Override
    public String getCategory() {
        return CaptchaCategory.JIGSAW.getConstant();
    }

    private JigsawCaptcha jigsawCaptcha;

    @Override
    public Captcha getCapcha(String key) {
        String identity = key;
        if (StringUtils.isBlank(identity)) {
            identity = IdUtil.fastUUID();
        }

        this.create(identity);
        return this.jigsawCaptcha;
    }

    @Override
    public Coordinate nextStamp(String key) {

        Metadata metadata = draw();

        JigsawCaptcha jigsawCaptcha = new JigsawCaptcha();
        jigsawCaptcha.setIdentity(key);
        jigsawCaptcha.setOriginalImageBase64(metadata.getOriginalImageBase64());
        jigsawCaptcha.setSliderImageBase64(metadata.getSliderImageBase64());

        this.jigsawCaptcha = jigsawCaptcha;

        return metadata.getCoordinate();
    }

    @Override
    public boolean verify(Verification verification) {

        if (ObjectUtils.isEmpty(verification) || ObjectUtils.isEmpty(verification.getCoordinate())) {
            throw new CaptchaParameterIllegalException("Parameter Stamp value is null");
        }

        Coordinate store = this.get(verification.getIdentity());
        if (ObjectUtils.isEmpty(store)) {
            throw new CaptchaHasExpiredException("Stamp is invalid!");
        }

        this.delete(verification.getIdentity());

        Coordinate real = verification.getCoordinate();

        if (this.isDeflected(real.getX(), store.getX(), getCaptchaProperties().getJigsaw().getDeviation()) || real.getY() != store.getY()) {
            throw new CaptchaMismatchException();
        }

        return true;
    }

    @Override
    public Metadata draw() {

        // 原生图片
        BufferedImage originalImage = this.getResourceProvider().getRandomOriginalImage();

        // 设置水印
        Graphics backgroundGraphics = originalImage.getGraphics();
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        addWatermark(backgroundGraphics, width, height);

        // 抠图图片
        String sliderImageBase64 = this.getResourceProvider().getRandomBase64TemplateImage();
        BufferedImage templateImage = ImgUtil.toImage(sliderImageBase64);

        return draw(originalImage, templateImage, sliderImageBase64);
    }

    /**
     * 绘制滑块拼图验证码图片元素
     *
     * @param originalImage     原始图片(验证码背景图)
     * @param templateImage     模版图片(拼图模版图片，抠图和滑块拼图的形状)
     * @param sliderImageBase64 滑块拼图图片Base64
     * @return 滑块拼图验证码数据
     */
    private Metadata draw(BufferedImage originalImage, BufferedImage templateImage, String sliderImageBase64) {

        int originalImageWidth = originalImage.getWidth();
        int originalImageHeight = originalImage.getHeight();
        int templateImageWidth = templateImage.getWidth();
        int templateImageHeight = templateImage.getHeight();

        log.trace("[Herodotus] |- Jigsaw captcha original image width is [{}], height is [{}].", originalImageWidth, originalImageHeight);
        log.trace("[Herodotus] |- Jigsaw captcha template image width is [{}], height is [{}].", templateImageWidth, templateImageHeight);

        // 随机生成拼图坐标
        Coordinate coordinate = createImageMattingCoordinate(originalImageWidth, originalImageHeight, templateImageWidth, templateImageHeight);
        int x = coordinate.getX();
        int y = coordinate.getY();

        // 根据模版抠出新的拼图图像
        BufferedImage jigsawImage = new BufferedImage(templateImageWidth, templateImageHeight, templateImage.getType());
        Graphics2D graphics = jigsawImage.createGraphics();

        // 如果需要生成RGB格式，需要做如下配置,Transparency 设置透明
        jigsawImage = graphics.getDeviceConfiguration().createCompatibleImage(templateImageWidth, templateImageHeight, Transparency.TRANSLUCENT);

        // 新建的图像根据模板颜色赋值,源图生成遮罩
        mattingByTemplate(originalImage, templateImage, jigsawImage, x, 0);

        // 添加干扰项
        int interferencePosition = createInterferencePosition(originalImageWidth, templateImageWidth, x);
        if (interferencePosition != 0) {
            addInterference(originalImage, sliderImageBase64, interferencePosition);
        }

        // 设置“抗锯齿”的属性
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setStroke(new BasicStroke(BOLD, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
        graphics.drawImage(jigsawImage, 0, 0, null);
        graphics.dispose();

        log.trace("[Herodotus] |- Jigsaw captcha jigsaw image width is [{}], height is [{}].", jigsawImage.getWidth(), jigsawImage.getHeight());

        Metadata metadata = new Metadata();
        metadata.setOriginalImageBase64(toBase64(originalImage));
        metadata.setSliderImageBase64(toBase64(jigsawImage));
        metadata.setCoordinate(coordinate);

        return metadata;
    }

    /**
     * 获取随机的抠出拼图坐标
     *
     * @param originalImageWidth  原始图片宽度
     * @param originalImageHeight 原始图片高度
     * @param templateImageWidth  拼图模版宽度
     * @param templateImageHeight 拼图模版高度
     * @return 拼图坐标 {@link Coordinate}
     */
    private Coordinate createImageMattingCoordinate(int originalImageWidth, int originalImageHeight, int templateImageWidth, int templateImageHeight) {

        int availableWidth = originalImageWidth - templateImageWidth;
        int availableHeight = originalImageHeight - templateImageHeight;

        int x = BOLD;
        int y = BOLD;

        if (availableWidth > 0) {
            x = RandomProvider.randomInt(availableWidth - OFFSET) + OFFSET;
        }

        if (availableHeight > 0) {
            y = RandomProvider.randomInt(availableHeight) + BOLD;
        }

        log.debug("[Herodotus] |- Jigsaw captcha image matting coordinate is x: [{}], y: [{}].", x, y);
        return new Coordinate(x, y);
    }

    /**
     * 根据拼图模版图片抠图
     *
     * @param originalImage 原图
     * @param templateImage 拼图模板图
     * @param jigsawImage   新抠出的小图
     * @param x             随机扣取坐标X
     * @param y             随机扣取坐标y
     */
    private void mattingByTemplate(BufferedImage originalImage, BufferedImage templateImage, BufferedImage jigsawImage, int x, int y) {
        // 临时数组遍历用于高斯模糊存周边像素值
        int[][] matrix = new int[AREA_SIZE][AREA_SIZE];
        int[] values = new int[AREA_ARRAY_SIZE];

        int templateImageWidth = templateImage.getWidth();
        int templateImageHeight = templateImage.getHeight();

        // 模板图像宽度
        for (int i = 0; i < templateImageWidth; i++) {
            // 模板图片高度
            for (int j = 0; j < templateImageHeight; j++) {

                int pixelX = x + i;
                int pixelY = y + j;

                // 如果模板图像当前像素点不是透明色 copy源文件信息到目标图片中
                int templateImageRgb = getImageRgb(templateImage, i, j);
                if (templateImageRgb < 0) {
                    jigsawImage.setRGB(i, j, getImageRgb(originalImage, pixelX, pixelY));
                    // 抠图区域高斯模糊
                    GaussianBlur.execute(originalImage, pixelX, pixelY, matrix, values, AREA_SIZE);
                }

                //防止数组越界判断
                if (isOutOfBound(i, j, templateImageWidth, templateImageHeight)) {
                    continue;
                }

                // 描边处理,取带像素和无像素的界点，判断该点是不是临界轮廓点,如果是设置该坐标像素是白色
                if (isCritical(templateImage, i, j, templateImageRgb)) {
                    jigsawImage.setRGB(i, j, Color.white.getRGB());
                    originalImage.setRGB(pixelX, pixelY, Color.white.getRGB());
                }
            }
        }
    }

    private int getImageRgb(BufferedImage bufferedImage, int i, int j) {
        return bufferedImage.getRGB(i, j);
    }


    private int getTemplateImageRightBorderRgb(BufferedImage templateImage, int i, int j) {
        return getImageRgb(templateImage, i + 1, j);
    }

    private int getTemplateImageBottomBorderRgb(BufferedImage templateImage, int i, int j) {
        return getImageRgb(templateImage, i, j + 1);
    }

    /**
     * 防止数组越界判断
     *
     * @param x                   x 坐标值
     * @param y                   y 坐标值
     * @param templateImageWidth  拼图图片宽度
     * @param templateImageHeight 拼图图片高度度
     * @return 是否越界， true 越界， false 没有越界
     */
    private boolean isOutOfBound(int x, int y, int templateImageWidth, int templateImageHeight) {
        return x == (templateImageWidth - 1) || y == (templateImageHeight - 1);
    }

    private boolean isPixelBoundary(int main, int boarder) {
        return main < 0 && boarder >= 0;
    }

    private boolean isNoPixelBoundary(int main, int boarder) {
        return main >= 0 && boarder < 0;
    }

    private boolean isBoundary(int main, int boarder) {
        return isNoPixelBoundary(main, boarder) || isPixelBoundary(main, boarder);
    }

    private boolean isCritical(BufferedImage templateImage, int x, int y, int baseRgb) {
        int rightBorderRgb = getTemplateImageRightBorderRgb(templateImage, x, y);
        int bottomBorderRgb = getTemplateImageBottomBorderRgb(templateImage, x, y);
        // 描边处理，,取带像素和无像素的界点，判断该点是不是临界轮廓点,如果是设置该坐标像素是白色
        return isBoundary(baseRgb, rightBorderRgb) || isBoundary(baseRgb, bottomBorderRgb);
    }

    private int createInterferencePosition(int originalImageWidth, int templateImageWidth, int x) {

        int interferenceOptions = getCaptchaProperties().getJigsaw().getInterference();

        int position = 0;

        if (interferenceOptions > 0) {
            if (originalImageWidth - x - BOLD > templateImageWidth * 2) {
                // 在原扣图右边插入干扰图
                position = RandomProvider.randomInt(x + templateImageWidth + BOLD, originalImageWidth - templateImageWidth);
            } else {
                // 在原扣图左边插入干扰图
                position = RandomProvider.randomInt(OFFSET, x - templateImageWidth - BOLD);
            }
        }

        if (interferenceOptions > 1) {
            position = RandomProvider.randomInt(templateImageWidth, OFFSET - templateImageWidth);
        }

        return position;
    }

    private void addInterference(BufferedImage originalImage, String sliderImageBase64, int position) {
        while (true) {
            String data = this.getResourceProvider().getRandomBase64TemplateImage();
            if (!sliderImageBase64.equals(data)) {
                interferenceByTemplate(originalImage, Objects.requireNonNull(ImgUtil.toImage(data)), position, 0);
                break;
            }
        }
    }

    /**
     * 根据拼图模版图片绘制干扰
     *
     * @param originalImage 原图
     * @param templateImage 拼图模板图
     * @param x             随机扣取坐标X
     * @param y             随机扣取坐标y
     */
    private void interferenceByTemplate(BufferedImage originalImage, BufferedImage templateImage, int x, int y) {
        //临时数组遍历用于高斯模糊存周边像素值
        int[][] matrix = new int[AREA_SIZE][AREA_SIZE];
        int[] values = new int[AREA_ARRAY_SIZE];

        int templateImageWidth = templateImage.getWidth();
        int templateImageHeight = templateImage.getHeight();
        // 模板图像宽度
        for (int i = 0; i < templateImageWidth; i++) {
            // 模板图片高度
            for (int j = 0; j < templateImageHeight; j++) {

                int pixelX = x + i;
                int pixelY = y + j;

                // 如果模板图像当前像素点不是透明色 copy源文件信息到目标图片中
                int templateImageRgb = getImageRgb(templateImage, i, j);
                if (templateImageRgb < 0) {
                    // 抠图区域高斯模糊
                    GaussianBlur.execute(originalImage, pixelX, pixelY, matrix, values, AREA_SIZE);
                }

                // 防止数组越界判断
                if (isOutOfBound(i, j, templateImageWidth, templateImageHeight)) {
                    continue;
                }

                //描边处理，,取带像素和无像素的界点，判断该点是不是临界轮廓点,如果是设置该坐标像素是白色
                if (isCritical(templateImage, i, j, templateImageRgb)) {
                    originalImage.setRGB(pixelX, pixelY, Color.white.getRGB());
                }
            }
        }
    }
}

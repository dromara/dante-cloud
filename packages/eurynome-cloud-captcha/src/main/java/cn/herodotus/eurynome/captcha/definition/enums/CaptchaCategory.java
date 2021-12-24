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
 * File Name: CaptchaCategory.java
 * Author: gengwei.zheng
 * Date: 2021/12/17 21:35:17
 */

package cn.herodotus.eurynome.captcha.definition.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.ImmutableMap;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 验证码类别 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/17 21:35
 */
@Schema(title = "验证码类别")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CaptchaCategory {

    /**
     * 验证码类别
     */
    JIGSAW(CaptchaCategory.JIGSAW_CAPTCHA, "滑块拼图验证码"),
    WORD_CLICK(CaptchaCategory.WORD_CLICK_CAPTCHA, "文字点选验证码"),
    ARITHMETIC(CaptchaCategory.ARITHMETIC_CAPTCHA, "算数类型验证码"),
    CHINESE(CaptchaCategory.CHINESE_CAPTCHA, "中文类型验证码"),
    CHINESE_GIF(CaptchaCategory.CHINESE_GIF_CAPTCHA, "中文GIF类型验证码"),
    SPEC_GIF(CaptchaCategory.SPEC_GIF_CAPTCHA, "GIF类型验证码"),
    SPEC(CaptchaCategory.SPEC_CAPTCHA, "PNG类型验证码"),
    HUTOOL_LINE(CaptchaCategory.HUTOOL_LINE_CAPTCHA, "Hutool线段干扰验证码"),
    HUTOOL_CIRCLE(CaptchaCategory.HUTOOL_CIRCLE_CAPTCHA, "Hutool圆圈干扰验证码"),
    HUTOOL_SHEAR(CaptchaCategory.HUTOOL_SHEAR_CAPTCHA, "Hutool扭曲干扰验证码"),
    HUTOOL_GIF(CaptchaCategory.HUTOOL_GIF_CAPTCHA, "Hutool GIF验证码");

    public static final String JIGSAW_CAPTCHA = "JIGSAW";
    public static final String WORD_CLICK_CAPTCHA = "WORD_CLICK";
    public static final String ARITHMETIC_CAPTCHA = "ARITHMETIC";
    public static final String CHINESE_CAPTCHA = "CHINESE";
    public static final String CHINESE_GIF_CAPTCHA = "CHINESE_GIF";
    public static final String SPEC_CAPTCHA = "SPEC";
    public static final String SPEC_GIF_CAPTCHA = "SPEC_GIF";
    public static final String HUTOOL_LINE_CAPTCHA = "HUTOOL_LINE";
    public static final String HUTOOL_CIRCLE_CAPTCHA = "HUTOOL_CIRCLE";
    public static final String HUTOOL_SHEAR_CAPTCHA = "HUTOOL_SHEAR";
    public static final String HUTOOL_GIF_CAPTCHA = "HUTOOL_GIF";

    private static final Map<String, CaptchaCategory> INDEX_MAP = new HashMap<>();
    private static final List<Map<String, Object>> JSON_STRUCT = new ArrayList<>();

    static {
        for (CaptchaCategory captchaCategory : CaptchaCategory.values()) {
            INDEX_MAP.put(captchaCategory.getConstant(), captchaCategory);
            JSON_STRUCT.add(captchaCategory.ordinal(),
                    ImmutableMap.<String, Object>builder()
                            .put("value", captchaCategory.ordinal())
                            .put("key", captchaCategory.name())
                            .put("text", captchaCategory.getDescription())
                            .build());
        }
    }

    @Schema(title = "常量值")
    private final String constant;
    @Schema(title = "文字")
    private final String description;

    CaptchaCategory(String constant, String description) {
        this.constant = constant;
        this.description = description;
    }

    public String getConstant() {
        return constant;
    }

    public String getDescription() {
        return description;
    }

    public static CaptchaCategory getCaptchaCategory(String name) {
        return INDEX_MAP.get(name);
    }

    public static List<Map<String, Object>> getJsonStruct() {
        return JSON_STRUCT;
    }
}

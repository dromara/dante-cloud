/*
 * Copyright (c) 2019-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-security
 * File Name: SocialProvider.java
 * Author: gengwei.zheng
 * Date: 2021/4/30 下午3:46
 * LastModified: 2021/4/4 下午5:58
 */

package cn.herodotus.eurynome.security.definition.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: SocialProvider </p>
 *
 * <p>Description: 社交登录提供商 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/3 15:56
 */
@ApiModel(value = "社交登录提供商")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SocialProvider {

    /**
     * 手机验证码登录
     */
    SMS("SMS", "手机短信验证码登录"),

    /**
     * 微信小程序登录
     */
    WXAPP("WXAPP", "微信小程序登录");

    @ApiModelProperty(value = "索引")
    private final String type;
    @ApiModelProperty(value = "文字")
    private final String description;

    private static final Map<String, SocialProvider> INDEX_MAP = new HashMap<>();
    private static final List<Map<String, Object>> TO_JSON_STRUCT = new ArrayList<>();

    static {
        for (SocialProvider socialProvider : SocialProvider.values()) {
            INDEX_MAP.put(socialProvider.name(), socialProvider);
            TO_JSON_STRUCT.add(socialProvider.ordinal(),
                    ImmutableMap.<String, Object>builder()
                            .put("value", socialProvider.ordinal())
                            .put("key", socialProvider.name())
                            .put("text", socialProvider.getDescription())
                            .build());
        }
    }

    SocialProvider(String type, String description) {
        this.type = type;
        this.description = description;
    }

    /**
     * 不加@JsonValue，转换的时候转换出完整的对象。
     * 加了@JsonValue，只会显示相应的属性的值
     *
     * 不使用@JsonValue @JsonDeserializer类里面要做相应的处理
     *
     * @return Enum索引
     */
    @JsonValue
    public String getType() {
        return type;
    }

    public String getDescription() {
        return this.description;
    }

    public static SocialProvider getProvider(String type) {
        return INDEX_MAP.get(type);
    }

    public static List<Map<String, Object>> getToJsonStruct() {
        return TO_JSON_STRUCT;
    }
}

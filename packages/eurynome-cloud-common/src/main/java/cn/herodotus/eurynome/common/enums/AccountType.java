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
 * Module Name: eurynome-cloud-common
 * File Name: AccountType.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.common.enums;

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
 * @author gengwei.zheng
 * 登录类型:password-密码、mobile-手机号、email-邮箱、weixin-微信、weibo-微博、qq-等等
 */
@ApiModel(description = "账号类型")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AccountType {
    /**
     * enum
     */
    PASSWORD(0, "密码"),
    MOBILE(1, "手机"),
    EMAIL(2, "邮箱"),
    WEIXIN(3, "微信"),
    QQ(4, "微信");

    @ApiModelProperty(value = "索引")
    private final Integer index;
    @ApiModelProperty(value = "文字")
    private final String text;

    private static final Map<Integer, AccountType> indexMap = new HashMap<>();
    private static final List<Map<String, Object>> toJsonStruct = new ArrayList<>();

    static {
        for (AccountType accountType : AccountType.values()) {
            indexMap.put(accountType.getIndex(), accountType);
            toJsonStruct.add(accountType.getIndex(),
                    ImmutableMap.<String, Object>builder()
                            .put("value", accountType.getIndex())
                            .put("key", accountType.name())
                            .put("text", accountType.getText())
                            .build());
        }
    }

    AccountType(Integer index, String text) {
        this.index = index;
        this.text = text;
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
    public Integer getIndex() {
        return index;
    }

    public String getText() {
        return this.text;
    }

    public static AccountType getAccountType(Integer index) {
        return indexMap.get(index);
    }

    public static List<Map<String, Object>> getToJsonStruct() {
        return toJsonStruct;
    }
}

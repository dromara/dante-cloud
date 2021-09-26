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
 * Module Name: eurynome-cloud-constant
 * File Name: StatusEnum.java
 * Author: gengwei.zheng
 * Date: 2021/06/29 15:58:29
 */

package cn.herodotus.eurynome.common.constant.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gengwei.zheng
 */
@Schema(name = "ResultStatus", title = "响应结果状态", description = "自定义错误码以及对应的、友好的错误信息")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusEnum {

    /**
     * 启用
     */
    ENABLE(0, "启用"),
    /**
     * 禁用
     */
    FORBIDDEN(1, "禁用"),
    /**
     * 锁定
     */
    LOCKING(2, "锁定"),
    /**
     * 过期
     */
    EXPIRED(3, "过期");

    @Schema(title =  "索引")
    private final Integer index;
    @Schema(title =  "文字")
    private final String text;

    private static final Map<Integer, StatusEnum> indexMap = new HashMap<>();
    private static final List<Map<String, Object>> toJsonStruct = new ArrayList<>();

    static {
        for (StatusEnum statusEnum : StatusEnum.values()) {
            indexMap.put(statusEnum.getIndex(), statusEnum);
            toJsonStruct.add(statusEnum.getIndex(),
                    ImmutableMap.<String, Object>builder()
                            .put("value", statusEnum.getIndex())
                            .put("key", statusEnum.name())
                            .put("text", statusEnum.getText())
                            .build());
        }
    }

    StatusEnum(Integer index, String text) {
        this.index = index;
        this.text = text;
    }

    /**
     * 不加@JsonValue，转换的时候转换出完整的对象。
     * 加了@JsonValue，只会显示相应的属性的值
     * <p>
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

    public static StatusEnum getStatus(Integer status) {
        return indexMap.get(status);
    }

    public static List<Map<String, Object>> getToJsonStruct() {
        return toJsonStruct;
    }
}

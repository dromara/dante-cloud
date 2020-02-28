/*
 * Copyright 2019-2019 the original author or authors.
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
 * Project Name: luban-cloud
 * Module Name: luban-cloud-component-common
 * File Name: StatusEnum.java
 * Author: gengwei.zheng
 * Date: 2019/11/8 下午4:47
 * LastModified: 2019/10/28 上午11:56
 */

package cn.herodotus.eurynome.component.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gengwei.zheng
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusEnum {

    FORBIDDEN(0, "禁用"),
    ENABLE(1, "启用"),
    LOCKING(2, "锁定"),
    EXPIRED(3, "过期");

    private Integer status;
    private String description;

    private static Map<Integer, StatusEnum> indexMap = new HashMap<>();
    private static List<Map<String, Object>> toJsonStruct = new ArrayList<>();

    static {
        for (StatusEnum statusEnum : StatusEnum.values()) {
            indexMap.put(statusEnum.status, statusEnum);
            toJsonStruct.add(statusEnum.status,
                    ImmutableMap.<String, Object>builder()
                            .put("value", statusEnum.getStatus())
                            .put("key", statusEnum.name())
                            .put("text", statusEnum.description)
                            .build());
        }
    }


    StatusEnum(Integer status, String description) {
        this.status = status;
        this.description = description;
    }

    /**
     * 不加@JsonValue，转换的时候转换出完整的对象。
     * 加了@JsonValue，只会显示相应的属性的值
     * 因此 使用@JsonValue @JsonDeserializer类里面要做相应的处理
     * @return
     */
    @JsonValue
    public Integer getStatus() {
        return this.status;
    }

    public String getDescription() {
        return this.description;
    }

    public static StatusEnum getStatus(Integer status) {
        return indexMap.get(status);
    }

    public static List<Map<String, Object>> getToJsonStruct() {
        return toJsonStruct;
    }
}

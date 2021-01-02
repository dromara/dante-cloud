/*
 * Copyright (c) 2019-2020 the original author or authors.
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
 * Module Name: eurynome-cloud-message
 * File Name: EventType.java
 * Author: gengwei.zheng
 * Date: 2020/5/28 上午11:23
 * LastModified: 2020/5/28 上午11:22
 */

package cn.herodotus.eurynome.message.bus.enums;

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
 * <p>File: EventActionType </p>
 *
 * <p>Description: Spring Cloud Bus 远程事件操作类型。 </p>
 *
 * 思路变化，暂时用不到。代码先保留以备后用。
 *
 * @author : gengwei.zheng
 * @date : 2020/5/28 11:22
 */
@ApiModel(value = "事件操作类型")
public enum EventActionType {

    /**
     * enum
     */
    SAVE_OR_UPDATE(0, "新增或者更新"),
    DELETE(1, "删除");

    @ApiModelProperty(value = "索引")
    private final Integer index;
    @ApiModelProperty(value = "文字")
    private final String text;

    private static final Map<Integer, EventActionType> INDEX_MAP = new HashMap<>();
    private static final Map<String, EventActionType> NAME_MAP = new HashMap<>();
    private static final List<Map<String, Object>> JSON_STRUCT = new ArrayList<>();

    static {
        for (EventActionType eventActionType : EventActionType.values()) {
            INDEX_MAP.put(eventActionType.getIndex(), eventActionType);
            NAME_MAP.put(eventActionType.name(), eventActionType);
            JSON_STRUCT.add(eventActionType.getIndex(),
                    ImmutableMap.<String, Object>builder()
                            .put("value", eventActionType.getIndex())
                            .put("key", eventActionType.name())
                            .put("text", eventActionType.getText())
                            .build());
        }
    }

    EventActionType(Integer index, String text) {
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

    public static EventActionType getEventType(Integer index) {
        return INDEX_MAP.get(index);
    }

    public static EventActionType getEventType(String name) {
        return NAME_MAP.get(name);
    }

    public static List<Map<String, Object>> getToJsonStruct() {
        return JSON_STRUCT;
    }
}

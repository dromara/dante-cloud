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
 * Module Name: eurynome-cloud-bpmn-rest
 * File Name: DebeziumEvent.java
 * Author: gengwei.zheng
 * Date: 2021/07/20 19:09:20
 */

package cn.herodotus.eurynome.bpmn.rest.domain.enums;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: Debezium事件枚举 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/20 19:07
 */
public enum DebeziumEvent {

    /**
     * Debezium更新操作
     */
    UPDATE("u","更新"),

    /**
     * Debezium创建操作
     */
    CREATE("c","创建"),

    /**
     * Debezium删除操作
     */
    DELETE("d","删除");

    private static final Map<String, DebeziumEvent> INDEX_MAP = new HashMap<>();
    private static final List<Map<String, Object>> JSON_STRUCT = new ArrayList<>();

    static {
        for (DebeziumEvent debeziumEvent : DebeziumEvent.values()) {
            INDEX_MAP.put(debeziumEvent.getAction(), debeziumEvent);
            JSON_STRUCT.add(debeziumEvent.ordinal(),
                    ImmutableMap.<String, Object>builder()
                            .put("value", debeziumEvent.ordinal())
                            .put("key", debeziumEvent.name())
                            .put("text", debeziumEvent.getDescription())
                            .build());
        }
    }

    private final String action;
    private final String description;

    DebeziumEvent(String action, String description) {
        this.action = action;
        this.description = description;
    }

    public String getAction() {
        return action;
    }

    public String getDescription() {
        return description;
    }

    public static DebeziumEvent getDebeziumEvent(String action) {
        return INDEX_MAP.get(action);
    }

    public static List<Map<String, Object>> getJsonStruct() {
        return JSON_STRUCT;
    }
}

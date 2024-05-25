/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Dante Cloud.
 *
 * Dante Cloud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dante Cloud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.cn>.
 */

package cn.herodotus.dante.bpmn.logic.domain.enums;

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

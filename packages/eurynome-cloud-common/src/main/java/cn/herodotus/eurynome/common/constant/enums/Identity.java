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
 * Module Name: eurynome-cloud-common
 * File Name: Identity.java
 * Author: gengwei.zheng
 * Date: 2021/09/12 01:56:12
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
 * <p>Description: 人员身份</p>
 *
 * @author gengwei.zheng
 * @date 2019/2/15
 */
@Schema(name = "人员身份")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Identity {
    /**
     * enum
     */
    LEADERSHIP(0, "领导"),
    SECTION_LEADER(1, "部所负责人"),
    STAFF(2, "员工");

    @Schema(title =  "索引")
    private final Integer index;
    @Schema(title =  "文字")
    private String text;

    private static final Map<Integer, Identity> indexMap = new HashMap<>();
    private static final List<Map<String, Object>> toJsonStruct = new ArrayList<>();

    static {
        for (Identity identity : Identity.values()) {
            indexMap.put(identity.getIndex(), identity);
            toJsonStruct.add(identity.getIndex(),
                    ImmutableMap.<String, Object>builder()
                            .put("value", identity.getIndex())
                            .put("key", identity.name())
                            .put("text", identity.getText())
                            .build());
        }
    }

    Identity(Integer index, String text) {
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
        return text;
    }

    public static Identity getIdentity(Integer status) {
        return indexMap.get(status);
    }

    public static List<Map<String, Object>> getToJsonStruct() {
        return toJsonStruct;
    }
}

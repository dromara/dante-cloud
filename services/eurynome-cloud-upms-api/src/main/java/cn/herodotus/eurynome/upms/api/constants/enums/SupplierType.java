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
 * Module Name: eurynome-cloud-upms-api
 * File Name: SupplierType.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.upms.api.constants.enums;

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
 * <p> Description : 供应商类型 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/19 16:17
 */
@ApiModel(value = "团队/供应商类型")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SupplierType {

    /**
     * enum
     */
    CORE(0, "平台核心团队"),
    BAT(1, "互联网大厂"),
    THIRD_PARTY(2, "第三方企业"),
    Outsourcing(3, "外包团队");

    @ApiModelProperty(value = "索引")
    private final Integer index;
    @ApiModelProperty(value = "文字")
    private final String text;

    private static final Map<Integer, SupplierType> indexMap = new HashMap<>();
    private static final List<Map<String, Object>> toJsonStruct = new ArrayList<>();

    static {
        for (SupplierType supplierType : SupplierType.values()) {
            indexMap.put(supplierType.getIndex(), supplierType);
            toJsonStruct.add(supplierType.getIndex(),
                    ImmutableMap.<String, Object>builder()
                            .put("value", supplierType.getIndex())
                            .put("key", supplierType.name())
                            .put("text", supplierType.getText())
                            .build());
        }
    }

    SupplierType(Integer index, String text) {
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
        return text;
    }

    public static SupplierType getSupplierType(Integer type) {
        return indexMap.get(type);
    }

    public static List<Map<String, Object>> getToJsonStruct() {
        return toJsonStruct;
    }
}

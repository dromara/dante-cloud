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
 * File Name: SerializerFeature.java
 * Author: gengwei.zheng
 * Date: 2019/11/8 下午4:52
 * LastModified: 2019/11/8 下午4:46
 */

package cn.herodotus.eurynome.component.common.jackson;

/**
 * fastjson提供了一些方便的序列化特性，下面设置的serializerFeatures特性主要针是对null的默认值处理
 * @author gengwei.zheng
 */
public enum SerializerFeature {

    /**
     * enum
     */
    WriteNullListAsEmpty,
    WriteNullStringAsEmpty,
    WriteNullNumberAsZero,
    WriteNullBooleanAsFalse,
    WriteNullMapAsEmpty;

    public final int mask;

    SerializerFeature() {
        mask = (1 << ordinal());
    }
}

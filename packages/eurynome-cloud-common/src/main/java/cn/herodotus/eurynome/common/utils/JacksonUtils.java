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
 * File Name: JacksonUtils.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.common.utils;

import cn.herodotus.eurynome.common.jackson.FastJsonSerializerFeatureCompatibleForJackson;
import cn.herodotus.eurynome.common.jackson.SerializerFeature;
import cn.herodotus.eurynome.common.jackson.deserializer.XssStringJsonDeserializer;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * <p>Description: Jackson单例工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/9/1 12:18
 */
public class JacksonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JacksonUtils.class);

    private static volatile JacksonUtils INSTANCE;
    private final ObjectMapper objectMapper;

    private JacksonUtils() {
        objectMapper = new ObjectMapper();
        // 设置为中国上海时区
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        // 空值不序列化
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 序列化时，日期的统一格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 排序key
        objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        // 忽略空bean转json错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 忽略在json字符串中存在，在java类中不存在字段，防止错误。
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 单引号处理
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);

        /**
         * 序列换成json时,将所有的long变成string
         * js中long过长精度丢失
         */
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        simpleModule.addDeserializer(String.class, new XssStringJsonDeserializer());

        objectMapper.registerModule(simpleModule);
        // 兼容fastJson 的一些空值处理
        SerializerFeature[] features = new SerializerFeature[]{
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteNullMapAsEmpty
        };
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(new FastJsonSerializerFeatureCompatibleForJackson(features)));
    }

    private static JacksonUtils getInstance() {
        if (ObjectUtils.isEmpty(INSTANCE)) {
            synchronized (JacksonUtils.class) {
                if (ObjectUtils.isEmpty(INSTANCE)) {
                    INSTANCE = new JacksonUtils();
                }
            }
        }

        return INSTANCE;
    }

    private ObjectMapper objectMapper() {
        return this.objectMapper;
    }

    public static ObjectMapper getObjectMapper() {
        return JacksonUtils.getInstance().objectMapper();
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            return getObjectMapper().readValue(json, clazz);
        } catch (JsonParseException e) {
            logger.error("[Herodotus] |- JacksonUtils toObject parse json error! {}", e.getMessage());
        } catch (JsonMappingException e) {
            logger.error("[Herodotus] |- JacksonUtils toObject mapping to object error! {}", e.getMessage());
        } catch (IOException e) {
            logger.error("[Herodotus] |- JacksonUtils toObject read content error! {}", e.getMessage());
        }

        return null;
    }

    public static <T> String toJson(T entity) {
        try {
            return getObjectMapper().writeValueAsString(entity);
        } catch (JsonParseException e) {
            logger.error("[Herodotus] |- JacksonUtils toCollection parse json error! {}", e.getMessage());
        } catch (JsonMappingException e) {
            logger.error("[Herodotus] |- JacksonUtils toCollection mapping to object error! {}", e.getMessage());
        } catch (IOException e) {
            logger.error("[Herodotus] |- JacksonUtils toCollection read content error! {}", e.getMessage());
        }

        return null;
    }

    public static <T> List<T> toList(String json, Class<T> clazz) {
        JavaType javaType = getObjectMapper().getTypeFactory().constructParametricType(ArrayList.class, clazz);
        try {
            return getObjectMapper().readValue(json, javaType);
        } catch (JsonParseException e) {
            logger.error("[Herodotus] |- JacksonUtils toCollection parse json error! {}", e.getMessage());
        } catch (JsonMappingException e) {
            logger.error("[Herodotus] |- JacksonUtils toCollection mapping to object error! {}", e.getMessage());
        } catch (IOException e) {
            logger.error("[Herodotus] |- JacksonUtils toCollection read content error! {}", e.getMessage());
        }

        return null;
    }

    public static <T> T toCollection(String json, TypeReference<T> typeReference) {
        try {
            return getObjectMapper().readValue(json, typeReference);
        } catch (JsonParseException e) {
            logger.error("-| [Herodotus]: JacksonUtils toCollection parse json error! {}", e.getMessage());
        } catch (JsonMappingException e) {
            logger.error("-| [Herodotus]: JacksonUtils toCollection mapping to object error! {}", e.getMessage());
        } catch (IOException e) {
            logger.error("-| [Herodotus]: JacksonUtils toCollection read content error! {}", e.getMessage());
        }

        return null;
    }
}

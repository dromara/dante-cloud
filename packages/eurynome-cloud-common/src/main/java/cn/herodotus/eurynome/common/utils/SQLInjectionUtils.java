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
 * File Name: SQLInjectionUtils.java
 * Author: gengwei.zheng
 * Date: 2021/09/01 12:19:01
 */

package cn.herodotus.eurynome.common.utils;

import cn.herodotus.eurynome.common.constant.magic.SymbolConstants;
import cn.hutool.core.net.URLDecoder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * <p>Description: SQL注入处理工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/9/1 12:19
 */
public class SQLInjectionUtils {

    private static final Logger log = LoggerFactory.getLogger(SQLInjectionUtils.class);

    private static final String SQL_REGEX = "\\b(and|or)\\b.{1,6}?(=|>|<|\\bin\\b|\\blike\\b)|\\/\\*.+?\\*\\/|<\\s*script\\b|\\bEXEC\\b|UNION.+?SELECT|UPDATE.+?SET|INSERT\\s+INTO.+?VALUES|(SELECT|DELETE).+?FROM|(CREATE|ALTER|DROP|TRUNCATE)\\s+(TABLE|DATABASE)";

    private static final Pattern SQL_PATTERN = Pattern.compile(SQL_REGEX, Pattern.CASE_INSENSITIVE);

    private static boolean matching(String lowerValue, String param) {
        if (SQL_PATTERN.matcher(param).find()) {
            log.error("[Herodotus] |- The parameter contains keywords {} that do not allow SQL!", lowerValue);
            return true;
        }
        return false;
    }

    private static String toLowerCase(Object obj) {
        //这里需要将参数转换为小写来处理
        return Optional.ofNullable(obj)
                .map(Object::toString)
                .map(String::toLowerCase)
                .orElse("");
    }

    private static boolean checking(Object value) {
        //这里需要将参数转换为小写来处理
        String lowerValue = toLowerCase(value);
        return matching(lowerValue, lowerValue);
    }

    /**
     * get请求sql注入校验
     *
     * @param value 具体的检验
     * @return 是否存在不合规内容
     */
    public static boolean checkForGet(String value) {

        //参数需要url编码
        //这里需要将参数转换为小写来处理
        //不改变原值
        //value示例 order=asc&pageNum=1&pageSize=100&parentId=0
        String lowerValue = URLDecoder.decode(value, StandardCharsets.UTF_8).toLowerCase();

        //获取到请求中所有参数值-取每个key=value组合第一个等号后面的值
        return Stream.of(lowerValue.split("\\&"))
                .map(kp -> kp.substring(kp.indexOf(SymbolConstants.EQUAL) + 1))
                .parallel()
                .anyMatch(param -> matching(lowerValue, param));
    }

    /**
     * post请求sql注入校验
     *
     * @param value 具体的检验
     * @return 是否存在不合规内容
     */
    public static boolean checkForPost(String value) {

        Object jsonObj = JSON.parse(value);
        if (jsonObj instanceof JSONObject) {
            JSONObject json = (JSONObject) jsonObj;
            return json.entrySet().stream().parallel().anyMatch(entry -> checking(entry.getValue()));
        }

        if (jsonObj instanceof JSONArray) {
            JSONArray json = (JSONArray) jsonObj;
            return json.stream().parallel().anyMatch(SQLInjectionUtils::checking);
        }

        return false;
    }
}

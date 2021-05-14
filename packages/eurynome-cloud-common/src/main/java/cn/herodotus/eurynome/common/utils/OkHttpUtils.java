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
 * File Name: OkHttpUtils.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.common.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>Description: OkHttpUtils </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/29 17:06
 */
@Slf4j
public class OkHttpUtils {

    public static final String AMPERSAND = "&";
    public static final String EQUAL = "=";
    public static final String QUESTION = "?";

    /**
     * 懒汉 安全 加同步
     * 私有的静态成员变量 只声明不创建
     * 私有的构造方法
     * 提供返回实例的静态方法
     */
    private static OkHttpClient okHttpClient = null;


    private OkHttpUtils() {
    }

    public static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            //加同步安全
            synchronized (OkHttpUtils.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .build();
                }
            }

        }

        return okHttpClient;
    }

    private static String addParamsToGetReqeust(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder(url);
        if (MapUtils.isNotEmpty(params)) {
            sb.append(QUESTION);

            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                sb.append(key);
                sb.append(EQUAL);
                sb.append(value);
                sb.append(AMPERSAND);
            }

            return StringUtils.removeEnd(sb.toString(), AMPERSAND);
        }

        return url;
    }

    public static String get(String url, Map<String, String> params){
        return get(url, new HashMap<>(8), params);
    }

    /**
     * Get请求
     *
     * @param url URL地址
     * @return 返回结果
     */
    public static String get(String url, Map<String, String> headers, Map<String, String> params){

        String urlWithParams = addParamsToGetReqeust(url, params);

        Request.Builder requestBuilder = new Request.Builder();
        if(MapUtils.isNotEmpty(headers)) {
            for (String key : headers.keySet()) {
                requestBuilder.addHeader(key, headers.get(key));
            }
        }

        Request request = requestBuilder.url(urlWithParams).build();

        try {
            Response response = getInstance().newCall(request).execute();
            String result = response.body().toString();
            log.debug("[OkHttp] |- OkHttp Get Request returned value is : [{}]", result);
            return result;
        } catch (IOException e) {
            log.error("[OkHttp] |- OkHttp Post Request Catch the Error!", e);
            return null;
        }
    }

    /**
     * Post请求
     *
     * @param url    URL地址
     * @param params 参数
     * @return 返回结果
     */
    public static String post(String url, Map<String, String> params) {
        if (MapUtils.isEmpty(params)) {
            params = new HashMap<>(8);
        }

        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> map : params.entrySet()) {
            String key = map.getKey();
            String value;
            if (map.getValue() == null) {
                value = "";
            } else {
                value = map.getValue();
            }
            formBodyBuilder.add(key, value);
        }
        FormBody formBody = formBodyBuilder.build();
        log.debug("[OkHttp] |- OkHttp Post Request add params : [{}]", JSON.toJSONString(params));

        try {
            Request request = new Request.Builder().url(url).post(formBody).build();
            Response response = getInstance().newCall(request).execute();
            String result = response.body().string();
            log.debug("[OkHttp] |- OkHttp Post Request returned value is : [{}]", result);
            return result;
        } catch (Exception e) {
            log.error("[OkHttp] |- OkHttp Post Request Catch the Error!", e);
            return null;
        }
    }
}

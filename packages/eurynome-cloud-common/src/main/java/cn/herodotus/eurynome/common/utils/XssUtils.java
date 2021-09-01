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
 * File Name: XssUtils.java
 * Author: gengwei.zheng
 * Date: 2021/09/01 12:21:01
 */

package cn.herodotus.eurynome.common.utils;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.owasp.validator.html.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

/**
 * <p>Description: Antisamy 单例 工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/9/1 12:21
 */
public class XssUtils {

    private static final Logger log = LoggerFactory.getLogger(XssUtils.class);

    private static volatile XssUtils INSTANCE;
    private final AntiSamy antiSamy;

    private XssUtils() {
        Policy policy = createPolicy();
        this.antiSamy = ObjectUtils.isNotEmpty(policy) ? new AntiSamy(policy) : new AntiSamy();
    }

    private static XssUtils getInstance() {
        if (ObjectUtils.isEmpty(INSTANCE)) {
            synchronized (XssUtils.class) {
                if (ObjectUtils.isEmpty(INSTANCE)) {
                    INSTANCE = new XssUtils();
                }
            }
        }

        return INSTANCE;
    }

    private Policy createPolicy() {
        try {
            URL url = ResourceUtils.getURL("classpath:antisamy/antisamy-ebay.xml");
            return Policy.getInstance(url);
        } catch (IOException | PolicyException e) {
            log.warn("[Eurynome] |- Antisamy create policy error! {}", e.getMessage());
            return null;
        }
    }

    private AntiSamy antiSamy() {
        return antiSamy;
    }

    public static AntiSamy getAntiSamy() {
        return XssUtils.getInstance().antiSamy();
    }

    public static CleanResults scan(String taintedHtml) throws ScanException, PolicyException {
        return getAntiSamy().scan(taintedHtml);
    }

    public static String cleaning(String value) {
        try {
            log.trace("[Eurynome] |- Before Antisamy cleaning XSS, value is: [{}]", value);

            // 使用AntiSamy清洗数据
            final CleanResults cleanResults = XssUtils.scan(value);
            // 获得安全的HTML输出
            value = cleanResults.getCleanHTML();
            // 对转义的HTML特殊字符（<、>、"等）进行反转义，因为AntiSamy调用scan方法时会将特殊字符转义
            String result = StringEscapeUtils.unescapeHtml4(value);

            log.trace("[Eurynome] |- After Antisamy cleaning XSS, value is: [{}]", value);
            return result;
        } catch (ScanException | PolicyException e) {
            log.error("[Eurynome] |- Antisamy cleaning XSS catch error! {}", e.getMessage());
        }
        return value;
    }
}

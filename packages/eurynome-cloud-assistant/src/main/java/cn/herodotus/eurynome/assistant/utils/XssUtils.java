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
 * Module Name: eurynome-cloud-assistant
 * File Name: XssUtils.java
 * Author: gengwei.zheng
 * Date: 2021/10/17 22:53:17
 */

package cn.herodotus.eurynome.assistant.utils;

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
    private final String nbsp;
    private final String quot;

    private XssUtils() {
        Policy policy = createPolicy();
        this.antiSamy = ObjectUtils.isNotEmpty(policy) ? new AntiSamy(policy) : new AntiSamy();
        this.nbsp = cleanHtml("&nbsp;");
        this.quot = cleanHtml("\"");
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
            URL url = ResourceUtils.getURL("classpath:antisamy/antisamy-anythinggoes.xml");
            return Policy.getInstance(url);
        } catch (IOException | PolicyException e) {
            log.warn("[Herodotus] |- Antisamy create policy error! {}", e.getMessage());
            return null;
        }
    }

    private CleanResults scan(String taintedHtml) throws ScanException, PolicyException {
        return antiSamy.scan(taintedHtml);
    }

    private String cleanHtml(String taintedHtml) {
        try {
            log.trace("[Herodotus] |- Before Antisamy Scan, value is: [{}]", taintedHtml);

            // 使用AntiSamy清洗数据
            final CleanResults cleanResults = scan(taintedHtml);
            String result = cleanResults.getCleanHTML();
            log.trace("[Herodotus] |- After  Antisamy Scan, value is: [{}]", result);
            return result;
        } catch (ScanException | PolicyException e) {
            log.error("[Herodotus] |- Antisamy scan catch error! {}", e.getMessage());
            return taintedHtml;
        }
    }

    public static String cleaning(String taintedHTML) {
        // 对转义的HTML特殊字符（<、>、"等）进行反转义，因为AntiSamy调用scan方法时会将特殊字符转义
        String cleanHtml = StringEscapeUtils.unescapeHtml4(getInstance().cleanHtml(taintedHTML));
        //AntiSamy会把“&nbsp;”转换成乱码，把双引号转换成"&quot;" 先将&nbsp;的乱码替换为空，双引号的乱码替换为双引号
        String temp = cleanHtml.replaceAll(getInstance().nbsp, "");
        String result = temp.replaceAll(getInstance().quot, "\"");
        log.trace("[Herodotus] |- After  Antisamy Well Formed, value is: [{}]", result);
        return result;
    }
}

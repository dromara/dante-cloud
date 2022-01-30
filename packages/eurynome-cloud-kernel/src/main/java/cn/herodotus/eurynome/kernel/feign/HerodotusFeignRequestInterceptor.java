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
 * Module Name: eurynome-cloud-kernel
 * File Name: HerodotusFeignRequestInterceptor.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.kernel.feign;

import cn.herodotus.engine.assistant.core.constants.SymbolConstants;
import cn.hutool.extra.servlet.ServletUtil;
import com.google.common.net.HttpHeaders;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: HerodotusFeignRequestInterceptor </p>
 *
 * <p>Description: 自定义FeignRequestInterceptor </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/6/8 12:01
 */
@Slf4j
public class HerodotusFeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest httpServletRequest = getHttpServletRequest();

        if (httpServletRequest != null) {
            Map<String, String> headers = ServletUtil.getHeaderMap(httpServletRequest);
            // 传递所有请求头,防止部分丢失
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                // 跳过content-length值的复制。因为服务之间调用需要携带一些用户信息之类的 所以实现了Feign的RequestInterceptor拦截器复制请求头，复制的时候是所有头都复制的,可能导致Content-length长度跟body不一致
                // @see https://blog.csdn.net/qq_39986681/article/details/107138740
                if (StringUtils.equalsIgnoreCase(key, HttpHeaders.CONTENT_LENGTH)) {
                    continue;
                }

                // 解决 UserAgent 信息被修改后，AppleWebKit/537.36 (KHTML,like Gecko)部分存在非法字符的问题
                if (StringUtils.equalsIgnoreCase(key, HttpHeaders.USER_AGENT)) {
                    value = StringUtils.replace(value, SymbolConstants.NEW_LINE, SymbolConstants.BLANK);
                    entry.setValue(value);
                }

                requestTemplate.header(entry.getKey(), entry.getValue());
            }

            log.debug("[Herodotus] |- FeignRequestInterceptor copy all need transfer header!");

            // 微服务之间传递的唯一标识,区分大小写所以通过httpServletRequest查询
            if (headers.containsKey(HttpHeaders.X_REQUEST_ID)) {
                String traceId = headers.get(HttpHeaders.X_REQUEST_ID);
                MDC.put("traceId", traceId);
                log.info("[Herodotus] |- Feign Request Interceptor Trace: {}", traceId);
            }
        }

        log.trace("[Herodotus] |- Feign Request Interceptor [{}]", requestTemplate.toString());
    }

    private HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            log.error("[Herodotus] |- Feign Request Interceptor can not get Request.");
            return null;
        }
    }
}

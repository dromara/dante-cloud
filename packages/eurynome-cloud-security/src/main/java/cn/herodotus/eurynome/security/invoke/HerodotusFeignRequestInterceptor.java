/*
 * Copyright (c) 2019-2020 the original author or authors.
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
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-security
 * File Name: HerodotusFeignRequestInterceptor.java
 * Author: gengwei.zheng
 * Date: 2020/6/8 下午12:01
 * LastModified: 2020/6/8 下午12:01
 */

package cn.herodotus.eurynome.security.invoke;

import cn.hutool.extra.servlet.ServletUtil;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
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
public class HerodotusFeignRequestInterceptor extends OAuth2FeignRequestInterceptor {

    public HerodotusFeignRequestInterceptor(OAuth2ClientContext oAuth2ClientContext, OAuth2ProtectedResourceDetails resource) {
        super(oAuth2ClientContext, resource);
    }

    public HerodotusFeignRequestInterceptor(OAuth2ClientContext oAuth2ClientContext, OAuth2ProtectedResourceDetails resource, String tokenType, String header) {
        super(oAuth2ClientContext, resource, tokenType, header);
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest httpServletRequest = getHttpServletRequest();

        if (httpServletRequest != null) {
            Map<String, String> headers = ServletUtil.getHeaderMap(httpServletRequest);
            // 传递所有请求头,防止部分丢失
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                requestTemplate.header(entry.getKey(), entry.getValue());
            }

            log.debug("[Eurynome] |- FeignRequestInterceptor copy all need transfer header!");
        }

        log.trace("[Eurynome] |- Feign Request Interceptor [{}]", requestTemplate.toString());

        super.apply(requestTemplate);
    }

    private HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            log.error("[Eurynome] |- Feign Request Interceptor can not get Request.");
            return null;
        }
    }
}

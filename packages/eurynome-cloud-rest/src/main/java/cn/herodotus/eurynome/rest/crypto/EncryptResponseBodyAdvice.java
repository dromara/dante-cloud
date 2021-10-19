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
 * Module Name: eurynome-cloud-rest
 * File Name: EncryptResponseBodyAdvice.java
 * Author: gengwei.zheng
 * Date: 2021/10/08 19:16:08
 */

package cn.herodotus.eurynome.rest.crypto;

import cn.herodotus.eurynome.assistant.annotation.rest.Crypto;
import cn.herodotus.eurynome.assistant.constant.HttpHeaders;
import cn.herodotus.eurynome.assistant.utils.JacksonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * <p>Description: 响应体加密Advice </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/8 19:16
 */
@RestControllerAdvice
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private static final Logger log = LoggerFactory.getLogger(EncryptResponseBodyAdvice.class);

    private InterfaceCryptoProcessor interfaceCryptoProcessor;

    public void setInterfaceCryptoProcessor(InterfaceCryptoProcessor interfaceCryptoProcessor) {
        this.interfaceCryptoProcessor = interfaceCryptoProcessor;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {

        String methodName = methodParameter.getMethod().getName();
        Crypto crypto = methodParameter.getMethodAnnotation(Crypto.class);

        boolean isSupports = ObjectUtils.isNotEmpty(crypto) && crypto.responseEncrypt();

        log.debug("[Herodotus] |- Is EncryptResponseBodyAdvice supports method [{}] ? Status is [{}].", methodName, isSupports);
        return isSupports;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        String sessionKey = request.getHeaders().get(HttpHeaders.X_HERODOTUS_SESSION).get(0);

        if (StringUtils.isBlank(sessionKey)) {
            log.warn("[Herodotus] |- Cannot find Herodotus Cloud custom session header. Use interface crypto founction need add X_HERODOTUS_SESSION to request header.");
            return body;
        }

        log.info("[Herodotus] |- EncryptResponseBodyAdvice begin encrypt data.");

        String methodName = methodParameter.getMethod().getName();
        String className = methodParameter.getDeclaringClass().getName();

        try {
            String bodyString = JacksonUtils.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(body);
            String result = interfaceCryptoProcessor.encryptToString(sessionKey, bodyString);
            if (StringUtils.isNotBlank(result)) {
                log.debug("[Herodotus] |- Encrypt response body for rest method [{}] in [{}] finished.", methodName, className);
                return result;
            } else {
                return body;
            }
        } catch (JsonProcessingException e) {
            log.debug("[Herodotus] |- Encrypt response body for rest method [{}] in [{}] catch error, skip encrypt operation.", methodName, className, e);
            return body;
        }
    }
}

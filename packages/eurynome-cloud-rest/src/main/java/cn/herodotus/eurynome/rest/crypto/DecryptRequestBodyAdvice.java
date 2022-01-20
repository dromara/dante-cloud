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
 * File Name: DecryptRequestBodyAdvice.java
 * Author: gengwei.zheng
 * Date: 2021/10/08 19:15:08
 */

package cn.herodotus.eurynome.rest.crypto;

import cn.herodotus.engine.rest.core.annotation.Crypto;
import cn.hutool.core.io.IoUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * <p>Description: RequestBody 解密 Advice </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/8 19:15
 */
@RestControllerAdvice
public class DecryptRequestBodyAdvice implements RequestBodyAdvice {

    private static final Logger log = LoggerFactory.getLogger(DecryptRequestBodyAdvice.class);

    private InterfaceCryptoProcessor interfaceCryptoProcessor;

    public void setInterfaceCryptoProcessor(InterfaceCryptoProcessor interfaceCryptoProcessor) {
        this.interfaceCryptoProcessor = interfaceCryptoProcessor;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

        String methodName = methodParameter.getMethod().getName();
        Crypto crypto = methodParameter.getMethodAnnotation(Crypto.class);

        boolean isSupports = ObjectUtils.isNotEmpty(crypto) && crypto.requestDecrypt();

        log.trace("[Herodotus] |- Is DecryptRequestBodyAdvice supports method [{}] ? Status is [{}].", methodName, isSupports);
        return isSupports;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {

        String sessionKey = httpInputMessage.getHeaders().get(cn.herodotus.eurynome.assistant.constant.HttpHeaders.X_HERODOTUS_SESSION).get(0);

        if (StringUtils.isBlank(sessionKey)) {
            log.warn("[Herodotus] |- Cannot find Herodotus Cloud custom session header. Use interface crypto founction need add X_HERODOTUS_SESSION to request header.");
            return httpInputMessage;
        }

        log.info("[Herodotus] |- DecryptRequestBodyAdvice begin decrypt data.");

        String methodName = methodParameter.getMethod().getName();
        String className = methodParameter.getDeclaringClass().getName();

        String content = IoUtil.read(httpInputMessage.getBody()).toString();
        if (StringUtils.isNotBlank(content)) {
            byte[] decrypt = interfaceCryptoProcessor.decrypt(sessionKey, content);
            log.debug("[Herodotus] |- Decrypt request body for rest method [{}] in [{}] finished.", methodName, className);
            return new DecryptHttpInputMessage(httpInputMessage, decrypt);
        } else {
            return httpInputMessage;
        }
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    public static class DecryptHttpInputMessage implements HttpInputMessage {

        private final HttpInputMessage httpInputMessage;
        private final byte[] data;

        public DecryptHttpInputMessage(HttpInputMessage httpInputMessage, byte[] data) {
            this.httpInputMessage = httpInputMessage;
            this.data = data;
        }

        @Override
        public InputStream getBody() throws IOException {
            return new ByteArrayInputStream(this.data);
        }

        @Override
        public HttpHeaders getHeaders() {
            return this.httpInputMessage.getHeaders();
        }
    }
}

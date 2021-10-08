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
 * File Name: DecryptRequestParamStringResolver.java
 * Author: gengwei.zheng
 * Date: 2021/10/08 19:16:08
 */

package cn.herodotus.eurynome.rest.crypto;

import cn.herodotus.eurynome.assistant.annotation.rest.Crypto;
import cn.herodotus.eurynome.common.constant.magic.HttpHeaders;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * <p>Description: 字符串类型的 @RequestParam 值加解密 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/8 19:16
 */
public class DecryptRequestParamStringResolver implements HandlerMethodArgumentResolver {

    private static final Logger log = LoggerFactory.getLogger(DecryptRequestParamStringResolver.class);

    private InterfaceCryptoProcessor interfaceCryptoProcessor;

    public void setInterfaceCryptoProcessor(InterfaceCryptoProcessor interfaceCryptoProcessor) {
        this.interfaceCryptoProcessor = interfaceCryptoProcessor;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {

        String methodName = methodParameter.getMethod().getName();

        Crypto crypto = methodParameter.getMethodAnnotation(Crypto.class);
        RequestParam requestParam = methodParameter.getParameterAnnotation(RequestParam.class);
        boolean isSupports = (ObjectUtils.isNotEmpty(crypto) && ObjectUtils.isNotEmpty(requestParam) && String.class.isAssignableFrom(methodParameter.getParameterType()) &&
                StringUtils.isNotBlank(requestParam.name()));

        log.debug("[Herodotus] |- Is DecryptRequestParamStringResolver supports method [{}] ? Status is [{}].", methodName, isSupports);
        return isSupports;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        String content = request.getParameter(methodParameter.getParameterName());
        String sessionKey = request.getHeader(HttpHeaders.X_HERODOTUS_SESSION);

        byte[] bytes = interfaceCryptoProcessor.decrypt(sessionKey, content);
        String value = StrUtil.str(bytes, StandardCharsets.UTF_8);

        log.debug("[Herodotus] |- Decrypt request param for @RequestPara [{}] value is [{}].", methodParameter.getParameterName(), value);
        return value;
    }
}

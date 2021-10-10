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
 * File Name: InterfaceSecurityConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/10/08 19:13:08
 */

package cn.herodotus.eurynome.rest.configuration;

import cn.herodotus.eurynome.data.stamp.AccessLimitedStampManager;
import cn.herodotus.eurynome.data.stamp.IdempotentStampManager;
import cn.herodotus.eurynome.data.stamp.SecretKeyStampManager;
import cn.herodotus.eurynome.rest.crypto.*;
import cn.herodotus.eurynome.rest.security.AccessLimitedInterceptor;
import cn.herodotus.eurynome.rest.security.IdempotentInterceptor;
import cn.herodotus.eurynome.rest.security.XssHttpServletFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: 接口安全配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/8 19:13
 */
@Configuration
public class InterfaceSecurityConfiguration {

    private static final Logger log = LoggerFactory.getLogger(InterfaceSecurityConfiguration.class);

    @Autowired
    private IdempotentStampManager idempotentStampManager;
    @Autowired
    private AccessLimitedStampManager accessLimitedStampManager;
    @Autowired
    private SecretKeyStampManager secretKeyStampManager;

    @Bean
    @ConditionalOnMissingBean
    public IdempotentInterceptor idempotentInterceptor() {
        IdempotentInterceptor idempotentInterceptor = new IdempotentInterceptor();
        idempotentInterceptor.setIdempotentStampManager(idempotentStampManager);
        log.trace("[Herodotus] |- Bean [Idempotent Interceptor] Auto Configure.");
        return idempotentInterceptor;
    }

    @Bean
    @ConditionalOnMissingBean
    public AccessLimitedInterceptor accessLimitedInterceptor() {
        AccessLimitedInterceptor accessLimitedInterceptor = new AccessLimitedInterceptor();
        accessLimitedInterceptor.setAccessLimitedStampManager(accessLimitedStampManager);
        log.trace("[Herodotus] |- Bean [Access Limited Interceptor] Auto Configure.");
        return accessLimitedInterceptor;
    }

    @Bean
    @ConditionalOnMissingBean
    public InterfaceCryptoProcessor interfaceCryptoProcessor() {
        InterfaceCryptoProcessor interfaceCryptoProcessor = new InterfaceCryptoProcessor();
        interfaceCryptoProcessor.setSecretKeyStampManager(secretKeyStampManager);
        log.trace("[Herodotus] |- Bean [Interface Crypto Processor] Auto Configure.");
        return interfaceCryptoProcessor;
    }

    @Bean
    @ConditionalOnMissingBean
    public XssHttpServletFilter xssHttpServletFilter() {
        XssHttpServletFilter xssHttpServletFilter = new XssHttpServletFilter();
        log.trace("[Herodotus] |- Bean [Xss Http Servlet Filter] Auto Configure.");
        return xssHttpServletFilter;
    }

    @Bean
    @ConditionalOnClass(InterfaceCryptoProcessor.class)
    @ConditionalOnMissingBean
    public DecryptRequestBodyAdvice decryptRequestBodyAdvice(InterfaceCryptoProcessor interfaceCryptoProcessor) {
        DecryptRequestBodyAdvice decryptRequestBodyAdvice = new DecryptRequestBodyAdvice();
        decryptRequestBodyAdvice.setInterfaceCryptoProcessor(interfaceCryptoProcessor);
        log.trace("[Herodotus] |- Bean [Decrypt Request Body Advice] Auto Configure.");
        return decryptRequestBodyAdvice;
    }

    @Bean
    @ConditionalOnClass(InterfaceCryptoProcessor.class)
    @ConditionalOnMissingBean
    public EncryptResponseBodyAdvice encryptResponseBodyAdvice(InterfaceCryptoProcessor interfaceCryptoProcessor) {
        EncryptResponseBodyAdvice encryptResponseBodyAdvice = new EncryptResponseBodyAdvice();
        encryptResponseBodyAdvice.setInterfaceCryptoProcessor(interfaceCryptoProcessor);
        log.trace("[Herodotus] |- Bean [Encrypt Response Body Advice] Auto Configure.");
        return encryptResponseBodyAdvice;
    }

    @Bean
    @ConditionalOnClass(InterfaceCryptoProcessor.class)
    @ConditionalOnMissingBean
    public DecryptRequestParamMapResolver decryptRequestParamStringResolver(InterfaceCryptoProcessor interfaceCryptoProcessor) {
        DecryptRequestParamMapResolver decryptRequestParamMapResolver = new DecryptRequestParamMapResolver();
        decryptRequestParamMapResolver.setInterfaceCryptoProcessor(interfaceCryptoProcessor);
        log.trace("[Herodotus] |- Bean [Decrypt Request Param Map Resolver] Auto Configure.");
        return decryptRequestParamMapResolver;
    }

    @Bean
    @ConditionalOnClass(InterfaceCryptoProcessor.class)
    @ConditionalOnMissingBean
    public DecryptRequestParamResolver decryptRequestParamResolver(InterfaceCryptoProcessor interfaceCryptoProcessor) {
        DecryptRequestParamResolver decryptRequestParamResolver = new DecryptRequestParamResolver();
        decryptRequestParamResolver.setInterfaceCryptoProcessor(interfaceCryptoProcessor);
        log.trace("[Herodotus] |- Bean [Decrypt Request Param Resolver] Auto Configure.");
        return decryptRequestParamResolver;
    }
}

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
 * File Name: IdempotentInterceptor.java
 * Author: gengwei.zheng
 * Date: 2021/09/01 12:34:01
 */

package cn.herodotus.eurynome.rest.security;

import cn.herodotus.eurynome.assistant.annotation.rest.Idempotent;
import cn.herodotus.eurynome.assistant.exception.operation.RepeatSubmissionException;
import cn.herodotus.eurynome.common.constant.magic.HttpHeaders;
import cn.herodotus.eurynome.common.constant.magic.SymbolConstants;
import cn.herodotus.eurynome.data.stamp.IdempotentStampManager;
import cn.hutool.crypto.SecureUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.format.DateTimeParseException;

/**
 * <p>Description: 幂等拦截器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/26 19:07
 */
public class IdempotentInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(IdempotentInterceptor.class);

    private static final String IDEMPOTENT_ATTRIBUTE = "Idempotent";

    private IdempotentStampManager idempotentStampManager;

    public void setIdempotentStampManager(IdempotentStampManager idempotentStampManager) {
        this.idempotentStampManager = idempotentStampManager;
    }

    private String generateKey(String sessionId, String url, String method) {

        if (StringUtils.isNotBlank(sessionId)) {
            String key = SecureUtil.md5(sessionId + SymbolConstants.COLON + url + SymbolConstants.COLON + method);
            log.debug("[Herodotus] |- IdempotentInterceptor key is [{}].", key);
            return key;
        } else {
            log.warn("[Herodotus] |- IdempotentInterceptor cannot create key, because sessionId is null.");
            return null;
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.trace("[Herodotus] |- IdempotentInterceptor preHandle postProcess.");

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        Idempotent idempotent = method.getAnnotation(Idempotent.class);
        if (idempotent != null) {
            // 幂等性校验, 根据缓存中是否存在Token进行校验。
            // 如果缓存中没有Token，通过放行, 同时在缓存中存入Token。
            // 如果缓存中有Token，意味着同一个操作反复操作，认为失败则抛出异常, 并通过统一异常处理返回友好提示
            String sessionId = request.getHeader(HttpHeaders.X_HERODOTUS_SESSION);
            if (StringUtils.isBlank(sessionId)) {
                HttpSession session = request.getSession();
                sessionId = session.getId();
            }

            String key = generateKey(sessionId, request.getRequestURI(), request.getMethod());
            if (StringUtils.isNotBlank(key)) {
                String token = idempotentStampManager.get(key);
                if (StringUtils.isBlank(token)) {
                    Duration configuredDuration = Duration.ZERO;
                    String annotationExpire = idempotent.expire();
                    if (StringUtils.isNotBlank(annotationExpire)) {
                        try {
                            configuredDuration = Duration.parse(annotationExpire);
                        } catch (DateTimeParseException e) {
                            log.warn("[Herodotus] |- AccessLimited duration value is incorrect, on api [{}].", request.getRequestURI());
                        }
                    }

                    if (!configuredDuration.isZero()) {
                        idempotentStampManager.create(key, configuredDuration);
                    } else {
                        idempotentStampManager.create(key);
                    }

                    request.setAttribute(IDEMPOTENT_ATTRIBUTE, key);

                    return true;
                } else {
                    throw new RepeatSubmissionException("Don't Repeat Submission");
                }
            }
        }

        return true;
    }
}

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
 * Module Name: eurynome-cloud-security
 * File Name: HerodotusAuthenticationEntryPoint.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.security.response;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.security.exception.SecurityGlobalExceptionHandler;
import cn.herodotus.eurynome.security.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义未认证处理
 *
 * @author gengwei.zheng
 */
public class HerodotusAuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint {

    private static final Logger log = LoggerFactory.getLogger(HerodotusAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException exception) throws IOException, ServletException {
        Result<String> result = SecurityGlobalExceptionHandler.resolveOauthException(exception, request.getRequestURI());
        response.setStatus(result.getStatus());
        WebUtils.renderJson(response, result);
    }
}
/*
 * Copyright (c) 2019-2021 the original author or authors.
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
 * File Name: SecurityGlobalExceptionHandler.java
 * Author: gengwei.zheng
 * Date: 2021/1/17 上午10:38
 * LastModified: 2020/6/24 下午3:46
 */

package cn.herodotus.eurynome.security.configuration;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.common.exception.GlobalExceptionHandler;
import cn.herodotus.eurynome.common.exception.PlatformException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Description: 统一异常处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/18 8:12
 */


@Slf4j
@ControllerAdvice
public class SecurityGlobalExceptionHandler extends GlobalExceptionHandler {

    /**
     * 定义错误显示页，error.html
     */
    public static final String DEFAULT_ERROR_VIEW = "/error";

    @ExceptionHandler({Exception.class, PlatformException.class})
    @ResponseBody
    public static Result<String> exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        Result<String> result = resolveException(ex, request.getRequestURI());
        response.setStatus(result.getStatus());
        return result;
    }

    /**
     * Rest Template 错误处理
     * @see :https://www.baeldung.com/spring-rest-template-error-handling
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({HttpClientErrorException.class, HttpServerErrorException.class})
    @ResponseBody
    public static Result<String> restTemplateException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        Result<String> result = resolveException(ex, request.getRequestURI());
        response.setStatus(result.getStatus());
        return result;
    }

    /**
     * 统一异常处理
     * AuthenticationException
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({AuthenticationException.class})
    @ResponseBody
    public static Result<String> authenticationException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        Result<String> result = resolveException(ex, request.getRequestURI());
        response.setStatus(result.getStatus());
        return result;
    }

    /**
     * OAuth2Exception
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({OAuth2Exception.class, ClientAuthenticationException.class})
    @ResponseBody
    public static Result<String> oauth2Exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        Result<String> result = resolveException(ex, request.getRequestURI());
        response.setStatus(result.getStatus());
        return result;
    }

    /**
     * 静态解析认证异常
     *
     * @param exception
     * @return
     */
    public static Result<String> resolveOauthException(Exception exception, String path) {

        Result<String> result;

        if (exception instanceof OAuth2Exception) {
            OAuth2Exception aex = (OAuth2Exception) exception;
            result = resolveException(OAuth2Exception.create(aex.getOAuth2ErrorCode(), aex.getMessage()), path);
        } else {
            result = resolveException(exception, exception.getMessage());
        }

        return result.message(exception.getMessage()).path(path);
    }

    public static ModelAndView errorView(Result<String> result) {
        // 设置跳转路径
        ModelAndView modelAndView = new ModelAndView(DEFAULT_ERROR_VIEW);
        // 将异常对象传递过去
        modelAndView.addObject(result);
        modelAndView.setViewName(DEFAULT_ERROR_VIEW);
        return modelAndView;
    }

}

/*
 * Copyright 2019-2019 the original author or authors.
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
 * Project Name: luban-cloud
 * Module Name: luban-cloud-component-security
 * File Name: WebGlobalExceptionHandler.java
 * Author: gengwei.zheng
 * Date: 2019/11/17 下午9:10
 * LastModified: 2019/11/17 下午8:55
 */

package cn.herodotus.eurynome.component.security.exception;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.common.enums.ResultStatus;
import cn.herodotus.eurynome.component.common.exception.GlobalExceptionHandler;
import cn.herodotus.eurynome.component.common.exception.PlatformException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

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

    @ExceptionHandler({Exception.class, PlatformException.class, AuthenticationException.class, OAuth2Exception.class, InvalidTokenException.class})
    @ResponseBody
    public static Result<Object> exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        Result<Object> result = resolveException(ex, request.getRequestURI());
        response.setStatus(result.getHttpStatus());
        return result;
    }

    /**
     * 静态解析认证异常
     *
     * @param ex
     * @return
     */
    public static Result<Object> resolveOauthException(Exception ex, String path) {

        Result<Object> result = getResult(ResultStatus.BAD_CREDENTIALS, HttpStatus.OK.value());

        if (ex instanceof OAuth2Exception) {
            OAuth2Exception aex = (OAuth2Exception) ex;
            result = resolveException(OAuth2Exception.create(aex.getOAuth2ErrorCode(), aex.getMessage()), path);
        } else {
            String error = Optional.ofNullable(ex.getMessage()).orElse("");
            if (error.contains(ResultStatus.USER_IS_DISABLED.getMessage())) {
                result.code(ResultStatus.ACCOUNT_DISABLED.getCode());
            }
        }

        return result.message(ex.getMessage()).path(path);
    }

    public static ModelAndView errorView(Result result) {
        // 设置跳转路径
        ModelAndView modelAndView = new ModelAndView(DEFAULT_ERROR_VIEW);
        // 将异常对象传递过去
        modelAndView.addObject(result);
        modelAndView.setViewName(DEFAULT_ERROR_VIEW);
        return modelAndView;
    }

}

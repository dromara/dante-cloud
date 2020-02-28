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
 * Module Name: luban-cloud-component-common
 * File Name: GlobalExceptionHandler.java
 * Author: gengwei.zheng
 * Date: 2019/11/8 下午4:59
 * LastModified: 2019/11/8 下午4:13
 */

package cn.herodotus.eurynome.component.common.exception;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.common.enums.ResultStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一异常处理器
 * @author gengwei.zheng
 */
@Slf4j
public class GlobalExceptionHandler {

    private static Map<String, Result> exceptionDictionary = new HashMap<>();

    static {
        exceptionDictionary.put("UsernameNotFoundException", getUnauthorizedResult(ResultStatus.USERNAME_NOT_FOUND));
        exceptionDictionary.put("BadCredentialsException", getUnauthorizedResult(ResultStatus.BAD_CREDENTIALS));
        exceptionDictionary.put("AccountExpiredException", getUnauthorizedResult(ResultStatus.ACCOUNT_EXPIRED));
        exceptionDictionary.put("LockedException", getUnauthorizedResult(ResultStatus.ACCOUNT_LOCKED));
        exceptionDictionary.put("DisabledException", getUnauthorizedResult(ResultStatus.ACCOUNT_DISABLED));
        exceptionDictionary.put("CredentialsExpiredException", getUnauthorizedResult(ResultStatus.CREDENTIALS_EXPIRED));
        exceptionDictionary.put("InvalidClientException", getUnauthorizedResult(ResultStatus.INVALID_CLIENT));
        exceptionDictionary.put("UnauthorizedClientException", getUnauthorizedResult(ResultStatus.UNAUTHORIZED_CLIENT));
        exceptionDictionary.put("InsufficientAuthenticationException", getUnauthorizedResult(ResultStatus.UNAUTHORIZED));
        exceptionDictionary.put("InvalidScopeException", getUnauthorizedResult(ResultStatus.INVALID_SCOPE));
        exceptionDictionary.put("InvalidTokenException", getResult(ResultStatus.INVALID_TOKEN, HttpStatus.SC_FORBIDDEN));
        exceptionDictionary.put("InvalidRequestException", getBadRequestResult(ResultStatus.INVALID_REQUEST));
        exceptionDictionary.put("RedirectMismatchException", getResult(ResultStatus.REDIRECT_URI_MISMATCH));
        exceptionDictionary.put("UnsupportedGrantTypeException", getResult(ResultStatus.UNSUPPORTED_GRANT_TYPE));
        exceptionDictionary.put("UnsupportedResponseTypeException", getResult(ResultStatus.UNSUPPORTED_RESPONSE_TYPE));
        exceptionDictionary.put("UserDeniedAuthorizationException", getResult(ResultStatus.ACCESS_DENIED));
        exceptionDictionary.put("HttpMessageNotReadableException", getBadRequestResult(ResultStatus.BAD_REQUEST));
        exceptionDictionary.put("TypeMismatchException", getBadRequestResult(ResultStatus.BAD_REQUEST));
        exceptionDictionary.put("MissingServletRequestParameterException", getBadRequestResult(ResultStatus.BAD_REQUEST));
        exceptionDictionary.put("NoHandlerFoundException", getResult(ResultStatus.NOT_FOUND, HttpStatus.SC_NOT_FOUND));
        exceptionDictionary.put("HttpRequestMethodNotSupportedException", getResult(ResultStatus.METHOD_NOT_ALLOWED, HttpStatus.SC_METHOD_NOT_ALLOWED));
        exceptionDictionary.put("HttpMediaTypeNotAcceptableException", getBadRequestResult(ResultStatus.MEDIA_TYPE_NOT_ACCEPTABLE));
        exceptionDictionary.put("IllegalArgumentException", getBadRequestResult(ResultStatus.ALERT));
        exceptionDictionary.put("InvalidGrantException", getUnauthorizedResult(ResultStatus.ALERT));
        exceptionDictionary.put("AccessDeniedException", getResult(ResultStatus.ACCESS_DENIED, HttpStatus.SC_FORBIDDEN));
        exceptionDictionary.put("MethodArgumentNotValidException", getResult(ResultStatus.ALERT));
    }

    private static Result getBadRequestResult(ResultStatus resultCode) {
        return getResult(resultCode, HttpStatus.SC_BAD_REQUEST);
    }

    private static Result getUnauthorizedResult(ResultStatus resultCode) {
        return getResult(resultCode, HttpStatus.SC_UNAUTHORIZED);
    }

    private static Result getResult(ResultStatus resultCode) {
        return getResult(resultCode, HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    protected static Result getResult(ResultStatus resultCode, int httpStatus) {
        return Result.failed().code(resultCode.getCode()).message(resultCode.getMessage()).httpStatus(httpStatus);
    }

    public static Result resolveException(Exception ex, String path) {

        log.debug("[Luban] |- Global Exception Handler, Path : [{}], Exception : [{}]", path, ex);

        Result result = Result.failed();

        String exceptionName = ex.getClass().getSimpleName();
        if (StringUtils.isNotEmpty(exceptionName)) {
            if (exceptionDictionary.containsKey(exceptionName)) {
                result = exceptionDictionary.get(exceptionName);
            } else {
                log.warn("[Luban] |- Global Exception Handler,  Can not find the exception name [{}] in dictionary, please do optimize ", exceptionName);
            }
        }
        result.path(path);

        log.error("[Luban] |- Global Exception Handler, Error is : {}", result);

        return result;
    }
}

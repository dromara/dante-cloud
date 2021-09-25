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
 * Module Name: eurynome-cloud-common
 * File Name: GlobalExceptionHandler.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.assistant.exception;

import cn.herodotus.eurynome.common.constant.enums.ResultStatus;
import cn.herodotus.eurynome.common.domain.Result;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一异常处理器
 *
 * @author gengwei.zheng
 */
public class HerodotusExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(HerodotusExceptionHandler.class);

    private static final Map<String, Result<String>> EXCEPTION_DICTIONARY = new HashMap<>();

    static {
        // 4**.** 对应错误
        // 401.** 对应错误
        EXCEPTION_DICTIONARY.put("UnauthorizedClientException", getUnauthorizedResult(ResultStatus.UNAUTHORIZED_CLIENT));
        EXCEPTION_DICTIONARY.put("AccessDeniedException", getUnauthorizedResult(ResultStatus.ACCESS_DENIED));
        EXCEPTION_DICTIONARY.put("AccessDeniedAuthorityLimitedException", getUnauthorizedResult(ResultStatus.ACCESS_DENIED_AUTHORITY_LIMITED));
        EXCEPTION_DICTIONARY.put("UserDeniedAuthorizationException", getInternalServerErrorResult(ResultStatus.ACCESS_DENIED));
        EXCEPTION_DICTIONARY.put("UsernameNotFoundException", getUnauthorizedResult(ResultStatus.USERNAME_NOT_FOUND));
        EXCEPTION_DICTIONARY.put("BadCredentialsException", getUnauthorizedResult(ResultStatus.BAD_CREDENTIALS));
        EXCEPTION_DICTIONARY.put("AccountExpiredException", getUnauthorizedResult(ResultStatus.ACCOUNT_EXPIRED));
        EXCEPTION_DICTIONARY.put("LockedException", getUnauthorizedResult(ResultStatus.ACCOUNT_LOCKED));
        EXCEPTION_DICTIONARY.put("DisabledException", getUnauthorizedResult(ResultStatus.ACCOUNT_DISABLED));
        EXCEPTION_DICTIONARY.put("CredentialsExpiredException", getUnauthorizedResult(ResultStatus.CREDENTIALS_EXPIRED));
        EXCEPTION_DICTIONARY.put("InsufficientAuthenticationException", getUnauthorizedResult(ResultStatus.UNAUTHORIZED));
        // 403.** 对应错误
        EXCEPTION_DICTIONARY.put("RepeatSubmissionException", getForbiddenResult(ResultStatus.REPEAT_SUBMISSION));
        EXCEPTION_DICTIONARY.put("FrequentRequestsException", getForbiddenResult(ResultStatus.FREQUENT_REQUESTS));
        // 404.** 对应错误
        EXCEPTION_DICTIONARY.put("NoHandlerFoundException", getResult(ResultStatus.HANDLER_NOT_FOUND, HttpStatus.SC_NOT_FOUND));
        // 405.** 对应错误
        EXCEPTION_DICTIONARY.put("HttpRequestMethodNotSupportedException", getResult(ResultStatus.METHOD_NOT_ALLOWED, HttpStatus.SC_METHOD_NOT_ALLOWED));
        // 406.** 对应错误
        EXCEPTION_DICTIONARY.put("UnsupportedGrantTypeException", getNotAcceptableResult(ResultStatus.UNSUPPORTED_GRANT_TYPE));
        EXCEPTION_DICTIONARY.put("UnsupportedResponseTypeException", getNotAcceptableResult(ResultStatus.UNSUPPORTED_RESPONSE_TYPE));
        // 412.** 对应错误
        EXCEPTION_DICTIONARY.put("InvalidGrantException", getPreconditionFailedResult(ResultStatus.INVALID_GRANT));
        EXCEPTION_DICTIONARY.put("InvalidTokenException", getPreconditionFailedResult(ResultStatus.INVALID_TOKEN));
        EXCEPTION_DICTIONARY.put("InvalidScopeException", getPreconditionFailedResult(ResultStatus.INVALID_SCOPE));
        EXCEPTION_DICTIONARY.put("InvalidClientException", getPreconditionFailedResult(ResultStatus.INVALID_GRANT));
        EXCEPTION_DICTIONARY.put("InvalidRequestException", getPreconditionFailedResult(ResultStatus.INVALID_REQUEST));
        EXCEPTION_DICTIONARY.put("RedirectMismatchException", getPreconditionFailedResult(ResultStatus.REDIRECT_URI_MISMATCH));
        // 415.** 对应错误
        EXCEPTION_DICTIONARY.put("HttpMediaTypeNotAcceptableException", getUnsupportedMediaTypeResult(ResultStatus.UNSUPPORTED_MEDIA_TYPE));

        // 5*.** 对应错误
        EXCEPTION_DICTIONARY.put("NullPointerException", getInternalServerErrorResult(ResultStatus.REDIRECT_URI_MISMATCH));
        EXCEPTION_DICTIONARY.put("IOException", getInternalServerErrorResult(ResultStatus.IO_EXCEPTION));
        EXCEPTION_DICTIONARY.put("HttpMessageNotReadableException", getInternalServerErrorResult(ResultStatus.HTTP_MESSAGE_NOT_READABLE_EXCEPTION));
        EXCEPTION_DICTIONARY.put("TypeMismatchException", getInternalServerErrorResult(ResultStatus.TYPE_MISMATCH_EXCEPTION));
        EXCEPTION_DICTIONARY.put("MissingServletRequestParameterException", getInternalServerErrorResult(ResultStatus.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION));
        EXCEPTION_DICTIONARY.put("IllegalArgumentException", getInternalServerErrorResult(ResultStatus.IllegalArgumentException));

        // 6*.** 对应错误
        EXCEPTION_DICTIONARY.put("BadSqlGrammarException", getInternalServerErrorResult(ResultStatus.BAD_SQL_GRAMMAR));
        EXCEPTION_DICTIONARY.put("DataIntegrityViolationException", getInternalServerErrorResult(ResultStatus.DATA_INTEGRITY_VIOLATION));
        EXCEPTION_DICTIONARY.put("TransactionRollbackException", getInternalServerErrorResult(ResultStatus.TRANSACTION_ROLLBACK));
        EXCEPTION_DICTIONARY.put("BindException", getValidationResult(ResultStatus.METHOD_ARGUMENT_NOT_VALID));
        EXCEPTION_DICTIONARY.put("MethodArgumentNotValidException", getValidationResult(ResultStatus.METHOD_ARGUMENT_NOT_VALID));

        // 7*.** 对应错误
        EXCEPTION_DICTIONARY.put("RedisPipelineException", getResult(ResultStatus.PIPELINE_INVALID_COMMANDS, HttpStatus.SC_INTERNAL_SERVER_ERROR));
        EXCEPTION_DICTIONARY.put("CacheConfigException", getResult(ResultStatus.CACHE_CONFIG_NOT_FOUND, HttpStatus.SC_NOT_IMPLEMENTED));
    }

    /**
     * 401	Unauthorized	请求要求用户的身份认证
     *
     * @param resultCode 401
     * @return {@link Result}
     */
    private static Result<String> getUnauthorizedResult(ResultStatus resultCode) {
        return getResult(resultCode, HttpStatus.SC_UNAUTHORIZED);
    }

    /**
     * 401	Unauthorized	请求要求用户的身份认证
     *
     * @param resultCode 401
     * @return {@link Result}
     */
    private static Result<String> getForbiddenResult(ResultStatus resultCode) {
        return getResult(resultCode, HttpStatus.SC_FORBIDDEN);
    }

    /**
     * 403	Forbidden	服务器理解请求客户端的请求，但是拒绝执行此请求
     *
     * @param resultCode 403
     * @return {@link Result}
     */
    private static Result<String> getNotAcceptableResult(ResultStatus resultCode) {
        return getResult(resultCode, HttpStatus.SC_NOT_ACCEPTABLE);
    }

    /**
     * 412 Precondition Failed	客户端请求信息的先决条件错误
     *
     * @param resultCode 412
     * @return {@link Result}
     */
    private static Result<String> getPreconditionFailedResult(ResultStatus resultCode) {
        return getResult(resultCode, HttpStatus.SC_PRECONDITION_FAILED);
    }

    /**
     * 415	Unsupported Media Type	服务器无法处理请求附带的媒体格式
     *
     * @param resultCode 415
     * @return {@link Result}
     */
    private static Result<String> getUnsupportedMediaTypeResult(ResultStatus resultCode) {
        return getResult(resultCode, HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
    }

    private static Result<String> getBadRequestResult(ResultStatus resultCode) {
        return getResult(resultCode, HttpStatus.SC_BAD_REQUEST);
    }

    private static Result<String> getValidationResult(ResultStatus resultCode) {
        return getResult(resultCode, HttpStatus.SC_NOT_ACCEPTABLE);
    }

    private static Result<String> getInternalServerErrorResult(ResultStatus resultCode) {
        return getResult(resultCode, HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    private static Result<String> getServiceUnavailableResult(ResultStatus resultCode) {
        return getResult(resultCode, HttpStatus.SC_SERVICE_UNAVAILABLE);
    }


    protected static Result<String> getResult(ResultStatus resultStatus, int httpStatus) {
        return new Result<String>().failed().code(resultStatus.getCode()).message(resultStatus.getMessage()).status(httpStatus);
    }

    public static Result<String> resolveException(Exception ex, String path) {

        log.trace("[Herodotus] |- Global Exception Handler, Path : [{}], Exception : [{}]", path, ex);

        Result<String> result = new Result<String>().failed();

        String exceptionName = ex.getClass().getSimpleName();
        if (StringUtils.isNotEmpty(exceptionName)) {
            if (EXCEPTION_DICTIONARY.containsKey(exceptionName)) {
                result = EXCEPTION_DICTIONARY.get(exceptionName);
            } else {
                log.warn("[Herodotus] |- Global Exception Handler,  Can not find the exception name [{}] in dictionary, please do optimize ", exceptionName);
            }
        }

        result.path(path);
        result.stackTrace(ex.getStackTrace());
        result.detail(ex.getMessage());

        log.debug("[Herodotus] |- Global Exception Handler, Error is : {}", result);

        return result;
    }
}

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
 * File Name: ResultStatus.java
 * Author: gengwei.zheng
 * Date: 2019/11/8 下午4:11
 * LastModified: 2019/10/28 上午11:56
 */

package cn.herodotus.eurynome.component.common.enums;

/**
 * 自定义返回码
 *
 * @author gengwei.zheng
 */

public enum ResultStatus {
    /**
     * 成功
     */
    OK(0, "Success"),
    FAIL(1000, "Fail"),
    ALERT(1001, "Alert"),

    /**
     * oauth2返回码
     */
    INVALID_TOKEN(2000, "Token has expired"),
    INVALID_SCOPE(2001, "Invalid Scope"),
    INVALID_REQUEST(2002, "Invalid Request"),
    INVALID_CLIENT(2003, "Invalid Client"),
    INVALID_GRANT(2004, "Invalid Grant"),
    REDIRECT_URI_MISMATCH(2005, "Redirect Uri Mismatch"),
    UNAUTHORIZED_CLIENT(2006, "Unauthorized Client"),
    EXPIRED_TOKEN(2007, "Expired Token"),
    UNSUPPORTED_GRANT_TYPE(2008, "Unsupported Grant Type"),
    UNSUPPORTED_RESPONSE_TYPE(2009, "Unsupported Response Type"),
    UNSUPPORTED_MEDIA_TYPE(2010, "Unsupported Media Type"),
    UNAUTHORIZED(2012, "Unauthorized"),
    SIGNATURE_DENIED(2013, "Signature Denied"),

    ACCESS_DENIED(4030, "Access Denied"),
    ACCESS_DENIED_BLACK_IP_LIMITED(4031, "Access Denied Black Ip Limited"),
    ACCESS_DENIED_WHITE_IP_LIMITED(4032, "Access Denied White Ip Limited"),
    ACCESS_DENIED_AUTHORITY_EXPIRED(4033, "Access Denied Authority Expired"),
    ACCESS_DENIED_UPDATING(4034, "Access Denied Updating"),
    ACCESS_DENIED_DISABLED(4035, "Access Denied Disabled"),
    ACCESS_DENIED_NOT_OPEN(4036, "Access Denied Not Open"),
    /**
     * 账号错误
     */
    BAD_CREDENTIALS(3000, "Bad Credentials"),
    ACCOUNT_DISABLED(3001, "Account Disabled"),
    ACCOUNT_EXPIRED(3002, "Account Expired"),
    CREDENTIALS_EXPIRED(3003, "Credentials Expired"),
    ACCOUNT_LOCKED(3004, "Account Locked"),
    USERNAME_NOT_FOUND(3005, "Username Not Found"),
    USER_IS_DISABLED(3006, "User is disabled"),


    /**
     * 请求错误
     */
    BAD_REQUEST(4000, "Bad Request"),
    NOT_FOUND(4004, "Not Found"),
    METHOD_NOT_ALLOWED(4005, "Method Not Allowed"),
    MEDIA_TYPE_NOT_ACCEPTABLE(4006, "Media Type Not Acceptable"),
    TOO_MANY_REQUESTS(4029, "Too Many Requests"),
    /**
     * 系统错误
     */
    ERROR(5000, "Error"),
    GATEWAY_TIMEOUT(5004, "Gateway Timeout"),
    SERVICE_UNAVAILABLE(5003, "Service Unavailable");


    private int code;
    private String message;

    ResultStatus() {
    }

    private ResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResultStatus getResultEnum(int code) {
        for (ResultStatus type : ResultStatus.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return ERROR;
    }

    public static ResultStatus getResultEnum(String message) {
        for (ResultStatus type : ResultStatus.values()) {
            if (type.getMessage().equals(message)) {
                return type;
            }
        }
        return ERROR;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}

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

package cn.herodotus.eurynome.common.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 自定义返回码
 * <p>
 * 主要分类说明：
 * 1*.**    认证授权，服务器收到请求，身份认证等安全性信息
 * 2*.**    成功，操作被成功接收并处理
 * 3*.**	需要后续操作，需要进一步的操作以完成请求
 * 4*.**	Java常规错误，请求包含语法错误或无法完成请求，
 * 40.**	HTTP请求错误，请求包含语法错误或无法完成请求，
 * 5*.**    平台错误，平台相关组件运行及操作错误。
 * 6*.**	关系数据库错误，服务器在处理请求的过程中发生了数据SQL操作等底层错误
 * 60.**	JDBC错误，服务器在处理请求的过程中发生了JDBC底层错误。
 * 61.**	JPA错误，服务器在处理请求的过程中发生了JPA错误。
 * 62.**	Hibernate错误，服务器在处理请求的过程中发生了Hibernate操作错误。
 * <p>
 * 其它内容逐步补充
 *
 * @author gengwei.zheng
 */
@ApiModel(description = "响应结果状态")
public enum ResultStatus {

    /**
     * 1*.**	认证授权
     */
    INVALID_TOKEN(1000, "无法解析的Token，也许Token已经失效"),
    INVALID_GRANT(1001, "授权模式错误"),
    INVALID_SCOPE(1002, "授权范围错误"),
    INVALID_CLIENT(1003, "非法的客户端"),

    ACCESS_DENIED(1100, "拒绝访问"),

    /**
     * 2*.** 成功
     */
    OK(2000, "成功"),

    /**
     * 4*.**	Java常规错误
     */
    FAIL(4000, "失败"),
    WARNING(4001, "警告"),

    METHOD_NOT_ALLOWED(4105, "请求方法不支持"),

    /**
     * 5*.* 为数据操作相关错误
     */
    BAD_SQL_GRAMMAR(6000, "低级SQL语法错误，检查SQL能否正常运行或者字段名称是否正确"),
    DATA_INTEGRITY_VIOLATION(6200, "该数据正在被其它数据引用，请先删除引用关系，再进行数据删除操作"),


    // TODO: 以下状态码和错误信息要重新梳理，重点要看自定义错误码以及对应的HttpStatus是否合适
    /**
     * oauth2返回码
     */


    INVALID_REQUEST(2002, "Invalid Request"),
    REDIRECT_URI_MISMATCH(2005, "Redirect Uri Mismatch"),
    UNAUTHORIZED_CLIENT(2006, "Unauthorized Client"),
    EXPIRED_TOKEN(2007, "Expired Token"),
    UNSUPPORTED_GRANT_TYPE(2008, "Unsupported Grant Type"),
    UNSUPPORTED_RESPONSE_TYPE(2009, "Unsupported Response Type"),
    UNSUPPORTED_MEDIA_TYPE(2010, "Unsupported Media Type"),
    UNAUTHORIZED(2012, "Unauthorized"),
    SIGNATURE_DENIED(2013, "Signature Denied"),


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
    BAD_REQUEST(4100, "Bad Request"),
    NOT_FOUND(4104, "Not Found"),

    MEDIA_TYPE_NOT_ACCEPTABLE(4106, "Media Type Not Acceptable"),
    TOO_MANY_REQUESTS(4129, "Too Many Requests"),
    /**
     * 系统错误
     */
    ERROR(5220, "Error"),
    GATEWAY_TIMEOUT(5004, "Gateway Timeout"),
    SERVICE_UNAVAILABLE(5003, "Service Unavailable");

    @ApiModelProperty(value = "结果代码")
    private final int code;
    @ApiModelProperty(value = "结果信息")
    private final String message;


    ResultStatus(int code, String message) {
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

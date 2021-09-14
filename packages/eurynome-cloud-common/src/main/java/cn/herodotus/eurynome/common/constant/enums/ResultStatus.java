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
 * Module Name: eurynome-cloud-constant
 * File Name: ResultStatus.java
 * Author: gengwei.zheng
 * Date: 2021/06/29 15:58:29
 */

package cn.herodotus.eurynome.common.constant.enums;

import io.swagger.v3.oas.annotations.media.Schema;

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
public enum ResultStatus {

    /**
     * 2*.** 成功
     */
    OK(20000, "成功"),

    /**
     * 4*.** Java常规错误
     */
    FAIL(40000, "失败"),
    WARNING(40001, "警告"),

    /**
     * 401.** 未经授权 Unauthorized	请求要求用户的身份认证
     */
    UNAUTHORIZED(40101, "未经授权，无法访问"),
    UNAUTHORIZED_CLIENT(40102, "未经授权的 Client"),
    ACCESS_DENIED(40103, "拒绝访问"),
    ACCESS_DENIED_AUTHORITY_LIMITED(40104, "权限不足，拒绝访问"),
    BAD_CREDENTIALS(40105, "无效的凭证"),
    ACCOUNT_DISABLED(40106, "账户禁用"),
    ACCOUNT_EXPIRED(40107, "账户过期"),
    CREDENTIALS_EXPIRED(40108, "凭证过期"),
    ACCOUNT_LOCKED(40109, "账户被锁定"),
    USERNAME_NOT_FOUND(40110, "用户未找到"),
    USER_IS_DISABLED(40111, "用户被禁用"),
    /**
     * 403.** 禁止的请求，与403对应
     */
    REPEAT_SUBMISSION(40301, "不要重复提交"),
    FREQUENT_REQUESTS(40302, "请求过于频繁请稍后再试"),
    SQL_INJECTION_REQUEST(40603, "疑似SQL注入请求"),
    /**
     *
     */
    NOT_FOUND(40400, "资源未找到"),
    HANDLER_NOT_FOUND(40401, "处理器未找到"),

    /**
     * 405.** 方法不允许 与405对应
     */
    METHOD_NOT_ALLOWED(40501, "请求方法不支持"),
    /**
     * 406.** 不接受的请求，与406对应
     */
    UNSUPPORTED_GRANT_TYPE(40601, "不支持的 Grant Type"),
    UNSUPPORTED_RESPONSE_TYPE(40602, "不支持的 Response Type"),
    /**
     * 412.* 未经授权 Precondition Failed 客户端请求信息的先决条件错误
     */
    INVALID_TOKEN(41201, "无法解析的Token，也许Token已经失效"),
    INVALID_GRANT(41202, "账号或者密码错误！"),
    INVALID_SCOPE(41203, "授权范围错误"),
    INVALID_CLIENT(41204, "非法的客户端"),
    INVALID_REQUEST(41205, "无效的请求，参数使用错误或配置无效."),
    REDIRECT_URI_MISMATCH(41206, "Redirect Uri Mismatch"),
    /**
     * 415.*	Unsupported Media Type	服务器无法处理请求附带的媒体格式
     */
    UNSUPPORTED_MEDIA_TYPE(41501, "不支持的 Media Type"),

    /**
     * 5*.00 系统错误
     */
    ERROR(50000, "Error"),
    NULL_POINTER_EXCEPTION(50001, "后台代码出现了空值"),
    IO_EXCEPTION(50002, "IO异常"),
    HTTP_MESSAGE_NOT_READABLE_EXCEPTION(50003, "Http Message 不可读异常"),
    TYPE_MISMATCH_EXCEPTION(50004, "类型不匹配"),
    MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION(50005, "Servlet请求参数缺失"),
    IllegalArgumentException(50006, "非法参数错误"),

    SERVICE_UNAVAILABLE(50301, "Service Unavailable"),
    GATEWAY_TIMEOUT(50500, "Gateway Timeout"),

    /**
     * 6*.* 为数据操作相关错误
     */
    BAD_SQL_GRAMMAR(60000, "低级SQL语法错误，检查SQL能否正常运行或者字段名称是否正确"),
    /**
     * 62.* 数据库操作相关错误
     */
    DATA_INTEGRITY_VIOLATION(62000, "该数据正在被其它数据引用，请先删除引用关系，再进行数据删除操作"),
    /**
     * 63.* Spring Boot Validation校验相关操作
     */
    METHOD_ARGUMENT_NOT_VALID(63000, "接口参数校验错误"),
    /**
     * 64.* 临时数据操作相关错误
     */
    TOKEN_DELETE_FAILED(64000, "Token 删除失败"),

    /**
     * 7*.* 基础设施交互错误
     * 71.* Redis 操作出现错误
     * 72.* Cache 操作出现错误
     */
    PIPELINE_INVALID_COMMANDS(71000, "Redis管道包含一个或多个无效命令"),
    CACHE_CONFIG_NOT_FOUND(72000, "服务需要使用缓存，但是未找到*-cache.yaml配置");

    @Schema(title = "结果代码")
    private final int code;
    @Schema(title = "结果信息")
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

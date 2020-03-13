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
 * File Name: Result.java
 * Author: gengwei.zheng
 * Date: 2019/11/8 下午4:11
 * LastModified: 2019/10/28 上午11:56
 */

package cn.herodotus.eurynome.component.common.domain;


import cn.herodotus.eurynome.component.common.enums.ResultStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import org.apache.http.HttpStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Description: 自定义统一响应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/29 14:50
 */
@ApiModel(value = "Result", description = "自定义统一响应实体")
public class Result<T> implements Serializable {

    /**
     * 响应编码
     */
    @ApiParam(name = "code", value = "自定义响应编码", defaultValue = "0")
    private int code = 0;
    /**
     * 提示消息
     */
    @ApiParam(name = "message", value = "响应提示信息")
    private String message;

    /**
     * 请求路径
     */
    @ApiParam(name = "path", value = "请求路径")
    private String path;

    /**
     * 响应数据
     */
    @ApiParam(name = "data", value = "响应数据")
    private T data;

    /**
     * http状态码
     */
    @ApiParam(name = "httpStatus", value = "http状态码")
    private int httpStatus;

    /**
     * 附加数据
     */
    @ApiParam(name = "error", value = "错误信息")
    private Throwable error;

    /**
     * 响应时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp = new Date();

    public Result() {
        super();
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public T getData() {
        return data;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Result<T> ok() {
        this.code = ResultStatus.OK.getCode();
        this.message = ResultStatus.OK.getMessage();
        this.httpStatus = HttpStatus.SC_OK;
        return this;
    }

    public Result<T> failed() {
        this.code = ResultStatus.FAIL.getCode();
        this.message = ResultStatus.FAIL.getMessage();
        this.httpStatus = HttpStatus.SC_INTERNAL_SERVER_ERROR;
        return this;
    }

    public Result<T> code(int code) {
        this.code = code;
        return this;
    }

    public Result<T> message(String message) {
        this.message = message;
        return this;
    }

    public Result<T> data(T data) {
        this.data = data;
        return this;
    }

    public Result<T> path(String path) {
        this.path = path;
        return this;
    }

    public Result<T> type(ResultStatus resultStatus) {
        this.code = resultStatus.getCode();
        this.message = resultStatus.getMessage();
        return this;
    }

    public Result<T> httpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public Result<T> error(Throwable error) {
        this.error = error;
        this.message = error.getMessage();
        return this;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                ", data=" + data +
                ", httpStatus=" + httpStatus +
                ", error=" + error +
                ", timestamp=" + timestamp +
                '}';
    }
}

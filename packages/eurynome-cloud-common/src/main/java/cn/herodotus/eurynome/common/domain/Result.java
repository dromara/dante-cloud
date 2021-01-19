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

package cn.herodotus.eurynome.common.domain;


import cn.herodotus.eurynome.common.enums.ResultStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.http.HttpStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: 统一响应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/29 14:50
 */
@ApiModel(description = "统一响应实体")
public class Result<T> implements Serializable {

    @ApiModelProperty(value = "自定义响应编码")
    private int code = 0;

    @ApiModelProperty(value = "响应返回信息")
    private String message;

    @ApiModelProperty(value = "请求路径")
    private String path;

    @ApiModelProperty(value = "响应返回数据")
    private T data;

    @ApiModelProperty(value = "http状态码")
    private int status;

    @ApiModelProperty(value = "错误堆栈信息")
    private Throwable error;

    @ApiModelProperty(value = "响应时间戳")
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

    public int getStatus() {
        return status;
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
        this.status = HttpStatus.SC_OK;
        return this;
    }

    public Result<T> failed() {
        this.code = ResultStatus.FAIL.getCode();
        this.message = ResultStatus.FAIL.getMessage();
        this.status = HttpStatus.SC_INTERNAL_SERVER_ERROR;
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

    public Result<T> status(int httpStatus) {
        this.status = httpStatus;
        return this;
    }

    public Result<T> error(Throwable error) {
        this.error = error;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("code", code)
                .add("message", message)
                .add("path", path)
                .add("data", data)
                .add("status", status)
                .add("error", error)
                .add("timestamp", timestamp)
                .toString();
    }

    public Map<String, Object> toModel() {
        Map<String, Object> result = new HashMap<>(8);
        result.put("code", code);
        result.put("message", message);
        result.put("path", path);
        result.put("data", data);
        result.put("status", status);
        result.put("error", error);
        result.put("timestamp", timestamp);
        return result;
    }
}

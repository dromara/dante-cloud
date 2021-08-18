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
 * Module Name: eurynome-cloud-common
 * File Name: Error.java
 * Author: gengwei.zheng
 * Date: 2021/08/18 18:12:18
 */

package cn.herodotus.eurynome.common.domain;

import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>Description: 错误详情 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/18 18:12
 */
@ApiModel(description = "响应错误详情")
public class Error implements Serializable {

    @ApiModelProperty(value = "Exception完整信息")
    private String detail;

    @ApiModelProperty(value = "额外的错误信息，目前主要是Validation的Message")
    private String message;

    @ApiModelProperty(value = "额外的错误代码，目前主要是Validation的Code")
    private String code;

    @ApiModelProperty(value = "额外的错误字段，目前主要是Validation的Field")
    private String field;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("detail", detail)
                .add("message", message)
                .add("code", code)
                .add("field", field)
                .toString();
    }
}

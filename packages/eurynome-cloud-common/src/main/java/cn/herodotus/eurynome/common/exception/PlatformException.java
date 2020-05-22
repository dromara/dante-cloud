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
 * File Name: PlatformException.java
 * Author: gengwei.zheng
 * Date: 2019/11/8 下午4:12
 * LastModified: 2019/11/8 下午4:12
 */

package cn.herodotus.eurynome.common.exception;

import cn.herodotus.eurynome.common.enums.ResultStatus;

/**
 * <p>Description: 平台基础Exception </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/12/18 15:31
 */
public class PlatformException extends RuntimeException {

    private int code = ResultStatus.ERROR.getCode();

    public PlatformException() {
    }

    public PlatformException(String message) {
        super(message);
    }

    public PlatformException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlatformException(Throwable cause) {
        super(cause);
    }

    public PlatformException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}

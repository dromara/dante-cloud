/*
 * Copyright 2019-2020 the original author or authors.
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
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-component-data
 * File Name: DataSourceNotExistException.java
 * Author: gengwei.zheng
 * Date: 2020/5/22 下午2:22
 * LastModified: 2020/5/22 下午2:22
 */

package cn.herodotus.eurynome.data.datasource.exception;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: DataSourceNotExistException </p>
 *
 * <p>Description: 数据源不存在 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/22 14:22
 */
public class DataSourceNotExistException extends DataSourceException {

    public DataSourceNotExistException() {
    }

    public DataSourceNotExistException(String message) {
        super(message);
    }

    public DataSourceNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataSourceNotExistException(Throwable cause) {
        super(cause);
    }

    public DataSourceNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

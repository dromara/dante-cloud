/*
 * Copyright (c) 2019-2020 the original author or authors.
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
 * Module Name: eurynome-cloud-data
 * File Name: DataSourceBeanCreationException.java
 * Author: gengwei.zheng
 * Date: 2020/5/24 下午4:49
 * LastModified: 2020/5/24 下午4:49
 */

package cn.herodotus.eurynome.data.datasource.exception;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: DataSourceMetadataCreationException </p>
 *
 * <p>Description: 数据源元数据配置错误 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/24 16:49
 */
public class DataSourceMetadataCreationException extends DataSourceConfigureException {

    public DataSourceMetadataCreationException() {
    }

    public DataSourceMetadataCreationException(String message) {
        super(message);
    }

    public DataSourceMetadataCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataSourceMetadataCreationException(Throwable cause) {
        super(cause);
    }

    public DataSourceMetadataCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

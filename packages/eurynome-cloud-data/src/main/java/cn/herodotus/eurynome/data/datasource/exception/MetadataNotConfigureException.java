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
 * File Name: DataSourceMetadataNotConfiguredException.java
 * Author: gengwei.zheng
 * Date: 2020/5/22 下午1:43
 * LastModified: 2020/5/22 下午1:43
 */

package cn.herodotus.eurynome.data.datasource.exception;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: DataSourceMetadataNotConfiguredException </p>
 *
 * <p>Description: 数据源没有设置错误 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/22 13:43
 */
public class MetadataNotConfigureException extends DataSourceConfigureException {

    public MetadataNotConfigureException() {
        super("Must config the dynamic data sources!");
    }
}

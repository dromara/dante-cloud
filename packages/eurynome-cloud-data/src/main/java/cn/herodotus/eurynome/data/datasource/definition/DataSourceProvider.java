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
 * File Name: DataSourceProvider.java
 * Author: gengwei.zheng
 * Date: 2020/5/22 下午2:36
 * LastModified: 2020/5/22 下午2:34
 */

package cn.herodotus.eurynome.data.datasource.definition;

import org.springframework.lang.NonNull;

import javax.sql.DataSource;
import java.util.Map;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: DataSourceProvider </p>
 *
 * <p>Description: 多数据源加载接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/22 14:34
 */
public interface DataSourceProvider {

    /**
     * 获取所有数据源
     *
     * @return 所有数据源，key为数据源名称
     */
    @NonNull
    Map<String, DataSource> getDataSources();
}

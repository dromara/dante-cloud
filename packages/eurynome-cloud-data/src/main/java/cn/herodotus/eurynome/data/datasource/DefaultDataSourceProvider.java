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
 * File Name: DynamicDataSourceProvider.java
 * Author: gengwei.zheng
 * Date: 2020/5/22 下午2:12
 * LastModified: 2020/5/22 下午2:12
 */

package cn.herodotus.eurynome.data.datasource;

import cn.herodotus.eurynome.data.datasource.definition.DataSourceProvider;
import cn.herodotus.eurynome.data.datasource.domain.DataSourceMetadata;
import cn.herodotus.eurynome.data.datasource.properties.DataSourceProperties;
import cn.herodotus.eurynome.data.datasource.properties.HikariProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: DynamicDataSourceProvider </p>
 *
 * <p>Description: 默认的数据源对象组装 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/22 14:12
 */
public class DefaultDataSourceProvider implements DataSourceProvider {

    private DataSourceProperties dataSourceProperties;
    private final HikariProperties hikariProperties;
    private final Map<String, DataSourceMetadata> dataSourceMetadatas;
    private final String primary;
    private final Boolean p6spy;

    public DefaultDataSourceProvider(DataSourceProperties dataSourceProperties, HikariProperties hikariProperties) {
        this.dataSourceProperties = dataSourceProperties;
        this.hikariProperties = hikariProperties;
        this.dataSourceMetadatas = dataSourceProperties.getMetadatas();
        this.primary = dataSourceProperties.getPrimary();
        this.p6spy = dataSourceProperties.getP6spy();
    }

    private Map<String, DataSource> createDataSources() {
        Map<String, DataSource> dataSources = new HashMap<>(dataSourceMetadatas.size());
        for (Map.Entry<String, DataSourceMetadata> item : dataSourceMetadatas.entrySet()) {
            DataSourceMetadata dataSourceMetadata = item.getValue();
            String poolName = dataSourceMetadata.getPoolName();
            if (StringUtils.isBlank(poolName)) {
                poolName = item.getKey();
            }
            dataSourceMetadata.setPoolName(poolName);
            dataSources.put(item.getKey(), createHikariDataSource(dataSourceMetadata));
        }
        return dataSources;
    }

    private DataSource createHikariDataSource(DataSourceMetadata dataSourceMetadata) {
        HikariConfig config = hikariProperties.getHikariConfig();
        config.setUsername(dataSourceMetadata.getUsername());
        config.setPassword(dataSourceMetadata.getPassword());
        config.setJdbcUrl(dataSourceMetadata.getUrl());
        config.setDriverClassName(dataSourceMetadata.getDriverClassName());
        config.setPoolName(dataSourceMetadata.getPoolName());
        return new HikariDataSource(config);
    }

    @Override
    public Map<String, DataSource> getDataSources() {
        return createDataSources();
    }

    @Override
    public String getPrimary() {
        return this.primary;
    }

    @Override
    public Boolean getP6spy() {
        return this.p6spy;
    }
}

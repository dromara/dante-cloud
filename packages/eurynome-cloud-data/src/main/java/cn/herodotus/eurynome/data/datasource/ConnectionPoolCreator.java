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
 * File Name: ConnectionPoolCreator.java
 * Author: gengwei.zheng
 * Date: 2020/5/21 下午2:27
 * LastModified: 2020/5/21 下午2:27
 */

package cn.herodotus.eurynome.data.datasource;

import cn.herodotus.eurynome.data.datasource.domain.DataSourceMetadata;
import cn.herodotus.eurynome.data.datasource.properties.HikariProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: ConnectionPoolCreator </p>
 *
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/21 14:27
 */
public class ConnectionPoolCreator {

    public DataSource createHikariDataSource(DataSourceMetadata dataSourceMetadata, HikariProperties hikariProperties) {
        HikariConfig config = hikariProperties.getHikariConfig();
        config.setUsername(dataSourceMetadata.getUsername());
        config.setPassword(dataSourceMetadata.getPassword());
        config.setJdbcUrl(dataSourceMetadata.getUrl());
        config.setDriverClassName(dataSourceMetadata.getDriverClassName());
        config.setPoolName(dataSourceMetadata.getPoolName());
        return new HikariDataSource(config);
    }
}

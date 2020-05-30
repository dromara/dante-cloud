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
 * File Name: DynamicDataSource.java
 * Author: gengwei.zheng
 * Date: 2020/5/21 上午10:43
 * LastModified: 2020/5/21 上午10:43
 */

package cn.herodotus.eurynome.data.datasource;

import cn.herodotus.eurynome.data.datasource.exception.DataSourceException;
import cn.herodotus.eurynome.data.datasource.exception.DataSourceNotExistException;
import cn.herodotus.eurynome.data.datasource.exception.RemoveDataSourceException;
import cn.herodotus.eurynome.data.datasource.properties.DataSourceMetadata;
import cn.herodotus.eurynome.data.datasource.properties.DataSourceProperties;
import cn.herodotus.eurynome.data.datasource.utils.SqlScriptExecutor;
import com.p6spy.engine.spy.P6DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: HerodotusDynamicDataSource </p>
 *
 * <p>Description: 自定义动态数据源 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/21 10:43
 */
@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    public static final String DEFAULT_DATASOURCE = "LOCAL_STORAGE";

    private final DataSourceProperties dataSourceProperties;
    private final HikariConfig hikariConfig;
    /**
     * 所有数据源
     */
    private final Map<String, DataSource> dataSources = new LinkedHashMap<>();
    private String primary = DEFAULT_DATASOURCE;
    private boolean p6spy;

    public DynamicRoutingDataSource(DataSourceProperties dataSourceProperties, HikariConfig hikariConfig) {
        this.dataSourceProperties = dataSourceProperties;
        this.hikariConfig = hikariConfig;
        this.setPrimary(dataSourceProperties.getPrimary());
        this.setP6spy(dataSourceProperties.getP6spy());
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public void setP6spy(boolean p6spy) {
        if (p6spy) {
            try {
                Class.forName("com.p6spy.engine.spy.P6DataSource");
                log.info("[Herodotus] |- Detect P6SPY plugin and enabled it");
                this.p6spy = true;
            } catch (Exception e) {
                log.warn("[Herodotus] |- Enabled P6SPY, but cannot found p6spy dependency");
            }
        } else {
            this.p6spy = false;
        }
    }

    /**
     * 使用H2作为本地默认存储
     * jpa动态数据源对动态数据源支持不好。jdbc和mybaites支持
     *
     * @return {DataSourceMetadata}
     * @see :https://www.imooc.com/article/43065
     */
    private DataSourceMetadata createLocalStroageMetadata() {
        DataSourceMetadata dataSourceMetadata = new DataSourceMetadata();
        dataSourceMetadata.setDriverClassName(EmbeddedDatabaseConnection.H2.getDriverClassName());
        dataSourceMetadata.setUrl(EmbeddedDatabaseConnection.H2.getUrl(DEFAULT_DATASOURCE));
        dataSourceMetadata.setUsername("herodotus");
        dataSourceMetadata.setPassword(DEFAULT_DATASOURCE);
        dataSourceMetadata.setPoolName(DEFAULT_DATASOURCE);
        dataSourceMetadata.setSchema(Collections.singletonList("classpath:localstorage-schema-h2.sql"));
        return dataSourceMetadata;
    }

    private HikariDataSource createHikariDataSource(DataSourceMetadata dataSourceMetadata, HikariConfig hikariConfig) {
        hikariConfig.setPoolName(dataSourceMetadata.getPoolName());
        hikariConfig.setDriverClassName(dataSourceMetadata.determineDriverClassName());
        hikariConfig.setJdbcUrl(dataSourceMetadata.determineUrl());
        hikariConfig.setUsername(dataSourceMetadata.determineUsername());
        hikariConfig.setPassword(dataSourceMetadata.determinePassword());
        return new HikariDataSource(hikariConfig);
    }

    private void scriptRunner(DataSourceMetadata dataSourceMetadata, DataSource dataSource) {
        List<String> schema = dataSourceMetadata.getSchema();
        List<String> data = dataSourceMetadata.getData();

        List<String> scripts = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(schema)) {
            scripts.addAll(schema);
        }
        if (CollectionUtils.isNotEmpty(data)) {
            scripts.addAll(data);
        }

        if (CollectionUtils.isNotEmpty(scripts)) {
            SqlScriptExecutor executor = new SqlScriptExecutor(dataSourceMetadata.isContinueOnError(), dataSourceMetadata.getSeparator());
            scripts.forEach(script -> executor.run(dataSource, script));
        }
    }

    private DataSource createDataSource(DataSourceMetadata dataSourceMetadata, HikariConfig hikariConfig) {
        HikariDataSource hikariDataSource = createHikariDataSource(dataSourceMetadata, hikariConfig);
        if (ObjectUtils.isNotEmpty(hikariDataSource)) {
            scriptRunner(dataSourceMetadata, hikariDataSource);
        }
        return hikariDataSource;
    }


    private DataSource wrapDataSource(String dataSourceName, DataSource dataSource) {
        if (p6spy) {
            dataSource = new P6DataSource(dataSource);
            log.info("[Herodotus] |- [{}] wrap p6spy plugin", dataSourceName);
        }
        return dataSource;
    }

    /**
     * 添加数据源
     *
     * @param dataSourceName 数据源名称
     * @param dataSource     数据源
     */
    public synchronized void addDataSource(String dataSourceName, DataSource dataSource) {
        if (!dataSources.containsKey(dataSourceName)) {
            dataSource = wrapDataSource(dataSourceName, dataSource);
            dataSources.put(dataSourceName, dataSource);
            log.info("[Herodotus] |- Load a datasource named [{}] success", dataSourceName);
        } else {
            log.warn("[Herodotus] |- Load a datasource named [{}] failed, because it already exist", dataSourceName);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        addDataSource(DEFAULT_DATASOURCE, createDataSource(createLocalStroageMetadata(), hikariConfig));

        if (MapUtils.isNotEmpty(dataSourceProperties.getMetadata())) {
            for (Map.Entry<String, DataSourceMetadata> item : dataSourceProperties.getMetadata().entrySet()) {
                DataSourceMetadata dataSourceMetadata = item.getValue();
                String poolName = dataSourceMetadata.getPoolName();
                if (StringUtils.isBlank(poolName)) {
                    poolName = item.getKey();
                }
                dataSourceMetadata.setPoolName(poolName);
                addDataSource(item.getKey(), createDataSource(dataSourceMetadata, hikariConfig));
            }
        }

        log.info("[Herodotus] |- initial loaded [{}] datasource,primary datasource named [{}]", dataSources.size(), primary);
    }

    /**
     * 获取数据源
     *
     * @param dataSourceName 数据源名称
     * @return 数据源
     */
    public DataSource getDataSource(String dataSourceName) {
        if (StringUtils.isBlank(dataSourceName)) {
            log.debug("[Herodotus] |- Switch to the primary datasource");
            return dataSources.get(primary);
        } else if (dataSources.containsKey(dataSourceName)) {
            return dataSources.get(dataSourceName);
        } else {
            throw new DataSourceNotExistException("Could not find a datasource named" + dataSourceName);
        }
    }

    /**
     * 删除数据源
     *
     * @param dataSourceName 数据源名称
     */
    public synchronized void removeDataSource(String dataSourceName) {
        if (StringUtils.isBlank(dataSourceName)) {
            throw new IllegalArgumentException("dataSourceName parameter could not be empty");
        }
        if (StringUtils.equals(dataSourceName, primary)) {
            throw new RemoveDataSourceException("could not remove primary datasource");
        }
        if (dataSources.containsKey(dataSourceName)) {
            DataSource dataSource = dataSources.get(dataSourceName);
            try {
                closeDataSource(dataSourceName, dataSource);
            } catch (Exception e) {
                throw new DataSourceException("remove the database named " + dataSourceName + " failed", e);
            }
            dataSources.remove(dataSourceName);

            log.info("[Herodotus] |- Remove the database named [{}] success", dataSourceName);
        } else {
            log.warn("[Herodotus] |- Could not find a database named [{}]", dataSourceName);
        }
    }

    @Override
    protected DataSource determineDataSource() {
        return getDataSource(DynamicDataSourceContextHolder.peek());
    }

    @Override
    public void destroy() throws Exception {
        log.info("[Herodotus] |-  Start closing ....");
        for (Map.Entry<String, DataSource> entry : dataSources.entrySet()) {
            String key = entry.getKey();
            DataSource value = entry.getValue();
            closeDataSource(key, value);
        }
        log.info("[Herodotus] |- All closed success.");
    }

    private void closeDataSource(String name, DataSource dataSource) throws NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        Class<? extends DataSource> clazz = dataSource.getClass();
        try {
            Method closeMethod = clazz.getDeclaredMethod("close");
            closeMethod.invoke(dataSource);
        } catch (NoSuchMethodException e) {
            log.warn("[Herodotus] |- Close the datasource named [{}] failed,", name);
        }
    }
}

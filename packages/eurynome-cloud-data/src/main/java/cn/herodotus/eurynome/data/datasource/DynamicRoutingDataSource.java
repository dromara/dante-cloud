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

import cn.herodotus.eurynome.data.datasource.definition.AbstractRoutingDataSource;
import cn.herodotus.eurynome.data.datasource.exception.DataSourceException;
import cn.herodotus.eurynome.data.datasource.exception.DataSourceNotExistException;
import cn.herodotus.eurynome.data.datasource.exception.PrimaryConfigureErrorException;
import cn.herodotus.eurynome.data.datasource.exception.RemovePrimaryDataSourceException;
import com.p6spy.engine.spy.P6DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

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

    /**
     * 所有数据源
     */
    private final Map<String, DataSource> wrappedDataSources = new LinkedHashMap<>();
    private Map<String, DataSource> dataSources;
    private String primary;
    private boolean p6spy;

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public void setDataSources(Map<String, DataSource> dataSources) {
        this.dataSources = dataSources;
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

    public Map<String, DataSource> getWrappedDataSources() {
        return wrappedDataSources;
    }

    /**
     * 添加数据源
     *
     * @param dataSourceName 数据源名称
     * @param dataSource     数据源
     */
    public synchronized void addDataSource(String dataSourceName, DataSource dataSource) {
        if (!wrappedDataSources.containsKey(dataSourceName)) {
            dataSource = wrapDataSource(dataSourceName, dataSource);
            wrappedDataSources.put(dataSourceName, dataSource);
            log.info("[Herodotus] |- load a datasource named [{}] success", dataSourceName);
        } else {
            log.warn("[Herodotus] |- load a datasource named [{}] failed, because it already exist", dataSourceName);
        }
    }

    private DataSource determinePrimaryDataSource() {
        log.debug("[Herodotus] |- Switch to the primary datasource");
        return wrappedDataSources.get(primary);
    }

    /**
     * 获取数据源
     *
     * @param dataSourceName 数据源名称
     * @return 数据源
     */
    public DataSource getDataSource(String dataSourceName) {
        if (StringUtils.isBlank(dataSourceName)) {
            return determinePrimaryDataSource();
        } else if (wrappedDataSources.containsKey(dataSourceName)) {
            return wrappedDataSources.get(dataSourceName);
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
            throw new RemovePrimaryDataSourceException("could not remove primary datasource");
        }
        if (wrappedDataSources.containsKey(dataSourceName)) {
            DataSource dataSource = wrappedDataSources.get(dataSourceName);
            try {
                closeDataSource(dataSourceName, dataSource);
            } catch (Exception e) {
                throw new DataSourceException("remove the database named " + dataSourceName + " failed", e);
            }
            wrappedDataSources.remove(dataSourceName);

            log.info("[Herodotus] |- Remove the database named [{}] success", dataSourceName);
        } else {
            log.warn("[Herodotus] |- Could not find a database named [{}]", dataSourceName);
        }
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

    private DataSource wrapDataSource(String dataSourceName, DataSource dataSource) {
        if (p6spy) {
            dataSource = new P6DataSource(dataSource);
            log.info("[Herodotus] |- [{}] wrap p6spy plugin", dataSourceName);
        }
        return dataSource;
    }

    @Override
    protected DataSource determineDataSource() {
        return getDataSource(DynamicDataSourceContextHolder.peek());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, DataSource> dataSources = this.dataSources;
        dataSources.forEach(this::addDataSource);

        if (dataSources.containsKey(primary)) {
            log.info("[Herodotus] |- initial loaded [{}] datasource,primary datasource named [{}]", dataSources.size(), primary);
        } else {
            throw new PrimaryConfigureErrorException("Please check the dynamic datasource setting of primary");
        }
    }

    @Override
    public void destroy() throws Exception {
        log.info("[Herodotus] |-  Start closing ....");
        for (Map.Entry<String, DataSource> entry : wrappedDataSources.entrySet()) {
            String key = entry.getKey();
            DataSource value = entry.getValue();
            closeDataSource(key, value);
        }
        log.info("[Herodotus] |- All closed success.");
    }
}

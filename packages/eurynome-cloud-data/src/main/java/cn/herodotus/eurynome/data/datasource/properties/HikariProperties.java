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
 * File Name: HikariProperties.java
 * Author: gengwei.zheng
 * Date: 2020/5/21 下午2:39
 * LastModified: 2020/5/21 下午2:39
 */

package cn.herodotus.eurynome.data.datasource.properties;

import com.zaxxer.hikari.HikariConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;

import java.util.Properties;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: HikariProperties </p>
 *
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/21 14:04
 */

@Slf4j
public class HikariProperties {

    /**
     * Properties changeable at runtime through the HikariConfigMXBean
     */
    private volatile long connectionTimeout = SECONDS.toMillis(30);
    private volatile long validationTimeout = SECONDS.toMillis(5);
    private volatile long idleTimeout = MINUTES.toMillis(10);
    private volatile long maxLifetime = MINUTES.toMillis(30);
    private volatile int maxPoolSize = 20;
    private volatile int minIdle = 10;
    /**
     * Properties NOT changeable at runtime
     */
    private String connectionTestQuery = "SELECT 1";
    private String poolName;
    private Properties dataSourceProperties;
    private Properties healthCheckProperties;

    public long getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public long getValidationTimeout() {
        return validationTimeout;
    }

    public void setValidationTimeout(long validationTimeout) {
        this.validationTimeout = validationTimeout;
    }

    public long getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(long idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public long getMaxLifetime() {
        return maxLifetime;
    }

    public void setMaxLifetime(long maxLifetime) {
        this.maxLifetime = maxLifetime;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public String getConnectionTestQuery() {
        return connectionTestQuery;
    }

    public void setConnectionTestQuery(String connectionTestQuery) {
        this.connectionTestQuery = connectionTestQuery;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public Properties getDataSourceProperties() {
        if (MapUtils.isNotEmpty(dataSourceProperties)) {
            return dataSourceProperties;
        } else {
            Properties dataSourceProperties = new Properties();
            dataSourceProperties.put("prepStmtCacheSize", 250);
            dataSourceProperties.put("prepStmtCacheSqlLimit", 2048);
            dataSourceProperties.put("cachePrepStmts", true);
            dataSourceProperties.put("cacheResultSetMetadata", true);
            dataSourceProperties.put("cacheServerConfiguration", true);
            dataSourceProperties.put("useServerPrepStmts", true);
            dataSourceProperties.put("useLocalSessionState", true);
            dataSourceProperties.put("rewriteBatchedStatement", true);
            dataSourceProperties.put("elideSetAutoCommits", true);
            dataSourceProperties.put("maintainTimeStats", false);
            return dataSourceProperties;
        }
    }

    public void setDataSourceProperties(Properties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    public Properties getHealthCheckProperties() {
        return healthCheckProperties;
    }

    public void setHealthCheckProperties(Properties healthCheckProperties) {
        this.healthCheckProperties = healthCheckProperties;
    }

    public HikariConfig getHikariConfig() {

        HikariConfig config = new HikariConfig();
        config.setConnectionTimeout(this.getConnectionTimeout());
        config.setIdleTimeout(this.getIdleTimeout());
        config.setMaxLifetime(this.getMaxLifetime());
        config.setMaximumPoolSize(this.getMaxPoolSize());
        config.setMinimumIdle(this.getMinIdle());
        config.setValidationTimeout(this.getValidationTimeout());
        config.setConnectionTestQuery(this.getConnectionTestQuery());
        if (MapUtils.isNotEmpty(this.getHealthCheckProperties())) {
            config.setHealthCheckProperties(this.getHealthCheckProperties());
        }
        if (MapUtils.isNotEmpty(this.getDataSourceProperties())) {
            config.setDataSourceProperties(this.getDataSourceProperties());
        }

        return config;
    }
}

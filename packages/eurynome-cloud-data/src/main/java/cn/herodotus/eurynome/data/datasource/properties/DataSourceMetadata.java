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
 * File Name: DataSourceMetadata.java
 * Author: gengwei.zheng
 * Date: 2020/5/24 下午4:43
 * LastModified: 2020/5/23 上午9:32
 */

package cn.herodotus.eurynome.data.datasource.properties;

import cn.herodotus.eurynome.data.datasource.exception.DataSourceMetadataCreationException;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.util.List;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: DataSourceProperty </p>
 *
 * <p>
 * Description: 动态数据源的元数据
 * 参考Spring Data Jpa配置构建 {@link org.springframework.boot.autoconfigure.jdbc.DataSourceProperties}
 * </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/21 14:11
 */
public class DataSourceMetadata {

    /**
     * 连接池名称
     */
    private String poolName;

    /**
     * Fully qualified name of the JDBC driver. Auto-detected based on the URL by default.
     */
    private String driverClassName;
    /**
     * JDBC URL of the database.
     */
    private String url;

    /**
     * Login username of the database.
     */
    private String username;

    /**
     * Login password of the database.
     */
    private String password;
    /**
     * Schema (DDL) script resource references.
     */
    private List<String> schema;

    /**
     * Username of the database to execute DDL scripts (if different).
     */
    private String schemaUsername;

    /**
     * Password of the database to execute DDL scripts (if different).
     */
    private String schemaPassword;

    /**
     * Data (DML) script resource references.
     */
    private List<String> data;

    /**
     * Username of the database to execute DML scripts (if different).
     */
    private String dataUsername;

    /**
     * Password of the database to execute DML scripts (if different).
     */
    private String dataPassword;

    /**
     * Whether to stop if an error occurs while initializing the database.
     */
    private boolean continueOnError = false;

    /**
     * Statement separator in SQL initialization scripts.
     */
    private String separator = ";";

    /**
     * SQL scripts encoding.
     */
    private Charset sqlScriptEncoding;

    /**
     * Return the configured driver or {@code null} if none was configured.
     * @return the configured driver
     * @see #determineDriverClassName()
     */
    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    /**
     * Determine the driver to use based on this configuration and the environment.
     * @return the driver to use
     */
    public String determineDriverClassName() {
        if (StringUtils.hasText(this.driverClassName)) {
            Assert.state(driverClassIsLoadable(), () -> "Cannot load driver class: " + this.driverClassName);
            return this.driverClassName;
        }
        String driverClassName = null;
        if (StringUtils.hasText(this.url)) {
            driverClassName = DatabaseDriver.fromJdbcUrl(this.url).getDriverClassName();
        }
        if (!StringUtils.hasText(driverClassName)) {
            throw new DataSourceMetadataCreationException("Failed to determine a suitable driver class");
        }
        return driverClassName;
    }

    private boolean driverClassIsLoadable() {
        try {
            ClassUtils.forName(this.driverClassName, null);
            return true;
        }
        catch (UnsupportedClassVersionError ex) {
            // Driver library has been compiled with a later JDK, propagate error
            throw ex;
        }
        catch (Throwable ex) {
            return false;
        }
    }

    /**
     * Return the configured url or {@code null} if none was configured.
     * @return the configured url
     * @see #determineUrl()
     */
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Determine the url to use based on this configuration and the environment.
     * @return the url to use
     */
    public String determineUrl() {
        if (StringUtils.hasText(this.url)) {
            return this.url;
        }
        if (!StringUtils.hasText(url)) {
            throw new DataSourceMetadataCreationException("Failed to determine suitable jdbc url");
        }
        return url;
    }

    /**
     * Return the configured username or {@code null} if none was configured.
     * @return the configured username
     * @see #determineUsername()
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Determine the username to use based on this configuration and the environment.
     * @return the username to use
     * @since 1.4.0
     */
    public String determineUsername() {
        if (StringUtils.hasText(this.username)) {
            return this.username;
        }
        return null;
    }

    /**
     * Return the configured password or {@code null} if none was configured.
     * @return the configured password
     * @see #determinePassword()
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Determine the password to use based on this configuration and the environment.
     */
    public String determinePassword() {
        if (StringUtils.hasText(this.password)) {
            return this.password;
        }
        return null;
    }

    public List<String> getSchema() {
        return schema;
    }

    public void setSchema(List<String> schema) {
        this.schema = schema;
    }

    public String getSchemaUsername() {
        return schemaUsername;
    }

    public void setSchemaUsername(String schemaUsername) {
        this.schemaUsername = schemaUsername;
    }

    public String getSchemaPassword() {
        return schemaPassword;
    }

    public void setSchemaPassword(String schemaPassword) {
        this.schemaPassword = schemaPassword;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public String getDataUsername() {
        return dataUsername;
    }

    public void setDataUsername(String dataUsername) {
        this.dataUsername = dataUsername;
    }

    public String getDataPassword() {
        return dataPassword;
    }

    public void setDataPassword(String dataPassword) {
        this.dataPassword = dataPassword;
    }

    public boolean isContinueOnError() {
        return continueOnError;
    }

    public void setContinueOnError(boolean continueOnError) {
        this.continueOnError = continueOnError;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public Charset getSqlScriptEncoding() {
        return sqlScriptEncoding;
    }

    public void setSqlScriptEncoding(Charset sqlScriptEncoding) {
        this.sqlScriptEncoding = sqlScriptEncoding;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }
}

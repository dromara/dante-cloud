/*
 * Copyright (c) 2019-2021 Gengwei Zheng (herodotus@aliyun.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-integration-influxdb
 * File Name: InfluxdbProperties.java
 * Author: gengwei.zheng
 * Date: 2021/11/19 15:17:19
 */

package cn.herodotus.eurynome.integration.influxdb.properties;

import cn.herodotus.eurynome.assistant.constant.PropertyConstants;
import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: Influxdb 配置参数 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/11/19 15:17
 */
@ConfigurationProperties(prefix = PropertyConstants.PROPERTY_INTEGRATION_INFLUXDB)
public class InfluxdbProperties {

    /**
     * Influxdb 1.X 用户名
     */
    private String username;
    /**
     * Influxdb 1.X 密码
     */
    private String password;
    /**
     * Influxdb 1.X 连接url
     */
    private String url;
    /**
     * Influxdb 1.X database
     */
    private String database;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("username", username)
                .add("password", password)
                .add("url", url)
                .add("database", database)
                .toString();
    }
}

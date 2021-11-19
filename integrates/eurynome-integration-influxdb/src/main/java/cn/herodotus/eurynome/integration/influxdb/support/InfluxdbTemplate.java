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
 * File Name: InfluxdbTemplate.java
 * Author: gengwei.zheng
 * Date: 2021/11/19 15:16:19
 */

package cn.herodotus.eurynome.integration.influxdb.support;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Pong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <p>Description: Influxdb 操作模版 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/11/19 15:16
 */
@Component
public class InfluxdbTemplate {

    private static final Logger log = LoggerFactory.getLogger(InfluxdbTemplate.class);

    private final InfluxDB influxdb;

    public InfluxdbTemplate(InfluxDB influxdb) {
        this.influxdb = influxdb;
    }

    /**
     * 测试连接是否正常
     *
     * @return true 正常
     */
    public boolean ping() {
        boolean isConnected = false;
        Pong pong;
        try {
            pong = influxdb.ping();
            if (pong != null) {
                isConnected = true;
            }
        } catch (Exception e) {
            log.error("[Herodotus] |- Influxdb ping the connection error.", e);
        }
        return isConnected;
    }
}

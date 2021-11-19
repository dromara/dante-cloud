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
 * File Name: InfluxdbConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/11/19 15:20:19
 */

package cn.herodotus.eurynome.integration.influxdb.configuration;

import cn.herodotus.eurynome.integration.influxdb.annotation.ConditionalOnInfluxdbEnabled;
import cn.herodotus.eurynome.integration.influxdb.properties.InfluxdbProperties;
import cn.herodotus.eurynome.integration.influxdb.support.InfluxdbTemplate;
import org.apache.commons.lang3.StringUtils;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * <p>Description: Influxdb 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/11/19 15:20
 */
@Configuration
@ConditionalOnInfluxdbEnabled
@EnableConfigurationProperties({InfluxdbProperties.class})
public class InfluxdbConfiguration {

    private static final Logger log = LoggerFactory.getLogger(InfluxdbConfiguration.class);

    @PostConstruct
    public void init() {
        log.info("[Herodotus] |- Plugin [Herodotus Influxdb] Auto Configure.");
    }

    @Bean
    public InfluxDB influxdb(InfluxdbProperties influxdbProperties) {

        InfluxDB influxdb;
        if (StringUtils.isNotBlank(influxdbProperties.getUsername()) && StringUtils.isNotBlank(influxdbProperties.getPassword())) {
            influxdb = InfluxDBFactory.connect(influxdbProperties.getUrl(), influxdbProperties.getUsername(), influxdbProperties.getPassword());
        } else {
            influxdb = InfluxDBFactory.connect(influxdbProperties.getUrl());
        }

        try {
            /**
             * 异步插入：
             * enableBatch这里第一个是point的个数，第二个是时间，单位毫秒
             * point的个数和时间是联合使用的，如果满100条或者2000毫秒
             * 满足任何一个条件就会发送一次写的请求。
             */
            influxdb.setDatabase(influxdbProperties.getDatabase()).enableBatch(100, 2000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("[Herodotus] |- Influxdb set database catch error.", e);
        } finally {
            influxdb.setRetentionPolicy("autogen");
        }
        influxdb.setLogLevel(InfluxDB.LogLevel.BASIC);
        return influxdb;
    }

    @Bean
    @ConditionalOnBean(InfluxDB.class)
    public InfluxdbTemplate influxdbTemplate(InfluxDB influxdb) {
        InfluxdbTemplate influxdbTemplate = new InfluxdbTemplate(influxdb);
        log.trace("[Herodotus] |- Bean [Influxdb Template Auto Configure.");
        return influxdbTemplate;
    }
}

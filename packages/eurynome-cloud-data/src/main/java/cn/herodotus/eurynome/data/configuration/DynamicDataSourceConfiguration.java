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
 * File Name: DynamicDataSourceConfiguration.java
 * Author: gengwei.zheng
 * Date: 2020/5/22 下午12:23
 * LastModified: 2020/5/22 下午12:23
 */

package cn.herodotus.eurynome.data.configuration;

import cn.herodotus.eurynome.data.datasource.DefaultDataSourceProvider;
import cn.herodotus.eurynome.data.datasource.DynamicRoutingDataSource;
import cn.herodotus.eurynome.data.datasource.definition.DataSourceProvider;
import cn.herodotus.eurynome.data.datasource.domain.DataSourceMetadata;
import cn.herodotus.eurynome.data.datasource.properties.DataSourceProperties;
import cn.herodotus.eurynome.data.datasource.properties.HikariProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: DynamicDataSourceConfiguration </p>
 *
 * <p>Description: 动态数据源配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/22 12:23
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({DataSourceProperties.class, HikariProperties.class})
@ComponentScan(basePackages = {
        "cn.herodotus.eurynome.data.datasource.aop"
})
public class DynamicDataSourceConfiguration {

    @Bean
    @ConditionalOnMissingBean(DataSourceProvider.class)
    public DataSourceProvider dataSourceProvider(DataSourceProperties dataSourceProperties, HikariProperties hikariProperties) {
        return new DefaultDataSourceProvider(dataSourceProperties, hikariProperties);
    }

    @Bean
    @ConditionalOnBean(DataSourceProvider.class)
    public DataSource dataSource(DataSourceProvider dataSourceProvider) {
        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
        dataSource.setDataSources(dataSourceProvider.getDataSources());
        dataSource.setPrimary(dataSourceProvider.getPrimary());
        dataSource.setP6spy(dataSourceProvider.getP6spy());
        return dataSource;
    }
}

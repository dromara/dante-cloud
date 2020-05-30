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
 * File Name: SqlScriptExecutor.java
 * Author: gengwei.zheng
 * Date: 2020/5/30 下午5:57
 * LastModified: 2020/5/30 下午5:57
 */

package cn.herodotus.eurynome.data.datasource.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: SqlScriptExecutor </p>
 *
 * <p>Description: ScriptRunner运行脚本工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/30 17:57
 */
@Slf4j
public class SqlScriptExecutor {

    /**
     * 错误是否继续
     */
    private boolean continueOnError;
    /**
     * 分隔符
     */
    private String separator;

    public SqlScriptExecutor(boolean continueOnError, String separator) {
        this.continueOnError = continueOnError;
        this.separator = separator;
    }

    /**
     * 执行数据库脚本
     *
     * @param dataSource 连接池
     * @param location   脚本位置
     */
    public void run(DataSource dataSource, String location) {
        if (StringUtils.hasText(location)) {
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            populator.setContinueOnError(continueOnError);
            populator.setSeparator(separator);
            if (location.startsWith("classpath:")) {
                location = location.substring(10);
            }
            ClassPathResource resource = new ClassPathResource(location);
            if (resource.exists()) {
                populator.addScript(resource);
                try {
                    DatabasePopulatorUtils.execute(populator, dataSource);
                } catch (Exception e) {
                    log.warn("[Herodotus] |- SqlScriptExecutor execute sql error", e);
                }
            } else {
                log.warn("[Herodotus] |- SqlScriptExecutor could not find schema or data file {}", location);
            }
        }
    }
}

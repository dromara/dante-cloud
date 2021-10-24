/*
 * Copyright (c) 2019-2021 Gengwei Zheng(herodotus@aliyun.com)
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
 * Module Name: eurynome-cloud-kernel
 * File Name: ManagementProperties.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.kernel.properties;

import cn.herodotus.eurynome.assistant.constant.PropertyConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.logging.LogLevel;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> Description : 平台管理相关配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/30 16:01
 */
@Slf4j
@ConfigurationProperties(prefix = PropertyConstants.PROPERTY_HERODOTUS_MANAGEMENT)
public class ManagementProperties {

    private final LogCenter logCenter = new LogCenter();
    private final ConfigCenter configCenter = new ConfigCenter();

    public LogCenter getLogCenter() {
        return logCenter;
    }

    public ConfigCenter getConfigCenter() {
        return configCenter;
    }

    public static class LogCenter implements Serializable {
        /**
         * 日志中心的logstash地址。
         */
        private String serverAddr = "127.0.0.1:5044";
        private LogLevel logLevel = LogLevel.INFO;
        private Map<String, LogLevel> loggers = new HashMap<>();

        public String getServerAddr() {
            return serverAddr;
        }

        public void setServerAddr(String serverAddr) {
            this.serverAddr = serverAddr;
        }

        public LogLevel getLogLevel() {
            return logLevel;
        }

        public void setLogLevel(LogLevel logLevel) {
            this.logLevel = logLevel;
        }

        public Map<String, LogLevel> getLoggers() {
            return loggers;
        }

        public void setLoggers(Map<String, LogLevel> loggers) {
            this.loggers = loggers;
        }
    }

    public static class ConfigCenter implements Serializable {

        /**
         * 配置文件查找路径的前缀，基于Spring的{@link PathMatchingResourcePatternResolver}进行查找
         * PathMatchingResourcePatternResolver支持：classpath classpath* 以及 web相对路径。
         *
         * 配置文件必须放置在resources下面，目录路径配置是相对于resources而言。
         */
        private String prefix = "classpath:configs";

        /**
         * Nacos等配置中心的地址。
         */
        private String serverAddr = "127.0.0.1:8848";

        /**
         * 是否进行配置初始化，默认为false
         */
        private boolean initialize = false;

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getServerAddr() {
            return serverAddr;
        }

        public void setServerAddr(String serverAddr) {
            this.serverAddr = serverAddr;
        }

        public boolean isInitialize() {
            return initialize;
        }

        public void setInitialize(boolean initialize) {
            this.initialize = initialize;
        }
    }
}

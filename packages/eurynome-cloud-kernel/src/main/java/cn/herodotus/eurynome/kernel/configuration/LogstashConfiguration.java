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
 * File Name: LogstashConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.kernel.configuration;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import cn.herodotus.eurynome.assistant.annotation.conditional.ConditionalOnLogCenterEnabled;
import cn.herodotus.eurynome.common.constant.magic.PropertyConstants;
import cn.herodotus.eurynome.kernel.logback.LogstashPattern;
import cn.herodotus.eurynome.kernel.properties.ManagementProperties;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.composite.JsonProviders;
import net.logstash.logback.composite.loggingevent.LoggingEventFormattedTimestampJsonProvider;
import net.logstash.logback.composite.loggingevent.LoggingEventPatternJsonProvider;
import net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.logging.LogLevel;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * <p> Description : logback 的 LogstashTcpSocketAppender。用于向日志中心发送日志</p>
 * <p>
 * 解决使用logback.xml配置LogstashTcpSocketAppender所带来的
 * 1、启动时无法连接抛错
 * 2、基于配置中心进行logstash地址参数配置效果不佳的问题。
 * </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/7 15:17
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({
        ManagementProperties.class
})
@ConditionalOnLogCenterEnabled
public class LogstashConfiguration {

    @Value(PropertyConstants.ANNOTATION_APPLICATION_NAME)
    private String serviceName;

    @Autowired
    private ManagementProperties managementProperties;

    @PostConstruct
    public void init() {
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        LoggerContext loggerContext = rootLogger.getLoggerContext();

        LogstashTcpSocketAppender logstashTcpSocketAppender = new LogstashTcpSocketAppender();
        logstashTcpSocketAppender.setName("HERODOTUS-LOGSTASH");
        logstashTcpSocketAppender.setContext(loggerContext);
        logstashTcpSocketAppender.addDestination(managementProperties.getLogCenter().getServerAddr());

        LoggingEventFormattedTimestampJsonProvider timestampJsonProvider = new LoggingEventFormattedTimestampJsonProvider();
        timestampJsonProvider.setTimeZone("UTC");
        timestampJsonProvider.setContext(loggerContext);
        LoggingEventPatternJsonProvider patternJsonProvider = new LoggingEventPatternJsonProvider();
        patternJsonProvider.setPattern(getJsonPattern());
        patternJsonProvider.setContext(loggerContext);

        JsonProviders<ILoggingEvent> jsonProviders = new JsonProviders<>();
        jsonProviders.addProvider(patternJsonProvider);
        jsonProviders.addProvider(timestampJsonProvider);

        LoggingEventCompositeJsonEncoder encoder = new LoggingEventCompositeJsonEncoder();
        encoder.setContext(loggerContext);
        encoder.setProviders(jsonProviders);
        encoder.start();

        logstashTcpSocketAppender.setEncoder(encoder);
        logstashTcpSocketAppender.start();

        rootLogger.addAppender(logstashTcpSocketAppender);
        rootLogger.setLevel(Level.toLevel(managementProperties.getLogCenter().getLogLevel().name(), Level.INFO));

        Map<String, LogLevel> loggers = managementProperties.getLogCenter().getLoggers();
        loggers.forEach((key, value) -> {
            Logger logger = (Logger) LoggerFactory.getLogger(key);
            logger.setLevel(Level.toLevel(value.name()));
        });

        log.debug("[Herodotus] |- Plugin [Herodotus Logstash] Auto Configure.");
    }

    private String getJsonPattern() {
        LogstashPattern pattern = new LogstashPattern();
        if (StringUtils.isNotBlank(serviceName)) {
            pattern.setService(serviceName);
        }
        return JSON.toJSONString(pattern);
    }
}

package cn.herodotus.eurynome.component.data.configuration;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import cn.herodotus.eurynome.component.data.logstash.LogstashJsonPattern;
import cn.herodotus.eurynome.operation.properties.ManagementProperties;
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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@Configuration
@ConditionalOnProperty(name = "herodotus.platform.management.log-center.server-addr")
public class LogstashConfiguration {

    @Value("${spring.application.name}")
    private String serviceName;
    @Autowired
    private ManagementProperties managementProperties;

    @PostConstruct
    public void init() {
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        LoggerContext loggerContext = rootLogger.getLoggerContext();

        LogstashTcpSocketAppender logstashTcpSocketAppender = new LogstashTcpSocketAppender();
        logstashTcpSocketAppender.setName("LOGSTASH");
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

        log.info("[Herodotus] |- Bean [LogstashTcpSocketAppender] Auto Configure.");
    }

    private String getJsonPattern() {
        LogstashJsonPattern pattern = new LogstashJsonPattern();
        if (StringUtils.isNotBlank(serviceName)) {
            pattern.setService(serviceName);
        }
        return JSON.toJSONString(pattern);
    }
}

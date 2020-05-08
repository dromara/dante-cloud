package cn.herodotus.eurynome.component.data.configuration;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import cn.herodotus.eurynome.component.management.properties.ManagementProperties;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.composite.JsonProviders;
import net.logstash.logback.composite.loggingevent.LoggingEventFormattedTimestampJsonProvider;
import net.logstash.logback.composite.loggingevent.LoggingEventPatternJsonProvider;
import net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.logging.LogLevel;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/7 15:17
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "herodotus.platform.management.log-center.server-addr")
@AutoConfigureAfter
public class LogstashConfiguration {

    private static final String JSON_PATTERN = "{" +
            "\"level\": \"%level\"," +
            "\"service\": \"${spring.application.name:-}\"," +
            "\"trace\": \"%X{X-B3-TraceId:-}\"," +
            "\"span\": \"%X{X-B3-SpanId:-}\"," +
            "\"parent\": \"%X{X-B3-ParentSpanId:-}\"," +
            "\"exportable\": \"%X{X-Span-Export:-}\"," +
            "\"pid\": \"${PID:-}\"," +
            "\"thread\": \"%thread\"," +
            "\"class\": \"%logger\"," +
            "\"log\": \"%message\"}";

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
        patternJsonProvider.setPattern(JSON_PATTERN);
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
}

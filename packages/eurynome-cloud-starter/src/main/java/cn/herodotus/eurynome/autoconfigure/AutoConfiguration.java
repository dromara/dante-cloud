package cn.herodotus.eurynome.autoconfigure;

import cn.herodotus.eurynome.localstorage.annotation.EnableHerodotusLocalStorage;
import cn.herodotus.eurynome.localstorage.service.SecurityMetadataService;
import cn.herodotus.eurynome.message.annotation.EnableHerodotusMessage;
import cn.herodotus.eurynome.message.queue.KafkaProducer;
import cn.herodotus.eurynome.message.stream.service.SecurityMetadataMessage;
import cn.herodotus.eurynome.rest.annotation.EnableHerodotusRest;
import cn.herodotus.eurynome.rest.metadata.RequestMappingScan;
import cn.herodotus.eurynome.rest.metadata.SecurityMetadataPersistence;
import cn.herodotus.eurynome.rest.properties.ApplicationProperties;
import cn.herodotus.eurynome.security.annotation.EnableHerodotusSecurity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author gengwei.zheng
 */
@Slf4j
@Configuration
@EnableHerodotusLocalStorage
@EnableHerodotusMessage
@EnableHerodotusRest
@EnableHerodotusSecurity
public class AutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(KafkaProducer.class)
    public KafkaProducer kafkaProducer(KafkaTemplate kafkaTemplate) {
        KafkaProducer kafkaProducer = new KafkaProducer(kafkaTemplate);
        log.debug("[Herodotus] |- Bean [Kafka Producer] Auto Configure.");
        return kafkaProducer;
    }

    @Bean
    @ConditionalOnMissingBean(SecurityMetadataPersistence.class)
    @ConditionalOnBean({SecurityMetadataMessage.class, SecurityMetadataService.class})
    public SecurityMetadataPersistence securityMetadataPersistence(SecurityMetadataService securityMetadataService, SecurityMetadataMessage securityMetadataMessage) {
        SecurityMetadataPersistence securityMetadataPersistence = new SecurityMetadataPersistence(securityMetadataService, securityMetadataMessage);
        log.debug("[Herodotus] |- Bean [Security Metadata Persistence] Auto Configure.");
        return securityMetadataPersistence;
    }

    /**
     * 自定义注解扫描
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(RequestMappingScan.class)
    @ConditionalOnBean(SecurityMetadataPersistence.class)
    public RequestMappingScan requestMappingScan(SecurityMetadataPersistence securityMetadataPersistence, ApplicationProperties applicationProperties) {
        RequestMappingScan requestMappingScan = new RequestMappingScan(securityMetadataPersistence, applicationProperties, EnableResourceServer.class);
        log.debug("[Herodotus] |- Bean [Request Mapping Scan] Auto Configure.");
        return requestMappingScan;
    }
}

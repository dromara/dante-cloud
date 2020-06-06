package cn.herodotus.eurynome.autoconfigure;

import cn.herodotus.eurynome.autoconfigure.components.SecurityMetadataProducer;
import cn.herodotus.eurynome.data.annotation.EnableHerodotusData;
import cn.herodotus.eurynome.message.queue.KafkaProducer;
import cn.herodotus.eurynome.rest.annotation.EnableHerodotusRest;
import cn.herodotus.eurynome.security.annotation.EnableHerodotusSecurity;
import cn.herodotus.eurynome.security.metadata.SecurityMetadataLocalStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.PostConstruct;

/**
 * @author gengwei.zheng
 */
@Slf4j
@Configuration
@EnableHerodotusData
@EnableHerodotusRest
@EnableHerodotusSecurity
public class AutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Components [Herodotus Starter] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean(KafkaProducer.class)
    public KafkaProducer kafkaProducer(KafkaTemplate kafkaTemplate) {
        KafkaProducer kafkaProducer = new KafkaProducer(kafkaTemplate);
        log.debug("[Herodotus] |- Bean [Kafka Producer] Auto Configure.");
        return kafkaProducer;
    }

    @Bean
    @ConditionalOnMissingBean(SecurityMetadataProducer.class)
    @ConditionalOnBean(SecurityMetadataLocalStorage.class)
    public SecurityMetadataProducer securityMetadataPersistence(KafkaProducer kafkaProducer, SecurityMetadataLocalStorage securityMetadataLocalStorage) {
        SecurityMetadataProducer securityMetadataProducer = new SecurityMetadataProducer();
        securityMetadataProducer.setKafkaProducer(kafkaProducer);
        securityMetadataProducer.setSecurityMetadataLocalStorage(securityMetadataLocalStorage);
        log.debug("[Herodotus] |- Bean [Security Metadata Persistence] Auto Configure.");
        return securityMetadataProducer;
    }
}

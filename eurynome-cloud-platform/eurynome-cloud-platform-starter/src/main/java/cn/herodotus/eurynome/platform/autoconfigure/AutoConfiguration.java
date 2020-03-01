package cn.herodotus.eurynome.platform.autoconfigure;

import cn.herodotus.eurynome.component.common.definition.RequestMappingStore;
import cn.herodotus.eurynome.component.data.annotation.EnableHerodotusData;
import cn.herodotus.eurynome.component.security.annotation.EnableHerodotusSecurity;
import cn.herodotus.eurynome.component.security.properties.ApplicationProperties;
import cn.herodotus.eurynome.component.sdk.annotation.KafkaRequestMappingStore;
import cn.herodotus.eurynome.component.sdk.configuration.kafka.KafkaProducer;
import cn.herodotus.eurynome.component.security.annotation.RequestMappingScan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author gengwei.zheng
 */
@Slf4j
@Configuration
@EnableHerodotusData
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
    @ConditionalOnMissingBean(KafkaRequestMappingStore.class)
    @ConditionalOnBean(KafkaProducer.class)
    public RequestMappingStore requestMappingStore(KafkaProducer kafkaProducer) {
        KafkaRequestMappingStore kafkaRequestMappingStore = new KafkaRequestMappingStore(kafkaProducer);
        log.debug("[Herodotus] |- Bean [Kafka Request Mapping Store] Auto Configure.");
        return kafkaRequestMappingStore;
    }

    /**
     * 自定义注解扫描
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(RequestMappingScan.class)
    @ConditionalOnBean(RequestMappingStore.class)
    public RequestMappingScan requestMappingScan(RequestMappingStore requestMappingStore, ApplicationProperties applicationProperties) {
        RequestMappingScan scan = new RequestMappingScan(requestMappingStore, applicationProperties);
        log.debug("[Herodotus] |- Bean [Request Mapping Scan] Auto Configure.");
        return scan;
    }
}

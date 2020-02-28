package cn.herodotus.eurynome.component.autoconfigure;

import cn.herodotus.eurynome.component.data.annotation.EnableHerodotusData;
import cn.herodotus.eurynome.component.security.annotation.EnableHerodotusSecurity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * @author gengwei.zheng
 */

@Slf4j
@Configuration
@EnableHerodotusData
@EnableHerodotusSecurity
public class AutoConfiguration {

//    @Bean
//    @ConditionalOnMissingBean(KafkaProducer.class)
//    public KafkaProducer kafkaProducer(KafkaTemplate kafkaTemplate) {
//        KafkaProducer kafkaProducer = new KafkaProducer(kafkaTemplate);
//        log.debug("[Luban] |- Bean [Kafka Producer] Auto Configure.");
//        return kafkaProducer;
//    }
//
//    @Bean
//    @ConditionalOnMissingBean(KafkaRequestMappingStore.class)
//    @ConditionalOnBean(KafkaProducer.class)
//    public RequestMappingStore requestMappingStore(KafkaProducer kafkaProducer) {
//        KafkaRequestMappingStore kafkaRequestMappingStore = new KafkaRequestMappingStore(kafkaProducer);
//        log.debug("[Luban] |- Bean [Kafka Request Mapping Store] Auto Configure.");
//        return kafkaRequestMappingStore;
//    }
//
//    /**
//     * 自定义注解扫描
//     *
//     * @return
//     */
//    @Bean
//    @ConditionalOnMissingBean(RequestMappingScan.class)
//    @ConditionalOnBean(RequestMappingStore.class)
//    public RequestMappingScan requestMappingScan(RequestMappingStore requestMappingStore, ApplicationProperties applicationProperties) {
//        RequestMappingScan scan = new RequestMappingScan(requestMappingStore, applicationProperties);
//        log.debug("[Luban] |- Bean [Request Mapping Scan] Auto Configure.");
//        return scan;
//    }
}

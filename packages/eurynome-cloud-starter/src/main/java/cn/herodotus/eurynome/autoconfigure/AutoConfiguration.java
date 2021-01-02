package cn.herodotus.eurynome.autoconfigure;

import cn.herodotus.eurynome.crud.annotation.EnableHerodotusCrud;
import cn.herodotus.eurynome.kernel.annotaion.EnableHerodotusFeign;
import cn.herodotus.eurynome.kernel.annotaion.EnableLogCenter;
import cn.herodotus.eurynome.message.queue.KafkaProducer;
import cn.herodotus.eurynome.security.annotation.EnableHerodotusSecurity;
import lombok.extern.slf4j.Slf4j;
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
@EnableHerodotusCrud
@EnableHerodotusSecurity
@EnableLogCenter
@EnableHerodotusFeign
public class AutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.info("[Eurynome] |- Components [Herodotus Starter] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean(KafkaProducer.class)
    public KafkaProducer kafkaProducer(KafkaTemplate kafkaTemplate) {
        KafkaProducer kafkaProducer = new KafkaProducer(kafkaTemplate);
        log.debug("[Eurynome] |- Bean [Kafka Producer] Auto Configure.");
        return kafkaProducer;
    }
}

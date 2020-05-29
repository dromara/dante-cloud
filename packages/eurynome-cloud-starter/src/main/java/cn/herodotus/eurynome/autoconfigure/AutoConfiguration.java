package cn.herodotus.eurynome.autoconfigure;

import cn.herodotus.eurynome.message.queue.KafkaProducer;
import cn.herodotus.eurynome.security.annotation.EnableHerodotusSecurity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author gengwei.zheng
 */
@Slf4j
@Configuration
@EnableHerodotusSecurity
public class AutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(KafkaProducer.class)
    public KafkaProducer kafkaProducer(KafkaTemplate kafkaTemplate) {
        KafkaProducer kafkaProducer = new KafkaProducer(kafkaTemplate);
        log.debug("[Herodotus] |- Bean [Kafka Producer] Auto Configure.");
        return kafkaProducer;
    }
}

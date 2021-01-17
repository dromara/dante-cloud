package cn.herodotus.eurynome.autoconfigure;

import cn.herodotus.eurynome.autoconfigure.logic.LocalCacheSecurityMetadata;
import cn.herodotus.eurynome.autoconfigure.logic.SecurityMetadataProducer;
import cn.herodotus.eurynome.kernel.annotation.EnableHerodotusKernel;
import cn.herodotus.eurynome.message.queue.KafkaProducer;
import cn.herodotus.eurynome.security.annotation.EnableHerodotusSecurity;
import cn.herodotus.eurynome.security.definition.service.SecurityMetadataStorage;
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
@EnableHerodotusSecurity
@EnableHerodotusKernel
public class AutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.info("[Eurynome] |- Starter [Herodotus Starter] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean(KafkaProducer.class)
    public KafkaProducer kafkaProducer(KafkaTemplate kafkaTemplate) {
        KafkaProducer kafkaProducer = new KafkaProducer(kafkaTemplate);
        log.debug("[Eurynome] |- Bean [Kafka Producer] Auto Configure.");
        return kafkaProducer;
    }

    /**
     * 服务自身权限验证所需的Security Metadata存储配置
     *
     * 服务权限验证逻辑：
     * 1、配置服务本地Security Metadata存储
     */
    @Bean
    @ConditionalOnMissingBean(SecurityMetadataStorage.class)
    public SecurityMetadataStorage securityMetadataStorage() {
        LocalCacheSecurityMetadata localCacheSecurityMetadata = new LocalCacheSecurityMetadata();
        log.debug("[Eurynome] |- Bean [Security Metadata Local Storage] Auto Configure.");
        return localCacheSecurityMetadata;
    }

    /**
     * 权限信息发送器
     *
     * 服务权限验证逻辑：
     * 3、将服务本地存储的Security Metadata，发送到统一认证中心。
     * 4、通过客户端，在统一认证中心配置用户权限
     */
    @Bean
    @ConditionalOnMissingBean(SecurityMetadataProducer.class)
    @ConditionalOnBean(SecurityMetadataStorage.class)
    public SecurityMetadataProducer securityMetadataProducer(KafkaProducer kafkaProducer, SecurityMetadataStorage securityMetadataStorage) {
        SecurityMetadataProducer securityMetadataProducer = new SecurityMetadataProducer();
        securityMetadataProducer.setKafkaProducer(kafkaProducer);
        securityMetadataProducer.setSecurityMetadataStorage(securityMetadataStorage);
        log.debug("[Eurynome] |- Bean [Security Metadata Persistence] Auto Configure.");
        return securityMetadataProducer;
    }
}

package cn.herodotus.eurynome.component.rest.configuration;

import cn.herodotus.eurynome.component.rest.interceptor.FeignRequestInterceptor;
import cn.herodotus.eurynome.component.rest.security.ThroughGatewayTrace;
import com.alibaba.cloud.sentinel.feign.HerodotusSentinelFeign;
import com.alibaba.csp.sentinel.SphU;
import feign.Feign;
import feign.Request;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.TimeUnit;

/**
 * <p> Description : 自定义通用的Feign Fallback处理工厂(基于Sentinel) </p>
 *
 * {@see https://blog.csdn.net/ttzommed/article/details/90669320}
 * @author : gengwei.zheng
 * @date : 2020/3/1 18:35
 */
@Slf4j
@Configuration
public class FeignConfiguration {

    @Bean
    @Scope("prototype")
    @ConditionalOnClass({SphU.class, Feign.class})
    @ConditionalOnProperty(name = "feign.sentinel.enabled")
    @Primary
    public Feign.Builder feignSentinelBuilder() {
        log.debug("[Herodotus] |- Bean [Herodotus Sentinel Feign] Auto Configure.");
        return HerodotusSentinelFeign.builder();
    }

    @Bean
    public FeignRequestInterceptor feignRequestInterceptor(ThroughGatewayTrace throughGatewayTrace) {
        FeignRequestInterceptor feignRequestInterceptor = new FeignRequestInterceptor(throughGatewayTrace);
        log.debug("[Herodotus] |- Bean [Feign Request Interceptor] Auto Configure.");
        return feignRequestInterceptor;
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(10, TimeUnit.SECONDS, 60, TimeUnit.SECONDS, true);
    }

    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default();
    }
}

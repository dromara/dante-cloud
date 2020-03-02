package cn.herodotus.eurynome.component.rest.configuration;

import com.alibaba.cloud.sentinel.feign.HerodotusSentinelFeign;
import com.alibaba.csp.sentinel.SphU;
import feign.Feign;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

/**
 * <p> Description : TODO </p>
 *
 * {@see https://blog.csdn.net/ttzommed/article/details/90669320}
 * @author : gengwei.zheng
 * @date : 2020/3/1 18:35
 */
@Configuration
public class HerodotusFeignFallbackConfiguration {

    @Bean
    @Scope("prototype")
    @ConditionalOnClass({SphU.class, Feign.class})
    @ConditionalOnProperty(name = "feign.sentinel.enabled")
    @Primary
    public Feign.Builder feignSentinelBuilder() {
        return HerodotusSentinelFeign.builder();
    }
}

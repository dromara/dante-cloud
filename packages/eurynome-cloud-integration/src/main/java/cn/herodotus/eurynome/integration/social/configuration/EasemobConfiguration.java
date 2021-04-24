package cn.herodotus.eurynome.integration.social.configuration;

import cn.herodotus.eurynome.integration.social.properties.EasemobProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;

/**
 * <p>Description: 环信启动配置类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/6 13:13
 */
@Slf4j
@ConditionalOnBean(RedisTemplate.class)
@EnableConfigurationProperties(EasemobProperties.class)
@ComponentScan(basePackages = {
        "cn.herodotus.eurynome.integration.social.service.easemob"
})
public class EasemobConfiguration {

    @PostConstruct
    public void init() {
        log.info("[Eurynome] |- Bean [Easemob] Auto Configure.");
    }
}

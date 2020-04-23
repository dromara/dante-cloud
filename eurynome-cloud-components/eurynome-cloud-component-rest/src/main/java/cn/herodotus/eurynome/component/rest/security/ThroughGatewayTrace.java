package cn.herodotus.eurynome.component.rest.security;

import cn.herodotus.eurynome.component.rest.properties.ApplicationProperties;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * <p> Description : 基于Redis信息的跟踪工具了 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/4 10:42
 */
public class ThroughGatewayTrace {

    private final RedisTemplate<Object, Object> redisTemplate;
    private final ApplicationProperties applicationProperties;

    public ThroughGatewayTrace(RedisTemplate<Object, Object> redisTemplate, ApplicationProperties applicationProperties) {
        this.redisTemplate = redisTemplate;
        this.applicationProperties = applicationProperties;
    }

    public boolean isAccessedThroughGateway() {
        return applicationProperties.isAccessedThroughGateway();
    }

    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }
}

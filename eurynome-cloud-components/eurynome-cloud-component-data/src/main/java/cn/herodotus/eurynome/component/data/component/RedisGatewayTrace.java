package cn.herodotus.eurynome.component.data.component;

import cn.herodotus.eurynome.component.data.properties.SecurityProperties;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * <p> Description : 基于Redis信息的跟踪工具了 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/4 10:42
 */
public class RedisGatewayTrace {

    private RedisTemplate<Object, Object> redisTemplate;
    private SecurityProperties securityProperties;
    /**
     * 过期时间
     */
    private long expired;
    /**
     * 过期前1分钟
     */
    private long threshold;

    public RedisGatewayTrace(RedisTemplate<Object, Object> redisTemplate, SecurityProperties securityProperties) {
        this.redisTemplate = redisTemplate;
        this.securityProperties = securityProperties;
        this.expired = securityProperties.getGateway().getExpired();
        this.threshold = securityProperties.getGateway().getThreshold();
    }

    public boolean isMustBeAccessed() {
        return securityProperties.getGateway().isMustBeAccessed();
    }


    @SuppressWarnings("ConstantConditions")
    public String create(String key) {
        if (StrUtil.isBlank(key)) {
            return null;
        }
        String secretKey;
        if (redisTemplate.hasKey(key)) {
            if (redisTemplate.boundHashOps(key).getExpire() < threshold) {
                redisTemplate.opsForValue().set(key, SecureUtil.md5(UUID.randomUUID().toString()), expired, TimeUnit.SECONDS);
            }
            secretKey = (String) redisTemplate.opsForValue().get(key);
        } else {
            secretKey = SecureUtil.md5(UUID.randomUUID().toString());
            redisTemplate.opsForValue().set(key, secretKey, expired, TimeUnit.SECONDS);
        }
        return secretKey;
    }

    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }
}

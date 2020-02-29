package cn.herodotus.eurynome.component.data.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 监听redis消息需要实现MessageListener接口
 * @author gengwei.zheng
 * @date 2019.09
 */
@Slf4j
public class CacheMessageListener implements MessageListener {

    private RedisTemplate<Object, Object> redisTemplate;

    private RedisCaffeineCacheManager redisCaffeineCacheManager;

    public CacheMessageListener(RedisTemplate<Object, Object> redisTemplate, RedisCaffeineCacheManager redisCaffeineCacheManager) {
        this.redisTemplate = redisTemplate;
        this.redisCaffeineCacheManager = redisCaffeineCacheManager;
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        CacheMessage cacheMessage = (CacheMessage) redisTemplate.getValueSerializer().deserialize(message.getBody());
        log.debug("[Herodotus] |- CacheMessageListener : recevice a redis topic message, clear local cache, the cacheName is {}, the key is {}", cacheMessage.getCacheName(), cacheMessage.getKey());
        redisCaffeineCacheManager.clearLocal(cacheMessage.getCacheName(), cacheMessage.getKey());
    }
}

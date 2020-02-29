package cn.herodotus.eurynome.component.data.cache;

import cn.herodotus.eurynome.component.data.properties.RedisCaffeineCacheProperties;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * 自定义 Redis Caffeine缓存管理器
 * @author gengwei.zheng
 * @date 2019.09
 */
@Slf4j
public class RedisCaffeineCacheManager implements CacheManager {

    private ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<String, Cache>();

    private RedisCaffeineCacheProperties cacheRedisCaffeineProperties;

    private RedisTemplate<Object, Object> redisTemplate;

    private boolean dynamic = true;

    private Set<String> cacheNames;

    public RedisCaffeineCacheManager(RedisCaffeineCacheProperties redisCaffeineCacheProperties, RedisTemplate<Object, Object> redisTemplate) {
        super();
        this.cacheRedisCaffeineProperties = redisCaffeineCacheProperties;
        this.redisTemplate = redisTemplate;
        this.dynamic = redisCaffeineCacheProperties.isDynamic();
        this.cacheNames = redisCaffeineCacheProperties.getCacheNames();
    }

    @Override
    public Cache getCache(String name) {
        Cache cache = cacheMap.get(name);
        if (cache != null) {
            return cache;
        }
        if (!dynamic && !cacheNames.contains(name)) {
            return cache;
        }

        cache = new RedisCaffeineCache(name, redisTemplate, caffeineCache(), cacheRedisCaffeineProperties);
        Cache oldCache = cacheMap.putIfAbsent(name, cache);
        log.debug("[Herodotus] |- RedisCaffeineCacheManager : create cache instance, the cache name is : {}", name);
        return oldCache == null ? cache : oldCache;
    }

    public com.github.benmanes.caffeine.cache.Cache<Object, Object> caffeineCache() {
        Caffeine<Object, Object> cacheBuilder = Caffeine.newBuilder();
        if (cacheRedisCaffeineProperties.getCaffeine().getExpireAfterAccess() > 0) {
            cacheBuilder.expireAfterAccess(cacheRedisCaffeineProperties.getCaffeine().getExpireAfterAccess(), TimeUnit.MILLISECONDS);
        }
        if (cacheRedisCaffeineProperties.getCaffeine().getExpireAfterWrite() > 0) {
            cacheBuilder.expireAfterWrite(cacheRedisCaffeineProperties.getCaffeine().getExpireAfterWrite(), TimeUnit.MILLISECONDS);
        }
        if (cacheRedisCaffeineProperties.getCaffeine().getInitialCapacity() > 0) {
            cacheBuilder.initialCapacity(cacheRedisCaffeineProperties.getCaffeine().getInitialCapacity());
        }
        if (cacheRedisCaffeineProperties.getCaffeine().getMaximumSize() > 0) {
            cacheBuilder.maximumSize(cacheRedisCaffeineProperties.getCaffeine().getMaximumSize());
        }
        if (cacheRedisCaffeineProperties.getCaffeine().getRefreshAfterWrite() > 0) {
            cacheBuilder.refreshAfterWrite(cacheRedisCaffeineProperties.getCaffeine().getRefreshAfterWrite(), TimeUnit.MILLISECONDS);
        }
        return cacheBuilder.build();
    }

    @Override
    public Collection<String> getCacheNames() {
        return this.cacheNames;
    }

    public void clearLocal(String cacheName, Object key) {
        Cache cache = cacheMap.get(cacheName);
        if (cache == null) {
            return;
        }

        RedisCaffeineCache redisCaffeineCache = (RedisCaffeineCache) cache;
        redisCaffeineCache.clearLocal(key);
    }
}

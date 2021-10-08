/*
 * Copyright (c) 2019-2021 Gengwei Zheng (herodotus@aliyun.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-data
 * File Name: HerodotusCache.java
 * Author: gengwei.zheng
 * Date: 2021/07/14 11:04:14
 */

package cn.herodotus.eurynome.data.cache.layer;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.cache.support.NullValue;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.lang.Nullable;

import java.util.concurrent.Callable;

/**
 * <p>Description: 自定义多级缓存基础类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/14 11:04
 */
public class HerodotusCache extends AbstractValueAdaptingCache {

    private static final Logger log = LoggerFactory.getLogger(HerodotusCache.class);

    private final String name;
    private final CaffeineCache caffeineCache;
    private final RedisCache redisCache;
    private final boolean desensitization;
    private final boolean clearRemoteOnExit;

    public HerodotusCache(String name, CaffeineCache caffeineCache, RedisCache redisCache, boolean desensitization, boolean clearRemoteOnExit, boolean allowNullValues) {
        super(allowNullValues);
        this.name = name;
        this.caffeineCache = caffeineCache;
        this.redisCache = redisCache;
        this.desensitization = desensitization;
        this.clearRemoteOnExit = clearRemoteOnExit;
    }

    private String secure(Object key) {
        String original = String.valueOf(key);
        if (desensitization) {
            if (StringUtils.isNotBlank(original) && StringUtils.startsWith(original, "sql:")) {
                String recent = SecureUtil.md5(original);
                log.trace("[Herodotus] |- CACHE - Secure the sql type key [{}] to [{}]", original, recent);
                return recent;
            }
        }
        return original;
    }

    @Override
    protected Object lookup(Object key) {
        String secure = secure(key);

        Object caffeineValue = caffeineCache.get(secure);
        if (ObjectUtils.isNotEmpty(caffeineValue)) {
            log.trace("[Herodotus] |- CACHE - Found the cache in caffeine cache, value is : [{}]", JSON.toJSONString(caffeineValue));
            return caffeineValue;
        }

        Object redisValue = redisCache.get(secure);
        if (ObjectUtils.isNotEmpty(redisValue)) {
            log.trace("[Herodotus] |- CACHE - Found the cache in redis cache, value is : [{}]", JSON.toJSONString(redisValue));
            return redisValue;
        }

        log.debug("[Herodotus] |- CACHE - Lookup the cache for key: [{}], value is null", secure);

        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this;
    }

    /**
     * 查询二级缓存
     *
     * @param key
     * @param valueLoader
     * @return
     */
    private <T> Object getRedisStoreValue(Object key, Callable<T> valueLoader) {
        T value = redisCache.get(key, valueLoader);
        log.trace("[Herodotus] |- CACHE - Get <T> with valueLoader form redis cache, hit the cache.");
        return toStoreValue(value);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Nullable
    public <T> T get(Object key, Callable<T> valueLoader) {
        String secure = secure(key);

        T value = (T) caffeineCache.getNativeCache().get(secure, k -> getRedisStoreValue(k, valueLoader));
        if (value instanceof NullValue) {
            log.debug("[Herodotus] |- CACHE - Get <T> with type form valueLoader Cache for key: [{}], value is null", secure);
            return null;
        }

        return value;
    }

    @Override
    public void put(Object key, Object value) {
        if (!isAllowNullValues() && value == null) {
            throw new IllegalArgumentException(String.format(
                    "Cache '%s' does not allow 'null' values. Avoid storing null via '@Cacheable(unless=\"#result == null\")' or configure RedisCache to allow 'null' via RedisCacheConfiguration.",
                    name));
        } else {
            String secure = secure(key);

            caffeineCache.put(secure, value);
            redisCache.put(secure, value);

            log.debug("[Herodotus] |- CACHE - Put data into Herodotus Cache, with key: [{}] and value: [{}]", secure, value);
        }
    }

    @Override
    public void evict(Object key) {
        String secure = secure(key);

        log.debug("[Herodotus] |- CACHE - Evict Herodotus Cache for key: {}", secure);

        // 删除的时候要先删除二级缓存再删除一级缓存，否则有并发问题
        redisCache.evict(secure);
        log.trace("[Herodotus] |- CACHE - Evict Herodotus Cache in redis cache, key: {}", secure);

        caffeineCache.evict(secure);
        log.trace("[Herodotus] |- CACHE - Evict Herodotus Cache in caffeine cache, key: {}", secure);
    }

    @Override
    public void clear() {
        log.trace("[Herodotus] |- CACHE - Clear Herodotus Cache.");

        if (clearRemoteOnExit) {
            redisCache.clear();
            log.trace("[Herodotus] |- CACHE - Clear Herodotus Cache in redis cache.");
        }

        caffeineCache.clear();
        log.trace("[Herodotus] |- CACHE - Clear Herodotus Cache in caffeine cache.");
    }

    @Override
    public ValueWrapper get(Object key) {

        String secure = secure(key);

        ValueWrapper caffeineValue = caffeineCache.get(secure);
        if (ObjectUtils.isNotEmpty(caffeineValue)) {
            log.trace("[Herodotus] |- CACHE - Get ValueWrapper data from caffeine cache, hit the cache.");
            return caffeineValue;
        }

        ValueWrapper redisValue = redisCache.get(secure);
        if (ObjectUtils.isNotEmpty(redisValue)) {
            log.trace("[Herodotus] |- CACHE - Get ValueWrapper data from redis cache, hit the cache.");
            return redisValue;
        }

        log.debug("[Herodotus] |- CACHE - Get ValueWrapper data form Herodotus Cache for key: [{}], value is null", secure);

        return null;
    }
}

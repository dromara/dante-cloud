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
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.util.Assert;

import java.util.concurrent.Callable;

/**
 * <p>Description: 自定义多级缓存基础类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/14 11:04
 */
public class HerodotusCache implements Cache {
    private static final Logger log = LoggerFactory.getLogger(HerodotusCache.class);

    private final String name;
    private final CaffeineCache caffeineCache;
    private final RedisCache redisCache;
    private final boolean desensitization;
    private final boolean clearRemoteOnExit;

    public HerodotusCache(String name, CaffeineCache caffeineCache, RedisCache redisCache, boolean desensitization, boolean clearRemoteOnExit) {
        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(caffeineCache, "CaffeineCache must not be null!");
        Assert.notNull(redisCache, "RedisCache must not be null!");
        this.caffeineCache = caffeineCache;
        this.redisCache = redisCache;
        this.name = name;
        this.desensitization = desensitization;
        this.clearRemoteOnExit = clearRemoteOnExit;
    }

    private String secure(Object key) {
        String original = String.valueOf(key);
        if (desensitization) {
            if (StringUtils.isNotBlank(original) && StringUtils.startsWith(original, "sql:")) {
                String recent = SecureUtil.md5(original);
                log.trace("[Eurynome] |- CACHE - Secure the sql type key [{}] to [{}]", original, recent);
                return recent;
            }
        }
        return original;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public CaffeineCache getNativeCache() {
        return this.caffeineCache;
    }

    @Override
    public ValueWrapper get(Object key) {

        String secure = secure(key);

        ValueWrapper caffeineValue = caffeineCache.get(secure);
        if (ObjectUtils.isNotEmpty(caffeineValue)) {
            log.trace("[Eurynome] |- CACHE - Get from caffeine cache, hit key: [{}]", secure);
            return caffeineValue;
        }

        ValueWrapper redisValue = redisCache.get(secure);
        if (ObjectUtils.isNotEmpty(redisValue)) {
            log.trace("[Eurynome] |- CACHE - Get from the redis cache, hit key: [{}]", secure);
            caffeineCache.put(secure, redisValue.get());
            log.trace("[Eurynome] |- CACHE - Sync the cache to caffeine cache, in lookup action");
            return redisValue;
        }

        log.trace("[Eurynome] |- CACHE - Can not get the key: [{}] in whole cache", secure);

        return null;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {

        String secure = secure(key);

        T caffeineValue = caffeineCache.get(secure, type);
        if (ObjectUtils.isNotEmpty(caffeineValue)) {
            log.trace("[Eurynome] |- CACHE - Get with type from caffeine cache, hit key: [{}]", secure);
            return caffeineValue;
        }

        T redisValue = redisCache.get(secure, type);
        if (ObjectUtils.isNotEmpty(redisValue)) {
            log.trace("[Eurynome] |- CACHE - Get with type from the redis cache, hit key: [{}]", secure);
            caffeineCache.put(secure, redisValue);
            log.trace("[Eurynome] |- CACHE - Sync the cache to caffeine cache, in lookup action");
            return redisValue;
        }

        log.trace("[Eurynome] |- CACHE - Can not get the key: [{}] with TYPE in whole cache", secure);

        return null;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {

        String secure = secure(key);

        T caffeineValue = caffeineCache.get(secure, valueLoader);
        if (ObjectUtils.isNotEmpty(caffeineValue)) {
            log.trace("[Eurynome] |- CACHE - Get with valueLoader from caffeine cache, hit key: [{}]", secure);
            return caffeineValue;
        }

        T redisValue = redisCache.get(secure, valueLoader);
        if (ObjectUtils.isNotEmpty(redisValue)) {
            log.trace("[Eurynome] |- CACHE - Get with valueLoader from the redis cache, hit key: [{}]", secure);
            caffeineCache.put(secure, redisValue);
            log.trace("[Eurynome] |- CACHE - Sync the cache to caffeine cache, in lookup action");
            return redisValue;
        }

        log.trace("[Eurynome] |- CACHE - Can not get the key: [{}] with VALUELOADER in whole cache", secure);

        return null;
    }

    @Override
    public void put(Object key, Object value) {

        String secure = secure(key);

        redisCache.put(secure, value);
        log.trace("[Eurynome] |- CACHE - Put into redis cache, with key: [{}] and value: [{}]", secure, value);

        caffeineCache.put(secure, value);
        log.trace("[Eurynome] |- CACHE - Put into caffeine cache, with key: [{}] and value: [{}]", secure, value);
    }

    @Override
    public void evict(Object key) {

        String secure = secure(key);

        redisCache.evict(secure);
        log.trace("[Eurynome] |- CACHE - Evict redis cache, key: {}", secure);

        caffeineCache.evict(secure);
        log.trace("[Eurynome] |- CACHE - Evict caffeine cache, key: {}", secure);
    }

    @Override
    public void clear() {

        if (clearRemoteOnExit) {
            redisCache.clear();
            log.trace("[Eurynome] |- CACHE - Clear redis cache.");
        }

        caffeineCache.clear();
        log.trace("[Eurynome] |- CACHE - Clear caffeine cache.");
    }
}

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
 * Module Name: eurynome-cloud-cache
 * File Name: HerodotusCacheManager.java
 * Author: gengwei.zheng
 * Date: 2021/10/08 22:54:08
 */

package cn.herodotus.eurynome.cache.enhance.layer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Description: 自定义多级缓存管理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/14 11:07
 */
public class HerodotusCacheManager implements CacheManager {

    private static final Logger log = LoggerFactory.getLogger(HerodotusCacheManager.class);

    private RedisCacheManager redisCacheManager;
    private CaffeineCacheManager caffeineCacheManager;
    private boolean desensitization = true;
    private boolean clearRemoteOnExit = false;
    private boolean allowNullValues = true;

    private boolean dynamic = true;

    private final Map<String, Cache> cacheMap = new ConcurrentHashMap<>(16);

    public HerodotusCacheManager() {
    }

    public HerodotusCacheManager(String... cacheNames) {
        setCacheNames(Arrays.asList(cacheNames));
    }

    public void setRedisCacheManager(RedisCacheManager redisCacheManager) {
        this.redisCacheManager = redisCacheManager;
    }

    public void setCaffeineCacheManager(CaffeineCacheManager caffeineCacheManager) {
        this.caffeineCacheManager = caffeineCacheManager;
    }

    public void setDesensitization(boolean desensitization) {
        this.desensitization = desensitization;
    }

    public void setClearRemoteOnExit(boolean clearRemoteOnExit) {
        this.clearRemoteOnExit = clearRemoteOnExit;
    }

    public boolean isAllowNullValues() {
        return allowNullValues;
    }

    public void setAllowNullValues(boolean allowNullValues) {
        this.allowNullValues = allowNullValues;
    }

    /**
     * Specify the set of cache names for this CacheManager's 'static' mode.
     * <p>The number of caches and their names will be fixed after a call to this method,
     * with no creation of further cache regions at runtime.
     * <p>Calling this with a {@code null} collection argument resets the
     * mode to 'dynamic', allowing for further creation of caches again.
     */
    public void setCacheNames(@Nullable Collection<String> cacheNames) {
        if (cacheNames != null) {
            for (String name : cacheNames) {
                this.cacheMap.put(name, createHerodotusCache(name));
            }
            this.dynamic = false;
        } else {
            this.dynamic = true;
        }
    }

    protected Cache createHerodotusCache(String name) {
        CaffeineCache caffeineCache = (CaffeineCache) this.caffeineCacheManager.getCache(name);
        RedisCache redisCache = (RedisCache) this.redisCacheManager.getCache(name);
        log.debug("[Herodotus] |- CACHE - Herodotus cache [{}] is CREATED.", name);
        return new HerodotusCache(name, caffeineCache, redisCache, desensitization, clearRemoteOnExit, isAllowNullValues());
    }

    @Override
    @Nullable
    public Cache getCache(String name) {
        return this.cacheMap.computeIfAbsent(name, cacheName ->
                this.dynamic ? createHerodotusCache(cacheName) : null);
    }

    @Override
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(this.cacheMap.keySet());
    }
}

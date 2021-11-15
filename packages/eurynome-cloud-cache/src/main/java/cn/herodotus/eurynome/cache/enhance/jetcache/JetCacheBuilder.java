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
 * File Name: JetCacheBuilder.java
 * Author: gengwei.zheng
 * Date: 2021/10/28 10:06:28
 */

package cn.herodotus.eurynome.cache.enhance.jetcache;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.support.CacheContext;
import com.alicp.jetcache.anno.support.CachedAnnoConfig;
import com.alicp.jetcache.anno.support.SpringConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * <p>Description: 自定义 Jetcache 非注解创建 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/28 10:06
 */
public class JetCacheBuilder {

    private static final Logger log = LoggerFactory.getLogger(JetCacheBuilder.class);

    private static final String UNDEFINED = "$$undefined$$";
    private static final String DEFAULT_AREA = "default";
    private static final int DEFAULT_EXPIRE = -2147483648;

    private final SpringConfigProvider springConfigProvider;

    public JetCacheBuilder(SpringConfigProvider springConfigProvider) {
        this.springConfigProvider = springConfigProvider;
    }

    public CacheContext getCacheContext() {
        return springConfigProvider.getCacheContext();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <K, V> Cache<K, V> create(String name, CachedAnnoConfig cachedAnnoConfig) {
        Cache cache = this.getCacheContext().__createOrGetCache(cachedAnnoConfig, cachedAnnoConfig.getArea(), name);
        log.debug("[Herodotus] |- JetCacheBuilder create cache [{}].", cachedAnnoConfig.getName());
        return cache;
    }

    public <K, V> Cache<K, V> create(String name, String area, CacheType cacheType, int expire, int localexpire, TimeUnit timeUnit, int localLimit) {
        CachedAnnoConfig cachedAnnoConfig = new CachedAnnoConfig();
        cachedAnnoConfig.setArea(area);
        cachedAnnoConfig.setName(name);
        cachedAnnoConfig.setTimeUnit(timeUnit);
        cachedAnnoConfig.setExpire(expire);
        cachedAnnoConfig.setLocalExpire(localexpire);
        cachedAnnoConfig.setCacheType(cacheType);
        cachedAnnoConfig.setLocalLimit(localLimit);
        cachedAnnoConfig.setSerialPolicy(UNDEFINED);
        cachedAnnoConfig.setKeyConvertor(UNDEFINED);
        return create(name, cachedAnnoConfig);
    }

    public <K, V> Cache<K, V> create(String name, String area, CacheType cacheType, int expire, int localexpire, TimeUnit timeUnit) {
        return create(name, area, cacheType, expire, localexpire, timeUnit, DEFAULT_EXPIRE);
    }

    public <K, V> Cache<K, V> create(String name, String area, CacheType cacheType, int expire, TimeUnit timeUnit) {
        return create(name, area, cacheType, expire, DEFAULT_EXPIRE, timeUnit);
    }

    public <K, V> Cache<K, V> create(String name, CacheType cacheType, int expire, TimeUnit timeUnit) {
        return create(name, DEFAULT_AREA, cacheType, expire, timeUnit);
    }

    public <K, V> Cache<K, V> create(String name, CacheType cacheType, int expire) {
        return create(name, cacheType, expire, TimeUnit.SECONDS);
    }

    public <K, V> Cache<K, V> create(String name, int expire) {
        return create(name, CacheType.BOTH, expire);
    }

    public <K, V> Cache<K, V> create(String name) {
        return create(name, DEFAULT_EXPIRE);
    }
}

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
 * File Name: HerodotusMybatisCache.java
 * Author: gengwei.zheng
 * Date: 2021/10/28 10:10:28
 */

package cn.herodotus.eurynome.data.cache.layer;

import cn.herodotus.eurynome.data.cache.jetcache.JetCacheBuilder;
import cn.hutool.extra.spring.SpringUtil;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>Description: 扩展的Mybatis二级缓存 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/28 10:10
 */
public class HerodotusMybatisCache implements Cache {

    private static final Logger log = LoggerFactory.getLogger(HerodotusMybatisCache.class);

    private final String id;
    private final com.alicp.jetcache.Cache<Object, Object> cache;
    private final AtomicInteger counter = new AtomicInteger(0);

    public HerodotusMybatisCache(String id) {
        this.id = id;
        JetCacheBuilder jetCacheBuilder = SpringUtil.getBean("jetcacheBuilder");
        this.cache = jetCacheBuilder.create(this.id);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        cache.put(key, value);
        counter.incrementAndGet();
        log.debug("[Herodotus] |- CACHE - Put data into Mybatis Cache, with key: [{}]", key);
    }

    @Override
    public Object getObject(Object key) {
        Object obj = cache.get(key);
        log.debug("[Herodotus] |- CACHE - Get data from Mybatis Cache, with key: [{}]", key);
        return obj;
    }

    @Override
    public Object removeObject(Object key) {
        Object obj = cache.remove(key);
        counter.decrementAndGet();
        log.debug("[Herodotus] |- CACHE - Remove data from Mybatis Cache, with key: [{}]", key);
        return obj;
    }

    @Override
    public void clear() {
        cache.close();
        log.debug("[Herodotus] |- CACHE - Clear Mybatis Cache.");
    }

    @Override
    public int getSize() {
        return counter.get();
    }
}

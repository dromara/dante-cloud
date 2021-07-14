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
 * File Name: HerodotusDomainDataStorageAccess.java
 * Author: gengwei.zheng
 * Date: 2021/07/14 21:10:14
 */

package cn.herodotus.eurynome.data.jpa.hibernate.cache.spi;

import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.cache.spi.support.DomainDataStorageAccess;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.springframework.cache.Cache;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/14 21:10
 */
public class HerodotusDomainDataStorageAccess implements DomainDataStorageAccess {

    private final Cache cache;

    public HerodotusDomainDataStorageAccess(Cache cache) {
        this.cache = cache;
    }

    private Object get(Object key) {
        Cache.ValueWrapper value = cache.get(key);
        if (ObjectUtils.isNotEmpty(value)) {
            return value.get();
        }
        return null;
    }

    @Override
    public Object getFromCache(Object key, SharedSessionContractImplementor session) {
        return this.get(key);
    }

    @Override
    public void putIntoCache(Object key, Object value, SharedSessionContractImplementor session) {
        cache.put(key, value);
    }

    @Override
    public boolean contains(Object key) {
        Object value = this.get(key);
        return ObjectUtils.isNotEmpty(value);
    }

    @Override
    public void evictData() {
        cache.clear();
    }

    @Override
    public void evictData(Object key) {
        cache.evict(key);
    }

    @Override
    public void release() {
        cache.invalidate();
    }
}

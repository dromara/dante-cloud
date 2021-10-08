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
 * File Name: AbstractCache.java
 * Author: gengwei.zheng
 * Date: 2021/06/29 15:58:29
 */

package cn.herodotus.eurynome.data.cache.query;

import cn.herodotus.eurynome.common.constant.magic.CacheConstants;
import cn.herodotus.eurynome.common.constant.magic.SymbolConstants;
import cn.herodotus.eurynome.common.definition.entity.AbstractEntity;
import com.alicp.jetcache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Array;
import java.util.*;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: AbstractCache </p>
 *
 * <p>Description: JetCache通用方法抽象类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/6/3 10:09
 */
@Slf4j
public abstract class AbstractCache<E extends AbstractEntity> extends CacheConstants {

    private static final String CACHE_NAME_ENTIRE = "entire";
    private static final String CACHE_NAME_DELETE_INDEX_LINK = "delete:link";
    private static final String CACHE_NAME_CLEAN_INDEX = "delete:clean";

    /**
     * jetcache 数据缓存，只存储实体数据
     *
     * @return jetcache
     */
    public abstract Cache<String, E> getCache();

    /**
     * jetcache 索引缓存
     * 增加一个额外的缓存，存储各类查询返回的实体ID集合作为索引
     * 以解决分页、条件查询等数据缓存问题。
     *
     * @return jetcache
     */
    public abstract Cache<String, Set<String>> getIndexCache();

    /**
     * 判断数据缓存Cache是否已经在Service中配置。
     *
     * @return True已经配置，False没有配置。
     */
    private boolean isCacheConfigured() {
        if (ObjectUtils.isNotEmpty(getCache())) {
            return true;
        } else {
            log.error("[Herodotus] |- Data Cache must be configured in the Service class or the cache will not take effect！");
            return false;
        }
    }

    /**
     * 判断数据缓存Index Cache是否已经在Service中配置。
     *
     * @return True已经配置，False没有配置。
     */
    private boolean isIndexCacheConfigured() {
        if (ObjectUtils.isNotEmpty(getIndexCache())) {
            return true;
        } else {
            log.error("[Herodotus] |- Index Cache must be configured in the Service class or the cache will not take effect！");
            return false;
        }
    }

    /**
     * 根据方法参数生成，生成Cache Name。
     * <p>
     * 这里叫Name而不是Key，主要原因是：
     * 1. 很多缓存中都是用Key这个英文名称，但是由于缓存的东西比较多，到处都是Key，很容让人混淆。
     * 2. Index 缓存中会针对不同的用途以及查询条件，用不同的名字作为Key，存储数据实体Id，以便从数据Cache中获取数据。
     * 3. 正因为如此，随时都用Key来命名会让人混淆。
     * 4. Service中，在创建Cache的时候，已经指定了名字，相当于一个实体对应一个数据Cache和Index Cache，就已经进行了隔离。
     * 5. 那么这里用Name这个英文来指代缓存Key，比用Key这个英文更容易理解不易混淆
     * <p>
     * 如果没有指定任何参数，那么就任务是findAll()查询。那么就将所有的索引放入all中。
     *
     * @param params 查询参数
     * @return Key
     */
    protected String generateIndexCacheName(Object... params) {
        StringBuilder key = new StringBuilder();
        if (ArrayUtils.isNotEmpty(params)) {
            for (Object param : params) {
                if (ClassUtils.isPrimitiveArray(param.getClass())) {
                    int length = Array.getLength(param);
                    for (int i = 0; i < length; i++) {
                        key.append(Array.get(param, i));
                        key.append(SymbolConstants.COMMA);
                    }
                } else if (ClassUtils.isPrimitiveOrWrapper(param.getClass()) || param instanceof String) {
                    key.append(param);
                } else {
                    log.warn("[Herodotus] |- Using an object as a cache name may lead to unexpected results. ");
                    key.append(param.hashCode());
                }
                key.append(SymbolConstants.DASH);
            }

            String dynamicKey = key.toString();
            log.debug("[Herodotus] |- Generate dynamic cache name={}", dynamicKey);
            return dynamicKey;
        } else {
            log.debug("[Herodotus] |- Using default cache name={}", CACHE_NAME_ENTIRE);
            return CACHE_NAME_ENTIRE;
        }
    }

    /**
     * 对放入缓存的Set进行类型指定。
     * <p>
     * Set是基类，如果不指定类型，读取时会出现错误。
     *
     * @param indexes Index Cache 缓存的值。
     * @return 实例化后的缓存值
     */
    private Set<String> instanceIndexCacheValue(Set<String> indexes) {
        Assert.notNull(indexes, "Indexes must not be null");
        if (indexes instanceof LinkedHashSet) {
            return indexes;
        } else {
            return new LinkedHashSet<>(indexes);
        }
    }

    /**
     * 将单独的值转换为Set工具方法，方便在Index Cache中存储
     *
     * @param value 需要在Index Cache中存储的值
     * @return Index Cache 值
     */
    private Set<String> convertToIndexCacheValue(String value) {
        Assert.notNull(value, "Value must not be null");
        Set<String> values = new LinkedHashSet<>();
        values.add(value);
        return values;
    }

    /**
     * 从Index Cache索引缓存读取值。
     * 如果Service中没有配置Index缓存，那么就仅返回一个空Set。
     *
     * @param cacheName 索引缓存Key
     * @return Set<String> 索引缓存
     */
    private Set<String> getFromIndexCache(String cacheName) {
        Assert.notNull(cacheName, "CacheName must not be null");
        if (isIndexCacheConfigured()) {
            Set<String> indexes = getIndexCache().get(cacheName);
            if (CollectionUtils.isNotEmpty(indexes)) {
                return instanceIndexCacheValue(indexes);
            }
        }

        return new LinkedHashSet<>();
    }

    /**
     * 读取索引缓存的值，方便获取只有一个值的cache，
     *
     * @param cacheName index 缓存名称
     * @return 缓存存储的值
     */
    protected String getOneValueFromIndexCache(String cacheName) {
        Set<String> indexes = getFromIndexCache(cacheName);
        if (CollectionUtils.isNotEmpty(indexes)) {
            List<String> temp = new ArrayList<>(indexes);
            return temp.get(0);
        }
        return null;
    }

    /**
     * 从Cache中（数据缓存）读取一个已缓存实体
     *
     * @param cacheName 实体标识 {@link AbstractEntity#getId()}
     * @return 缓存的实体
     */
    private E getFromCache(String cacheName) {
        Assert.notNull(cacheName, "CacheName must not be null");
        if (isCacheConfigured()) {
            return getCache().get(cacheName);
        }
        return null;
    }

    /**
     * 从缓存中读取多个实体，获取实体List
     *
     * @param indexes 实体缓存Key的集合
     * @return 实体列表
     */
    private List<E> getFromCache(Set<String> indexes) {
        if (isCacheConfigured() && CollectionUtils.isNotEmpty(indexes)) {
            Map<String, E> domains = getCache().getAll(indexes);
            return new ArrayList<>(domains.values());
        }
        return new ArrayList<>();
    }


    /**
     * 向Index Cache中批量写入值
     *
     * @param caches 多个缓存内容
     */
    private void putIntoIndexCache(Map<String, Set<String>> caches) {
        if (isIndexCacheConfigured() && MapUtils.isNotEmpty(caches)) {
            getIndexCache().putAll(caches);
        }
    }

    /**
     * 向Index Cache中写入值
     *
     * @param cacheName 缓存名称，即Jetcache中的Key
     * @param indexes   cacheName对应的值
     */
    private void putIntoIndexCache(String cacheName, Set<String> indexes) {
        Assert.notNull(cacheName, "CacheName must not be null");
        if (isIndexCacheConfigured() && CollectionUtils.isNotEmpty(indexes)) {
            getIndexCache().put(cacheName, indexes);
        }
    }

    /**
     * 向Index Cache中写入值。
     *
     * @param cacheName  缓存名称，即Jetcache中的Key
     * @param cacheValue cacheName对应的值
     */
    protected void putIntoIndexCache(String cacheName, String cacheValue) {
        putIntoIndexCache(cacheName, convertToIndexCacheValue(cacheValue));
    }


    /**
     * 向Index Cache某个缓存中，增加值
     *
     * @param cacheName 索引缓存Key
     * @param indexes   索引缓存Value集合
     */
    private void appendToIndexCache(String cacheName, Set<String> indexes) {
        if (CollectionUtils.isNotEmpty(indexes)) {
            Set<String> cachedIndexes = getFromIndexCache(cacheName);
            if (CollectionUtils.isNotEmpty(cachedIndexes)) {
                cachedIndexes.addAll(indexes);
                putIntoIndexCache(cacheName, cachedIndexes);
            } else {
                putIntoIndexCache(cacheName, instanceIndexCacheValue(indexes));
            }
        }
    }

    /**
     * 添加 Index Cache
     *
     * @param cacheName 索引缓存Key
     * @param value     索引缓存Value
     */
    private void appendToIndexCache(String cacheName, String value) {
        if (StringUtils.isNotBlank(value)) {
            appendToIndexCache(cacheName, convertToIndexCacheValue(value));
        }
    }

    /**
     * 向Cache中写入多个值
     *
     * @param caches 多个缓存内容
     */
    protected void putIntoCache(Map<String, E> caches) {
        if (isCacheConfigured() && MapUtils.isNotEmpty(caches)) {
            getCache().putAll(caches);
        }
    }

    /**
     * 向Cache中写入一个实体
     *
     * @param object 实体
     */
    protected void putIntoCache(E object) {
        if (isCacheConfigured() && ObjectUtils.isNotEmpty(object)) {
            Assert.notNull(object.getId(), "object.getId() must not be null");
            getCache().put(object.getId(), object);
        }
    }

    /**
     * 创建属性关联映射。
     *
     * @param cacheTemplate 自定义Index缓存存储模版。
     */
    private void createPropertyLink(CacheTemplate<E> cacheTemplate) {
        // 如果有反向关联，则添加
        if (cacheTemplate.hasPropertyLink()) {
            appendToIndexCache(CACHE_NAME_DELETE_INDEX_LINK, cacheTemplate.getDeleteIndexes());
            appendToIndexCache(CACHE_NAME_CLEAN_INDEX, CACHE_NAME_DELETE_INDEX_LINK);
            putIntoIndexCache(cacheTemplate.getPropertyLinks());
        }
    }

    /**
     * 缓存一个实体
     *
     * @param object 实体
     */
    public void writeToCache(E object) {
        if (ObjectUtils.isNotEmpty(object)) {
            E cachedDomain = getFromCache(object.getId());
            if (ObjectUtils.isEmpty(cachedDomain)) {
                // 如果缓存中读取不到该对象，意味着是新增操作。
                // 那么就将所有的索引清空，以防漏查数据。
                clearIndexCache();

                CacheTemplate<E> cacheTemplate = new CacheTemplate<>();
                cacheTemplate.append(object);
                // 如果有反向关联，则添加
                createPropertyLink(cacheTemplate);
            }

            // 写入缓存数据
            putIntoCache(object);
        }
    }

    /**
     * 针对数据量不大的、而且使用非常频繁的findAll操作进行缓存。以便提升查询速度。
     * 如果数据量很大，使用该种方式缓存，会将数据全部放入内存中。
     * 如果内存可以支持的了，可以考虑；否则建议使用其它方式提升查询效率。
     *
     * @param objects
     * @param params
     */
    public void writeToCache(Collection<E> objects, Object... params) {
        if (CollectionUtils.isNotEmpty(objects)) {
            String cacheName = generateIndexCacheName(params);

            CacheTemplate<E> cacheTemplate = new CacheTemplate<>();
            cacheTemplate.append(objects);

            // 写入缓存数据
            putIntoCache(cacheTemplate.getDomains());
            // 添加数据查询索引
            appendToIndexCache(cacheName, cacheTemplate.getQueryIndexes());
            appendToIndexCache(CACHE_NAME_CLEAN_INDEX, cacheName);
            // 如果有反向关联，则添加
            createPropertyLink(cacheTemplate);
        }
    }


    /**
     * 从缓存中读取一个实体
     *
     * @param domainId 实体ID
     * @return 实体
     */
    public E readOneFromCache(String domainId) {
        return getFromCache(domainId);
    }

    /**
     * 通过映射实体属性获取实体
     *
     * @param domainLinkProperty 映射属性{@link AbstractEntity#getLinkedProperty()}
     * @return 实体
     */
    public E readOneFromCacheByLink(String domainLinkProperty) {
        String link = getOneValueFromIndexCache(domainLinkProperty);
        if (StringUtils.isNotBlank(link)) {
            return readOneFromCache(link);
        }
        return null;
    }

    /**
     * 根据参数（查询条件）从缓存中读取数据
     *
     * @param params 查询条件
     * @return 实体列表
     */
    public List<E> readFromCache(Object... params) {
        String cacheName = generateIndexCacheName(params);
        Set<String> indexes = getFromIndexCache(cacheName);
        return getFromCache(indexes);
    }

    protected long parseTotal(String value) {
        if (StringUtils.isNotBlank(value)) {
            return Long.parseLong(value);
        }
        return 0;
    }

    /**
     * 从数据缓存中删除
     *
     * @param domainId 实体ID
     */
    private void removeFromCache(String domainId) {
        Assert.notNull(domainId, "DomainId must not be null");
        if (isCacheConfigured()) {
            getCache().remove(domainId);
        }
    }

    /**
     * 从Index Cache中批量删除
     *
     * @param indexes 缓存Key
     */
    private void removeFromIndexCache(Set<String> indexes) {
        if (isIndexCacheConfigured() && CollectionUtils.isNotEmpty(indexes)) {
            getIndexCache().removeAll(indexes);
        }
    }

    private void clearIndexCache(String cacheName) {
        Set<String> indexes = getFromIndexCache(cacheName);
        removeFromIndexCache(indexes);
    }

    private void clearIndexCache() {
        clearIndexCache(CACHE_NAME_DELETE_INDEX_LINK);
        clearIndexCache(CACHE_NAME_CLEAN_INDEX);
    }

    /**
     * 删除一个实体
     *
     * @param id 实体ID
     */
    public void deleteFromCache(String id) {
        removeFromCache(id);
        clearIndexCache();
    }
}

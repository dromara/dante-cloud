package cn.herodotus.eurynome.component.data.base.service;

import cn.herodotus.eurynome.component.data.base.entity.BaseCacheEntity;
import com.alicp.jetcache.Cache;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p> Description : 基础缓存服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/19 21:43
 */
public abstract class BaseCacheService<D extends BaseCacheEntity, ID extends Serializable> extends BaseCrudService<D, ID> {

    private static final String CACHE_ALL_INDEX_KEY = "all";

    /**
     * jetcache 数据缓存
     * @return jetcache
     */
    public abstract Cache<String, D> getCache();

    /**
     * jetcache 索引缓存
     * @return jetcache
     */
    public abstract Cache<String, Set<String>> getIndexCache();

    private Map<String, D> collectionToMap(Collection<D> objects) {
        return objects.stream().collect(Collectors.toMap(D::getDomainCacheKey, object -> object));
    }

    private Set<String> collectionIdToSet(Collection<D> objects) {
        return objects.stream().map(D::getDomainCacheKey).collect(Collectors.toSet());
    }

    /**
     * 针对数据量不大的、而且使用非常频繁的findAll操作进行缓存。以便提升查询速度。
     * 如果数据量很大，使用该种方式缓存，会将数据全部放入内存中。
     * 如果内存可以支持的了，可以考虑；否则建议使用其它方式提升查询效率。
     * <p>
     * 其它比如page或者条件查询，目前看做缓存意义不大。根据实际情况再考虑是否增加
     *
     * @param objects
     */
    protected void cacheAll(Collection<D> objects) {
        if (CollectionUtils.isNotEmpty(objects) && ObjectUtils.isNotEmpty(getCache())) {
            Set<String> allIndexSet = collectionIdToSet(objects);
            getIndexCache().put(CACHE_ALL_INDEX_KEY, allIndexSet);
            getCache().putAll(collectionToMap(objects));
        }
    }

    protected void cache(Collection<D> objects) {
        if (CollectionUtils.isNotEmpty(objects) && ObjectUtils.isNotEmpty(getCache())) {
            getCache().putAll(collectionToMap(objects));
        }
    }


    protected List<D> getAllFromCache() {
        Set<String> allIndexSet = getIndexCache().get(CACHE_ALL_INDEX_KEY);
        if (CollectionUtils.isNotEmpty(allIndexSet)) {
            Map<String, D> cachedDatas = getCache().getAll(allIndexSet);
            return new ArrayList<>(cachedDatas.values());
        }

        return null;
    }

    protected D getFromCache(String objectKey) {
        return getCache().get(objectKey);
    }

    protected void remove(String objectKey) {
        Set<String> allIndexSet = getIndexCache().get(CACHE_ALL_INDEX_KEY);
        if (CollectionUtils.isNotEmpty(allIndexSet)) {
            allIndexSet.remove(objectKey);
            getIndexCache().put(CACHE_ALL_INDEX_KEY, allIndexSet);
        }

        this.getCache().remove(objectKey);
    }

    protected void cache(D object) {
        if (ObjectUtils.isNotEmpty(object) && ObjectUtils.isNotEmpty(getCache())) {
            getCache().put(object.getDomainCacheKey(), object);
        }
    }
}

package cn.herodotus.eurynome.component.data.base.service;

import cn.herodotus.eurynome.component.common.constants.SymbolConstants;
import cn.herodotus.eurynome.component.data.base.entity.AbstractEntity;
import com.alicp.jetcache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.ClassUtils;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p> Description : 基础缓存服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/19 21:43
 */
@Slf4j
public abstract class AbstractCacheService<E extends AbstractEntity, ID extends Serializable> implements Service<E, ID> {

    private static final String INDEX_CACHE_KEY_FIND_ALL = "all";
    private static final String INDEX_CACHE_KEY_PAGE_CONTENT = "pagination:content";
    private static final String INDEX_CACHE_KEY_PAGE_TURN = "pagination:turn";
    private static final String INDEX_CACHE_KEY_PAGE_TOTAL = "pagination:total";
    private static final String INDEX_CACHE_KEY_CONDITIONALS = "conditionals";
    private static final String INDEX_CACHE_KEY_PROPERTY_LINK = "property:link";
    private static final String INDEX_CACHE_KEY_PROPERTY_REVERSE = "property:reverse";

    /**
     * jetcache 数据缓存
     *
     * @return jetcache
     */
    public abstract Cache<String, E> getCache();

    /**
     * jetcache 索引缓存
     *
     * @return jetcache
     */
    public abstract Cache<String, Set<String>> getIndexCache();

    /**
     * 集合转为Map工具方法
     *
     * @param objects 实体集合
     * @return Map
     */
    private Map<String, E> collectionToMap(Collection<E> objects) {
        return objects.stream().map(this::createLink).collect(Collectors.toMap(E::getId, object -> object));
    }

    /**
     * 把集合中实体的Id取出，拼装为Set工具方法
     *
     * @param objects 实体集合
     * @return 实体Id Set
     */
    private Set<String> collectionIdToSet(Collection<E> objects) {
        return objects.stream().map(E::getId).collect(Collectors.toSet());
    }

    private String getOneFromSet(Set<String> collection) {
        List<String> list = new ArrayList<>(collection);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 避免空值检测工具方法
     *
     * @param objects 实体集合
     * @return 是否为空
     */
    private boolean isNotEmpty(Collection<E> objects) {
        return CollectionUtils.isNotEmpty(objects) && ObjectUtils.isNotEmpty(getCache()) && ObjectUtils.isNotEmpty(getIndexCache());
    }

    private boolean isNotEmpty(E object) {
        return ObjectUtils.isNotEmpty(object) && ObjectUtils.isNotEmpty(getCache()) && ObjectUtils.isNotEmpty(getIndexCache());
    }

    /**
     * 实体是否存在于缓存中
     *
     * @param object 实体
     * @return 是否存在
     */
    private boolean isExist(E object) {
        E domain = getFromCache(object.getId());
        return !ObjectUtils.isEmpty(domain);
    }

    private boolean isNotExist(E object) {
        return !isExist(object);
    }

    /**
     * 读取一个已缓存实体
     *
     * @param key 实体标识 {@link AbstractEntity#getId()}
     * @return 缓存的实体
     */
    protected E getFromCache(String key) {
        return getCache().get(key);
    }

    /**
     * 读取数据对应的索引
     *
     * @param indexKey 索引缓存Key
     * @return Set<String> 索引缓存
     */
    protected Set<String> getFromIndexCache(String indexKey) {
        Set<String> result = new HashSet<>();

        Set<String> indexes = getIndexCache().get(indexKey);
        if (CollectionUtils.isNotEmpty(indexes)) {
            result.addAll(indexes);
        }

        return result;
    }

    /**
     * 根据索引缓存中的实体key值，从数据索引中读取数据
     *
     * @param indexKey 索引缓存Key
     * @return List<D> 缓存实体
     */
    protected List<E> findFromCache(String indexKey) {
        Set<String> indexes = getFromIndexCache(indexKey);
        if (CollectionUtils.isNotEmpty(indexes)) {
            Map<String, E> domains = getCache().getAll(indexes);
            return new ArrayList<>(domains.values());
        }
        return new ArrayList<>();
    }

    private void cacheIndex(String indexKey, String indexValue) {
        Set<String> indexes = new HashSet<>();
        indexes.add(indexValue);
        cacheIndex(indexKey, indexes);
    }

    private void cacheIndex(String indexKey, Set<String> indexes) {
        getIndexCache().put(indexKey, indexes);
    }

    private void removeIndex(String indexKey) {
        getIndexCache().remove(indexKey);
    }

    private void removeIndexAll(Set<String> indexes) {
        getIndexCache().removeAll(indexes);
    }

    /**
     * 添加 Index Cache
     *
     * @param indexKey   索引缓存Key
     * @param indexValue 索引缓存Value
     */
    private void appendIndex(String indexKey, String indexValue) {
        Set<String> indexes = getFromIndexCache(indexKey);
        if (!indexes.contains(indexValue)) {
            indexes.add(indexValue);
            cacheIndex(indexKey, indexes);
        }
    }

    /**
     * 添加 Index Cache
     *
     * @param indexKey 索引缓存Key
     * @param indexes  索引缓存Value集合
     */
    private void appendIndexes(String indexKey, Set<String> indexes) {
        Set<String> cachedIndexes = getFromIndexCache(indexKey);
        if (!CollectionUtils.containsAll(cachedIndexes, indexes)) {
            log.debug("[Herodotus] |- Before cacheIndexes destination={}, source={}", cachedIndexes.size(), indexes.size());
            cachedIndexes.addAll(indexes);
            log.debug("[Herodotus] |- After cacheIndexes destination={}", cachedIndexes.size());
            cacheIndex(indexKey, cachedIndexes);
        }
    }

    /**
     * 添加 Index Cache
     *
     * @param objects  实体集合
     * @param indexKey 索引缓存Key
     */
    private void appendIndexes(Collection<E> objects, String indexKey) {
        Set<String> dataIndexes = collectionIdToSet(objects);
        appendIndexes(indexKey, dataIndexes);
    }

    private String generatePageDataIndexKey(int pageNumber, int pageSize) {
        return INDEX_CACHE_KEY_PAGE_CONTENT + SymbolConstants.UNDERLINE + pageSize + SymbolConstants.UNDERLINE + (pageNumber + 1);
    }

    /**
     * 根据动态参数生成，自动生成Index Key
     *
     * @param params 动态参数
     * @return Key
     */
    private String generateConditionalIndexKey(Object... params) {
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
                    log.warn("[Herodotus] |- Using an object as a cache key may lead to unexpected results. ");
                    key.append(param.hashCode());
                }
                key.append(SymbolConstants.DASH);
            }
        }

        String result = key.toString();
        log.debug("[Herodotus] |- Using cache key={}", result);
        return result;
    }

    /**
     * 清楚Page相关的索引
     */
    private void clearPageIndex() {
        Set<String> indexes = getFromIndexCache(INDEX_CACHE_KEY_PAGE_TURN);
        if (CollectionUtils.isNotEmpty(indexes)) {
            removeIndexAll(indexes);
        }
        removeIndex(INDEX_CACHE_KEY_PAGE_TURN);
        removeIndex(INDEX_CACHE_KEY_PAGE_TOTAL);
    }

    private void clearFindAllIndex() {
        removeIndex(INDEX_CACHE_KEY_FIND_ALL);
    }

    private void clearConditionalIndex() {
        removeIndex(INDEX_CACHE_KEY_CONDITIONALS);
    }

    private void clearLinkIndex() {
        Set<String> linkIndexes = getFromIndexCache(INDEX_CACHE_KEY_PROPERTY_LINK);
        Set<String> reverseIndexes = getFromIndexCache(INDEX_CACHE_KEY_PROPERTY_REVERSE);
        removeIndexAll(linkIndexes);
        removeIndexAll(reverseIndexes);
    }

    /**
     * 删除所有索引缓存
     */
    private void clearIndex() {
        clearFindAllIndex();
        clearPageIndex();
        clearConditionalIndex();
    }

    private E createLink(E object) {
        if (ObjectUtils.isNotEmpty(object) && StringUtils.isNotBlank(object.getLinkedProperty())) {
            appendIndex(INDEX_CACHE_KEY_PROPERTY_LINK, object.getLinkedProperty());
            appendIndex(INDEX_CACHE_KEY_PROPERTY_REVERSE, object.getId());
            cacheIndex(object.getLinkedProperty(), object.getId());
            cacheIndex(object.getId(), object.getLinkedProperty());
        }

        return object;
    }

    private void removeLink(String domainCacheKey) {
        Set<String> reverseIndex = getFromIndexCache(domainCacheKey);
        String linkedProperty = getOneFromSet(reverseIndex);
        removeIndex(domainCacheKey);
        removeIndex(linkedProperty);
    }

    /**
     * 通过linkedProperty读取缓存实体
     *
     * @param linkedProperty 实体关联property值
     * @return 缓存实体
     */
    protected E getFromLinkedProperties(String linkedProperty) {
        Set<String> linkIndex = getFromIndexCache(linkedProperty);
        String reverseIndex = getOneFromSet(linkIndex);
        if (StringUtils.isNotBlank(reverseIndex)) {
            return getFromCache(reverseIndex);
        }
        return null;
    }

    /**
     * 缓存或者更新一个实体
     *
     * @param object 实体对象
     */
    protected void cache(E object) {
        if (isNotEmpty(object)) {
            if (isNotExist(object)) {
                clearIndex();
                createLink(object);
            }
            getCache().put(object.getId(), object);
        }
    }

    /**
     * 缓存实体集合
     *
     * @param objects 实体集合
     */
    protected void cache(Collection<E> objects) {
        getCache().putAll(collectionToMap(objects));
    }


    /**
     * 缓存实体集合，并根据指定的index key生成实体的索引
     *
     * @param objects  实体集合
     * @param indexKey 集合索引Key
     */
    private void cache(Collection<E> objects, String indexKey) {
        if (isNotEmpty(objects)) {
            appendIndexes(objects, indexKey);
            cache(objects);
        }
    }

    /**
     * 缓存全部实体。
     * <p>
     * 针对数据量不大的、而且使用非常频繁的findAll操作进行缓存。以便提升查询速度。
     * 如果数据量很大，使用该种方式缓存，会将数据全部放入内存中。
     * 如果内存可以支持的了，可以考虑；否则建议使用其它方式提升查询效率。
     *
     * @param objects 实体对象集合
     */
    protected void cacheFindAll(Collection<E> objects) {
        cache(objects, INDEX_CACHE_KEY_FIND_ALL);
    }

    /**
     * 从缓存中获取全部实体
     *
     * @return 全部实体集合
     */
    protected List<E> getFindAllFromCache() {
        return findFromCache(INDEX_CACHE_KEY_FIND_ALL);
    }


    /**
     * 根据动态参数生成数据缓存key，并进行数据缓存。用于条件查询。
     *
     * @param objects 实体集合
     * @param params  动态参数
     */
    protected void cacheConditional(Collection<E> objects, Object... params) {
        String key = generateConditionalIndexKey(params);
        appendIndex(INDEX_CACHE_KEY_CONDITIONALS, key);
        cache(objects, key);
    }

    /**
     * 根据动态参数获取缓存实体。用于条件查询，动态参数要与cacheConditional一致，才能获取结果。
     *
     * @param params 动态参数
     * @return List 实体集合
     */
    protected List<E> getConditionalFromCache(Object... params) {
        String key = generateConditionalIndexKey(params);
        return findFromCache(key);
    }


    /**
     * 缓存JPA Page相关数据
     *
     * @param pages jpa 分页查询结果
     */
    protected void cachePage(Page<E> pages) {
        Collection<E> objects = pages.getContent();
        if (isNotEmpty(objects)) {
            String pageIndexKey = generatePageDataIndexKey(pages.getNumber(), pages.getSize());
            appendIndex(INDEX_CACHE_KEY_PAGE_TURN, pageIndexKey);
            appendIndex(INDEX_CACHE_KEY_PAGE_TOTAL, String.valueOf(pages.getTotalElements()));
            appendIndexes(objects, pageIndexKey);
            cache(objects);
        }
    }

    /**
     * 从缓存中读取分页数据
     *
     * @param pageNumber 当前页码
     * @param pageSize 每页数据条数
     * @return Page Jpa Page对象
     */
    protected Page<E> getPageFromCache(int pageNumber, int pageSize) {
        List<E> content = findFromCache(generatePageDataIndexKey(pageNumber, pageSize));
        Set<String> totalIndexes = getFromIndexCache(INDEX_CACHE_KEY_PAGE_TOTAL);
        String totalValue = getOneFromSet(totalIndexes);

        if (CollectionUtils.isNotEmpty(content) && StringUtils.isNotBlank(totalValue)) {
            return new PageImpl<>(content, PageRequest.of(pageNumber, pageSize), Long.parseLong(totalValue));
        } else {
            return new PageImpl<>(new ArrayList<>(), PageRequest.of(pageNumber, pageSize), 0);
        }
    }


    /**
     * 从FindAll操作索引中，删除指定key
     *
     * @param key 指定索引中的Key
     */
    private void removeFindAllIndex(String key) {
        Set<String> indexes = getFromIndexCache(INDEX_CACHE_KEY_FIND_ALL);
        if (CollectionUtils.isNotEmpty(indexes)) {
            indexes.remove(key);
            cacheIndex(INDEX_CACHE_KEY_FIND_ALL, indexes);
        }
    }

    protected void remove(String key) {
        clearPageIndex();
        clearConditionalIndex();
        getCache().remove(key);
        removeLink(key);
        removeFindAllIndex(key);
    }
}

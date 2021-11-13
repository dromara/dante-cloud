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
 * Module Name: eurynome-cloud-rest
 * File Name: AbstractCacheService.java
 * Author: gengwei.zheng
 * Date: 2021/08/26 20:40:26
 */

package cn.herodotus.eurynome.rest.base.service;

import cn.herodotus.eurynome.assistant.constant.SymbolConstants;
import cn.herodotus.eurynome.assistant.definition.entity.AbstractEntity;
import cn.herodotus.eurynome.assistant.definition.entity.Entity;
import cn.herodotus.eurynome.cache.enhance.query.AbstractCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.io.Serializable;
import java.util.List;

/**
 * <p> Description : 基础缓存服务 </p>
 * <p>
 * 不管是Spring Cache 还是 JetCache，对于单独对象缓存的处理都不会有太大问题。主要存在问题的点在于全部查询、条件查询、分页查询的处理上。
 * 目前大多采用的方式是直接缓存查询返回的集合，这样带来的问题就是集合中某个数据存在修改、删除就不会表现出来。
 * 根据目前使用的情况看，只有ehcache在这方面处理比较好，但是它只能本地缓存。因此自己设计一些处理，来部分解决查询缓存的问题。
 * <p>
 * 主要实现逻辑：针对每个实体服务，构建两个jetcache。一个Data Cache和一个Index Cache
 * 一、Data Cache：
 * 1、用来存储基础数据，以{@link Entity#getId()}的返回值作为数据缓存key，通常为实体ID，value即为key对应实体
 * 2、只用ID作为缓存Key存储数据，只能通过ID查询，对于类似findByUsername的查询就难以支持。
 * 通过{@link AbstractEntity#getLinkedProperty()} 来指定非ID查询属性，缓存ID与该属性以及实体的映射关系来实现。
 * 这种方式只试用于，实体中存在除了ID以外还有unique字段的情况，用getLinkedProperty()指定该unique字段。
 *
 * @author : gengwei.zheng
 * @date : 2020/2/19 21:43
 */
@Slf4j
public abstract class AbstractCacheService<E extends AbstractEntity, ID extends Serializable> extends AbstractCache<E> {

    private static final String CACHE_NAME_PREFIX_PAGE = "page:";
    private static final String CACHE_NAME_PAGE_TOTAL = "total";

    private String generatePageIndexCacheNamePrefix(String queryName) {
        return CACHE_NAME_PREFIX_PAGE + queryName + SymbolConstants.COLON;
    }

    private String generatePageTotalIndexCacheName(String queryName) {
        return generatePageIndexCacheNamePrefix(queryName) + CACHE_NAME_PAGE_TOTAL;
    }

    private String generatePageIndexCacheName(String queryName, int pageNumber, int pageSize) {
        return generatePageIndexCacheNamePrefix(queryName) + pageSize + SymbolConstants.DASH + (pageNumber + 1);
    }

    /**
     * 缓存分页数据
     *
     * @param pages  JPA Page对象
     * @param params 除了pageNumber 和 pageSize以外的参数
     */
    public void writeToCache(Page<E> pages, Object... params) {
        String queryName = generateIndexCacheName(params);
        String cacheName = generatePageIndexCacheName(queryName, pages.getNumber(), pages.getSize());
        String totalCacheName = generatePageTotalIndexCacheName(queryName);
        writeToCache(pages.getContent(), cacheName);
        putIntoIndexCache(totalCacheName, String.valueOf(pages.getTotalElements()));
    }

    /**
     * 读取分页数据
     *
     * @param pageNumber 当前页面
     * @param pageSize   数据条目
     * @param params     查询条件
     * @return JPA page
     */
    public Page<E> readPageFromCache(int pageNumber, int pageSize, Object... params) {
        String queryName = generateIndexCacheName(params);
        String cacheName = generatePageIndexCacheName(queryName, pageNumber, pageSize);
        String totalCacheName = generatePageTotalIndexCacheName(queryName);
        List<E> content = readFromCache(cacheName);
        long total = parseTotal(getOneValueFromIndexCache(totalCacheName));
        return new PageImpl<>(content, PageRequest.of(pageNumber, pageSize), total);
    }


}

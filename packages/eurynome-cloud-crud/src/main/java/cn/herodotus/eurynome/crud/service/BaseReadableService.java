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
 * Module Name: eurynome-cloud-crud
 * File Name: BaseReadableService.java
 * Author: gengwei.zheng
 * Date: 2021/07/07 16:51:07
 */

package cn.herodotus.eurynome.crud.service;

import cn.herodotus.eurynome.common.definition.entity.AbstractEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Description: 只读操作的基础通用Service </p>
 *
 * 该Service只包含“读”相关的操作，这个是为了支持数据库视图的读取。
 *
 * @author : gengwei.zheng
 * @date : 2021/7/7 16:51
 */
public abstract class BaseReadableService <E extends AbstractEntity, ID extends Serializable> extends AbstractCacheService<E, ID> implements ReadableService<E, ID>{

    private final static Logger log = LoggerFactory.getLogger(BaseReadableService.class);

    @Override
    public E findById(ID id) {
        E domain = readOneFromCache(String.valueOf(id));
        if (ObjectUtils.isEmpty(domain)) {
            domain = ReadableService.super.findById(id);
            writeToCache(domain);
        }

        log.debug("[Eurynome] |- BaseReadableService findById.");
        return domain;
    }

    @Override
    public Page<E> findByPage(int pageNumber, int pageSize) {
        Page<E> pages = readPageFromCache(pageNumber, pageSize);
        if (CollectionUtils.isEmpty(pages.getContent())) {
            pages = ReadableService.super.findByPage(pageNumber, pageSize);
            writeToCache(pages);
        }

        log.debug("[Eurynome] |- BaseReadableService findByPage.");
        return pages;
    }

    @Override
    public Page<E> findByPage(int pageNumber, int pageSize, Sort.Direction direction, String... properties) {
        Page<E> pages = readPageFromCache(pageNumber, pageSize);
        if (CollectionUtils.isEmpty(pages.getContent())) {
            pages = ReadableService.super.findByPage(pageNumber, pageSize, direction, properties);
            writeToCache(pages);
        }

        log.debug("[Eurynome] |- BaseReadableService findByPage.");
        return pages;
    }

    @Override
    public List<E> findAll() {
        List<E> domains = readFromCache();
        if (CollectionUtils.isEmpty(domains)) {
            domains = ReadableService.super.findAll();
            writeToCache(domains);
        }
        log.debug("[Eurynome] |- BaseReadableService findAll.");
        return domains;
    }
}

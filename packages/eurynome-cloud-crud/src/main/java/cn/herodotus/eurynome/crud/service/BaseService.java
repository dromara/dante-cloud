/*
 * Copyright (c) 2019-2021 Gengwei Zheng(herodotus@aliyun.com)
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
 * File Name: BaseService.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.crud.service;

import cn.herodotus.eurynome.common.definition.entity.AbstractEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

/**
 * <p> Description : BaseService </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/29 18:22
 */
@Slf4j
public abstract class BaseService<E extends AbstractEntity, ID extends Serializable> extends AbstractCacheService<E, ID> {

    @Override
    public E findById(ID id) {
        E domain = readOneFromCache(String.valueOf(id));
        if (ObjectUtils.isEmpty(domain)) {
            domain = super.findById(id);
            writeToCache(domain);
        }

        log.debug("[Eurynome] |- AbstractCrudService Service findById.");
        return domain;
    }

    @Override
    public void deleteById(ID id) {
        super.deleteById(id);
        deleteFromCache(String.valueOf(id));
        log.debug("[Eurynome] |- AbstractCrudService Service delete.");
    }

    @Override
    public E saveOrUpdate(E domain) {
        E savedDomain = super.saveAndFlush(domain);
        writeToCache(savedDomain);
        log.debug("[Eurynome] |- AbstractCrudService Service saveOrUpdate.");
        return savedDomain;
    }

    @Override
    public Page<E> findByPage(int pageNumber, int pageSize) {
        Page<E> pages = readPageFromCache(pageNumber, pageSize);
        if (CollectionUtils.isEmpty(pages.getContent())) {
            pages = super.findByPage(pageNumber, pageSize);
            writeToCache(pages);
        }

        log.debug("[Eurynome] |- AbstractCrudService Service findByPage.");
        return pages;
    }

    @Override
    public Page<E> findByPage(int pageNumber, int pageSize, Sort.Direction direction, String... properties) {
        Page<E> pages = readPageFromCache(pageNumber, pageSize);
        if (CollectionUtils.isEmpty(pages.getContent())) {
            pages = super.findByPage(pageNumber, pageSize, direction, properties);
            writeToCache(pages);
        }

        log.debug("[Eurynome] |- AbstractCrudService Service findByPage.");
        return pages;
    }

    @Override
    public List<E> findAll() {
        List<E> domains = readFromCache();
        if (CollectionUtils.isEmpty(domains)) {
            domains = super.findAll();
            writeToCache(domains);
        }
        log.debug("[Eurynome] |- AbstractCrudService Service findAll.");
        return domains;
    }
}

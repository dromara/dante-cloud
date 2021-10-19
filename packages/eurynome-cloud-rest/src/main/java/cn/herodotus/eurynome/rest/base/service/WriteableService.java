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
 * File Name: WriteableService.java
 * Author: gengwei.zheng
 * Date: 2021/09/25 10:32:25
 */

package cn.herodotus.eurynome.rest.base.service;

import cn.herodotus.eurynome.assistant.definition.entity.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Description: 可读、可写的Service基础接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/7 16:47
 */
public interface WriteableService<E extends Entity, ID extends Serializable> extends ReadableService<E, ID> {
    /**
     * 删除数据
     *
     * @param entity 数据对应实体
     */
    default void delete(E entity) {
        getRepository().delete(entity);
    }

    /**
     * 批量全部删除
     */
    default void deleteAllInBatch() {
        getRepository().deleteAllInBatch();
    }

    /**
     * 删除指定多个数据
     *
     * @param entities 数据对应实体集合
     */
    default void deleteAll(Iterable<E> entities) {
        getRepository().deleteAll(entities);
    }

    /**
     * 删除全部数据
     */
    default void deleteAll() {
        getRepository().deleteAll();
    }

    /**
     * 根据ID删除数据
     *
     * @param id 数据对应ID
     */
    default void deleteById(ID id) {
        getRepository().deleteById(id);
    }

    /**
     * 保存数据
     *
     * @param domain 数据对应实体
     * @return 已保存数据
     */
    default E save(E domain) {
        return getRepository().save(domain);
    }

    /**
     * 批量保存
     *
     * @param entities 实体集合
     * @return 已经保存的实体集合
     */
    default <S extends E> List<S> saveAll(Iterable<S> entities) {
        return getRepository().saveAll(entities);
    }

    /**
     * 保存并且刷新
     *
     * @param entity 实体
     * @return 保存后实体
     */
    default E saveAndFlush(E entity) {
        return getRepository().saveAndFlush(entity);
    }

    /**
     * 保存或者更新
     *
     * @param entity 实体
     * @return 保存后实体
     */
    default E saveOrUpdate(E entity) {
        return saveAndFlush(entity);
    }

    /**
     * 刷新实体状态
     */
    default void flush() {
        getRepository().flush();
    }
}

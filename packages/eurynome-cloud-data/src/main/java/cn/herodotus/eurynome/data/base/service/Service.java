/*
 * Copyright 2019-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * Project Name: luban-cloud
 * Module Name: luban-cloud-component-data
 * File Name: BaseService.java
 * Author: gengwei.zheng
 * Date: 2019/11/24 下午3:09
 * LastModified: 2019/11/7 下午2:28
 */

package cn.herodotus.eurynome.data.base.service;

import cn.herodotus.eurynome.data.base.entity.Entity;

import java.io.Serializable;

public interface Service<E extends Entity, ID extends Serializable> extends ReadableService<E, ID> {

    default void delete(E entity) {
        getRepository().delete(entity);
    }

    default void deleteAllInBatch() {
        getRepository().deleteAllInBatch();
    }

    default void deleteAll(Iterable<E> entities) {
        getRepository().deleteAll(entities);
    }

    default void deleteAll() {
        getRepository().deleteAll();
    }

    default void deleteInBatch(Iterable<E> entities) {
        getRepository().deleteInBatch(entities);
    }

    default void deleteById(ID id) {
        getRepository().deleteById(id);
    }

    default E save(E domain) {
        return getRepository().save(domain);
    }

    default Iterable<E> saveAll(Iterable<E> entities) {
        return getRepository().saveAll(entities);
    }

    default E saveAndFlush(E entity) {
        return getRepository().saveAndFlush(entity);
    }

    default E saveOrUpdate(E entity) {
        return saveAndFlush(entity);
    }

    default void flush() {
        getRepository().flush();
    }
}

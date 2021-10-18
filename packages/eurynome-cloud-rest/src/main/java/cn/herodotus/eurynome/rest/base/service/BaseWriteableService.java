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
 * File Name: BaseWriteableService.java
 * Author: gengwei.zheng
 * Date: 2021/08/26 20:40:26
 */

package cn.herodotus.eurynome.rest.base.service;

import cn.herodotus.eurynome.assistant.definition.entity.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * <p> Description : BaseService </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/29 18:22
 */
public abstract class BaseWriteableService<E extends AbstractEntity, ID extends Serializable> extends BaseReadableService<E, ID> implements WriteableService<E, ID> {

    private final static Logger log = LoggerFactory.getLogger(BaseWriteableService.class);

    @Override
    public void deleteById(ID id) {
        WriteableService.super.deleteById(id);
        deleteFromCache(String.valueOf(id));
        log.debug("[Herodotus] |- AbstractCrudService Service delete.");
    }

    @Override
    public E saveOrUpdate(E domain) {
        E savedDomain = WriteableService.super.saveAndFlush(domain);
        writeToCache(savedDomain);
        log.debug("[Herodotus] |- AbstractCrudService Service saveOrUpdate.");
        return savedDomain;
    }
}

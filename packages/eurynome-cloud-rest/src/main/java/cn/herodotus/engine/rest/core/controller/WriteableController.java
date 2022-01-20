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
 * File Name: WriteableController.java
 * Author: gengwei.zheng
 * Date: 2021/09/25 10:33:25
 */

package cn.herodotus.engine.rest.core.controller;

import cn.herodotus.engine.assistant.core.domain.entity.AbstractEntity;
import cn.herodotus.engine.data.core.service.WriteableService;
import cn.herodotus.engine.assistant.core.domain.Result;

import java.io.Serializable;

/**
 * <p>Description: 可写Controller </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/7 17:29
 */
public interface WriteableController<E extends AbstractEntity, ID extends Serializable> extends ReadableController<E, ID> {

    /**
     * 获取Service
     *
     * @return Service
     */
    WriteableService<E, ID> getWriteableService();

    /**
     * 保存或更新实体
     *
     * @param domain 实体参数
     * @return 用Result包装的实体
     */

    default Result<E> saveOrUpdate(E domain) {
        E savedDomain = getWriteableService().saveOrUpdate(domain);
        return result(savedDomain);
    }

    /**
     * 删除数据
     *
     * @param id 实体ID
     * @return 用Result包装的信息
     */
    default Result<String> delete(ID id) {
        Result<String> result = result(String.valueOf(id));
        getWriteableService().deleteById(id);
        return result;
    }
}

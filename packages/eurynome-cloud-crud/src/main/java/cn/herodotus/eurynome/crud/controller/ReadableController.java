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
 * File Name: ReadableController.java
 * Author: gengwei.zheng
 * Date: 2021/07/07 17:27:07
 */

package cn.herodotus.eurynome.crud.controller;

import cn.herodotus.eurynome.common.definition.entity.AbstractEntity;
import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.crud.service.BaseReadableService;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 只读Controller </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/7 17:27
 */
public interface ReadableController<E extends AbstractEntity, ID extends Serializable> extends Controller {

    /**
     * 获取Service
     *
     * @return Service
     */
    BaseReadableService<E, ID> getBaseReadableService();

    default Result<Map<String, Object>> findByPage(Integer pageNumber, Integer pageSize) {
        Page<E> pages = getBaseReadableService().findByPage(pageNumber, pageSize);
        return result(pages);
    }

    default Result<List<E>> findAll() {
        List<E> domains = getBaseReadableService().findAll();
        return result(domains);
    }

    default Result<E> findById(ID id) {
        E domain = getBaseReadableService().findById(id);
        return result(domain);
    }
}

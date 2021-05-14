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
 * File Name: BaseController.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.crud.controller;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.common.definition.entity.AbstractEntity;
import cn.herodotus.eurynome.crud.service.BaseService;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p> Description : BaseController 单独提取出一些公共方法，是为了解决某些支持feign的controller，requestMapping 不方便统一编写的问题。 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/30 11:00
 */
public abstract class BaseController<E extends AbstractEntity, ID extends Serializable> extends AbstractController {

    /**
     * 获取Service
     * @return Service
     */
    public abstract BaseService<E, ID> getBaseService();

    public Result<Map<String, Object>> findByPage(Integer pageNumber, Integer pageSize) {
        Page<E> pages = getBaseService().findByPage(pageNumber, pageSize);
        return result(pages);
    }

    public Result<E> saveOrUpdate(E domain) {
        E savedDomain = getBaseService().saveOrUpdate(domain);
        return result(savedDomain);
    }

    public Result<String> delete(ID id) {
        Result<String> result = result(String.valueOf(id));
        getBaseService().deleteById(id);
        return result;
    }

    public Result<List<E>> findAll() {
        List<E> domains = getBaseService().findAll();
        return result(domains);
    }
}

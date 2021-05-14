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
 * File Name: AbstractController.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.crud.controller;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.common.definition.entity.AbstractEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> Description : BaseController </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/29 18:56
 */
public abstract class AbstractController {

    protected <E extends AbstractEntity> Result<E> result(E domain) {
        Result<E> result = new Result<>();
        if (ObjectUtils.isNotEmpty(domain)) {
            return result.ok().message("操作成功！").data(domain);
        } else {
            return result.failed().message("操作失败！");
        }
    }

    protected <E extends AbstractEntity> Result<List<E>> result(List<E> domains) {
        Result<List<E>> result = new Result<>();
        if (CollectionUtils.isNotEmpty(domains)) {
            return result.ok().message("查询数据成功！").data(domains);
        } else {
            return result.failed().message("查询数据失败！");
        }
    }

    protected <E extends AbstractEntity> Result<Map<String, Object>> result(Page<E> pages) {
        Result<Map<String, Object>> result = new Result<>();
        if (ObjectUtils.isNotEmpty(pages)) {
            return result.ok().message("查询数据成功！").data(getPageInfoMap(pages));
        } else {
            return result.failed().message("查询失败！");
        }
    }

    protected Result<Map<String, Object>> result(Map<String, Object> map) {
        Result<Map<String, Object>> result = new Result<>();
        if (MapUtils.isNotEmpty(map)) {
            return result.ok().message("查询数据成功！").data(map);
        } else {
            return result.failed().message("查询失败！");
        }
    }

    protected <ID extends Serializable> Result<String> result(ID parameter) {
        Result<String> result = new Result<>();
        if (ObjectUtils.isNotEmpty(parameter)) {
            return result.ok().message("操作成功！");
        } else {
            return result.failed().message("操作失败！");
        }
    }

    protected <E extends AbstractEntity> Map<String, Object> getPageInfoMap(Page<E> pages) {
        return getPageInfoMap(pages.getContent(), pages.getTotalPages(), pages.getTotalElements());
    }

    protected <E extends AbstractEntity> Map<String, Object> getPageInfoMap(List<E> content, int totalPages, long totalElements) {
        Map<String, Object> result = new HashMap<>(8);
        result.put("content", content);
        result.put("totalPages", totalPages);
        result.put("totalElements", totalElements);
        return result;
    }
}

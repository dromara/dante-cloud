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
 * File Name: Controller.java
 * Author: gengwei.zheng
 * Date: 2021/09/25 10:31:25
 */

package cn.herodotus.eurynome.rest.base.controller;

import cn.herodotus.eurynome.assistant.definition.entity.AbstractEntity;
import cn.herodotus.eurynome.assistant.domain.Result;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: Controller基础定义 </p>
 * <p>
 * 这里只在方法上做了泛型，主要是考虑到返回的结果数据可以是各种类型，而不一定受限于某一种类型。
 *
 * @author : gengwei.zheng
 * @date : 2021/7/7 17:24
 */
public interface Controller {

    /**
     * 数据实体转换为统一响应实体
     *
     * @param domain 数据实体
     * @param <E>    {@link AbstractEntity} 子类型
     * @return {@link Result} Entity
     */
    default <E extends AbstractEntity> Result<E> result(E domain) {
        if (ObjectUtils.isNotEmpty(domain)) {
            return Result.content(domain);
        } else {
            return Result.empty();
        }
    }

    /**
     * 数据列表转换为统一响应实体
     *
     * @param domains 数据实体 List
     * @param <E>     {@link AbstractEntity} 子类型
     * @return {@link Result} List
     */
    default <E extends AbstractEntity> Result<List<E>> result(List<E> domains) {
        if (ObjectUtils.isNotEmpty(domains)) {
            if (CollectionUtils.isNotEmpty(domains)) {
                return Result.success("查询数据成功！", domains);
            } else {
                return Result.empty("未查询到数据！");
            }
        } else {
            return Result.failure("查询数据失败！");
        }
    }

    /**
     * 数据分页对象转换为统一响应实体
     *
     * @param pages 分页查询结果 {@link Page}
     * @param <E>   {@link AbstractEntity} 子类型
     * @return {@link Result} Map
     */
    default <E extends AbstractEntity> Result<Map<String, Object>> result(Page<E> pages) {
        if (ObjectUtils.isNotEmpty(pages)) {
            if (CollectionUtils.isNotEmpty(pages.getContent())) {
                return Result.success("查询数据成功！", getPageInfoMap(pages));
            } else {
                return Result.empty("未查询到数据！");
            }
        } else {
            return Result.failure("查询数据失败！");
        }
    }

    /**
     * 数据 Map 转换为统一响应实体
     *
     * @param map 数据 Map
     * @return {@link Result} Map
     */
    default Result<Map<String, Object>> result(Map<String, Object> map) {
        if (ObjectUtils.isNotEmpty(map)) {
            if (MapUtils.isNotEmpty(map)) {
                return Result.success("查询数据成功！", map);
            } else {
                return Result.empty("未查询到数据！");
            }
        } else {
            return Result.failure("数据数据失败！");
        }
    }

    /**
     * 数据操作结果转换为统一响应实体
     *
     * @param parameter 数据ID
     * @param <ID>      ID 数据类型
     * @return {@link Result} String
     */
    default <ID extends Serializable> Result<String> result(ID parameter) {
        if (ObjectUtils.isNotEmpty(parameter)) {
            return Result.success();
        } else {
            return Result.failure();
        }
    }

    /**
     * 数据操作结果转换为统一响应实体
     *
     * @param status 操作状态
     * @return {@link Result} String
     */
    default Result<String> result(boolean status) {
        if (status) {
            return Result.success();
        } else {
            return Result.failure();
        }
    }

    /**
     * Page 对象转换为 Map
     *
     * @param pages 分页查询结果 {@link Page}
     * @param <E>   {@link AbstractEntity} 子类型
     * @return Map
     */
    default <E extends AbstractEntity> Map<String, Object> getPageInfoMap(Page<E> pages) {
        return getPageInfoMap(pages.getContent(), pages.getTotalPages(), pages.getTotalElements());
    }

    /**
     * Page 对象转换为 Map
     *
     * @param content       数据实体 List
     * @param totalPages    分页总页数
     * @param totalElements 总的数据条目
     * @param <E>           {@link AbstractEntity} 子类型
     * @return Map
     */
    default <E extends AbstractEntity> Map<String, Object> getPageInfoMap(List<E> content, int totalPages, long totalElements) {
        Map<String, Object> result = new HashMap<>(8);
        result.put("content", content);
        result.put("totalPages", totalPages);
        result.put("totalElements", totalElements);
        return result;
    }
}

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
 * File Name: BaseReadableRestController.java
 * Author: gengwei.zheng
 * Date: 2021/08/24 12:23:24
 */

package cn.herodotus.eurynome.rest.base.controller;

import cn.herodotus.eurynome.assistant.annotation.rest.AccessLimited;
import cn.herodotus.eurynome.common.definition.entity.AbstractEntity;
import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.rest.base.business.Pager;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>Description: 只读RestController </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/7 17:38
 */
public abstract class BaseReadableRestController<E extends AbstractEntity, ID extends Serializable> implements ReadableController<E, ID> {

    @AccessLimited
    @ApiOperation(value = "分页查询数据", notes = "通过pageNumber和pageSize获取分页数据", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pager", required = true, value = "分页Bo对象", dataType = "Pager", dataTypeClass = Pager.class, paramType = "query")
    })
    @GetMapping
    public Result<Map<String, Object>> findByPage(Pager pager) {
        return ReadableController.super.findByPage(pager.getPageNumber(), pager.getPageSize());
    }
}

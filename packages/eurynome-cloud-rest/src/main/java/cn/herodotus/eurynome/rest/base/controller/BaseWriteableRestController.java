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
 * File Name: BaseWriteableRestController.java
 * Author: gengwei.zheng
 * Date: 2021/08/24 12:23:24
 */

package cn.herodotus.eurynome.rest.base.controller;

import cn.herodotus.eurynome.assistant.annotation.rest.Idempotent;
import cn.herodotus.eurynome.common.definition.entity.AbstractEntity;
import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.rest.base.service.ReadableService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

/**
 * <p> Description : BaseRestController </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/29 15:28
 */
public abstract class BaseWriteableRestController<E extends AbstractEntity, ID extends Serializable> extends BaseReadableRestController<E, ID> implements WriteableController<E, ID> {

    @Override
    public ReadableService<E, ID> getReadableService() {
        return this.getWriteableService();
    }

    @Idempotent
    @ApiOperation(value = "保存或更新数据", notes = "接收JSON数据，转换为实体，进行保存或更新", produces = "application/json", consumes = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "domain", required = true, value = "可转换为实体的json数据", dataType = "AbstractEntity", dataTypeClass = AbstractEntity.class, paramType = "body")
    })
    @PostMapping
    @Override
    public Result<E> saveOrUpdate(@RequestBody E domain) {
        return WriteableController.super.saveOrUpdate(domain);
    }

    @Idempotent
    @ApiOperation(value = "删除数据", notes = "根据实体ID删除数据，以及相关联的关联数据", consumes = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "实体ID，@Id注解对应的实体属性", dataType = "Serializable", dataTypeClass = Serializable.class, paramType = "body")
    })
    @DeleteMapping
    @Override
    public Result<String> delete(@RequestBody ID id) {
        return WriteableController.super.delete(id);
    }
}

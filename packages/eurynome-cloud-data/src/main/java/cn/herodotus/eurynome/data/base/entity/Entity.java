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
 * File Name: BaseEntity.java
 * Author: gengwei.zheng
 * Date: 2019/11/8 下午5:51
 * LastModified: 2019/11/8 下午4:13
 */

package cn.herodotus.eurynome.data.base.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>Description: BaseEntity </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/29 17:27
 */
public interface Entity extends Serializable {

    /**
     * 根据该值作为缓存对象的Key
     * @return CacheKey
     */
    @ApiModelProperty(hidden = true)
    public String getId();
}

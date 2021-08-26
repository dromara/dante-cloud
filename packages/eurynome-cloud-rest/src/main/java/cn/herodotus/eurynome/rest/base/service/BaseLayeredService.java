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
 * File Name: BaseLayeredService.java
 * Author: gengwei.zheng
 * Date: 2021/08/24 12:23:24
 */

package cn.herodotus.eurynome.rest.base.service;

import cn.herodotus.eurynome.common.definition.entity.AbstractEntity;

import java.io.Serializable;

/**
 * <p>Description: 基于自研Hibernate多层二级缓存的基础服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/14 17:53
 */
public abstract class BaseLayeredService < E extends AbstractEntity, ID extends Serializable> implements WriteableService<E, ID>{
}

/*
 * Copyright (c) 2019-2020 the original author or authors.
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
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-rest
 * File Name: RequestMappingPersistence.java
 * Author: gengwei.zheng
 * Date: 2020/5/29 下午8:24
 * LastModified: 2020/5/29 下午8:24
 */

package cn.herodotus.eurynome.rest.definition;

import cn.herodotus.eurynome.common.definition.entity.AbstractEntity;

import java.util.List;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: RequestMappingPersistence </p>
 *
 * <p>Description: RequestMappingPersistence </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/29 20:24
 */
public interface RequestMappingPersistence<E extends AbstractEntity> {

    /**
     * RequestMapping 存储
     * @param collection RequestMapping数据集合
     */
    void store(List<E> collection);
}

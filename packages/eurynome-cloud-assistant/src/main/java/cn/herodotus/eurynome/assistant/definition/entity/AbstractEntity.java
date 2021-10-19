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
 * Module Name: eurynome-cloud-assistant
 * File Name: AbstractEntity.java
 * Author: gengwei.zheng
 * Date: 2021/10/17 22:53:17
 */

package cn.herodotus.eurynome.assistant.definition.entity;


/**
 * <p> Description : BaseCacheEntity </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/24 16:38
 */
public abstract class AbstractEntity implements Entity {

    /**
     * 数据表中除主键以外的唯一数据属性
     * <p>
     * 有些实体，只使用一个属性作为Cache Key {@link #getId()} ()} 不能满足需求。
     * 例如：SysUser，使用UserId和UserName查询都比较频繁，用UserId作为缓存的Key，那么用UserName就无法从缓存读取。
     * 除非用UserId作为缓存Key，缓存一遍实体，用UserName再作为缓存Key，缓存一遍实体。这样增加缓存管理复杂度。
     * <p>
     * 通过getLinkedProperty()，维护一个Map。
     * 通过这个Map，实现指定属性与CacheKey的映射。
     *
     * @return LinkedProperty
     */
    public abstract String getLinkedProperty();
}

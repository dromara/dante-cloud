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
 * Module Name: eurynome-cloud-cache
 * File Name: AbstractCacheEntity.java
 * Author: gengwei.zheng
 * Date: 2021/12/17 21:24:17
 */

package cn.herodotus.eurynome.cache.definition;

import cn.herodotus.eurynome.assistant.definition.entity.Entity;

/**
 * <p>Description: 自定义缓存基础实体 </p>
 * <p>
 * 把早期自定义多级缓存使用到的基础类提取出来，一方面减少对其它代码的影响，另一方面做代码保存备用
 *
 * @author : gengwei.zheng
 * @date : 2021/12/17 21:24
 */
public abstract class AbstractCacheEntity implements Entity {

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

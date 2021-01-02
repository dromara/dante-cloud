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
 * Module Name: eurynome-cloud-data
 * File Name: CacheTemplate.java
 * Author: gengwei.zheng
 * Date: 2020/6/3 下午4:09
 * LastModified: 2020/6/3 下午4:09
 */

package cn.herodotus.eurynome.data.cache;

import cn.herodotus.eurynome.common.definition.entity.AbstractEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: CacheTemplate </p>
 *
 * <p>Description: 基于JetCache的，通用查询模型 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/6/3 16:09
 */
public class CacheTemplate <D extends AbstractEntity> implements Serializable {

    private final Set<String> queryIndexes = new LinkedHashSet<>();
    private final Map<String, D> domains = new LinkedHashMap<>();

    private final Set<String> deleteIndexes = new LinkedHashSet<>();
    private final Map<String, Set<String>> propertyLinks = new LinkedHashMap<>();

    private boolean propertyLink = false;

    public boolean hasPropertyLink() {
        return propertyLink;
    }

    public Set<String> getQueryIndexes() {
        return queryIndexes;
    }

    public Map<String, D> getDomains() {
        return domains;
    }

    public Set<String> getDeleteIndexes() {
        return deleteIndexes;
    }

    public Map<String, Set<String>> getPropertyLinks() {
        return propertyLinks;
    }

    public void append(D object) {
        queryIndexes.add(object.getId());
        domains.put(object.getId(), object);

        if (StringUtils.isNotBlank(object.getLinkedProperty())) {
            propertyLink = true;
            deleteIndexes.add(object.getLinkedProperty());
            propertyLinks.put(object.getLinkedProperty(), convertToIndexCacheValue(object.getId()));
        }
    }

    public void append(Collection<D> objects) {
        objects.forEach(this::append);
    }

    /**
     * 将单独的值转换为Set工具方法，方便在Index Cache中存储
     *
     * @param value 需要在Index Cache中存储的值
     * @return Index Cache 值
     */
    private Set<String> convertToIndexCacheValue(String value) {
        Assert.notNull(value, "Value must not be null");
        Set<String> values = new LinkedHashSet<>();
        values.add(value);
        return values;
    }
}

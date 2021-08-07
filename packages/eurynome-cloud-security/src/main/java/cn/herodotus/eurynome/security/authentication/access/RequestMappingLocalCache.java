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
 * Module Name: eurynome-cloud-security
 * File Name: RequestMappingLocalCache.java
 * Author: gengwei.zheng
 * Date: 2021/07/28 18:15:28
 */

package cn.herodotus.eurynome.security.authentication.access;

import cn.herodotus.eurynome.data.cache.query.CacheTemplate;
import cn.herodotus.eurynome.security.definition.domain.RequestMapping;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>Description: RequestMapping 服务本地缓存 </p>
 * <p>
 * 因为是使用Request Mapping值作为权限，所以将扫描后的Request Mapping值缓存至本地，便于使用。
 *
 * @author : gengwei.zheng
 * @date : 2021/7/28 18:15
 */
@Component
public class RequestMappingLocalCache {
    private static final Logger log = LoggerFactory.getLogger(RequestMappingLocalCache.class);

    private static final String ALL = "all";

    private final Cache<String, RequestMapping> dataCache = Caffeine.newBuilder().maximumSize(10_000).build();

    private final Cache<String, Set<String>> indexCache = Caffeine.newBuilder().maximumSize(10_000).build();

    public void save(List<RequestMapping> requestMappings) {
        if (CollectionUtils.isNotEmpty(requestMappings)) {
            CacheTemplate<RequestMapping> cacheTemplate = new CacheTemplate<>();
            cacheTemplate.append(requestMappings);
            dataCache.putAll(cacheTemplate.getDomains());
            indexCache.put(ALL, cacheTemplate.getQueryIndexes());
            log.debug("[Eurynome] |- Local Storage batch save the request mappings");
        }
    }

    public List<RequestMapping> findAll() {
        Set<String> indexes = indexCache.getIfPresent(ALL);
        if (CollectionUtils.isNotEmpty(indexes)) {
            Map<String, RequestMapping> result = dataCache.getAllPresent(indexes);
            if (MapUtils.isNotEmpty(result)) {
                log.debug("[Eurynome] |- Get the request mappings from local storage SUCCESS!");
                return new ArrayList<>(result.values());
            }
        }

        log.warn("[Eurynome] |- Cannot Get the request mappings from local storage, or the result is empty!");
        return new ArrayList<>();
    }
}

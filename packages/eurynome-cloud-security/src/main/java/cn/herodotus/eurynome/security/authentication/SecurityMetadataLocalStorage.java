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
 * Module Name: eurynome-cloud-security
 * File Name: SecurityMetadataLocalStorage.java
 * Author: gengwei.zheng
 * Date: 2020/6/3 下午2:58
 * LastModified: 2020/6/3 下午2:33
 */

package cn.herodotus.eurynome.security.authentication;

import cn.herodotus.eurynome.data.cache.CacheTemplate;
import cn.herodotus.eurynome.security.metadata.RequestMapping;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: SecurityMetadataLocalStorage </p>
 *
 * <p>Description: 本地存储，用于存储Security Metadata Source </p>
 *
 * 这里没有用自己编写的{@link cn.herodotus.eurynome.data.cache.AbstractCache}，主要是因为在应用启动的过程中，JetCache启动时机与Oauth不同。会产生注入的循环问题。
 *
 * @author : gengwei.zheng
 * @date : 2020/6/3 9:57
 */
@Slf4j
@Component
public class SecurityMetadataLocalStorage {

    private static final String ALL = "all";

    private final Cache<String, RequestMapping> dataCache = Caffeine.newBuilder().maximumSize(10_000).build();

    private final Cache<String, Set<String>> indexCache = Caffeine.newBuilder().maximumSize(10_000).build();
    ;

    public void save(List<RequestMapping> requestMappings) {
        if (CollectionUtils.isNotEmpty(requestMappings)) {
            CacheTemplate<RequestMapping> cacheTemplate = new CacheTemplate<>();
            cacheTemplate.append(requestMappings);
            dataCache.putAll(cacheTemplate.getDomains());
            indexCache.put(ALL, cacheTemplate.getQueryIndexes());
            log.debug("[Herodotus] |- Local Storage batch save the request mappings");
        }
    }

    public List<RequestMapping> findAll() {
        Set<String> indexes = indexCache.getIfPresent(ALL);
        if (CollectionUtils.isNotEmpty(indexes)) {
            Map<String, RequestMapping> result = dataCache.getAllPresent(indexes);
            if (MapUtils.isNotEmpty(result)) {
                log.debug("[Herodotus] |- Get the request mappings from local storage SUCCESS!");
                return new ArrayList<>(result.values());
            }
        }

        log.warn("[Herodotus] |- Cannot Get the request mappings from local storage, or the result is empty!");
        return new ArrayList<>();
    }
}

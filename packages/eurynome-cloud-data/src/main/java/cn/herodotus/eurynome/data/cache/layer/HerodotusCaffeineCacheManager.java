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
 * Module Name: eurynome-cloud-data
 * File Name: HerodotusCaffeineCacheManager.java
 * Author: gengwei.zheng
 * Date: 2021/10/28 10:07:28
 */

package cn.herodotus.eurynome.data.cache.layer;

import cn.herodotus.eurynome.assistant.constant.SymbolConstants;
import cn.herodotus.eurynome.data.properties.CacheProperties;
import cn.herodotus.eurynome.data.properties.Expire;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import java.util.Map;

/**
 * <p>Description: 扩展的 CaffeineCacheManager </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/28 10:07
 */
public class HerodotusCaffeineCacheManager extends CaffeineCacheManager {

    private static final Logger log = LoggerFactory.getLogger(HerodotusCaffeineCacheManager.class);

    private final CacheProperties cacheProperties;

    public HerodotusCaffeineCacheManager(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
        this.setAllowNullValues(cacheProperties.getAllowNullValues());
    }

    public HerodotusCaffeineCacheManager(CacheProperties cacheProperties, String... cacheNames) {
        super(cacheNames);
        this.cacheProperties = cacheProperties;
        this.setAllowNullValues(cacheProperties.getAllowNullValues());
    }

    @Override
    protected Cache<Object, Object> createNativeCaffeineCache(String name) {
        Map<String, Expire> expires = cacheProperties.getExpires();
        if (MapUtils.isNotEmpty(expires)) {
            String key = StringUtils.replace(name, SymbolConstants.COLON, cacheProperties.getSeparator());
            if (expires.containsKey(key)) {
                Expire expire = expires.get(key);
                log.debug("[Herodotus] |- CACHE - Caffeine cache [{}] is setted to use CUSTEM exprie.", name);
                return Caffeine.newBuilder().expireAfterWrite(expire.getDuration(), expire.getUnit()).build();
            }
        }

        return super.createNativeCaffeineCache(name);
    }
}

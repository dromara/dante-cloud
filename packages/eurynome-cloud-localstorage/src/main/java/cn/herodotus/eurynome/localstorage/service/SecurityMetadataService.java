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
 * Module Name: eurynome-cloud-localstorage
 * File Name: SecurityMetadataService.java
 * Author: gengwei.zheng
 * Date: 2020/5/28 下午9:26
 * LastModified: 2020/5/28 下午5:01
 */

package cn.herodotus.eurynome.localstorage.service;

import cn.herodotus.eurynome.data.base.repository.BaseRepository;
import cn.herodotus.eurynome.data.base.service.BaseService;
import cn.herodotus.eurynome.data.datasource.annotation.DataSource;
import cn.herodotus.eurynome.localstorage.constants.LocalStorageConstants;
import cn.herodotus.eurynome.localstorage.entity.SecurityMetadata;
import cn.herodotus.eurynome.localstorage.repository.SecurityMetadataRepository;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: SecurityMetadataService </p>
 *
 * <p>Description: SecurityMetadataService </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/28 12:52
 */
@Slf4j
@Service
@DataSource
public class SecurityMetadataService extends BaseService<SecurityMetadata, String> {

    private static final String CACHE_NAME = LocalStorageConstants.CACHE_NAME_SECURITY_METADATA;

    @Autowired
    private SecurityMetadataRepository securityMetadataRepository;

    @CreateCache(name = CACHE_NAME, expire = LocalStorageConstants.DEFAULT_UPMS_CACHE_EXPIRE, cacheType = CacheType.BOTH, localLimit = LocalStorageConstants.DEFAULT_UPMS_LOCAL_LIMIT)
    private Cache<String, SecurityMetadata> dataCache;

    @CreateCache(name = CACHE_NAME + LocalStorageConstants.INDEX_CACHE_NAME, expire = LocalStorageConstants.DEFAULT_UPMS_CACHE_EXPIRE, cacheType = CacheType.BOTH, localLimit = LocalStorageConstants.DEFAULT_UPMS_LOCAL_LIMIT)
    private Cache<String, Set<String>> indexCache;

    @Override
    public Cache<String, SecurityMetadata> getCache() {
        return dataCache;
    }

    @Override
    public Cache<String, Set<String>> getIndexCache() {
        return indexCache;
    }

    @Override
    public BaseRepository<SecurityMetadata, String> getRepository() {
        return securityMetadataRepository;
    }
}

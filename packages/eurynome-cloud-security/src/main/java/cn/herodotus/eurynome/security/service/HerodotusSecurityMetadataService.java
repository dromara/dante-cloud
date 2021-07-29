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
 * File Name: SecurityMetadataStorageService.java
 * Author: gengwei.zheng
 * Date: 2021/07/28 18:22:28
 */

package cn.herodotus.eurynome.security.service;

import cn.herodotus.eurynome.security.authentication.access.RequestMappingLocalCache;
import cn.herodotus.eurynome.security.definition.domain.RequestMapping;
import cn.herodotus.eurynome.security.definition.service.StrategySecurityMetadataService;
import com.google.common.collect.ImmutableList;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.List;

/**
 * <p>Description: 权限元数据存储 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/28 18:22
 */
public class HerodotusSecurityMetadataService {

    private StrategySecurityMetadataService strategySecurityMetadataService;

    private RequestMappingLocalCache requestMappingLocalCache;

    public void setStrategySecurityMetadataService(StrategySecurityMetadataService strategySecurityMetadataService) {
        this.strategySecurityMetadataService = strategySecurityMetadataService;
    }

    public void setRequestMappingLocalCache(RequestMappingLocalCache requestMappingLocalCache) {
        this.requestMappingLocalCache = requestMappingLocalCache;
    }

    @EventListener
    public void store(ApplicationReadyEvent applicationReadyEvent) throws Exception {

        List<RequestMapping> requestMappings = requestMappingLocalCache.findAll();

        if (CollectionUtils.isNotEmpty(requestMappings)) {
            List<RequestMapping> securityMetadata = ImmutableList.copyOf(requestMappings);
            strategySecurityMetadataService.store(securityMetadata);
        }
    }
}

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

import cn.herodotus.eurynome.security.definition.domain.RequestMapping;
import cn.herodotus.eurynome.security.definition.service.StrategyRequestMappingGatherService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Description: 权限元数据存储 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/28 18:22
 */
@Service
public class RequestMappingGatherService {

    private static final Logger log = LoggerFactory.getLogger(RequestMappingGatherService.class);

    private StrategyRequestMappingGatherService strategyRequestMappingGatherService;

    public void setStrategySecurityMetadataService(StrategyRequestMappingGatherService strategyRequestMappingGatherService) {
        this.strategyRequestMappingGatherService = strategyRequestMappingGatherService;
    }

    @Async
    public void postProcess(List<RequestMapping> requestMappings) {
        if (CollectionUtils.isNotEmpty(requestMappings) && ObjectUtils.isNotEmpty(strategyRequestMappingGatherService)) {
            log.debug("[Eurynome] |- Request mapping gather async process begin!");
            strategyRequestMappingGatherService.store(requestMappings);
            log.debug("[Eurynome] |- Request mapping gather async process finished!");
        }
    }
}

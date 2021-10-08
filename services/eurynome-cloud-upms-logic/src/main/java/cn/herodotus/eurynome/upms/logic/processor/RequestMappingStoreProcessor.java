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
 * Module Name: eurynome-cloud-upms-logic
 * File Name: RequestMappingStoreProcessor.java
 * Author: gengwei.zheng
 * Date: 2021/08/11 20:49:11
 */

package cn.herodotus.eurynome.upms.logic.processor;

import cn.herodotus.eurynome.security.definition.domain.RequestMapping;
import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;
import cn.herodotus.eurynome.upms.api.helper.UpmsHelper;
import cn.herodotus.eurynome.upms.logic.service.system.SysAuthorityService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Description: RequestMapping存储服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/7 20:50
 */
@Service
public class RequestMappingStoreProcessor {

    private static final Logger log = LoggerFactory.getLogger(RequestMappingStoreProcessor.class);

    private final SysAuthorityService sysAuthorityService;

    @Autowired
    public RequestMappingStoreProcessor(SysAuthorityService sysAuthorityService) {
        this.sysAuthorityService = sysAuthorityService;
    }

    @Async
    public void postProcess(List<RequestMapping> requestMappings) {
        log.debug("[Herodotus] |- [Async] - Request Mapping Async Process Begin!");

        List<SysAuthority> sysAuthorities = UpmsHelper.convertRequestMappingsToSysAuthorities(requestMappings);

        List<SysAuthority> result = sysAuthorityService.batchSaveOrUpdate(sysAuthorities);
        if (CollectionUtils.isNotEmpty(result)) {
            log.info("[Herodotus] |- Store Service Resources Success!");
        } else {
            log.error("[Herodotus] |- Store Service Resources May Be Error, Please Check!");
        }

        log.debug("[Herodotus] |- [Async] - Request Mapping Async Process End!");
    }
}

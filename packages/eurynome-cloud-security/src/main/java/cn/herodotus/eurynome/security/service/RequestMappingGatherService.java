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

import cn.herodotus.engine.web.core.domain.RequestMapping;
import cn.herodotus.eurynome.assistant.constant.ServiceConstants;
import cn.herodotus.eurynome.message.support.DestinationSupport;
import cn.herodotus.eurynome.security.authentication.RequestMappingLocalCache;
import cn.herodotus.eurynome.security.event.LocalRequestMappingGatherEvent;
import cn.herodotus.eurynome.security.event.remote.RemoteRequestMappingGatherEvent;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
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

    private RequestMappingLocalCache requestMappingLocalCache;

    public void setRequestMappingLocalCache(RequestMappingLocalCache requestMappingLocalCache) {
        this.requestMappingLocalCache = requestMappingLocalCache;
    }

    /**
     * 发布远程事件，传送RequestMapping
     *
     * @param requestMappings    扫描到的RequestMapping
     * @param applicationContext {@link ApplicationContext}
     * @param serviceId          当前服务的service name。目前取的是：spring.application.name, applicationContext.getApplicationName取到的是空串
     * @param isDistributed      是否是分布式架构
     */
    public void postProcess(List<RequestMapping> requestMappings, ApplicationContext applicationContext, String serviceId, boolean isDistributed) {

        requestMappingLocalCache.save(requestMappings);

        if (!isDistributed || StringUtils.equals(serviceId, ServiceConstants.SERVICE_NAME_UPMS)) {
            log.debug("[Herodotus] |- (3) Request mapping gather service trigger LOCAL event!");
            applicationContext.publishEvent(new LocalRequestMappingGatherEvent(requestMappings));
        } else {
            String source = JSON.toJSONString(requestMappings);
            log.debug("[Herodotus] |- (3) Request mapping gather service trigger REMOTE event!");
            applicationContext.publishEvent(new RemoteRequestMappingGatherEvent(source, serviceId, DestinationSupport.create(ServiceConstants.SERVICE_NAME_UPMS)));
        }
    }
}

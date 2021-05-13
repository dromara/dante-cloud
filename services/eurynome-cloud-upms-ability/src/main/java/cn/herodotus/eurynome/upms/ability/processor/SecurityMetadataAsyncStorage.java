/*
 * Copyright (c) 2019-2021 Gengwei Zheng(herodotus@aliyun.com)
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
 * Module Name: eurynome-cloud-upms-ability
 * File Name: SecurityMetadataAsyncStorage.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:46:07
 */

package cn.herodotus.eurynome.upms.ability.processor;

import cn.herodotus.eurynome.security.definition.domain.RequestMapping;
import cn.herodotus.eurynome.upms.logic.service.system.SysAuthorityService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Description: 对包含RequestMapping信息的消息，进行存储 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/26 10:10
 */
@Slf4j
@Service
public class SecurityMetadataAsyncStorage extends AbstractSecurityMetadataStorage {

    private final SysAuthorityService sysAuthorityService;

    @Autowired
    public SecurityMetadataAsyncStorage(SysAuthorityService sysAuthorityService) {
        super(sysAuthorityService);
        this.sysAuthorityService = sysAuthorityService;
    }

    @Async
    public void store(String message) {
        log.debug("[Eurynome] |- Received Service Resources Message: [{}]", message);

        List<RequestMapping> requestMappings = JSON.parseArray(message, RequestMapping.class);
        if (CollectionUtils.isNotEmpty(requestMappings)) {
            store(requestMappings);
        }
    }
}

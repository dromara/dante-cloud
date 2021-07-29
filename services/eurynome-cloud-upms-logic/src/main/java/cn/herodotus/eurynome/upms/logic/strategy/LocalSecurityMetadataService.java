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
 * File Name: LocalSecurityMetadataStorageService.java
 * Author: gengwei.zheng
 * Date: 2021/07/28 19:01:28
 */

package cn.herodotus.eurynome.upms.logic.strategy;

import cn.herodotus.eurynome.security.definition.domain.RequestMapping;
import cn.herodotus.eurynome.security.definition.service.StrategySecurityMetadataService;
import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;
import cn.herodotus.eurynome.upms.api.helper.UpmsHelper;
import cn.herodotus.eurynome.upms.logic.service.system.SysAuthorityService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * <p>Description: LocalSecurity本地直联服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/28 19:01
 */
public class LocalSecurityMetadataService implements StrategySecurityMetadataService {
    private static final Logger log = LoggerFactory.getLogger(LocalSecurityMetadataService.class);

    private static final String DDL_AUTO_TYPE_NONE = "none";

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    @Autowired
    private SysAuthorityService sysAuthorityService;

    @Override
    public void store(List<RequestMapping> requestMappings) {
        log.debug("[Eurynome] |- spring.jpa.ddl-auto value is : {}", ddlAuto);

        if (StringUtils.isNotEmpty(ddlAuto) && !StringUtils.equalsIgnoreCase(ddlAuto, DDL_AUTO_TYPE_NONE)) {

            List<SysAuthority> sysAuthorities = UpmsHelper.convertRequestMappingsToSysAuthorities(requestMappings);

            List<SysAuthority> result = sysAuthorityService.batchSaveOrUpdate(sysAuthorities);
            if (CollectionUtils.isNotEmpty(result)) {
                log.info("[Eurynome] |- Store Service Resources Success!");
            } else {
                log.error("[Eurynome] |- Store Service Resources May Be Error, Please Check!");
            }
        }

        log.debug("[Eurynome] |- Skip Store Service Resources.");
    }
}

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
 * Module Name: eurynome-cloud-oauth-starter
 * File Name: DataSourceSecurityMetadata.java
 * Author: gengwei.zheng
 * Date: 2021/05/13 11:08:13
 */

package cn.herodotus.eurynome.oauth.autoconfigure.logic;

import cn.herodotus.eurynome.security.definition.domain.RequestMapping;
import cn.herodotus.eurynome.security.definition.service.SecurityMetadataStorage;
import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;
import cn.herodotus.eurynome.upms.api.helper.UpmsHelper;
import cn.herodotus.eurynome.upms.logic.service.system.SysAuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * <p>Project: eurynome-cloud-athena </p>
 * <p>File: DataSourceSecurityMetadata </p>
 *
 * <p>Description: 直连数据源的SecurityMetadata存储 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/12/30 14:54
 */
@Slf4j
public class DataSourceSecurityMetadata extends SecurityMetadataStorage {

    private static final String DDL_AUTO_TYPE_NONE = "none";

    private SysAuthorityService sysAuthorityService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    public DataSourceSecurityMetadata(SysAuthorityService sysAuthorityService) {
        this.sysAuthorityService = sysAuthorityService;
    }

    @Override
    public void save(List<RequestMapping> requestMappings) {

        log.debug("[Eurynome] |- spring.jpa.ddl-auto value is : {}", ddlAuto);

        if (StringUtils.isNotEmpty(ddlAuto) && !StringUtils.equalsIgnoreCase(ddlAuto, DDL_AUTO_TYPE_NONE)) {

            List<SysAuthority> sysAuthorities = UpmsHelper.convertRequestMappingsToSysAuthorities(requestMappings);

            List<SysAuthority> result = sysAuthorityService.batchSaveOrUpdate(sysAuthorities);
            if (CollectionUtils.isNotEmpty(result)) {
                log.info("[Eurynome] |- Store Service Resources Success!");
            } else {
                log.error("[Eurynome] |- Store Service Resources May Be Error, Please Check!");
            }

            log.info("[Eurynome] |- Store Service Resources Success!");
        }
    }

    @Override
    public List<RequestMapping> findAll() {
        List<SysAuthority> sysAuthorities = sysAuthorityService.findAll();
        return UpmsHelper.convertSysAuthoritiesToRequestMappings(sysAuthorities);
    }
}

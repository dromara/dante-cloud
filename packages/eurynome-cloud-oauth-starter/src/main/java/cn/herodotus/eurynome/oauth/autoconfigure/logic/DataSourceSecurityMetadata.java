/*
 * Copyright (c) 2020-2021 the original author or authors.
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
 * Project Name: eurynome-cloud-athena
 * Module Name: eurynome-cloud-athena-kernel
 * File Name: DataSourceSecurityMetadata.java
 * Author: gengwei.zheng
 * Date: 2021/1/7 下午5:36
 * LastModified: 2021/1/7 下午5:34
 */

package cn.herodotus.eurynome.oauth.autoconfigure.logic;

import cn.herodotus.eurynome.security.definition.RequestMapping;
import cn.herodotus.eurynome.security.definition.service.SecurityMetadataStorage;
import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;
import cn.herodotus.eurynome.upms.api.helper.UpmsHelper;
import cn.herodotus.eurynome.upms.logic.service.system.SysAuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

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

    private SysAuthorityService sysAuthorityService;

    public DataSourceSecurityMetadata(SysAuthorityService sysAuthorityService) {
        this.sysAuthorityService = sysAuthorityService;
    }

    @Override
    public void save(List<RequestMapping> requestMappings) {

        List<SysAuthority> sysAuthorities = UpmsHelper.convertRequestMappingsToSysAuthorities(requestMappings);

        List<SysAuthority> result = sysAuthorityService.batchSaveOrUpdate(sysAuthorities);
        if (CollectionUtils.isNotEmpty(result)) {
            log.info("[Eurynome] |- Store Service Resources Success!");
        } else {
            log.error("[Eurynome] |- Store Service Resources May Be Error, Please Check!");
        }

        log.info("[Eurynome] |- Store Service Resources Success!");
    }

    @Override
    public List<RequestMapping> findAll() {
        List<SysAuthority> sysAuthorities = sysAuthorityService.findAll();
        return UpmsHelper.convertSysAuthoritiesToRequestMappings(sysAuthorities);
    }
}

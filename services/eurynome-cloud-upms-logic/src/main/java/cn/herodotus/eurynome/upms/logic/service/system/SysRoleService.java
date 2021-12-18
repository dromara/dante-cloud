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
 * File Name: SysRoleService.java
 * Author: gengwei.zheng
 * Date: 2021/08/26 20:40:26
 */

package cn.herodotus.eurynome.upms.logic.service.system;

import cn.herodotus.eurynome.data.base.service.BaseLayeredService;
import cn.herodotus.eurynome.data.base.repository.BaseRepository;
import cn.herodotus.eurynome.upms.logic.entity.system.SysAuthority;
import cn.herodotus.eurynome.upms.logic.entity.system.SysRole;
import cn.herodotus.eurynome.upms.logic.repository.system.SysRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: SysRoleService </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/25 11:07
 */
@Service
public class SysRoleService extends BaseLayeredService<SysRole, String> {

    private static final Logger log = LoggerFactory.getLogger(SysRoleService.class);

    private final SysRoleRepository sysRoleRepository;

    @Autowired
    public SysRoleService(SysRoleRepository sysRoleRepository) {
        this.sysRoleRepository = sysRoleRepository;
    }

    @Override
    public BaseRepository<SysRole, String> getRepository() {
        return this.sysRoleRepository;
    }

    public SysRole authorize(String roleId, String[] authorities) {

        Set<SysAuthority> sysAuthorities = new HashSet<>();
        for (String authority : authorities) {
            SysAuthority sysAuthority = new SysAuthority();
            sysAuthority.setAuthorityId(authority);
            sysAuthorities.add(sysAuthority);
        }

        SysRole sysRole = findById(roleId);
        sysRole.setAuthorities(sysAuthorities);

        log.debug("[Herodotus] |- SysRole Service authorize.");
        return saveOrUpdate(sysRole);
    }

    public SysRole findByRoleCode(String roleCode) {
        SysRole sysRole = sysRoleRepository.findByRoleCode(roleCode);
        log.debug("[Herodotus] |- SysRole Service findByRoleCode.");
        return sysRole;
    }

    public SysRole findByRoleId(String roleId) {
        SysRole sysRole = sysRoleRepository.findByRoleId(roleId);
        log.debug("[Herodotus] |- SysRole Service findByRoleId.");
        return sysRole;
    }
}

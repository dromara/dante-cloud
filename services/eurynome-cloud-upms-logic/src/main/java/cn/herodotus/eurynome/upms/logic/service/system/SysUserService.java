/*
 * Copyright 2019-2019 the original author or authors.
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
 * Project Name: luban-cloud
 * Module Name: luban-cloud-upms-logic
 * File Name: SysUserService.java
 * Author: gengwei.zheng
 * Date: 2019/11/19 上午11:03
 * LastModified: 2019/11/19 上午11:00
 */

package cn.herodotus.eurynome.upms.logic.service.system;

import cn.herodotus.eurynome.rest.base.service.BaseLayeredService;
import cn.herodotus.eurynome.data.base.repository.BaseRepository;
import cn.herodotus.eurynome.upms.api.entity.system.SysRole;
import cn.herodotus.eurynome.upms.api.entity.system.SysUser;
import cn.herodotus.eurynome.upms.logic.repository.system.SysUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: SysUserService </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/9 10:03
 */
@Service
public class SysUserService extends BaseLayeredService<SysUser, String> {

    private static final Logger log = LoggerFactory.getLogger(SysUserService.class);

    private final SysUserRepository sysUserRepository;

    @Autowired
    public SysUserService(SysUserRepository sysUserRepository) {
        this.sysUserRepository = sysUserRepository;
    }

    @Override
    public BaseRepository<SysUser, String> getRepository() {
        return sysUserRepository;
    }

    public SysUser findByUserName(String userName) {
        SysUser sysUser = sysUserRepository.findByUserName(userName);
        log.debug("[Eurynome] |- SysUser Service findByUserName.");
        return sysUser;
    }

    public SysUser assign(String userId, String[] roleIds) {

        log.debug("[Eurynome] |- SysUser Service assign.");

        Set<SysRole> sysRoleSet = new HashSet<>();
        for (String roleId : roleIds) {
            SysRole sysRole = new SysRole();
            sysRole.setRoleId(roleId);
            sysRoleSet.add(sysRole);
        }

        SysUser sysUser = findById(userId);
        sysUser.setRoles(sysRoleSet);

        return saveOrUpdate(sysUser);
    }
}

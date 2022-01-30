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
 * File Name: SysUserService.java
 * Author: gengwei.zheng
 * Date: 2021/09/18 21:07:18
 */

package cn.herodotus.eurynome.upms.logic.service.system;

import cn.herodotus.engine.assistant.core.enums.AccountType;
import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.engine.data.core.service.BaseLayeredService;
import cn.herodotus.engine.security.core.definition.domain.HerodotusUserDetails;
import cn.herodotus.engine.security.core.definition.domain.SocialUserDetails;
import cn.herodotus.engine.security.core.utils.SecurityUtils;
import cn.herodotus.eurynome.upms.logic.entity.system.SysDefaultRole;
import cn.herodotus.eurynome.upms.logic.entity.system.SysRole;
import cn.herodotus.eurynome.upms.logic.entity.system.SysUser;
import cn.herodotus.eurynome.upms.logic.helper.UpmsHelper;
import cn.herodotus.eurynome.upms.logic.repository.system.SysUserRepository;
import cn.hutool.core.util.IdUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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
    private final SysDefaultRoleService sysDefaultRoleService;

    @Autowired
    public SysUserService(SysUserRepository sysUserRepository, SysDefaultRoleService sysDefaultRoleService) {
        this.sysUserRepository = sysUserRepository;
        this.sysDefaultRoleService = sysDefaultRoleService;
    }

    @Override
    public BaseRepository<SysUser, String> getRepository() {
        return sysUserRepository;
    }

    public SysUser findByUserName(String userName) {
        SysUser sysUser = sysUserRepository.findByUserName(userName);
        log.debug("[Herodotus] |- SysUser Service findByUserName.");
        return sysUser;
    }

    public SysUser findByUserId(String userId) {
        SysUser sysUser = sysUserRepository.findByUserId(userId);
        log.debug("[Herodotus] |- SysUser Service findByUserId.");
        return sysUser;
    }

    public SysUser changePassword(String userId, String password) {
        SysUser sysUser = findByUserId(userId);
        sysUser.setPassword(SecurityUtils.encrypt(password));
        log.debug("[Herodotus] |- SysUser Service changePassword.");
        return saveOrUpdate(sysUser);
    }

    public SysUser assign(String userId, String[] roleIds) {
        log.debug("[Herodotus] |- SysUser Service assign.");

        SysUser sysUser = findByUserId(userId);
        return this.register(sysUser, roleIds);
    }

    public SysUser register(SysUser sysUser, String[] roleIds) {
        Set<SysRole> sysRoles = new HashSet<>();
        for (String roleId : roleIds) {
            SysRole sysRole = new SysRole();
            sysRole.setRoleId(roleId);
            sysRoles.add(sysRole);
        }
        return this.register(sysUser, sysRoles);
    }

    public SysUser register(SysUser sysUser, AccountType source) {
        SysDefaultRole sysDefaultRole = sysDefaultRoleService.findByScene(source);
        if (ObjectUtils.isNotEmpty(sysDefaultRole)) {
            SysRole sysRole = sysDefaultRole.getRole();
            if (ObjectUtils.isNotEmpty(sysRole)) {
                return this.register(sysUser, sysRole);
            }
        }
        log.error("[Herodotus] |- Default role for [{}] is not set correct, may case register error!", source);
        return null;
    }

    public SysUser register(SysUser sysUser, SysRole sysRole) {
        Set<SysRole> sysRoles = new HashSet<>();
        sysRoles.add(sysRole);
        return this.register(sysUser, sysRoles);
    }

    public SysUser register(SysUser sysUser, Set<SysRole> sysRoles) {
        if (CollectionUtils.isNotEmpty(sysRoles)) {
            sysUser.setRoles(sysRoles);
        }
        log.debug("[Herodotus] |- SysUser Service register.");
        return saveOrUpdate(sysUser);
    }

    private String enhance(String userName) {
        if (StringUtils.isNotBlank(userName)) {
            SysUser checkedSysUser = this.findByUserName(userName);
            if (ObjectUtils.isNotEmpty(checkedSysUser)) {
                return checkedSysUser.getUserName() + IdUtil.nanoId(6);
            } else {
                return userName;
            }
        } else {
            return "Herodotus" + IdUtil.nanoId(6);
        }
    }

    public SysUser register(SocialUserDetails socialUserDetails) {
        SysUser sysUser = new SysUser();

        String userName = enhance(socialUserDetails.getUserName());
        sysUser.setUserName(userName);

        String nickName = socialUserDetails.getNickName();
        if (StringUtils.isNotBlank(nickName)) {
            sysUser.setNickName(nickName);
        }

        String phoneNumber = socialUserDetails.getPhoneNumber();
        if (StringUtils.isNotBlank(phoneNumber)) {
            sysUser.setPhoneNumber(SecurityUtils.encrypt(phoneNumber));
        }

        String avatar = socialUserDetails.getAvatar();
        if (StringUtils.isNotBlank(avatar)) {
            sysUser.setAvatar(avatar);
        }

        sysUser.setPassword(SecurityUtils.encrypt("herodotus-cloud"));

        return register(sysUser, AccountType.getAccountType(socialUserDetails.getSource()));
    }

    public HerodotusUserDetails registerUserDetails(SocialUserDetails socialUserDetails) {
        SysUser newSysUser = register(socialUserDetails);

        log.debug("[Herodotus] |- SysUser Service register UserDetails.");
        return UpmsHelper.convertSysUserToHerodotusUserDetails(newSysUser);
    }
}

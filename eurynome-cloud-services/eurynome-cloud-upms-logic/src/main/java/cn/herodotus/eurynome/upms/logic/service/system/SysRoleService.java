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
 * File Name: SysRoleService.java
 * Author: gengwei.zheng
 * Date: 2019/11/19 上午11:03
 * LastModified: 2019/11/19 上午11:00
 */

package cn.herodotus.eurynome.upms.logic.service.system;

import cn.herodotus.eurynome.component.data.base.service.BaseCacheService;
import cn.herodotus.eurynome.upms.api.constants.UpmsConstants;
import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;
import cn.herodotus.eurynome.upms.api.entity.system.SysRole;
import cn.herodotus.eurynome.upms.logic.repository.system.SysRoleRepository;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/25 11:07
 */
@Service
@Slf4j
public class SysRoleService extends BaseCacheService<SysRole, String> {

    @CreateCache(name = UpmsConstants.CACHE_NAME_SYS_ROLE, expire = 3600, cacheType = CacheType.BOTH, localLimit = 100)
    private Cache<String, SysRole> sysRoleCache;

    @CreateCache(name = UpmsConstants.CACHE_NAME_SYS_ROLE_INDEX, expire = 3600, cacheType = CacheType.BOTH, localLimit = 100)
    private Cache<String, Set<String>> sysRoleIndexCache;

    private final SysRoleRepository sysRoleRepository;

    @Autowired
    public SysRoleService(SysRoleRepository sysRoleRepository) {
        this.sysRoleRepository = sysRoleRepository;
    }

    @Override
    public Cache<String, SysRole> getCache() {
        return sysRoleCache;
    }

    @Override
    public Cache<String, Set<String>> getIndexCache() {
        return sysRoleIndexCache;
    }

    @Override
    public SysRole saveOrUpdate(SysRole sysRole) {
        SysRole savedSysRole = sysRoleRepository.saveAndFlush(sysRole);
        this.cache(savedSysRole);
        log.debug("[Luban UPMS] |- SysRole Service saveOrUpdate.");
        return savedSysRole;
    }

    public SysRole authorize(String roleId, String[] authorities) {

        log.debug("[Luban UPMS] |- SysRole Service authorize.");

        Set<SysAuthority> sysAuthorities = new HashSet<>();
        for (String authority : authorities) {
            SysAuthority sysAuthority = new SysAuthority();
            sysAuthority.setAuthorityId(authority);
            sysAuthorities.add(sysAuthority);
        }

        SysRole sysRole = findById(roleId);
        sysRole.setAuthorities(sysAuthorities);

        return saveOrUpdate(sysRole);
    }

    @Override
    public SysRole findById(String roleId) {
        SysRole sysRole = this.getFromCache(roleId);
        if (ObjectUtils.isEmpty(sysRole)) {
            sysRole = sysRoleRepository.findByRoleId(roleId);
            this.cache(sysRole);
        }
        log.debug("[Luban UPMS] |- SysRole Service findByRoleId.");
        return sysRole;
    }

    @Override
    public void deleteById(String roleId) {
        log.debug("[Luban UPMS] |- SysRole Service deleteById.");
        sysRoleRepository.deleteByRoleId(roleId);
        this.remove(roleId);
    }

    @Override
    public Page<SysRole> findByPage(int pageNumber, int pageSize) {
        Page<SysRole> pages = sysRoleRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "roleId"));
        this.cache(pages.getContent());
        log.debug("[Luban UPMS] |- SysRole Service findByPage.");
        return pages;
    }

}

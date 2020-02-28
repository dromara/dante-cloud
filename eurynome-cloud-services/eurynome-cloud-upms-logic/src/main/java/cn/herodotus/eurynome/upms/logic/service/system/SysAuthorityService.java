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
 * File Name: SysAuthorityService.java
 * Author: gengwei.zheng
 * Date: 2019/11/19 上午11:03
 * LastModified: 2019/11/19 上午11:00
 */

package cn.herodotus.eurynome.upms.logic.service.system;

import cn.herodotus.eurynome.component.common.enums.AuthorityType;
import cn.herodotus.eurynome.component.data.service.BaseCrudWithCacheSerivce;
import cn.herodotus.eurynome.upms.api.constants.UpmsCacheConstants;
import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;
import cn.herodotus.eurynome.upms.logic.repository.system.SysAuthorityRepository;
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

import java.util.List;

/**
 * <p>Description: SysAuthority 服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/25 16:11
 */
@Slf4j
@Service
public class SysAuthorityService extends BaseCrudWithCacheSerivce<SysAuthority, String> {

    @CreateCache(name = UpmsCacheConstants.CACHE_NAME_SYSAUTHORITY, expire = 3600, cacheType = CacheType.BOTH, localLimit = 100)
    private Cache<String, SysAuthority> sysAuthorityCache;

    private final SysAuthorityRepository sysAuthorityRepository;

    @Autowired
    public SysAuthorityService(SysAuthorityRepository sysAuthorityRepository) {
        this.sysAuthorityRepository = sysAuthorityRepository;
    }

    @Override
    public Cache<String, SysAuthority> getCache() {
        return sysAuthorityCache;
    }

    public List<SysAuthority> findAll() {
        List<SysAuthority> sysAuthorities = sysAuthorityRepository.findAll();
        this.cacheCollection(sysAuthorities);
        log.debug("[Luban UPMS] |- SysAuthority Service findAll.");
        return sysAuthorities;
    }

    public List<SysAuthority> batchSaveOrUpdate(List<SysAuthority> sysAuthorities) {
        log.debug("[Luban UPMS] |- SysAuthority Service batchSaveOrUpdate.");
        return sysAuthorityRepository.saveAll(sysAuthorities);
    }

    @Override
    public SysAuthority saveOrUpdate(SysAuthority sysAuthority) {
        SysAuthority savedSysAuthority = sysAuthorityRepository.saveAndFlush(sysAuthority);
        this.cacheEntity(savedSysAuthority);
        log.debug("[Luban UPMS] |- SysAuthority Service saveOrUpdate.");
        return savedSysAuthority;
    }

    @Override
    public SysAuthority findById(String authorityId) {
        SysAuthority sysAuthority = this.getCache().get(authorityId);
        if (ObjectUtils.isEmpty(sysAuthority)) {
            sysAuthority = sysAuthorityRepository.findByAuthorityId(authorityId);
            this.cacheEntity(sysAuthority);
        }
        log.debug("[Luban UPMS] |- SysAuthority Service findById.");
        return sysAuthority;
    }

    @Override
    public void deleteById(String authorityId) {
        log.debug("[Luban UPMS] |- SysAuthority Service deleteById.");
        sysAuthorityRepository.deleteByAuthorityId(authorityId);
        this.getCache().remove(authorityId);
    }

    @Override
    public Page<SysAuthority> findByPage(int pageNumber, int pageSize) {
        Page<SysAuthority> pages = sysAuthorityRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "updateTime"));
        this.cacheCollection( pages.getContent());
        log.debug("[Luban UPMS] |- SysAuthority Service findByPage.");
        return pages;
    }

    public List<SysAuthority> findAllByAuthorityType(AuthorityType authorityType) {
        List<SysAuthority> sysAuthorities = sysAuthorityRepository.findAllByAuthorityType(authorityType);
        this.cacheCollection( sysAuthorities);
        return sysAuthorities;
    }
}

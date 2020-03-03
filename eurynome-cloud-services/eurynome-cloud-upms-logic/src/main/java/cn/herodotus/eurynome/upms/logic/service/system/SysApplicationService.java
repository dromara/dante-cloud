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
 * File Name: SysApplicationService.java
 * Author: gengwei.zheng
 * Date: 2019/11/19 上午11:03
 * LastModified: 2019/11/19 上午11:00
 */

package cn.herodotus.eurynome.upms.logic.service.system;

import cn.herodotus.eurynome.component.data.base.service.BaseCacheService;
import cn.herodotus.eurynome.upms.api.constants.UpmsConstants;
import cn.herodotus.eurynome.upms.api.entity.system.SysApplication;
import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;
import cn.herodotus.eurynome.upms.logic.repository.system.SysApplicationRepository;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheInvalidate;
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
 * @author gengwei.zheng
 */
@Slf4j
@Service
public class SysApplicationService extends BaseCacheService<SysApplication, String> {

    @CreateCache(name = UpmsConstants.CACHE_NAME_SYSAPPLICATION, expire = 3600, cacheType = CacheType.BOTH, localLimit = 100)
    private Cache<String, SysApplication> sysApplicationCache;

    @CreateCache(name = UpmsConstants.CACHE_NAME_SYSAPPLICATION_INDEX, expire = 3600, cacheType = CacheType.BOTH, localLimit = 100)
    private Cache<String, Set<String>> sysApplicationIndexCache;

    private final SysApplicationRepository sysApplicationRepository;

    @Autowired
    public SysApplicationService(SysApplicationRepository sysApplicationRepository) {
        this.sysApplicationRepository = sysApplicationRepository;
    }

    @Override
    public Cache<String, SysApplication> getCache() {
        return sysApplicationCache;
    }

    @Override
    public Cache<String, Set<String>> getIndexCache() {
        return sysApplicationIndexCache;
    }

    @Override
    public SysApplication saveOrUpdate(SysApplication sysApplication) {
        SysApplication savedSysApplication = sysApplicationRepository.saveAndFlush(sysApplication);
        this.cache(savedSysApplication);
        log.debug("[Luban UPMS] |- SysApplication Service saveOrUpdate.");
        return savedSysApplication;
    }

    public SysApplication findByClientId(String clientId) {
        SysApplication sysApplication = sysApplicationRepository.findByClientId(clientId);
        log.debug("[Luban UPMS] |- SysApplication Service findByClientId.");
        return sysApplication;
    }

    public SysApplication authorize(String applicationId, String[] authorities) {

        log.debug("[Luban UPMS] |- SysApplication Service authorize.");

        Set<SysAuthority> sysAuthorities = new HashSet<>();
        for (String authority : authorities) {
            SysAuthority sysAuthority = new SysAuthority();
            sysAuthority.setAuthorityId(authority);
            sysAuthorities.add(sysAuthority);
        }

        SysApplication sysApplication = findById(applicationId);
        sysApplication.setAuthorities(sysAuthorities);

        return saveOrUpdate(sysApplication);
    }

    @Override
    public SysApplication findById(String applicationId) {
        SysApplication sysApplication = this.getFromCache(applicationId);

        if (ObjectUtils.isEmpty(sysApplication)){
            sysApplication = sysApplicationRepository.findByApplicationId(applicationId);
            this.cache(sysApplication);
        }
        log.debug("[Luban UPMS] |- SysApplication Service findById.");
        return sysApplication;
    }

    @CacheInvalidate(name = UpmsConstants.CACHE_NAME_SYSAPPLICATION, key = "#applicationId")
    @Override
    public void deleteById(String applicationId) {
        log.debug("[Luban UPMS] |- SysApplication Service deleteById.");
        sysApplicationRepository.deleteById(applicationId);
        this.getCache().remove(applicationId);
    }

    /**
     * 使用Spring Data Jpa对Page，缓存会出错。
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public Page<SysApplication> findByPage(int pageNumber, int pageSize) {
        Page<SysApplication> pages = sysApplicationRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "updateTime"));
        this.cache(pages.getContent());
        log.debug("[Luban UPMS] |- SysApplication Service findByPage.");
        return pages;
    }


}

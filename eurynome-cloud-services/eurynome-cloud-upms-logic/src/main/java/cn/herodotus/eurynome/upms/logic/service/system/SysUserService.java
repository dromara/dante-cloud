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

import cn.herodotus.eurynome.component.data.base.service.BaseCrudWithCacheSerivce;
import cn.herodotus.eurynome.upms.api.constants.UpmsCacheConstants;
import cn.herodotus.eurynome.upms.api.entity.system.SysUser;
import cn.herodotus.eurynome.upms.logic.repository.system.SysUserRepository;
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

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/9 10:03
 */
@Slf4j
@Service
public class SysUserService extends BaseCrudWithCacheSerivce<SysUser, String> {

    @CreateCache(name = UpmsCacheConstants.CACHE_NAME_SYSUSER, expire = 3600, cacheType = CacheType.BOTH, localLimit = 100)
    private Cache<String, SysUser> sysUserCache;

    private final SysUserRepository sysUserRepository;

    @Autowired
    public SysUserService(SysUserRepository sysUserRepository) {
        this.sysUserRepository = sysUserRepository;
    }

    @Override
    public Cache<String, SysUser> getCache() {
        return sysUserCache;
    }

    public SysUser findSysUserByUserName(String userName) {
        SysUser sysUser = sysUserRepository.findByUserName(userName);
        this.cacheEntity(sysUser);
        log.debug("[Luban UPMS] |- SysUser Service findSysUserByUserName.");
        return sysUser;
    }

    @Override
    public SysUser saveOrUpdate(SysUser sysUser) {
        SysUser savedSysUser = sysUserRepository.saveAndFlush(sysUser);
        this.cacheEntity(savedSysUser);
        log.debug("[Luban UPMS] |- SysUser Service saveOrUpdate.");
        return savedSysUser;
    }

    /**
     * @param userId
     * @return
     * @Cacheable 应用到读取数据的方法上，即可缓存的方法，如查找方法：先从缓存中读取，如果没有再调用方法获取数据，然后把数据添加到缓存中
     * @CachePut 应用到写数据的方法上，如新增/修改方法，调用方法时会自动把相应的数据放入缓存
     * @CacheEvict 即应用到移除数据的方法上，如删除方法，调用方法时会从缓存中移除相应的数据
     */
    @Override
    public SysUser findById(String userId) {
        SysUser sysUser = this.getCache().get(userId);

        if (ObjectUtils.isEmpty(sysUser)) {
            sysUser = sysUserRepository.findByUserId(userId);
            this.cacheEntity(sysUser);
        }

        log.debug("[Luban UPMS] |- SysUser Service findById.");
        return sysUser;
    }

    @Override
    public void deleteById(String userId) {
        log.debug("[Luban UPMS] |- SysUser Service delete.");
        sysUserRepository.deleteByUserId(userId);
        this.getCache().remove(userId);
    }

    @Override
    public Page<SysUser> findByPage(int pageNumber, int pageSize) {
        Page<SysUser> pages = sysUserRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "userId"));
        this.cacheCollection(pages.getContent());
        log.debug("[Luban UPMS] |- SysUser Service findByPage.");
        return pages;
    }
}

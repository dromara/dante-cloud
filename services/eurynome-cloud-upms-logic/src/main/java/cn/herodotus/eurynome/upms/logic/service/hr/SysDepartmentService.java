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
 * File Name: SysDepartmentService.java
 * Author: gengwei.zheng
 * Date: 2019/11/19 上午11:03
 * LastModified: 2019/11/19 上午10:59
 */

package cn.herodotus.eurynome.upms.logic.service.hr;

import cn.herodotus.eurynome.data.base.repository.BaseRepository;
import cn.herodotus.eurynome.data.base.service.BaseService;
import cn.herodotus.eurynome.upms.api.constants.UpmsConstants;
import cn.herodotus.eurynome.upms.api.entity.hr.SysDepartment;
import cn.herodotus.eurynome.upms.logic.repository.hr.SysDepartmentRepository;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * <p>Description: 部门管理服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/20 11:50
 */
@Slf4j
@Service
public class SysDepartmentService extends BaseService<SysDepartment, String> {

    private static final String CACHE_NAME = UpmsConstants.CACHE_NAME_SYS_DEPARTMENT;

    @CreateCache(name = CACHE_NAME, expire = UpmsConstants.DEFAULT_UPMS_CACHE_EXPIRE, cacheType = CacheType.BOTH, localLimit = UpmsConstants.DEFAULT_UPMS_LOCAL_LIMIT)
    private Cache<String, SysDepartment> dataCache;

    @CreateCache(name = CACHE_NAME + UpmsConstants.INDEX_CACHE_NAME, expire = UpmsConstants.DEFAULT_UPMS_CACHE_EXPIRE, cacheType = CacheType.BOTH, localLimit = UpmsConstants.DEFAULT_UPMS_LOCAL_LIMIT)
    private Cache<String, Set<String>> indexCache;

    private final SysDepartmentRepository sysDepartmentRepository;

    @Autowired
    public SysDepartmentService(SysDepartmentRepository sysDepartmentRepository) {
        this.sysDepartmentRepository = sysDepartmentRepository;
    }

    @Override
    public BaseRepository<SysDepartment, String> getRepository() {
        return sysDepartmentRepository;
    }

    @Override
    public Cache<String, SysDepartment> getCache() {
        return dataCache;
    }

    @Override
    public Cache<String, Set<String>> getIndexCache() {
        return indexCache;
    }

    public List<SysDepartment> findAllByOrganizationId(String organizationId) {
        List<SysDepartment> sysDepartments = readFromCache(organizationId);
        if (CollectionUtils.isEmpty(sysDepartments)) {
            sysDepartments = sysDepartmentRepository.findAllByOrganizationId(organizationId);
            writeToCache(sysDepartments, organizationId);
        }
        return sysDepartments;
    }
}

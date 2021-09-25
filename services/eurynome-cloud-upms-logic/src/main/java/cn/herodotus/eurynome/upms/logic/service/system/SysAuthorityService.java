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
 * File Name: SysAuthorityService.java
 * Author: gengwei.zheng
 * Date: 2021/09/25 10:46:25
 */

package cn.herodotus.eurynome.upms.logic.service.system;

import cn.herodotus.eurynome.common.constant.enums.AuthorityType;
import cn.herodotus.eurynome.rest.base.service.BaseLayeredService;
import cn.herodotus.eurynome.data.base.repository.BaseRepository;
import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;
import cn.herodotus.eurynome.upms.logic.repository.system.SysAuthorityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.List;

/**
 * <p>Description: SysAuthorityService </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/25 16:11
 */
@Service
public class SysAuthorityService extends BaseLayeredService<SysAuthority, String> {

    private static final Logger log = LoggerFactory.getLogger(SysAuthorityService.class);

    private final SysAuthorityRepository sysAuthorityRepository;

    @Autowired
    public SysAuthorityService(SysAuthorityRepository sysAuthorityRepository) {
        this.sysAuthorityRepository = sysAuthorityRepository;
    }

    @Override
    public BaseRepository<SysAuthority, String> getRepository() {
        return this.sysAuthorityRepository;
    }

    public List<SysAuthority> batchSaveOrUpdate(List<SysAuthority> sysAuthorities) {
        log.debug("[Herodotus] |- SysAuthority Service batchSaveOrUpdate.");
        return sysAuthorityRepository.saveAllAndFlush(sysAuthorities);
    }

    public List<SysAuthority> findAllByAuthorityType(AuthorityType authorityType) {
        List<SysAuthority> sysAuthorities = sysAuthorityRepository.findAllByAuthorityType(authorityType);
        log.debug("[Herodotus] |- SysAuthority Service findAllByAuthorityType.");
        return sysAuthorities;
    }
}

/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2019-2022 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.eurynome.module.upms.logic.service.system;

import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.engine.data.core.service.BaseLayeredService;
import cn.herodotus.eurynome.module.upms.logic.entity.system.SysAuthority;
import cn.herodotus.eurynome.module.upms.logic.entity.system.SysScope;
import cn.herodotus.eurynome.module.upms.logic.repository.system.SysScopeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * <p> Description : OauthScopeService </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/19 17:00
 */
@Service
public class SysScopeService extends BaseLayeredService<SysScope, String> {

    private static final Logger log = LoggerFactory.getLogger(SysScopeService.class);

    @Autowired
    private SysScopeRepository sysScopeRepository;

    @Override
    public BaseRepository<SysScope, String> getRepository() {
        return sysScopeRepository;
    }

    public SysScope authorize(String scopeId, String[] authorities) {

        Set<SysAuthority> sysAuthorities = new HashSet<>();
        for (String authority : authorities) {
            SysAuthority sysAuthority = new SysAuthority();
            sysAuthority.setAuthorityId(authority);
            sysAuthorities.add(sysAuthority);
        }

        SysScope sysScope = findById(scopeId);
        sysScope.setAuthorities(sysAuthorities);

        log.debug("[Herodotus] |- OauthScopes Service authorize.");

        return saveOrUpdate(sysScope);
    }
}

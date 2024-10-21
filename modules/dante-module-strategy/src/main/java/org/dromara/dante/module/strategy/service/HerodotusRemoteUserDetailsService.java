/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Dante Cloud.
 *
 * Dante Cloud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dante Cloud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.cn>.
 */

package org.dromara.dante.module.strategy.service;

import org.dromara.dante.module.strategy.definition.AbstractStrategyUserDetailsService;
import org.dromara.dante.module.strategy.feign.RemoteSocialDetailsService;
import org.dromara.dante.module.strategy.feign.RemoteUserDetailsService;
import cn.herodotus.engine.assistant.definition.domain.Result;
import cn.herodotus.engine.assistant.definition.domain.oauth2.AccessPrincipal;
import cn.herodotus.engine.oauth2.core.definition.domain.HerodotusUser;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysUser;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * <p>Description: UserDetail远程调用服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/23 9:21
 */
public class HerodotusRemoteUserDetailsService extends AbstractStrategyUserDetailsService {

    private final RemoteUserDetailsService remoteUserDetailsService;
    private final RemoteSocialDetailsService remoteSocialDetailsService;

    public HerodotusRemoteUserDetailsService(RemoteUserDetailsService remoteUserDetailsService, RemoteSocialDetailsService remoteSocialDetailsService) {
        this.remoteUserDetailsService = remoteUserDetailsService;
        this.remoteSocialDetailsService = remoteSocialDetailsService;
    }

    @Override
    public HerodotusUser findUserDetailsByUsername(String username) throws UsernameNotFoundException {
        Result<SysUser> result = remoteUserDetailsService.findByUsername(username);

        SysUser sysUser = result.getData();
        return this.convertSysUser(sysUser, username);
    }

    @Override
    public HerodotusUser findUserDetailsBySocial(String source, AccessPrincipal accessPrincipal) {
        Result<HerodotusUser> result = remoteSocialDetailsService.findUserDetailsBySocial(source, accessPrincipal);
        return result.getData();
    }
}

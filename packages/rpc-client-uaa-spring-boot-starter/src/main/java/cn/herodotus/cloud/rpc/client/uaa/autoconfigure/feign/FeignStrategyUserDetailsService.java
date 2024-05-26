/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Herodotus Cloud.
 *
 * Herodotus Cloud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Herodotus Cloud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.vip>.
 */

package cn.herodotus.cloud.rpc.client.uaa.autoconfigure.feign;

import cn.herodotus.cloud.rpc.client.uaa.autoconfigure.definition.AbstractStrategyUserDetailsService;
import cn.herodotus.cloud.rpc.client.uaa.autoconfigure.feign.api.RemoteSocialDetailsService;
import cn.herodotus.cloud.rpc.client.uaa.autoconfigure.feign.api.RemoteUserDetailsService;
import cn.herodotus.stirrup.core.definition.domain.Result;
import cn.herodotus.stirrup.core.identity.domain.AccessPrincipal;
import cn.herodotus.stirrup.core.identity.domain.HerodotusUser;
import cn.herodotus.stirrup.logic.upms.entity.security.SysUser;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * <p>Description: UserDetail远程调用服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/23 9:21
 */
public class FeignStrategyUserDetailsService extends AbstractStrategyUserDetailsService {

    private final RemoteUserDetailsService remoteUserDetailsService;
    private final RemoteSocialDetailsService remoteSocialDetailsService;

    public FeignStrategyUserDetailsService(RemoteUserDetailsService remoteUserDetailsService, RemoteSocialDetailsService remoteSocialDetailsService) {
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

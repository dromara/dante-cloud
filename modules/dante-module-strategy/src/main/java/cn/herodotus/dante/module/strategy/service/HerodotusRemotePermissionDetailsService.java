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

package cn.herodotus.dante.module.strategy.service;

import cn.herodotus.dante.module.strategy.definition.AbstractStrategyPermissionDetailsService;
import cn.herodotus.dante.module.strategy.feign.RemoteAuthorityDetailsService;
import cn.herodotus.engine.assistant.definition.domain.Result;
import cn.herodotus.engine.oauth2.core.definition.domain.HerodotusPermission;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysPermission;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: 远程权限服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/4/1 19:01
 */
public class HerodotusRemotePermissionDetailsService extends AbstractStrategyPermissionDetailsService {

    private final RemoteAuthorityDetailsService remoteAuthorityDetailsService;

    public HerodotusRemotePermissionDetailsService(RemoteAuthorityDetailsService remoteAuthorityDetailsService) {
        this.remoteAuthorityDetailsService = remoteAuthorityDetailsService;
    }

    @Override
    public List<HerodotusPermission> findAll() {
        Result<List<SysPermission>> result = remoteAuthorityDetailsService.findAll();
        List<SysPermission> authorities = result.getData();
        if (CollectionUtils.isNotEmpty(authorities)) {
            return toEntities(authorities);
        }
        return new ArrayList<>();
    }
}

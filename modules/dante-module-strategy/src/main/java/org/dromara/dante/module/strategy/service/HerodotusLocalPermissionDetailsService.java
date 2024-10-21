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

import org.dromara.dante.module.strategy.definition.AbstractStrategyPermissionDetailsService;
import cn.herodotus.engine.oauth2.core.definition.domain.HerodotusPermission;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysPermission;
import cn.herodotus.engine.supplier.upms.logic.service.security.SysPermissionService;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: 本地权限服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/4/1 18:56
 */
public class HerodotusLocalPermissionDetailsService extends AbstractStrategyPermissionDetailsService {

    private final SysPermissionService sysPermissionService;

    public HerodotusLocalPermissionDetailsService(SysPermissionService sysPermissionService) {
        this.sysPermissionService = sysPermissionService;
    }

    @Override
    public List<HerodotusPermission> findAll() {
        List<SysPermission> authorities = sysPermissionService.findAll();
        ;
        if (CollectionUtils.isNotEmpty(authorities)) {
            return toEntities(authorities);
        }

        return new ArrayList<>();
    }
}

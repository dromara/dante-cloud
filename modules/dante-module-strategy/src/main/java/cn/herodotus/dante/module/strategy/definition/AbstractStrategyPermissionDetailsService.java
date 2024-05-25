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

package cn.herodotus.dante.module.strategy.definition;

import cn.herodotus.engine.oauth2.core.definition.domain.HerodotusPermission;
import cn.herodotus.engine.oauth2.core.definition.strategy.StrategyPermissionDetailsService;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysPermission;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Description: 抽象的StrategyAuthorityDetailsService </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/4/1 19:09
 */
public abstract class AbstractStrategyPermissionDetailsService implements StrategyPermissionDetailsService {

    protected List<HerodotusPermission> toEntities(List<SysPermission> permissions) {
        return permissions.stream().map(this::toEntity).collect(Collectors.toList());
    }

    protected HerodotusPermission toEntity(SysPermission object) {
        HerodotusPermission herodotusPermission = new HerodotusPermission();
        herodotusPermission.setPermissionId(object.getPermissionId());
        herodotusPermission.setPermissionCode(object.getPermissionCode());
        herodotusPermission.setPermissionName(object.getPermissionName());
        return herodotusPermission;
    }
}

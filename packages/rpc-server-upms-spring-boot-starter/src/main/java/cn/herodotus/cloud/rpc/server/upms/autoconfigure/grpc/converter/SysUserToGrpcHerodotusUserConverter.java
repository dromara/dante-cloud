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

package cn.herodotus.cloud.rpc.server.upms.autoconfigure.grpc.converter;

import cn.herodotus.stirrup.core.definition.constants.SymbolConstants;
import cn.herodotus.stirrup.grpc.user.GrpcHerodotusUser;
import cn.herodotus.stirrup.logic.upms.converter.AbstractSysUserConverter;
import cn.herodotus.stirrup.logic.upms.entity.security.SysPermission;
import cn.herodotus.stirrup.logic.upms.entity.security.SysRole;
import cn.herodotus.stirrup.logic.upms.entity.security.SysUser;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: SysUser 转 HerodotusGrpcUser 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/3/11 17:09
 */
public class SysUserToGrpcHerodotusUserConverter extends AbstractSysUserConverter<GrpcHerodotusUser> {
    @Override
    public GrpcHerodotusUser convert(SysUser source) {
        Set<String> authorities = new HashSet<>();
        Set<String> roles = new HashSet<>();

        for (SysRole sysRole : source.getRoles()) {
            roles.add(sysRole.getRoleCode());
            Set<SysPermission> sysPermissions = sysRole.getPermissions();
            if (CollectionUtils.isNotEmpty(sysPermissions)) {
                sysPermissions.forEach(sysAuthority -> authorities.add(sysAuthority.getPermissionCode()));
            }
        }

        String employeeId = ObjectUtils.isNotEmpty(source.getEmployee()) ? source.getEmployee().getEmployeeId() : "";

        GrpcHerodotusUser.Builder builder = GrpcHerodotusUser.newBuilder()
                .setUserId(source.getUserId())
                .setUsername(source.getUsername())
                .setPassword(source.getPassword())
                .setAvatar(valid(source.getAvatar()))
                .setEmployeeId(valid(employeeId))
                .setEnabled(isEnabled(source))
                .setAccountNonExpired(isAccountNonExpired(source))
                .setCredentialsNonExpired(isCredentialsNonExpired(source))
                .setAccountNonLocked(isAccountNonLocked(source))
                .addAllRoles(roles)
                .addAllAuthorities(authorities);

        return builder.build();
    }

    private String valid(String data) {
        if (StringUtils.isNotBlank(data)) {
            return data;
        } else {
            return SymbolConstants.BLANK;
        }
    }
}

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

import cn.herodotus.stirrup.core.identity.domain.HerodotusUser;
import cn.herodotus.stirrup.grpc.user.GrpcHerodotusUser;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: HerodotusUser 转 GrpcHerodotusUser 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/3/18 16:04
 */
public class HerodotusUserToGrpcHerodotusUserConverter implements Converter<HerodotusUser, GrpcHerodotusUser> {
    @Override
    public GrpcHerodotusUser convert(HerodotusUser source) {

        GrpcHerodotusUser.Builder builder = GrpcHerodotusUser.newBuilder()
                .setUserId(source.getUserId())
                .setUsername(source.getUsername())
                .setPassword(source.getPassword())
                .setAvatar(source.getAvatar())
                .setEmployeeId(source.getEmployeeId())
                .setEnabled(source.isEnabled())
                .setAccountNonExpired(source.isAccountNonExpired())
                .setCredentialsNonExpired(source.isCredentialsNonExpired())
                .setAccountNonLocked(source.isAccountNonLocked());

        Set<String> roles = source.getRoles();
        if (CollectionUtils.isNotEmpty(roles)) {
            roles.forEach(builder::addRoles);
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        if (CollectionUtils.isNotEmpty(authorities)) {
            authorities.forEach(grantedAuthority -> builder.addAuthorities(grantedAuthority.getAuthority()));
        }

        return builder.build();
    }
}

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

package cn.herodotus.cloud.rpc.client.uaa.autoconfigure.grpc.converter;

import cn.herodotus.stirrup.core.identity.domain.HerodotusGrantedAuthority;
import cn.herodotus.stirrup.core.identity.domain.HerodotusUser;
import cn.herodotus.stirrup.grpc.user.GrpcHerodotusUser;
import com.google.protobuf.ProtocolStringList;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: GrpcHerodotusUser 转 HerodotusUser 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/3/12 16:37
 */
public class GrpcToHerodotusUserConverter implements Converter<GrpcHerodotusUser, HerodotusUser> {
    @Override
    public HerodotusUser convert(GrpcHerodotusUser grpcUser) {

        ProtocolStringList roleList = grpcUser.getRolesList();
        Set<String> roles = new HashSet<>(roleList);

        Set<GrantedAuthority> authorities = new HashSet<>();
        ProtocolStringList authorityList = grpcUser.getAuthoritiesList();
        if (CollectionUtils.isNotEmpty(authorityList)) {
            authorityList.iterator().forEachRemaining(item -> authorities.add(new HerodotusGrantedAuthority(item)));
        }

        return new HerodotusUser(grpcUser.getUserId(), grpcUser.getUsername(), grpcUser.getPassword(),
                grpcUser.getEnabled(),
                grpcUser.getAccountNonExpired(),
                grpcUser.getCredentialsNonExpired(),
                grpcUser.getAccountNonLocked(),
                authorities, roles, grpcUser.getEmployeeId(), grpcUser.getAvatar());
    }
}

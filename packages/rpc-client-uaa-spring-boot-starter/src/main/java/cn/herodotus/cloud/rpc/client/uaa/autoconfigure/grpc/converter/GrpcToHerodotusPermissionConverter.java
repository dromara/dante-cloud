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

import cn.herodotus.stirrup.core.identity.domain.HerodotusPermission;
import cn.herodotus.stirrup.grpc.permission.GrpcHerodotusPermission;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

/**
 * <p>Description: List<GrpcHerodotusPermission> 转 List<HerodotusPermission> 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/3/19 15:40
 */
public class GrpcToHerodotusPermissionConverter implements Converter<List<GrpcHerodotusPermission>, List<HerodotusPermission>> {
    @Override
    public List<HerodotusPermission> convert(List<GrpcHerodotusPermission> source) {
        return source.stream().map(this::toHerodotusPermission).toList();
    }

    private HerodotusPermission toHerodotusPermission(GrpcHerodotusPermission source) {
        HerodotusPermission herodotusPermission = new HerodotusPermission();
        herodotusPermission.setPermissionId(source.getPermissionId());
        herodotusPermission.setPermissionCode(source.getPermissionCode());
        herodotusPermission.setPermissionName(source.getPermissionName());
        return herodotusPermission;
    }
}

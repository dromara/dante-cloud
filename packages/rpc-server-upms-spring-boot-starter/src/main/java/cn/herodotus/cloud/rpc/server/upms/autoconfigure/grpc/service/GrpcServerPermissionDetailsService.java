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

package cn.herodotus.cloud.rpc.server.upms.autoconfigure.grpc.service;

import cn.herodotus.cloud.rpc.server.upms.autoconfigure.grpc.converter.HerodotusToGrpcPermissionConverter;
import cn.herodotus.stirrup.grpc.permission.GrpcHerodotusPermissionServiceGrpc;
import cn.herodotus.stirrup.grpc.permission.GrpcHerodotusPermissions;
import cn.herodotus.stirrup.logic.upms.entity.security.SysPermission;
import cn.herodotus.stirrup.logic.upms.service.security.SysPermissionService;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

/**
 * <p>Description: Grpc Server Permission 服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/3/19 14:34
 */
@GrpcService
public class GrpcServerPermissionDetailsService extends GrpcHerodotusPermissionServiceGrpc.GrpcHerodotusPermissionServiceImplBase {

    private final SysPermissionService sysPermissionService;
    private final Converter<List<SysPermission>, GrpcHerodotusPermissions> toGrpcPermissions;

    public GrpcServerPermissionDetailsService(SysPermissionService sysPermissionService) {
        this.sysPermissionService = sysPermissionService;
        this.toGrpcPermissions = new HerodotusToGrpcPermissionConverter();
    }

    @Override
    public void findAll(Empty request, StreamObserver<GrpcHerodotusPermissions> responseObserver) {
        List<SysPermission> sysPermissions = sysPermissionService.findAll();
        if (CollectionUtils.isNotEmpty(sysPermissions)) {
            responseObserver.onNext(toGrpcPermissions.convert(sysPermissions));
        }
        responseObserver.onCompleted();
    }
}

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

package cn.herodotus.cloud.rpc.client.uaa.autoconfigure.grpc;

import cn.herodotus.cloud.module.common.constants.ServiceNameConstants;
import cn.herodotus.cloud.rpc.client.uaa.autoconfigure.grpc.converter.GrpcToHerodotusPermissionConverter;
import cn.herodotus.stirrup.core.identity.domain.HerodotusPermission;
import cn.herodotus.stirrup.core.identity.strategy.StrategyPermissionDetailsService;
import cn.herodotus.stirrup.grpc.permission.GrpcHerodotusPermission;
import cn.herodotus.stirrup.grpc.permission.GrpcHerodotusPermissionServiceGrpc;
import cn.herodotus.stirrup.grpc.permission.GrpcHerodotusPermissions;
import com.google.protobuf.Empty;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: HerodotusPermission GRPC 服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/3/19 15:37
 */
@Service
public class GrpcStrategyPermissionService implements StrategyPermissionDetailsService {

    @GrpcClient(ServiceNameConstants.SERVICE_NAME_UPMS)
    private GrpcHerodotusPermissionServiceGrpc.GrpcHerodotusPermissionServiceBlockingStub grpcHerodotusPermissionServiceBlockingStub;

    private final Converter<List<GrpcHerodotusPermission>, List<HerodotusPermission>> toPermissions = new GrpcToHerodotusPermissionConverter();

    @Override
    public List<HerodotusPermission> findAll() {
        GrpcHerodotusPermissions grpcHerodotusPermissions = grpcHerodotusPermissionServiceBlockingStub.findAll(Empty.getDefaultInstance());

        List<GrpcHerodotusPermission> permissions = grpcHerodotusPermissions.getPermissionsList();
        if (CollectionUtils.isNotEmpty(permissions)) {
            return toPermissions.convert(permissions);
        }
        return new ArrayList<>();
    }
}

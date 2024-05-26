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

import cn.herodotus.cloud.rpc.server.upms.autoconfigure.grpc.converter.GrpcToAccessPrincipalConverter;
import cn.herodotus.cloud.rpc.server.upms.autoconfigure.grpc.converter.HerodotusUserToGrpcHerodotusUserConverter;
import cn.herodotus.cloud.rpc.server.upms.autoconfigure.grpc.converter.SysUserToGrpcHerodotusUserConverter;
import cn.herodotus.stirrup.core.identity.domain.AccessPrincipal;
import cn.herodotus.stirrup.core.identity.domain.HerodotusUser;
import cn.herodotus.stirrup.core.identity.handler.AbstractSocialAuthenticationHandler;
import cn.herodotus.stirrup.grpc.user.*;
import cn.herodotus.stirrup.logic.upms.entity.security.SysUser;
import cn.herodotus.stirrup.logic.upms.service.security.SysUserService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.core.convert.converter.Converter;

/**
 * <p>Description: Herodotus GRPC User Service  </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/3/11 17:05
 */
@GrpcService
public class GrpcServerUserDetailsService extends GrpcHerodotusUserServiceGrpc.GrpcHerodotusUserServiceImplBase {

    private final SysUserService sysUserService;
    private final Converter<SysUser, GrpcHerodotusUser> toGrpcUser;
    private final Converter<GrpcAccessPrincipal, AccessPrincipal> toAccessPrincipal;
    private final Converter<HerodotusUser, GrpcHerodotusUser> toGrpcHerodotusUser;
    private final AbstractSocialAuthenticationHandler abstractSocialAuthenticationHandler;

    public GrpcServerUserDetailsService(SysUserService sysUserService, AbstractSocialAuthenticationHandler abstractSocialAuthenticationHandler) {
        this.sysUserService = sysUserService;
        this.abstractSocialAuthenticationHandler = abstractSocialAuthenticationHandler;
        this.toGrpcUser = new SysUserToGrpcHerodotusUserConverter();
        this.toAccessPrincipal = new GrpcToAccessPrincipalConverter();
        this.toGrpcHerodotusUser = new HerodotusUserToGrpcHerodotusUserConverter();
    }

    @Override
    public void findUserDetailsByUsername(SignInRequest request, StreamObserver<GrpcHerodotusUser> responseObserver) {
        SysUser sysUser = sysUserService.findByUsername(request.getUsername());
        responseObserver.onNext(toGrpcUser.convert(sysUser));
        responseObserver.onCompleted();
    }

    @Override
    public void findUserDetailsBySocial(SocialSignInRequest request, StreamObserver<GrpcHerodotusUser> responseObserver) {
        String source = request.getSource();
        GrpcAccessPrincipal grpcAccessPrincipal = request.getAccessPrincipal();
        AccessPrincipal accessPrincipal = toAccessPrincipal.convert(grpcAccessPrincipal);

        HerodotusUser herodotusUser = this.abstractSocialAuthenticationHandler.authentication(source, accessPrincipal);
        responseObserver.onNext(toGrpcHerodotusUser.convert(herodotusUser));
        responseObserver.onCompleted();
    }
}

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
import cn.herodotus.cloud.rpc.client.uaa.autoconfigure.grpc.converter.AccessPrincipalToGrpcConverter;
import cn.herodotus.cloud.rpc.client.uaa.autoconfigure.grpc.converter.GrpcToHerodotusUserConverter;
import cn.herodotus.stirrup.core.identity.domain.AccessPrincipal;
import cn.herodotus.stirrup.core.identity.domain.HerodotusUser;
import cn.herodotus.stirrup.core.identity.strategy.StrategyUserDetailsService;
import cn.herodotus.stirrup.grpc.user.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

/**
 * <p>Description: HerodotusUser GRPC 服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/3/19 11:41
 */
@Service
public class GrpcStrategyUserService implements StrategyUserDetailsService {

    @GrpcClient(ServiceNameConstants.SERVICE_NAME_UPMS)
    private GrpcHerodotusUserServiceGrpc.GrpcHerodotusUserServiceBlockingStub grpcHerodotusUserServiceBlockingStub;

    private final Converter<GrpcHerodotusUser, HerodotusUser> toHerodotusUser = new GrpcToHerodotusUserConverter();
    private final Converter<AccessPrincipal, GrpcAccessPrincipal> toGrpcAccessPrincipal = new AccessPrincipalToGrpcConverter();

    @Override
    public HerodotusUser findUserDetailsByUsername(String username) throws AuthenticationException {
        SignInRequest request = SignInRequest.newBuilder().setUsername(username).build();

        GrpcHerodotusUser grpcHerodotusUser = grpcHerodotusUserServiceBlockingStub.findUserDetailsByUsername(request);
        return toHerodotusUser.convert(grpcHerodotusUser);
    }

    @Override
    public HerodotusUser findUserDetailsBySocial(String source, AccessPrincipal accessPrincipal) throws AuthenticationException {
        SocialSignInRequest request = SocialSignInRequest.newBuilder()
                .setSource(source)
                .setAccessPrincipal(toGrpcAccessPrincipal.convert(accessPrincipal))
                .build();
        GrpcHerodotusUser grpcHerodotusUser = grpcHerodotusUserServiceBlockingStub.findUserDetailsBySocial(request);
        return toHerodotusUser.convert(grpcHerodotusUser);
    }
}

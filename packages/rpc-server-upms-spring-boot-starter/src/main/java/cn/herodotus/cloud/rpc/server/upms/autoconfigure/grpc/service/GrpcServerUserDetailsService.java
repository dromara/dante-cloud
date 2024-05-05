/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.apache.org/licenses/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6.若您的项目无法满足以上几点，可申请商业授权
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

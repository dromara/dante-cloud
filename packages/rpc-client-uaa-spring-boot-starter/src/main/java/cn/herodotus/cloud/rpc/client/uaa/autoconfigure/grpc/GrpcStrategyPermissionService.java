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

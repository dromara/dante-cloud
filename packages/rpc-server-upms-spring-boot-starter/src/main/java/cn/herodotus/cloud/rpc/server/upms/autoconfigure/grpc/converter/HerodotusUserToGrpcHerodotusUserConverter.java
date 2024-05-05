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

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

import cn.herodotus.stirrup.core.definition.constants.SymbolConstants;
import cn.herodotus.stirrup.grpc.user.GrpcHerodotusUser;
import cn.herodotus.stirrup.logic.upms.converter.AbstractSysUserConverter;
import cn.herodotus.stirrup.logic.upms.entity.security.SysPermission;
import cn.herodotus.stirrup.logic.upms.entity.security.SysRole;
import cn.herodotus.stirrup.logic.upms.entity.security.SysUser;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: SysUser 转 HerodotusGrpcUser 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/3/11 17:09
 */
public class SysUserToGrpcHerodotusUserConverter extends AbstractSysUserConverter<GrpcHerodotusUser> {
    @Override
    public GrpcHerodotusUser convert(SysUser source) {
        Set<String> authorities = new HashSet<>();
        Set<String> roles = new HashSet<>();

        for (SysRole sysRole : source.getRoles()) {
            roles.add(sysRole.getRoleCode());
            Set<SysPermission> sysPermissions = sysRole.getPermissions();
            if (CollectionUtils.isNotEmpty(sysPermissions)) {
                sysPermissions.forEach(sysAuthority -> authorities.add(sysAuthority.getPermissionCode()));
            }
        }

        String employeeId = ObjectUtils.isNotEmpty(source.getEmployee()) ? source.getEmployee().getEmployeeId() : "";

        GrpcHerodotusUser.Builder builder = GrpcHerodotusUser.newBuilder()
                .setUserId(source.getUserId())
                .setUsername(source.getUsername())
                .setPassword(source.getPassword())
                .setAvatar(valid(source.getAvatar()))
                .setEmployeeId(valid(employeeId))
                .setEnabled(isEnabled(source))
                .setAccountNonExpired(isAccountNonExpired(source))
                .setCredentialsNonExpired(isCredentialsNonExpired(source))
                .setAccountNonLocked(isAccountNonLocked(source))
                .addAllRoles(roles)
                .addAllAuthorities(authorities);

        return builder.build();
    }

    private String valid(String data) {
        if (StringUtils.isNotBlank(data)) {
            return data;
        } else {
            return SymbolConstants.BLANK;
        }
    }
}

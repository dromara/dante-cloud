/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
 * 4.分发源码时候，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dante.module.upms.logic.helper;


import cn.herodotus.dante.module.upms.logic.entity.system.SysAuthority;
import cn.herodotus.dante.module.upms.logic.entity.system.SysRole;
import cn.herodotus.dante.module.upms.logic.entity.system.SysUser;
import cn.herodotus.engine.data.core.enums.DataItemStatus;
import cn.herodotus.engine.oauth2.core.definition.domain.HerodotusGrantedAuthority;
import cn.herodotus.engine.oauth2.core.definition.domain.HerodotusUser;
import cn.herodotus.engine.oauth2.core.utils.SecurityUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: 实体转换帮助类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/18 11:38
 */
public class UpmsHelper {

    public static HerodotusUser convertSysUserToHerodotusUser(SysUser sysUser) {

        Set<HerodotusGrantedAuthority> authorities = new HashSet<>();

        Set<String> roles = new HashSet<>();
        for (SysRole sysRole : sysUser.getRoles()) {
            roles.add(sysRole.getRoleCode());
            authorities.add(new HerodotusGrantedAuthority(SecurityUtils.wellFormRolePrefix(sysRole.getRoleCode())));
            Set<SysAuthority> sysAuthorities = sysRole.getAuthorities();
            if (CollectionUtils.isNotEmpty(sysAuthorities)) {
                sysAuthorities.forEach(sysAuthority -> authorities.add(new HerodotusGrantedAuthority((sysAuthority.getAuthorityCode()))));
            }
        }

        String employeeId = ObjectUtils.isNotEmpty(sysUser.getEmployee()) ? sysUser.getEmployee().getEmployeeId() : null;

        return new HerodotusUser(sysUser.getUserId(), sysUser.getUserName(), sysUser.getPassword(),
                isEnabled(sysUser),
                isAccountNonExpired(sysUser),
                isCredentialsNonExpired(sysUser),
                isNonLocked(sysUser),
                authorities, roles, employeeId, sysUser.getAvatar());
    }

    private static boolean isEnabled(SysUser sysUser) {
        return sysUser.getStatus() != DataItemStatus.FORBIDDEN;
    }

    private static boolean isNonLocked(SysUser sysUser) {
        return !(sysUser.getStatus() == DataItemStatus.LOCKING);
    }

    private static boolean isNonExpired(LocalDateTime localDateTime) {
        if (ObjectUtils.isEmpty(localDateTime)) {
            return true;
        } else {
            return localDateTime.isAfter(LocalDateTime.now());
        }
    }

    private static boolean isAccountNonExpired(SysUser sysUser) {
        if (sysUser.getStatus() == DataItemStatus.EXPIRED) {
            return false;
        }

        return isNonExpired(sysUser.getAccountExpireAt());
    }

    private static boolean isCredentialsNonExpired(SysUser sysUser) {
        return isNonExpired(sysUser.getCredentialsExpireAt());
    }
}

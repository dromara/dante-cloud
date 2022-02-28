/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
 * Eurynome Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Eurynome Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/eurynome-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/eurynome-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.eurynome.module.upms.logic.helper;


import cn.herodotus.engine.data.core.enums.DataItemStatus;
import cn.herodotus.engine.security.core.definition.domain.HerodotusUser;
import cn.herodotus.engine.security.core.utils.SecurityUtils;
import cn.herodotus.engine.web.core.domain.RequestMapping;
import cn.herodotus.eurynome.module.upms.logic.entity.system.SysAuthority;
import cn.herodotus.eurynome.module.upms.logic.entity.system.SysRole;
import cn.herodotus.eurynome.module.upms.logic.entity.system.SysUser;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Description: 实体转换帮助类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/18 11:38
 */
public class UpmsHelper {

    public static HerodotusUser convertSysUserToHerodotusUserDetails(SysUser sysUser) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        for (SysRole sysRole : sysUser.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(SecurityUtils.wellFormRolePrefix(sysRole.getRoleCode())));
            Set<SysAuthority> sysAuthorities = sysRole.getAuthorities();
            if (CollectionUtils.isNotEmpty(sysAuthorities)) {
                sysAuthorities.forEach(sysAuthority -> authorities.add(new SimpleGrantedAuthority((sysAuthority.getAuthorityCode()))));
            }
        }

        return new HerodotusUser(sysUser.getUserId(), sysUser.getUserName(), sysUser.getPassword(),
                sysUser.getStatus() == DataItemStatus.ENABLE,
                sysUser.getStatus() != DataItemStatus.LOCKING,
                sysUser.getStatus() != DataItemStatus.EXPIRED,
                sysUser.getStatus() != DataItemStatus.EXPIRED, authorities);
    }

    public static List<SysAuthority> convertRequestMappingsToSysAuthorities(Collection<RequestMapping> requestMappings) {
        if (CollectionUtils.isNotEmpty(requestMappings)) {
            return requestMappings.stream().map(UpmsHelper::convertRequestMappingToSysAuthority).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private static SysAuthority convertRequestMappingToSysAuthority(RequestMapping requestMapping) {
        SysAuthority sysAuthority = new SysAuthority();
        sysAuthority.setAuthorityId(requestMapping.getMetadataId());
        sysAuthority.setAuthorityName(requestMapping.getMetadataName());
        sysAuthority.setAuthorityCode(requestMapping.getMetadataCode());
        sysAuthority.setRequestMethod(requestMapping.getRequestMethod());
        sysAuthority.setServiceId(requestMapping.getServiceId());
        sysAuthority.setUrl(requestMapping.getUrl());
        sysAuthority.setParentId(requestMapping.getParentId());
        sysAuthority.setClassName(requestMapping.getClassName());
        sysAuthority.setMethodName(requestMapping.getMethodName());
        return sysAuthority;
    }
}

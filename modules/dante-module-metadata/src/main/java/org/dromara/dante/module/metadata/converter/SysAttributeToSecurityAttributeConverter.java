/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Cloud 采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package org.dromara.dante.module.metadata.converter;

import cn.herodotus.engine.logic.upms.entity.security.SysAttribute;
import cn.herodotus.engine.logic.upms.entity.security.SysPermission;
import cn.herodotus.engine.oauth2.core.definition.domain.SecurityAttribute;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * <p>Description: SysAttribute 转 SecurityAttribute 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/9/15 0:29
 */
public class SysAttributeToSecurityAttributeConverter implements Converter<SysAttribute, SecurityAttribute> {
    @Override
    public SecurityAttribute convert(SysAttribute sysAttribute) {
        SecurityAttribute securityAttribute = new SecurityAttribute();
        securityAttribute.setAttributeId(sysAttribute.getAttributeId());
        securityAttribute.setAttributeCode(sysAttribute.getAttributeCode());
        securityAttribute.setWebExpression(sysAttribute.getWebExpression());
        securityAttribute.setPermissions(permissionToCommaDelimitedString(sysAttribute.getPermissions()));
        securityAttribute.setUrl(sysAttribute.getUrl());
        securityAttribute.setRequestMethod(sysAttribute.getRequestMethod());
        securityAttribute.setServiceId(sysAttribute.getServiceId());
        securityAttribute.setAttributeName(sysAttribute.getDescription());
        return securityAttribute;


    }

    private String permissionToCommaDelimitedString(Set<SysPermission> sysAuthorities) {
        if (CollectionUtils.isNotEmpty(sysAuthorities)) {
            List<String> codes = sysAuthorities.stream().map(SysPermission::getPermissionCode).toList();
            return StringUtils.collectionToCommaDelimitedString(codes);
        } else {
            return "";
        }
    }
}

/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 *    Author: ZHENGGENGWEI<码匠君>
 *    Contact: <herodotus@aliyun.com>
 *    Blog and source code availability: <https://gitee.com/herodotus/herodotus-cloud>
 */

package cn.herodotus.dante.module.metadata.converter;

import cn.herodotus.engine.oauth2.core.definition.domain.SecurityAttribute;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysAttribute;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysPermission;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * <p>Description: SysAttribute 转 SecurityAttribute 转换器</p>
 *
 * @author : gengwei.zheng
 * @date : 3.1.3.4
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

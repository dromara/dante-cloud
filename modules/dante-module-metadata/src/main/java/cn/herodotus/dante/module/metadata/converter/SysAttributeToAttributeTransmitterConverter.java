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

import cn.herodotus.engine.supplier.upms.logic.entity.security.SysAttribute;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysPermission;
import cn.herodotus.stirrup.oauth2.core.definition.domain.AttributeTransmitter;
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
public class SysAttributeToAttributeTransmitterConverter implements Converter<SysAttribute, AttributeTransmitter> {
    @Override
    public AttributeTransmitter convert(SysAttribute sysAttribute) {
        AttributeTransmitter transmitter = new AttributeTransmitter();
        transmitter.setAttributeId(sysAttribute.getAttributeId());
        transmitter.setAttributeCode(sysAttribute.getAttributeCode());
        transmitter.setWebExpression(sysAttribute.getWebExpression());
        transmitter.setPermissions(permissionToCommaDelimitedString(sysAttribute.getPermissions()));
        transmitter.setUrl(sysAttribute.getUrl());
        transmitter.setRequestMethod(sysAttribute.getRequestMethod());
        transmitter.setServiceId(sysAttribute.getServiceId());
        transmitter.setAttributeName(sysAttribute.getDescription());
        transmitter.setClassName(sysAttribute.getClassName());
        transmitter.setMethodName(sysAttribute.getMethodName());
        return transmitter;


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

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

package cn.herodotus.cloud.module.metadata.converter;

import cn.herodotus.stirrup.core.identity.domain.AttributeTransmitter;
import cn.herodotus.stirrup.logic.upms.entity.security.SysAttribute;
import cn.herodotus.stirrup.logic.upms.entity.security.SysPermission;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * <p>Description: SysAttribute 转 SecurityAttribute 转换器</p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/23 22:59
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

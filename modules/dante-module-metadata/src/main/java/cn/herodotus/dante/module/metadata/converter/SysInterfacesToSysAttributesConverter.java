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
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysInterface;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Description: List<SysInterface> 转 List<SysAttribute> 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/23 22:59
 */
public class SysInterfacesToSysAttributesConverter implements Converter<List<SysInterface>, List<SysAttribute>> {
    @Override
    public List<SysAttribute> convert(List<SysInterface> sysInterfaces) {
        if (CollectionUtils.isNotEmpty(sysInterfaces)) {
            return sysInterfaces.stream().map(this::convert).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private SysAttribute convert(SysInterface sysInterface) {
        SysAttribute sysAttribute = new SysAttribute();
        sysAttribute.setAttributeId(sysInterface.getInterfaceId());
        sysAttribute.setAttributeCode(sysInterface.getInterfaceCode());
        sysAttribute.setRequestMethod(sysInterface.getRequestMethod());
        sysAttribute.setServiceId(sysInterface.getServiceId());
        sysAttribute.setClassName(sysInterface.getClassName());
        sysAttribute.setMethodName(sysInterface.getMethodName());
        sysAttribute.setUrl(sysInterface.getUrl());
        sysAttribute.setStatus(sysInterface.getStatus());
        sysAttribute.setReserved(sysInterface.getReserved());
        sysAttribute.setDescription(sysInterface.getDescription());
        sysAttribute.setReversion(sysInterface.getReversion());
        sysAttribute.setCreateTime(sysInterface.getCreateTime());
        sysAttribute.setUpdateTime(sysInterface.getUpdateTime());
        sysAttribute.setRanking(sysInterface.getRanking());
        return sysAttribute;
    }

}

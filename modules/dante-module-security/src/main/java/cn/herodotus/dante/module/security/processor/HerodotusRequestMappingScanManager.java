/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2019-2022 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.dante.module.security.processor;

import cn.herodotus.dante.module.common.ServiceNameConstants;
import cn.herodotus.engine.event.core.local.LocalRequestMappingGatherEvent;
import cn.herodotus.engine.event.security.remote.RemoteRequestMappingGatherEvent;
import cn.herodotus.engine.oauth2.authorization.processor.SecurityMetadataSourceAnalyzer;
import cn.herodotus.engine.web.core.context.ServiceContext;
import cn.herodotus.engine.web.core.definition.RequestMappingScanManager;
import cn.herodotus.engine.web.core.domain.RequestMapping;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * <p>Description: 自定义 RequestMappingScanManager</p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/17 0:08
 */
@Component
public class HerodotusRequestMappingScanManager implements RequestMappingScanManager{

    private final SecurityMetadataSourceAnalyzer securityMetadataSourceAnalyzer;

    public HerodotusRequestMappingScanManager(SecurityMetadataSourceAnalyzer securityMetadataSourceAnalyzer) {
        this.securityMetadataSourceAnalyzer = securityMetadataSourceAnalyzer;
    }

    @Override
    public Class<? extends Annotation> getScanAnnotationClass() {
        return EnableWebSecurity.class;
    }

    @Override
    public String getDestinationServiceName() {
        return ServiceNameConstants.SERVICE_NAME_UPMS;
    }

    @Override
    public void postLocalStorage(List<RequestMapping> requestMappings) {
        securityMetadataSourceAnalyzer.processSecurityRequestMapping();
    }

    @Override
    public void postLocalProcess(List<RequestMapping> data) {
        ServiceContext.getInstance().publishEvent(new LocalRequestMappingGatherEvent(data));
    }

    @Override
    public void postRemoteProcess(String data, String originService, String destinationService) {
        ServiceContext.getInstance().publishEvent(new RemoteRequestMappingGatherEvent(data, originService, destinationService));
    }
}

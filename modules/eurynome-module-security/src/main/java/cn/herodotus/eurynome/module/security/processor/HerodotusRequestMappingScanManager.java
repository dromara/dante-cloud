/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2019-2022 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.eurynome.module.security.processor;

import cn.herodotus.engine.event.core.local.LocalRequestMappingGatherEvent;
import cn.herodotus.engine.event.security.remote.RemoteRequestMappingGatherEvent;
import cn.herodotus.engine.web.core.context.ServiceContext;
import cn.herodotus.engine.web.core.definition.RequestMappingScanManager;
import cn.herodotus.engine.web.core.domain.RequestMapping;
import cn.herodotus.eurynome.module.common.ServiceNameConstants;
import org.springframework.cloud.bus.event.PathDestinationFactory;
import org.springframework.context.ApplicationEvent;
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

    @Override
    public Class<? extends Annotation> getScanAnnotationClass() {
        return EnableWebSecurity.class;
    }

    @Override
    public boolean isDistributedArchitecture() {
        return ServiceContext.getInstance().isDistributedArchitecture();
    }

    @Override
    public String getDestinationServiceName() {
        return ServiceNameConstants.SERVICE_NAME_UPMS;
    }

    @Override
    public void postLocalStorage(List<RequestMapping> requestMappings) {

    }

    @Override
    public ApplicationEvent createLocalGatherEvent(List<RequestMapping> requestMappings) {
        return new LocalRequestMappingGatherEvent(requestMappings);
    }

    @Override
    public ApplicationEvent createRemoteGatherEvent(String source, String originService, String destinationService) {
        return new RemoteRequestMappingGatherEvent(source, originService, new PathDestinationFactory().getDestination(destinationService));
    }
}

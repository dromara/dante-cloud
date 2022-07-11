/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.eurynome.authentication.processor;

import cn.herodotus.engine.event.core.local.LocalChangeUserStatusEvent;
import cn.herodotus.engine.event.security.remote.RemoteChangeUserStatusEvent;
import cn.herodotus.engine.oauth2.compliance.definition.AccountStatusChangeService;
import cn.herodotus.engine.web.core.context.ServiceContext;
import cn.herodotus.engine.web.core.domain.UserStatus;
import cn.herodotus.eurynome.module.common.ServiceNameConstants;

/**
 * <p>Description: 用户状态变更处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/10 17:25
 */
public class HerodotusAccountStatusChangeService implements AccountStatusChangeService {
    @Override
    public String getDestinationServiceName() {
        return ServiceNameConstants.SERVICE_NAME_UPMS;
    }

    @Override
    public void postLocalProcess(UserStatus data) {
        ServiceContext.getInstance().publishEvent(new LocalChangeUserStatusEvent(data));
    }

    @Override
    public void postRemoteProcess(String data, String originService, String destinationService) {
        ServiceContext.getInstance().publishEvent(new RemoteChangeUserStatusEvent(data, originService, destinationService));
    }
}

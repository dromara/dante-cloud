/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.eurynome.module.upms.rest.listener;

import cn.herodotus.engine.assistant.core.json.jackson2.utils.JacksonUtils;
import cn.herodotus.engine.data.core.enums.DataItemStatus;
import cn.herodotus.engine.event.security.remote.RemoteChangeUserStatusEvent;
import cn.herodotus.engine.web.core.domain.UserStatus;
import cn.herodotus.eurynome.module.upms.logic.service.system.SysUserService;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * <p>Description: 远程用户状态变更监听 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/10 18:31
 */
@Component
public class RemoteChangeUserStatusListener implements ApplicationListener<RemoteChangeUserStatusEvent> {

    private static final Logger log = LoggerFactory.getLogger(RemoteChangeUserStatusListener.class);

    private final SysUserService sysUserService;

    @Autowired
    public RemoteChangeUserStatusListener(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    public void onApplicationEvent(RemoteChangeUserStatusEvent event) {

        log.info("[Herodotus] |- Request mapping gather REMOTE listener, response event!");

        String data = event.getData();
        log.debug("[Herodotus] |- Fetch data [{}]", data);
        if (ObjectUtils.isNotEmpty(data)) {
            UserStatus userStatus = JacksonUtils.toObject(data, UserStatus.class);
            if (ObjectUtils.isNotEmpty(userStatus)) {
                DataItemStatus dataItemStatus = DataItemStatus.valueOf(userStatus.getStatus());
                if (ObjectUtils.isNotEmpty(dataItemStatus)) {
                    sysUserService.changeStatus(userStatus.getUserId(), dataItemStatus);
                }
            }
        }
    }
}

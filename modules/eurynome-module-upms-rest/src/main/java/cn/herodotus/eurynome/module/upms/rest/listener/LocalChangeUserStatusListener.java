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

import cn.herodotus.engine.data.core.enums.DataItemStatus;
import cn.herodotus.engine.event.core.local.LocalChangeUserStatusEvent;
import cn.herodotus.engine.web.core.domain.UserStatus;
import cn.herodotus.eurynome.module.upms.logic.service.system.SysUserService;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * <p>Description: 本地用户状态变更监听 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/10 17:59
 */
@Component
public class LocalChangeUserStatusListener implements ApplicationListener<LocalChangeUserStatusEvent> {

    private static final Logger log = LoggerFactory.getLogger(LocalChangeUserStatusListener.class);
    private final SysUserService sysUserService;

    public LocalChangeUserStatusListener(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    public void onApplicationEvent(LocalChangeUserStatusEvent event) {

        log.info("[Herodotus] |- Change user status gather LOCAL listener, response event!");

        UserStatus userStatus = event.getData();
        if (ObjectUtils.isNotEmpty(userStatus)) {
            DataItemStatus dataItemStatus = DataItemStatus.valueOf(userStatus.getStatus());
            if (ObjectUtils.isNotEmpty(dataItemStatus)) {
                sysUserService.changeStatus(userStatus.getUserId(), dataItemStatus);
            }
        }
    }
}

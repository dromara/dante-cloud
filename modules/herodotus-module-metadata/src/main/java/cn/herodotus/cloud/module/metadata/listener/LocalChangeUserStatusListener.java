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

package cn.herodotus.cloud.module.metadata.listener;

import cn.herodotus.stirrup.logic.upms.service.security.SysUserService;
import cn.herodotus.stirrup.data.core.enums.DataItemStatus;
import cn.herodotus.stirrup.message.ability.domain.UserStatus;
import cn.herodotus.stirrup.message.ability.event.ChangeUserStatusEvent;
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
public class LocalChangeUserStatusListener implements ApplicationListener<ChangeUserStatusEvent> {

    private static final Logger log = LoggerFactory.getLogger(LocalChangeUserStatusListener.class);
    private final SysUserService sysUserService;

    public LocalChangeUserStatusListener(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    public void onApplicationEvent(ChangeUserStatusEvent event) {

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

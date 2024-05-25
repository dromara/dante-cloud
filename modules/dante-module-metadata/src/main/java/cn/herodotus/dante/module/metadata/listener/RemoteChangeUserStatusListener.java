/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Dante Cloud.
 *
 * Dante Cloud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dante Cloud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.cn>.
 */

package cn.herodotus.dante.module.metadata.listener;

import cn.herodotus.engine.assistant.core.json.jackson2.utils.Jackson2Utils;
import cn.herodotus.engine.data.core.enums.DataItemStatus;
import cn.herodotus.engine.message.core.logic.domain.UserStatus;
import cn.herodotus.engine.oauth2.resource.autoconfigure.bus.RemoteChangeUserStatusEvent;
import cn.herodotus.engine.supplier.upms.logic.service.security.SysUserService;
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
            UserStatus userStatus = Jackson2Utils.toObject(data, UserStatus.class);
            if (ObjectUtils.isNotEmpty(userStatus)) {
                DataItemStatus dataItemStatus = DataItemStatus.valueOf(userStatus.getStatus());
                if (ObjectUtils.isNotEmpty(dataItemStatus)) {
                    sysUserService.changeStatus(userStatus.getUserId(), dataItemStatus);
                }
            }
        }
    }
}

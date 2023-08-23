/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dante.module.metadata.listener;

import cn.herodotus.engine.assistant.core.json.jackson2.utils.Jackson2Utils;
import cn.herodotus.engine.data.core.enums.DataItemStatus;
import cn.herodotus.engine.message.core.domain.UserStatus;
import cn.herodotus.engine.message.security.event.RemoteChangeUserStatusEvent;
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

/*
 * Copyright (c) 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-localstorage
 * File Name: SecurityMetadataSourceChangeRemoteListener.java
 * Author: gengwei.zheng
 * Date: 2020/5/28 下午1:07
 * LastModified: 2020/5/28 下午1:07
 */

package cn.herodotus.eurynome.message.bus.listener;

import cn.herodotus.eurynome.message.bus.event.SecurityMetadataSourceChangeRemoteEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: SecurityMetadataSourceChangeRemoteListener </p>
 *
 * <p>Description: 消息总线本地 SecurityMetadataPublisher 变更监听</p>
 * <p>
 * 思路变化，暂时用不到。代码先保留以备后用。
 *
 * @author : gengwei.zheng
 * @date : 2020/5/28 13:07
 */
@Slf4j
@Component
public class SecurityMetadataSourceChangeRemoteListener implements ApplicationListener<SecurityMetadataSourceChangeRemoteEvent> {

    @Override
    public void onApplicationEvent(SecurityMetadataSourceChangeRemoteEvent event) {

        log.debug("[Eurynome] |- Receive SecurityMetadataSourceChangeRemoteEvent from [{}] for operation [{}]", event.getOriginService(), event.getAction());
    }
}

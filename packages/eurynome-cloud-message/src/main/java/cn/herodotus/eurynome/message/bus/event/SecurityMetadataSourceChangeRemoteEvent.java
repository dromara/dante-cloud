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
 * Module Name: eurynome-cloud-message
 * File Name: InterfaceChangeRemoteEvent.java
 * Author: gengwei.zheng
 * Date: 2020/5/26 下午2:34
 * LastModified: 2020/5/26 下午2:34
 */

package cn.herodotus.eurynome.message.bus.event;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: SecurityMetadataSourceChangeRemoteEvent </p>
 *
 * <p>Description: Security Metadata Source 变更远程事件 </p>
 *
 * 思路变化，暂时用不到。代码先保留以备后用。
 *
 * @author : gengwei.zheng
 * @date : 2020/5/26 14:34
 */
public class SecurityMetadataSourceChangeRemoteEvent extends RemoteApplicationEvent {

    private String action;

    @SuppressWarnings("unused")
    public SecurityMetadataSourceChangeRemoteEvent() {
        // for serializers
        // 不加这个构造函数，Jackson序列化会出错。
    }

    public SecurityMetadataSourceChangeRemoteEvent(Object source, String originService, String destinationService, String action) {
        super(source, originService, destinationService);
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}

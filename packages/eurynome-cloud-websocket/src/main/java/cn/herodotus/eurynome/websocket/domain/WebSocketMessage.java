/*
 * Copyright (c) 2019-2021 Gengwei Zheng (herodotus@aliyun.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-websocket
 * File Name: WebSocketMessage.java
 * Author: gengwei.zheng
 * Date: 2021/10/24 21:42:24
 */

package cn.herodotus.eurynome.websocket.domain;

import java.io.Serializable;

/**
 * <p>Description: WebSocket发送消息参数实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/24 21:42
 */
public class WebSocketMessage<T> implements Serializable {

    private String to;

    private String channel;

    private T payload;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}

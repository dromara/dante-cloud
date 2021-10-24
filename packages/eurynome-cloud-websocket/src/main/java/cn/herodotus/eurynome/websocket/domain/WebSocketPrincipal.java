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
 * File Name: WebSocketPrincipal.java
 * Author: gengwei.zheng
 * Date: 2021/10/24 21:43:24
 */

package cn.herodotus.eurynome.websocket.domain;

import com.google.common.base.MoreObjects;

import java.security.Principal;

/**
 * <p>Description: Websocket登录连接对象 </p>
 * <p>
 * 用于保存websocket连接过程中需要存储的业务参数
 *
 * @author : gengwei.zheng
 * @date : 2021/10/24 21:43
 */
public class WebSocketPrincipal implements Principal {

    private String token;

    public WebSocketPrincipal(String token) {
        this.token = token;
    }

    @Override
    public String getName() {
        return this.token;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("token", token)
                .toString();
    }
}
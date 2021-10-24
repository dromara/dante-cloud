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
 * File Name: WebSocketHandshakeHandler.java
 * Author: gengwei.zheng
 * Date: 2021/10/24 21:46:24
 */

package cn.herodotus.eurynome.websocket.interceptor;

import cn.herodotus.eurynome.assistant.constant.HttpHeaders;
import cn.herodotus.eurynome.websocket.domain.WebSocketPrincipal;
import cn.herodotus.eurynome.websocket.properties.WebSocketProperties;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

/**
 * <p>Description: 设置认证用户信息 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/24 21:46
 */
public class WebSocketHandshakeHandler extends DefaultHandshakeHandler {

    private static final Logger log = LoggerFactory.getLogger(WebSocketHandshakeHandler.class);

    private WebSocketProperties webSocketProperties;

    public void setWebSocketProperties(WebSocketProperties webSocketProperties) {
        this.webSocketProperties = webSocketProperties;
    }

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {

        Principal principal = request.getPrincipal();
        if (ObjectUtils.isNotEmpty(principal)) {
            log.debug("[Herodotus] |- Get user principal from request, value is  [{}].", principal.getName());
            return principal;
        }

        Object user = null;
        HttpServletRequest httpServletRequest = getHttpServletRequest(request);
        if (ObjectUtils.isNotEmpty(httpServletRequest)) {
            user = httpServletRequest.getAttribute(webSocketProperties.getPrincipalAttribute());
            if (ObjectUtils.isEmpty(user)) {
                user = httpServletRequest.getParameter(webSocketProperties.getPrincipalAttribute());
                if (ObjectUtils.isEmpty(user)) {
                    user = httpServletRequest.getHeader(HttpHeaders.X_HERODOTUS_SESSION);
                } else {
                    log.debug("[Herodotus] |- Get user principal [{}] from request parameter, use parameter  [{}]..", user, webSocketProperties.getPrincipalAttribute());
                }
            } else {
                log.debug("[Herodotus] |- Get user principal [{}] from request attribute, use attribute  [{}]..", user, webSocketProperties.getPrincipalAttribute());
            }
        }

        if (ObjectUtils.isEmpty(user)) {
            HttpSession httpSession = getSession(request);
            if (ObjectUtils.isNotEmpty(httpSession)) {
                user = httpSession.getAttribute(webSocketProperties.getPrincipalAttribute());
                if (ObjectUtils.isEmpty(user)) {
                    user = httpSession.getId();
                } else {
                    log.debug("[Herodotus] |- Get user principal [{}] from httpsession, use attribute  [{}].", user, webSocketProperties.getPrincipalAttribute());
                }
            } else {
                log.error("[Herodotus] |- Cannot find session from websocket request.");
                return null;
            }
        } else {
            log.debug("[Herodotus] |- Get user principal [{}] from request header X_HERODOTUS_SESSION.", user);
        }

        return new WebSocketPrincipal((String) user);
    }

    private HttpServletRequest getHttpServletRequest(ServerHttpRequest request) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
            return serverRequest.getServletRequest();
        }

        return null;
    }

    private HttpSession getSession(ServerHttpRequest request) {
        HttpServletRequest httpServletRequest = getHttpServletRequest(request);
        if (ObjectUtils.isNotEmpty(httpServletRequest)) {
            return httpServletRequest.getSession(false);
        }
        return null;
    }
}

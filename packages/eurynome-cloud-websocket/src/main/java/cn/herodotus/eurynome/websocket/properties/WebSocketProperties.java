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
 * File Name: WebSocketProperties.java
 * Author: gengwei.zheng
 * Date: 2021/10/24 21:40:24
 */

package cn.herodotus.eurynome.websocket.properties;

import cn.herodotus.eurynome.assistant.constant.PropertyConstants;
import cn.herodotus.eurynome.assistant.constant.SymbolConstants;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Description: Web Socket 配置参数 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/24 21:40
 */
@ConfigurationProperties(prefix = PropertyConstants.PROPERTY_HERODOTUS_WEBSOCKET)
public class WebSocketProperties {

    /**
     * 客户端尝试连接端点
     */
    private String endpoint = "stomp/websocketjs";
    /**
     * WebSocket 广播消息代理地址
     */
    private String broadcast = "topic";

    /**
     * WebSocket 点对点消息代理地址
     */
    private String peerToPeer = "private";

    /**
     * 全局使用的消息前缀
     */
    private List<String> applicationDestinationPrefixes;

    /**
     * 点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
     */
    private String userDestinationPrefix;

    /**
     * 集群模式下，信息同步消息队列Topic
     */
    private String topic = "ws";

    /**
     * 请求中传递的用户身份标识属性名
     */
    private String principalAttribute = "openid";

    private String format(String endpoint) {
        if (StringUtils.isNotBlank(endpoint) && !StringUtils.startsWith(endpoint, SymbolConstants.FORWARD_SLASH)) {
            return SymbolConstants.FORWARD_SLASH + endpoint;
        } else {
            return endpoint;
        }
    }

    public String getEndpoint() {
        return format(endpoint);
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBroadcast() {
        return format(broadcast);
    }

    public void setBroadcast(String broadcast) {
        this.broadcast = broadcast;
    }

    public String getPeerToPeer() {
        return format(peerToPeer);
    }

    public void setPeerToPeer(String peerToPeer) {
        this.peerToPeer = peerToPeer;
    }

    public List<String> getApplicationDestinationPrefixes() {
        return applicationDestinationPrefixes;
    }

    public void setApplicationDestinationPrefixes(List<String> applicationDestinationPrefixes) {
        this.applicationDestinationPrefixes = applicationDestinationPrefixes;
    }

    public String[] getApplicationPrefixes() {
        List<String> prefixes = this.getApplicationDestinationPrefixes();
        if (CollectionUtils.isNotEmpty(prefixes)) {
            List<String> wellFormed = prefixes.stream().map(this::format).collect(Collectors.toList());
            String[] result = new String[wellFormed.size()];
            return wellFormed.toArray(result);
        } else {
            return new String[]{};
        }
    }

    public String getUserDestinationPrefix() {
        return format(userDestinationPrefix);
    }

    public void setUserDestinationPrefix(String userDestinationPrefix) {
        this.userDestinationPrefix = userDestinationPrefix;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPrincipalAttribute() {
        return principalAttribute;
    }

    public void setPrincipalAttribute(String principalAttribute) {
        this.principalAttribute = principalAttribute;
    }
}


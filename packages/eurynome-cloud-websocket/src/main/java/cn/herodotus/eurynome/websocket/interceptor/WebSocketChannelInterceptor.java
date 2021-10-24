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
 * File Name: WebSocketChannelInterceptor.java
 * Author: gengwei.zheng
 * Date: 2021/10/24 21:47:24
 */

package cn.herodotus.eurynome.websocket.interceptor;

import cn.herodotus.eurynome.websocket.domain.WebSocketPrincipal;
import cn.herodotus.eurynome.websocket.properties.WebSocketProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import javax.servlet.http.HttpSession;

/**
 * <p>Description: Websocket消息监听 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/24 21:47
 */
public class WebSocketChannelInterceptor implements ChannelInterceptor {

    private static final Logger log = LoggerFactory.getLogger(WebSocketChannelInterceptor.class);

    private WebSocketProperties webSocketProperties;

    public void setWebSocketProperties(WebSocketProperties webSocketProperties) {
        this.webSocketProperties = webSocketProperties;
    }

    /**
     * 在消息发送之前调用，方法中可以对消息进行修改，如果此方法返回值为空，则不会发生实际的消息发送调用
     *
     * @param message {@link Message}
     * @param channel {@link MessageChannel}
     * @return {@link Message}
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        /*
         * 1. 判断是否为首次连接请求，如果已经连接过，直接返回message
         * 2. 网上有种写法是在这里封装认证用户的信息，本文是在http阶段，websockt 之前就做了认证的封装，所以这里直接取的信息
         */

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            /*
             * 1. 这里获取就是JS stompClient.connect(headers, function (frame){.......}) 中header的信息
             * 2. JS中header可以封装多个参数，格式是{key1:value1,key2:value2}
             * 3. header参数的key可以一样，取出来就是list
             * 4. 样例代码header中只有一个token，所以直接取0位
             */
            String token = accessor.getNativeHeader(webSocketProperties.getPrincipalAttribute()).get(0);

            /*
             * 1. 这里直接封装到StompHeaderAccessor 中，可以根据自身业务进行改变
             * 2. 封装大搜StompHeaderAccessor中后，可以在@Controller / @MessageMapping注解的方法中直接带上StompHeaderAccessor
             *    就可以通过方法提供的 getUser()方法获取到这里封装user对象
             * 2. 例如可以在这里拿到前端的信息进行登录鉴权
             */
            WebSocketPrincipal principal = (WebSocketPrincipal) accessor.getUser();
            log.debug("[Herodotus] |- Authentication user [{}], Token from frontend is [{}].", principal, token);
        }

        return message;
    }

    /**
     * 在消息发送后立刻调用
     *
     * @param message {@link Message}
     * @param channel {@link MessageChannel}
     * @param sent    boolean值参数表示该调用的返回值
     */
    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        /*
         * 拿到消息头对象后，我们可以做一系列业务操作
         * 1. 通过getSessionAttributes()方法获取到websocketSession，
         *    就可以取到我们在WebSocketHandshakeInterceptor拦截器中存在session中的信息
         * 2. 我们也可以获取到当前连接的状态，做一些统计，例如统计在线人数，或者缓存在线人数对应的令牌，方便后续业务调用
         */
        HttpSession httpSession = (HttpSession) accessor.getSessionAttributes().get("HTTP_SESSION");

    }

    /**
     * 1. 在消息发送完成后调用，而不管消息发送是否产生异常，在此方法中，我们可以做一些资源释放清理的工作
     * 2. 此方法的触发必须是preSend方法执行成功，且返回值不为null,发生了实际的消息推送，才会触发
     *
     * @param message {@link Message}
     * @param channel {@link MessageChannel}
     * @param sent    boolean值参数表示该调用的返回值
     * @param ex      失败时抛出的 Exception
     */
    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {

    }

    /**
     * 在消息被实际检索之前调用, 只适用于(PollableChannels, 轮询场景)，在websocket的场景中用不到
     *
     * @param channel channel {@link MessageChannel}
     * @return 如果返回false, 则不会对检索任何消息
     */
    @Override
    public boolean preReceive(MessageChannel channel) {
        return false;
    }

    /**
     * 在检索到消息之后，返回调用方之前调用，可以进行信息修改。适用于PollableChannels，轮询场景
     *
     * @param message {@link Message}
     * @param channel {@link MessageChannel}
     * @return 如果返回null, 就不会进行下一步操作
     */
    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        return message;
    }

    /**
     * 1. 在消息接收完成之后调用，不管发生什么异常，可以用于消息发送后的资源清理
     * 2. 只有当preReceive 执行成功，并返回true才会调用此方法
     * 3. 适用于PollableChannels，轮询场景
     *
     * @param message {@link Message}
     * @param channel {@link MessageChannel}
     * @param ex      失败时抛出的 Exception
     */
    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {

    }
}
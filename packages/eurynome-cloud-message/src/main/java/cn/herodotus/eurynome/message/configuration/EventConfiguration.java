/*
 * Copyright (c) 2019-2022 Gengwei Zheng (herodotus@aliyun.com)
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
 * Module Name: eurynome-cloud-message
 * File Name: EventConfiguration.java
 * Author: gengwei.zheng
 * Date: 2022/01/11 14:51:11
 */

package cn.herodotus.eurynome.message.configuration;

import cn.herodotus.eurynome.assistant.annotation.ConditionalOnDistributedArchitecture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * <p>Description: 平台事件配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/11 14:51
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDistributedArchitecture
@RemoteApplicationEventScan({
        "cn.herodotus.eurynome.message.event"
})
public class EventConfiguration {

    private static final Logger log = LoggerFactory.getLogger(EventConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Plugin [Herodotus Kafka] Auto Configure.");
    }
}


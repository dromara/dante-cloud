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
 * File Name: BusConfiguration.java
 * Author: gengwei.zheng
 * Date: 2020/5/28 下午12:13
 * LastModified: 2020/5/28 下午12:13
 */

package cn.herodotus.eurynome.message.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: BusConfiguration </p>
 *
 * <p>Description: 消息总线配置 </p>
 * <p>
 * 思路变化，暂时用不到。代码先保留以备后用。
 *
 * @author : gengwei.zheng
 * @date : 2020/5/28 12:13
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@RemoteApplicationEventScan(basePackages = {
        "cn.herodotus.eurynome.message.bus.event"
})
public class MessageBusConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Eurynome] |- Plugin [Herodotus Message Bus] Auto Configure.");
    }
}

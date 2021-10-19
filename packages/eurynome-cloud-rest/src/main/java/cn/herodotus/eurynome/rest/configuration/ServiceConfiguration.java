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
 * Module Name: eurynome-cloud-rest
 * File Name: ServiceConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/08/26 20:40:26
 */

package cn.herodotus.eurynome.rest.configuration;

import cn.herodotus.eurynome.assistant.utils.EnvUtils;
import cn.herodotus.eurynome.rest.properties.ServiceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * <p>Description: 服务信息配置类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/6/13 17:02
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ServiceProperties.class)
public class ServiceConfiguration {

    /**
     * 当前服务的端口
     */
    @Value("${server.port}")
    private int serverPort;

    /**
     * 当前服务的名称
     */
    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private ServiceProperties serviceProperties;

    @PostConstruct
    public void postConstruct() {
        serviceProperties.setPort(serverPort);
        serviceProperties.setIp(EnvUtils.getHostAddress());
        serviceProperties.setApplicationName(applicationName);
        log.debug("[Herodotus] |- Plugin [Herodotus Service] Auto Configure.");
    }
}

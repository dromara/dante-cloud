/*
 * Copyright 2019-2019 the original author or authors.
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
 * Project Name: luban-cloud
 * Module Name: luban-cloud-component-security
 * File Name: BanWebServerFactoryCustomizer.java
 * Author: gengwei.zheng
 * Date: 2019/11/18 下午2:41
 * LastModified: 2019/11/17 下午8:55
 */

package cn.herodotus.eurynome.rest.configuration;

import io.undertow.server.DefaultByteBufferPool;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <p>Description: Undertow 配置解决 启动的一个WARN问题 </p>
 * 
 * @author : gengwei.zheng
 * @date : 2019/11/17 16:07
 */
@Slf4j
@Component
public class UndertowWebServerFactoryCustomizer implements WebServerFactoryCustomizer<UndertowServletWebServerFactory> {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Bean [Undertow Websockets] Auto Configure.");
    }

    @Override
    public void customize(UndertowServletWebServerFactory factory) {

        factory.addDeploymentInfoCustomizers(deploymentInfo -> {
            WebSocketDeploymentInfo webSocketDeploymentInfo = new WebSocketDeploymentInfo();
            webSocketDeploymentInfo.setBuffers(new DefaultByteBufferPool(false, 1024));
            deploymentInfo.addServletContextAttribute(WebSocketDeploymentInfo.ATTRIBUTE_NAME, webSocketDeploymentInfo);
        });
    }
}

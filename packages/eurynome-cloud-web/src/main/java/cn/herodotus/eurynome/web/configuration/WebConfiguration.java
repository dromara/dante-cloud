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
 * Module Name: eurynome-cloud-web
 * File Name: WebConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/11/13 23:06:13
 */

package cn.herodotus.eurynome.web.configuration;

import cn.herodotus.eurynome.web.properties.PlatformProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

/**
 * <p>Description: Web 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/11/13 23:06
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(PlatformProperties.class)
@Import({
        JacksonConfiguration.class,
        RestTemplateConfiguration.class,
        ServiceConfiguration.class,
        OpenApiConfiguration.class,
        UndertowWebServerFactoryCustomizer.class,
})
public class WebConfiguration {

    private static final Logger log = LoggerFactory.getLogger(WebConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Component [Herodotus Web] Auto Configure.");
    }
}

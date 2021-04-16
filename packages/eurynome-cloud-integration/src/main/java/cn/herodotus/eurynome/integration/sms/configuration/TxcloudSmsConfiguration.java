/*
 * Copyright (c) 2019-2021 the original author or authors.
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
 * Module Name: eurynome-cloud-integration
 * File Name: TxcloudSmsConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/4/17 上午5:22
 * LastModified: 2021/4/17 上午5:22
 */

package cn.herodotus.eurynome.integration.sms.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

/**
 * <p>File: TxcloudSmsConfiguration </p>
 *
 * <p>Description: 腾讯短信服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/17 5:22
 */
@Slf4j
@EnableConfigurationProperties(TxcloudSmsConfiguration.class)
@ComponentScan(basePackages = {
        "cn.herodotus.eurynome.integration.sms.service.txcloud"
})
public class TxcloudSmsConfiguration {

    @PostConstruct
    public void init() {
        log.info("[Eurynome] |- Bean [Tencent Cloud Sms] Auto Configure.");
    }
}

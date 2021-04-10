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
 * Module Name: eurynome-cloud-integration-rest
 * File Name: WxappRestConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/4/10 下午4:48
 * LastModified: 2021/4/10 下午4:48
 */

package cn.herodotus.eurynome.integration.rest.social.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

/**
 * <p>File: WxappRestConfiguration </p>
 *
 * <p>Description: 微信小程序Rest配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/10 16:48
 */
@Slf4j
@ComponentScan(basePackages = {
        "cn.herodotus.eurynome.integration.rest.social.logic.wxapp",
        "cn.herodotus.eurynome.integration.rest.social.controller.wxapp"
})
public class WxappRestConfiguration {

    @PostConstruct
    public void init() {
        log.info("[Eurynome] |- Bean [Wexin Mini App REST] Auto Configure.");
    }
}

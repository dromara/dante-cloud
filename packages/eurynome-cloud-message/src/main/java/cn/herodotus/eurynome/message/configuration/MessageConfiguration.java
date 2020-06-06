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
 * File Name: MessageConfiguration.java
 * Author: gengwei.zheng
 * Date: 2020/5/31 下午4:08
 * LastModified: 2020/5/31 下午4:08
 */

package cn.herodotus.eurynome.message.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: MessageConfiguration </p>
 *
 * <p>Description: MessageConfiguration </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/31 16:08
 */
@Slf4j
@Configuration
public class MessageConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Components [Herodotus Message] Auto Configure.");
    }
}

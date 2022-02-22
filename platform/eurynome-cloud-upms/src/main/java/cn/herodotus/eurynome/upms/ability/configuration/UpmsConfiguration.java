/*
 * Copyright (c) 2019-2021 Gengwei Zheng(herodotus@aliyun.com)
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
 * Module Name: eurynome-cloud-upms-ability
 * File Name: UpmsAbilityConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/05/13 11:51:13
 */

package cn.herodotus.eurynome.upms.ability.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: UpmsAbilityConfiguration </p>
 *
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/1/19 15:24
 */
@Slf4j
@Configuration
public class UpmsConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Service [Upms Ability] Auto Configure.");
    }
}

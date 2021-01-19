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
 * Module Name: eurynome-cloud-upms-ability
 * File Name: UpmsAbilityConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/1/19 下午3:24
 * LastModified: 2021/1/19 下午3:24
 */

package cn.herodotus.eurynome.upms.ability.configuration;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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
@ComponentScan(basePackages = {
        "cn.herodotus.eurynome.upms.ability.service.oauth",
        "cn.herodotus.eurynome.upms.ability.controller.oauth"
})
@EnableJpaRepositories(basePackages = {
        "cn.herodotus.eurynome.upms.ability.repository.oauth"
})
@EnableMethodCache(basePackages = {
        "cn.herodotus.eurynome.upms.ability.service.oauth"
})
public class UpmsAbilityConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.info("[Eurynome] |- Service [Upms Ability] Auto Configure.");
    }
}

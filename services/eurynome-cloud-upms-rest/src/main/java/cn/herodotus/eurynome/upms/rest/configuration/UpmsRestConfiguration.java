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
 * Module Name: eurynome-cloud-upms-rest
 * File Name: UpmsConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/1/5 上午11:58
 * LastModified: 2021/1/5 上午11:58
 */

package cn.herodotus.eurynome.upms.rest.configuration;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: UpmsConfiguration </p>
 *
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/1/5 11:58
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {
        "cn.herodotus.eurynome.upms.rest.configuration",
        "cn.herodotus.eurynome.upms.rest.service",
        "cn.herodotus.eurynome.upms.rest.service.oauth",
        "cn.herodotus.eurynome.upms.rest.controller",
        "cn.herodotus.eurynome.upms.rest.controller.oauth"
})
@EnableJpaRepositories(basePackages = {
        "cn.herodotus.eurynome.upms.rest.repository.oauth",
})
@EnableMethodCache(basePackages = {
        "cn.herodotus.eurynome.upms.rest.service.oauth",
})
public class UpmsRestConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.info("[Eurynome] |- Bean [Upms Rest] Auto Configure.");
    }
}

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
 * Module Name: eurynome-cloud-localstorage
 * File Name: LocalStorageConfiguration.java
 * Author: gengwei.zheng
 * Date: 2020/5/28 下午9:27
 * LastModified: 2020/5/28 下午9:27
 */

package cn.herodotus.eurynome.localstorage.configuration;

import cn.herodotus.eurynome.data.annotation.EnableDynamicDataSource;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: LocalStorageConfiguration </p>
 *
 * <p>Description: LocalStorageConfiguration </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/28 21:27
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableDynamicDataSource
@ComponentScan(basePackages = {
        "cn.herodotus.eurynome.localstorage.service"
})
@EnableJpaRepositories(basePackages = {
        "cn.herodotus.eurynome.localstorage.repository",
})
@EntityScan(basePackages = {
        "cn.herodotus.eurynome.localstorage.entity"
})
@EnableMethodCache(basePackages = {
        "cn.herodotus.eurynome.localstorage.service"
})
public class LocalStorageConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Bean [Local Storage] Auto Configure.");
    }
}

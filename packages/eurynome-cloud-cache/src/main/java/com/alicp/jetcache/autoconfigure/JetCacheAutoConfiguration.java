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
 * Module Name: eurynome-cloud-cache
 * File Name: JetCacheAutoConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/12/04 13:40:04
 */

package com.alicp.jetcache.autoconfigure;

import com.alicp.jetcache.anno.support.GlobalCacheConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

/**
 * <p>Description: 重新定义JetCacheAutoConfiguration </p>
 *
 * 解决JetCacheAutoConfiguration依赖循环问题
 *
 * @author : gengwei.zheng
 * @date : 2021/12/4 13:40
 */
@Configuration
@ConditionalOnClass(GlobalCacheConfig.class)
@Import({RedisAutoConfiguration.class,
        CaffeineAutoConfiguration.class,
        MockRemoteCacheAutoConfiguration.class,
        LinkedHashMapAutoConfiguration.class,
        RedisLettuceAutoConfiguration.class,
        RedisSpringDataAutoConfiguration.class})
public class JetCacheAutoConfiguration {

    public static final String GLOBAL_CACHE_CONFIG_NAME = "globalCacheConfig";

    private static final Logger log = LoggerFactory.getLogger(JetCacheAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Component [Custom JetCache] Auto Configure.");
    }
}

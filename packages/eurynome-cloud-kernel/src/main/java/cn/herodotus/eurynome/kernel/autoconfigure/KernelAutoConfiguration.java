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
 * Module Name: eurynome-cloud-kernel
 * File Name: KernelAutoConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/12/04 15:40:04
 */

package cn.herodotus.eurynome.kernel.autoconfigure;

import cn.herodotus.eurynome.kernel.configuration.FeignConfiguration;
import cn.herodotus.eurynome.kernel.configuration.LogstashConfiguration;
import cn.herodotus.eurynome.kernel.configuration.SentinelConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

/**
 * <p>Description: Kernel 自动注入 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/4 15:40
 */
@Configuration(proxyBeanMethods = false)
@Import({
        LogstashConfiguration.class,
        FeignConfiguration.class,
        SentinelConfiguration.class
})
public class KernelAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(KernelAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Components [Herodotus Kernel] Auto Configure.");
    }
}

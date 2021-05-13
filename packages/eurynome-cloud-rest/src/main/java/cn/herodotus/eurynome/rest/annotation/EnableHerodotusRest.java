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
 * Module Name: eurynome-cloud-rest
 * File Name: EnableHerodotusRest.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.rest.annotation;

import cn.herodotus.eurynome.rest.configuration.*;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>Description: 开启Herodotus REST核心注解 </p>
 * <p>
 * 目前主要功能：
 * 1.开启ApplicationProperties， RestProperties， SwaggerProperties
 * 2.启用RestTemplate配置
 * 3.启用Swagger配置
 * 4.解决Undertow启动警告问题
 *
 * @author : gengwei.zheng
 * @date : 2020/3/2 11:01
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({
        JacksonConfiguration.class,
        RestConfiguration.class,
        RestTemplateConfiguration.class,
        SwaggerConfiguration.class,
        UndertowWebServerFactoryCustomizer.class
})
public @interface EnableHerodotusRest {
}

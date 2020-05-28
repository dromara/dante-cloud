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
 * File Name: EnableMessageBus.java
 * Author: gengwei.zheng
 * Date: 2020/5/28 下午12:12
 * LastModified: 2020/5/28 下午12:12
 */

package cn.herodotus.eurynome.message.annotation;

import cn.herodotus.eurynome.message.configuration.BusConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: EnableMessageBus </p>
 *
 * <p>Description: 是否开启消息总线注解 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/28 12:12
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(BusConfiguration.class)
public @interface EnableMessageBus {
}

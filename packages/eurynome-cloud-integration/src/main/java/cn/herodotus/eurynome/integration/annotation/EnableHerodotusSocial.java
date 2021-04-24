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
 * Module Name: eurynome-cloud-integration
 * File Name: EnableHerodotusSocial.java
 * Author: gengwei.zheng
 * Date: 2021/4/10 下午4:18
 * LastModified: 2021/4/10 下午4:18
 */

package cn.herodotus.eurynome.integration.annotation;

import cn.herodotus.eurynome.integration.social.annotation.EnableWxapp;

import java.lang.annotation.*;

/**
 * <p>File: EnableHerodotusSocial </p>
 *
 * <p>Description: 统一开启Social </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/10 16:18
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableWxapp
public @interface EnableHerodotusSocial {
}

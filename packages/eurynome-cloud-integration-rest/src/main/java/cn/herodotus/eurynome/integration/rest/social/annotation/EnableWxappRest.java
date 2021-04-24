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
 * Module Name: eurynome-cloud-integration-rest
 * File Name: EnableWxappRest.java
 * Author: gengwei.zheng
 * Date: 2021/4/10 下午4:51
 * LastModified: 2021/4/10 下午4:51
 */

package cn.herodotus.eurynome.integration.rest.social.annotation;

import cn.herodotus.eurynome.integration.rest.social.configuration.WxappRestConfiguration;
import cn.herodotus.eurynome.integration.social.annotation.EnableWxapp;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>File: EnableWxappRest </p>
 *
 * <p>Description: 开启微信小城Rest </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/10 16:51
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableWxapp
@Import(WxappRestConfiguration.class)
public @interface EnableWxappRest {
}

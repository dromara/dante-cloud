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
 * File Name: EnableWxapp.java
 * Author: gengwei.zheng
 * Date: 2021/4/3 下午7:43
 * LastModified: 2021/4/3 下午3:37
 */

package cn.herodotus.eurynome.integration.annotation;

import cn.herodotus.eurynome.integration.configuration.WxappConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: EnableWxapp </p>
 *
 * <p>Description: 开启微信小程序相关配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/3/28 15:27
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(WxappConfiguration.class)
public @interface EnableWxapp {
}

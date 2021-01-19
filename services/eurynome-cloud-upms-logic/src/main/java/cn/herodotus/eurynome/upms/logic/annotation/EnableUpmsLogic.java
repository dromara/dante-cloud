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
 * Module Name: eurynome-cloud-upms-logic
 * File Name: EnableUpmsLogic.java
 * Author: gengwei.zheng
 * Date: 2020/6/9 下午1:55
 * LastModified: 2020/6/9 下午1:55
 */

package cn.herodotus.eurynome.upms.logic.annotation;

import cn.herodotus.eurynome.upms.api.annotation.EnableUpmsInterface;
import cn.herodotus.eurynome.upms.logic.configuration.UpmsLogicConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: EnableUpmsLogic </p>
 *
 * <p>Description: EnableUpmsLogic </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/6/9 13:55
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableUpmsInterface
@Import(UpmsLogicConfiguration.class)
public @interface EnableUpmsLogic {
}

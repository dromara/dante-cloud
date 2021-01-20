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
 * File Name: EnableUpmsRest.java
 * Author: gengwei.zheng
 * Date: 2021/1/5 上午11:57
 * LastModified: 2021/1/5 上午11:57
 */

package cn.herodotus.eurynome.upms.rest.annotation;

import cn.herodotus.eurynome.upms.rest.configuration.UpmsRestConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: EnableUpmsRest </p>
 *
 * <p>Description: EnableUpmsRest </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/1/5 11:57
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(UpmsRestConfiguration.class)
public @interface EnableUpmsRest {
}

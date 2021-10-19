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
 * Module Name: eurynome-cloud-assistant
 * File Name: AccessLimited.java
 * Author: gengwei.zheng
 * Date: 2021/08/26 20:40:26
 */

package cn.herodotus.eurynome.assistant.annotation.rest;

import java.lang.annotation.*;
import java.time.Duration;

/**
 * <p>Description: 接口防刷注解 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/26 18:39
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface AccessLimited {

    /**
     * 单位时间内同一个接口可以访问的次数
     *
     * @return int
     */
    int maxTimes() default 5;

    /**
     * 持续时间，即在多长时间内，限制访问多少次。具体单位根据TimeUnit的设置而定。
     * <p>
     * 使用Duration格式{@link Duration}
     * <p>
     * 默认为：0，即不设置该属性。那么就使用StampProperies中的配置进行设置。
     * 如果设置了该值，就以该值进行设置。
     */
    String duration() default "";
}

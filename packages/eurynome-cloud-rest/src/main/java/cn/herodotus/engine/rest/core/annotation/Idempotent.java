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
 * Module Name: eurynome-cloud-rest
 * File Name: Idempotent.java
 * Author: gengwei.zheng
 * Date: 2021/10/19 21:35:19
 */

package cn.herodotus.engine.rest.core.annotation;

import java.lang.annotation.*;

/**
 * <p>Description: 幂等标识注解 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/26 18:39
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Idempotent {

    /**
     * 过期时间，即幂等签章有效时间。使用Duration格式配置。。
     * <p>
     * 默认为：空，即不设置该属性。那么就使用StampProperies中的配置进行设置。
     * 如果设置了该值，就以该值进行设置。
     *
     * @return {@link Long}
     */
    String expire() default "";
}

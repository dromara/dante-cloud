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
 * Module Name: eurynome-cloud-data
 * File Name: DataSource.java
 * Author: gengwei.zheng
 * Date: 2020/5/23 下午5:25
 * LastModified: 2020/5/23 下午5:25
 */

package cn.herodotus.eurynome.data.datasource.annotation;

import java.lang.annotation.*;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: DataSource </p>
 *
 * <p>Description: 数据源注解 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/23 17:25
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

    /**
     * 数据源名称
     *
     * @return 要切换的数据源名称
     */
    String value();

}

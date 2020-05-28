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
 * File Name: LocalStorageConstants.java
 * Author: gengwei.zheng
 * Date: 2020/5/28 下午4:23
 * LastModified: 2020/5/28 下午4:18
 */

package cn.herodotus.eurynome.localstorage.constants;

import cn.herodotus.eurynome.data.constants.CacheConstants;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: LocalStorageConstants </p>
 *
 * <p>Description: LocalStorageConstants </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/28 12:59
 */
public class LocalStorageConstants extends CacheConstants {

    public static final String CACHE_AREA_PREFIX = "data:localstorage:";

    public static final String CACHE_NAME_SECURITY_METADATA = CACHE_AREA_PREFIX + "security:metadata:";
}

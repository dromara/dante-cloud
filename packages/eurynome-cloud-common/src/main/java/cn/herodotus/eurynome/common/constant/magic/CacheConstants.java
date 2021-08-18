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
 * Module Name: eurynome-cloud-constant
 * File Name: CacheConstants.java
 * Author: gengwei.zheng
 * Date: 2021/08/07 20:09:07
 */

package cn.herodotus.eurynome.common.constant.magic;

/**
 * <p>Description: 缓存相关常量 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/7 20:09
 */
public class CacheConstants {

    public static final String CACHE_PREFIX = "cache:";

    public static final String CACHE_SIMPLE_BASE_PREFIX = CACHE_PREFIX + "simple:";
    public static final String CACHE_TOKEN_BASE_PREFIX = CACHE_PREFIX + "token:";

    public static final String CACHE_SECURITY_PREFIX = CACHE_PREFIX + "security:";
    public static final String CACHE_SECURITY_METADATA_PREFIX = CACHE_SECURITY_PREFIX + "metadata:";

    public static final String CACHE_NAME_SECURITY_METADATA_ATTRIBUTES = CACHE_SECURITY_METADATA_PREFIX + "attributes:";
    public static final String CACHE_NAME_SECURITY_METADATA_INDEXABLE = CACHE_SECURITY_METADATA_PREFIX + "indexable:";
    public static final String CACHE_NAME_SECURITY_METADATA_COMPATIBLE = CACHE_SECURITY_METADATA_PREFIX + "compatible:";

    public static final int DEFAULT_UPMS_CACHE_EXPIRE = 86400;
    public static final int DEFAULT_UPMS_LOCAL_LIMIT = 1000;

    public static final String INDEX_CACHE_NAME = "index:";
}

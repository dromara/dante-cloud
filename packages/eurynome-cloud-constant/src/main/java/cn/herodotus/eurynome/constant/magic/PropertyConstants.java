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
 * File Name: PropertyConstants.java
 * Author: gengwei.zheng
 * Date: 2021/08/07 20:09:07
 */

package cn.herodotus.eurynome.constant.magic;

/**
 * <p>Description: 平台涉及的各种Property值 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/7 20:09
 */
public class PropertyConstants {

    public static final String PROPERTY_PREFIX_PLATFORM = "herodotus.platform";
    public static final String PROPERTY_PREFIX_VERIFICATION_CODE = "herodotus.sms";
    public static final String PROPERTY_PREFIX_REST = PROPERTY_PREFIX_PLATFORM + ".rest";
    public static final String PROPERTY_PREFIX_SECURITY = PROPERTY_PREFIX_PLATFORM + ".security";
    public static final String PROPERTY_PREFIX_JUSTAUTH = PROPERTY_PREFIX_PLATFORM + ".social.justauth";
    public static final String PROPERTY_PREFIX_SERVICE = "herodotus.environment.service";

    public static final String ITEM_SPRING_APPLICATION_NAME = "spring.application.name";
    public static final String ITEM_SPRING_JPA_HIBERNATE_DDL_AUTO = "spring.jpa.hibernate.ddl-auto";

    public static final String ITEM_PLATFORM_DATA_ACCESS_STRATEGY = PROPERTY_PREFIX_PLATFORM + ".data-access-strategy";
    public static final String ITEM_PLATFORM_ARCHITECTURE = PROPERTY_PREFIX_PLATFORM + ".architecture";
}

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
 * Module Name: eurynome-cloud-upms-logic
 * File Name: UpmsConstants.java
 * Author: gengwei.zheng
 * Date: 2021/11/15 15:31:15
 */

package cn.herodotus.eurynome.module.upms.constants;

/**
 * <p> Description : Upms服务常量 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/19 20:06
 */
public interface UpmsConstants {

    String AREA_PREFIX = "data:upms:";

    String REGION_SYS_USER = AREA_PREFIX + "sys:user";
    String REGION_SYS_ROLE = AREA_PREFIX + "sys:role";
    String REGION_SYS_DEFAULT_ROLE = AREA_PREFIX + "sys:defaults:role";
    String REGION_SYS_AUTHORITY = AREA_PREFIX + "sys:authority";
    String REGION_SYS_SECURITY_ATTRIBUTE = AREA_PREFIX + "sys:security:attribute";
    String REGION_SYS_OWNERSHIP = AREA_PREFIX + "sys:ownership";
    String REGION_SYS_SOCIAL_USER = AREA_PREFIX + "sys:social:user";
    String REGION_VIEW_SYS_OWNERSHIP = AREA_PREFIX + "view:sys:ownership";

    String REGION_DEVELOPMENT_SUPPLIER = AREA_PREFIX + "development:supplier";

    String REGION_OAUTH_APPLICATIONS = AREA_PREFIX + "oauth:applications";
    String REGION_OAUTH_CLIENTDETAILS = AREA_PREFIX + "oauth:clientdetails";
    String REGION_OAUTH_MICROSERVICES = AREA_PREFIX + "oauth:microservices";
    String REGION_OAUTH_SCOPES = AREA_PREFIX + "oauth:scopes";
    String REGION_OAUTH_EXPRESSIONS = AREA_PREFIX + "oauth:expressions";
    String REGION_OAUTH_DYNAMIC_EXPRESSIONS = AREA_PREFIX + "oauth:expressions:dynamic";
    String REGION_OAUTH_IP_ADDRESSES = AREA_PREFIX + "oauth:ipaddresses";

    String REGION_SYS_DEPARTMENT = AREA_PREFIX + "sys:department";
    String REGION_SYS_EMPLOYEE = AREA_PREFIX + "sys:employee";
    String REGION_SYS_EMPLOYEE_DEPARTMENT = AREA_PREFIX + "sys:employee:department";
    String REGION_SYS_ORGANIZATION = AREA_PREFIX + "sys:organization";
    String REGION_SYS_POSITION = AREA_PREFIX + "sys:position";
}

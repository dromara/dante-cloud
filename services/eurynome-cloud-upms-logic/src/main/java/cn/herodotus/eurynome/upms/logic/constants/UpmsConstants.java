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

package cn.herodotus.eurynome.upms.logic.constants;

import cn.herodotus.eurynome.assistant.enums.*;
import cn.herodotus.eurynome.cache.constant.CacheConstants;
import cn.herodotus.eurynome.upms.logic.constants.enums.SupplierType;
import cn.herodotus.eurynome.upms.logic.constants.enums.TechnologyType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> Description : Upms服务常量 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/19 20:06
 */
public class UpmsConstants extends CacheConstants {

    /**
     * 服务名称
     */
    public static final String SERVICE_NAME = "eurynome-cloud-upms-ability";

    public static final String AREA_PREFIX = "data:upms:";

    public static final String REGION_SYS_USER = AREA_PREFIX + "sys:user";
    public static final String REGION_SYS_ROLE = AREA_PREFIX + "sys:role";
    public static final String REGION_SYS_DEFAULT_ROLE = AREA_PREFIX + "sys:defaults:role";
    public static final String REGION_SYS_AUTHORITY = AREA_PREFIX + "sys:authority";
    public static final String REGION_SYS_SECURITY_ATTRIBUTE = AREA_PREFIX + "sys:security:attribute";
    public static final String REGION_SYS_OWNERSHIP = AREA_PREFIX + "sys:ownership";
    public static final String REGION_SYS_SOCIAL_USER = AREA_PREFIX + "sys:social:user";
    public static final String REGION_VIEW_SYS_OWNERSHIP = AREA_PREFIX + "view:sys:ownership";

    public static final String REGION_DEVELOPMENT_SUPPLIER = AREA_PREFIX + "development:supplier";

    public static final String REGION_OAUTH_APPLICATIONS = AREA_PREFIX + "oauth:applications";
    public static final String REGION_OAUTH_CLIENTDETAILS = AREA_PREFIX + "oauth:clientdetails";
    public static final String REGION_OAUTH_MICROSERVICES = AREA_PREFIX + "oauth:microservices";
    public static final String REGION_OAUTH_SCOPES = AREA_PREFIX + "oauth:scopes";
    public static final String REGION_OAUTH_EXPRESSIONS = AREA_PREFIX + "oauth:expressions";
    public static final String REGION_OAUTH_DYNAMIC_EXPRESSIONS = AREA_PREFIX + "oauth:expressions:dynamic";
    public static final String REGION_OAUTH_IP_ADDRESSES = AREA_PREFIX + "oauth:ipaddresses";

    public static final String REGION_SYS_DEPARTMENT = AREA_PREFIX + "sys:department";
    public static final String REGION_SYS_EMPLOYEE = AREA_PREFIX + "sys:employee";
    public static final String REGION_SYS_EMPLOYEE_DEPARTMENT = AREA_PREFIX + "sys:employee:department";
    public static final String REGION_SYS_ORGANIZATION = AREA_PREFIX + "sys:organization";
    public static final String REGION_SYS_POSITION = AREA_PREFIX + "sys:position";

    private static final List<Map<String, Object>> STATUS_ENUM = StatusEnum.getToJsonStruct();
    private static final List<Map<String, Object>> APPLICATION_TYPE_ENUM = ApplicationType.getToJsonStruct();
    private static final List<Map<String, Object>> OAUTH2_GRANT_TYPE_ENUM = GrantType.getToJsonStruct();
    private static final List<Map<String, Object>> TECHNOLOGY_TYPE_ENUM = TechnologyType.getToJsonStruct();
    private static final List<Map<String, Object>> SUPPLIER_TYPE_ENUM = SupplierType.getToJsonStruct();
    private static final List<Map<String, Object>> GENDER_ENUM = Gender.getToJsonStruct();
    private static final List<Map<String, Object>> IDENTITY_ENUM = Identity.getToJsonStruct();
    private static final List<Map<String, Object>> ORGANIZATION_CATEGORY_ENUM = OrganizationCategory.getToJsonStruct();


    public static Map<String, Object> getAllEnums() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("status", STATUS_ENUM);
        map.put("applicationType", APPLICATION_TYPE_ENUM);
        map.put("grantType", OAUTH2_GRANT_TYPE_ENUM);
        map.put("technologyType", TECHNOLOGY_TYPE_ENUM);
        map.put("supplierType", SUPPLIER_TYPE_ENUM);
        map.put("gender", GENDER_ENUM);
        map.put("identity", IDENTITY_ENUM);
        map.put("organizationCategory", ORGANIZATION_CATEGORY_ENUM);
        return map;
    }
}

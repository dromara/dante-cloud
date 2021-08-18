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

package cn.herodotus.eurynome.common.constant.magic;

/**
 * <p>Description: 平台涉及的各种Property值 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/7 20:09
 */
public class PropertyConstants {

    public static final String PROPERTY_PREFIX_HERODOTUS = "herodotus";
    public static final String PROPERTY_PREFIX_PLATFORM = PROPERTY_PREFIX_HERODOTUS + ".platform";
    public static final String PROPERTY_PREFIX_AUDIT = PROPERTY_PREFIX_HERODOTUS + ".audit";
    public static final String PROPERTY_PREFIX_MANAGEMENT = PROPERTY_PREFIX_HERODOTUS + ".management";
    public static final String PROPERTY_PREFIX_MESSAGE = PROPERTY_PREFIX_HERODOTUS + ".message";
    public static final String PROPERTY_PREFIX_SOCIAL = PROPERTY_PREFIX_HERODOTUS + ".social";

    public static final String PROPERTY_PREFIX_CACHE = PROPERTY_PREFIX_PLATFORM + ".cache";
    public static final String PROPERTY_PREFIX_REST = PROPERTY_PREFIX_PLATFORM + ".rest";
    public static final String PROPERTY_PREFIX_SECURITY = PROPERTY_PREFIX_PLATFORM + ".security";

    public static final String PROPERTY_PREFIX_MANAGEMENT_NACOS = PROPERTY_PREFIX_MANAGEMENT + ".nacos";
    public static final String PROPERTY_PREFIX_MANAGEMENT_QUEUE = PROPERTY_PREFIX_MANAGEMENT + ".queue";
    public static final String PROPERTY_PREFIX_MANAGEMENT_SERVICE = PROPERTY_PREFIX_MANAGEMENT + ".service";

    public static final String PROPERTY_PREFIX_SOCIAL_JUSTAUTH = PROPERTY_PREFIX_SOCIAL + ".justauth";
    public static final String PROPERTY_PREFIX_SOCIAL_EASEMOB = PROPERTY_PREFIX_SOCIAL + ".easemob";
    public static final String PROPERTY_PREFIX_SOCIAL_WXAPP = PROPERTY_PREFIX_SOCIAL + ".wxapp";
    public static final String PROPERTY_PREFIX_SOCIAL_WXMPP = PROPERTY_PREFIX_SOCIAL + ".wxmpp";

    public static final String PROPERTY_PREFIX_MESSAGE_SMS = PROPERTY_PREFIX_MESSAGE + ".sms";
    public static final String PROPERTY_PREFIX_MESSAGE_SMS_VERIFICATION_CODE = PROPERTY_PREFIX_MESSAGE_SMS + ".verification-code";
    public static final String PROPERTY_PREFIX_MESSAGE_PUSH_APNS = PROPERTY_PREFIX_MESSAGE + ".push.apns";
    public static final String PROPERTY_PREFIX_MESSAGE_PUSH_JPUSH = PROPERTY_PREFIX_MESSAGE + ".push.jpush";

    public static final String PROPERTY_PREFIX_AUDIT_ALIYUN = PROPERTY_PREFIX_AUDIT + ".aliyun";
    public static final String PROPERTY_PREFIX_AUDIT_BAIDU = PROPERTY_PREFIX_AUDIT + ".baidu";
    public static final String PROPERTY_PREFIX_AUDIT_TIANYAN = PROPERTY_PREFIX_AUDIT + ".tianyan";


    public static final String ITEM_SPRING_APPLICATION_NAME = "spring.application.name";
    public static final String ITEM_SPRING_JPA_HIBERNATE_DDL_AUTO = "spring.jpa.hibernate.ddl-auto";

    public static final String ITEM_PLATFORM_DATA_ACCESS_STRATEGY = PROPERTY_PREFIX_PLATFORM + ".data-access-strategy";
    public static final String ITEM_PLATFORM_ARCHITECTURE = PROPERTY_PREFIX_PLATFORM + ".architecture";
    public static final String ITEM_PLATFORM_KAFKA_ENABLED = PROPERTY_PREFIX_MANAGEMENT_QUEUE + ".kafka.enabled";

    public static final String ANNOTATION_DEBEZIUM_ENABLED = "${herodotus.platform.debezium.enabled}";
}

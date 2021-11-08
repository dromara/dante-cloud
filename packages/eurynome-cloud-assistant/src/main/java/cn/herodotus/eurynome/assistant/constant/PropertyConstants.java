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
 * File Name: PropertyConstants.java
 * Author: gengwei.zheng
 * Date: 2021/10/17 22:56:17
 */

package cn.herodotus.eurynome.assistant.constant;

/**
 * <p>Description: 平台涉及的各种Property值 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/7 20:09
 */
public class PropertyConstants {

    public static final String PROPERTY_ENABLED = ".enabled";

    public static final String PROPERTY_PREFIX_SPRING = "spring";
    public static final String PROPERTY_PREFIX_HERODOTUS = "herodotus";

    public static final String PROPERTY_SPRING_REDIS = PROPERTY_PREFIX_SPRING + ".redis";

    public static final String PROPERTY_HERODOTUS_PLATFORM = PROPERTY_PREFIX_HERODOTUS + ".platform";
    public static final String PROPERTY_HERODOTUS_AUDIT = PROPERTY_PREFIX_HERODOTUS + ".audit";
    public static final String PROPERTY_HERODOTUS_MANAGEMENT = PROPERTY_PREFIX_HERODOTUS + ".management";
    public static final String PROPERTY_HERODOTUS_MESSAGE = PROPERTY_PREFIX_HERODOTUS + ".message";
    public static final String PROPERTY_HERODOTUS_SOCIAL = PROPERTY_PREFIX_HERODOTUS + ".social";
    public static final String PROPERTY_HERODOTUS_WEBSOCKET = PROPERTY_PREFIX_HERODOTUS + ".websocket";
    public static final String PROPERTY_HERODOTUS_OSS = PROPERTY_PREFIX_HERODOTUS + ".oss";

    public static final String PROPERTY_REDIS_REDISSON = PROPERTY_SPRING_REDIS + ".redisson";

    public static final String PROPERTY_PLATFORM_CACHE = PROPERTY_HERODOTUS_PLATFORM + ".cache";
    public static final String PROPERTY_PLATFORM_REST = PROPERTY_HERODOTUS_PLATFORM + ".rest";
    public static final String PROPERTY_PLATFORM_SECURITY = PROPERTY_HERODOTUS_PLATFORM + ".security";
    public static final String PROPERTY_PLATFORM_SWAGGER = PROPERTY_HERODOTUS_PLATFORM + ".swagger";
    public static final String PROPERTY_PLATFORM_STAMP = PROPERTY_HERODOTUS_PLATFORM + ".stamp";

    public static final String PROPERTY_MANAGEMENT_NACOS = PROPERTY_HERODOTUS_MANAGEMENT + ".nacos";
    public static final String PROPERTY_MANAGEMENT_QUEUE = PROPERTY_HERODOTUS_MANAGEMENT + ".queue";
    public static final String PROPERTY_MANAGEMENT_LOG_CENTER = PROPERTY_HERODOTUS_MANAGEMENT + ".log-center";
    public static final String PROPERTY_MANAGEMENT_SERVICE = PROPERTY_HERODOTUS_MANAGEMENT + ".service";

    public static final String PROPERTY_SOCIAL_JUSTAUTH = PROPERTY_HERODOTUS_SOCIAL + ".justauth";
    public static final String PROPERTY_SOCIAL_EASEMOB = PROPERTY_HERODOTUS_SOCIAL + ".easemob";
    public static final String PROPERTY_SOCIAL_WXAPP = PROPERTY_HERODOTUS_SOCIAL + ".wxapp";
    public static final String PROPERTY_SOCIAL_WXMPP = PROPERTY_HERODOTUS_SOCIAL + ".wxmpp";

    public static final String PROPERTY_MESSAGE_SMS = PROPERTY_HERODOTUS_MESSAGE + ".sms";
    public static final String PROPERTY_MESSAGE_SMS_VERIFICATION_CODE = PROPERTY_MESSAGE_SMS + ".verification-code";
    public static final String PROPERTY_MESSAGE_PUSH_APNS = PROPERTY_HERODOTUS_MESSAGE + ".push.apns";
    public static final String PROPERTY_MESSAGE_PUSH_JPUSH = PROPERTY_HERODOTUS_MESSAGE + ".push.jpush";

    public static final String PROPERTY_AUDIT_ALIYUN = PROPERTY_HERODOTUS_AUDIT + ".aliyun";
    public static final String PROPERTY_AUDIT_BAIDU = PROPERTY_HERODOTUS_AUDIT + ".baidu";
    public static final String PROPERTY_AUDIT_TIANYAN = PROPERTY_HERODOTUS_AUDIT + ".tianyan";

    public static final String PROPERTY_OSS_MINIO = PROPERTY_HERODOTUS_OSS + ".minio";

    public static final String ITEM_SPRING_APPLICATION_NAME = "spring.application.name";
    public static final String ITEM_SPRING_JPA_HIBERNATE_DDL_AUTO = "spring.jpa.hibernate.ddl-auto";
    public static final String ITEM_SPRING_SQL_INIT_PLATFORM = "spring.sql.init.platform";

    public static final String ITEM_PLATFORM_DATA_ACCESS_STRATEGY = PROPERTY_HERODOTUS_PLATFORM + ".data-access-strategy";
    public static final String ITEM_PLATFORM_ARCHITECTURE = PROPERTY_HERODOTUS_PLATFORM + ".architecture";
    public static final String ITEM_MINIO_ENDPOINT = PROPERTY_OSS_MINIO + ".endpoint";
    public static final String ITEM_MINIO_ACCESSKEY = PROPERTY_OSS_MINIO + ".accessKey";
    public static final String ITEM_MINIO_SECRETKEY = PROPERTY_OSS_MINIO + ".secretKey";

    public static final String ITEM_SWAGGER_ENABLED = PROPERTY_PLATFORM_SWAGGER + PROPERTY_ENABLED;
    public static final String ITEM_REDISSON_ENABLED = PROPERTY_REDIS_REDISSON + PROPERTY_ENABLED;
    public static final String ITEM_KAFKA_ENABLED = PROPERTY_MANAGEMENT_QUEUE + ".kafka" + PROPERTY_ENABLED;
    public static final String ITEM_LOG_CENTER_ENABLED = PROPERTY_MANAGEMENT_LOG_CENTER + ".server-addr";

    public static final String ANNOTATION_PREFIX = "${";
    public static final String ANNOTATION_SUFFIX = "}";


    public static final String ANNOTATION_APPLICATION_NAME = ANNOTATION_PREFIX + ITEM_SPRING_APPLICATION_NAME + ANNOTATION_SUFFIX;
    public static final String ANNOTATION_SQL_INIT_PLATFORM = ANNOTATION_PREFIX + ITEM_SPRING_SQL_INIT_PLATFORM + ANNOTATION_SUFFIX;
    public static final String ANNOTATION_DEBEZIUM_ENABLED = "${herodotus.platform.debezium.enabled}";
}

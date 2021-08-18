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
 * File Name: PropertyResolver.java
 * Author: gengwei.zheng
 * Date: 2021/08/07 20:27:07
 */

package cn.herodotus.eurynome.assistant.utils;

import cn.herodotus.eurynome.common.constant.magic.PlatformConstants;
import cn.herodotus.eurynome.common.constant.magic.PropertyConstants;
import org.springframework.core.env.Environment;

/**
 * <p>Description: 配置信息读取工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/7 20:27
 */
public class PropertyResolver {

    private static String getProperty(Environment environment, String property) {
        return environment.getProperty(property);
    }

    private static String getProperty(Environment environment, String property, String defaultValue) {
        return environment.getProperty(property, defaultValue);
    }

    public static String getDataAccessStrategy(Environment environment, String defaultValue) {
        return environment.getProperty(PropertyConstants.ITEM_PLATFORM_DATA_ACCESS_STRATEGY, defaultValue);
    }

    public static String getDataAccessStrategy(Environment environment) {
        return environment.getProperty(PropertyConstants.ITEM_PLATFORM_DATA_ACCESS_STRATEGY);
    }

    public static String getDdlAuto(Environment environment) {
        return getDdlAuto(environment, PlatformConstants.NONE);
    }

    public static String getDdlAuto(Environment environment, String defaultValue) {
        return environment.getProperty(PropertyConstants.ITEM_SPRING_JPA_HIBERNATE_DDL_AUTO, defaultValue);
    }

    public static String getApplicationName(Environment environment) {
        return environment.getProperty(PropertyConstants.ITEM_SPRING_APPLICATION_NAME);
    }
}

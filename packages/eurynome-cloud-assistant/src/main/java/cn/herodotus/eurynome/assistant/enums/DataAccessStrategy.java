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
 * File Name: DataAccessStrategy.java
 * Author: gengwei.zheng
 * Date: 2021/10/17 22:53:17
 */

package cn.herodotus.eurynome.assistant.enums;

/**
 * <p>Description: 数据访问策略枚举类型 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/28 19:06
 */
public enum DataAccessStrategy {

    /**
     * 本地访问，利用service直连数据库
     */
    LOCAL,

    /**
     * 远程访问，利用Feign等远程访问数据
     */
    REMOTE;
}

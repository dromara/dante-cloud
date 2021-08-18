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
 * File Name: Architecture.java
 * Author: gengwei.zheng
 * Date: 2021/06/29 15:58:29
 */

package cn.herodotus.eurynome.common.constant.enums;

/**
 * <p> Description : 用于区分是单体应用还是微服务应用 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/26 11:33
 */
public enum Architecture {

    /**
     * 分布式架构
     */
    DISTRIBUTED,

    /**
     * 单体式架构
     */
    MONOCOQUE;
}

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
 * Module Name: eurynome-cloud-security
 * File Name: StrategySecurityMetadataService.java
 * Author: gengwei.zheng
 * Date: 2021/07/28 18:14:28
 */

package cn.herodotus.eurynome.security.definition.service;

import cn.herodotus.eurynome.security.definition.domain.RequestMapping;

import java.util.List;

/**
 * <p>Description: Authority 服务策略定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/28 18:14
 */
public interface StrategyRequestMappingGatherService {

    /**
     * 将扫描到的RequestMapping转换为权限数据，统一汇总存储。
     *
     * @param requestMappings 扫描的接口数据{@link RequestMapping}
     */
    void store(List<RequestMapping> requestMappings);
}

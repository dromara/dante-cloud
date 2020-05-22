/*
 * Copyright 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-operation
 * File Name: NacosDiscovery.java
 * Author: gengwei.zheng
 * Date: 2020/5/22 下午5:00
 * LastModified: 2020/5/19 下午3:31
 */

package cn.herodotus.eurynome.operation.nacos;

import cn.herodotus.eurynome.operation.properties.ManagementProperties;

/**
 * <p> Description : NacosDiscovery </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/30 15:59
 */
public class HerodotusNacosDiscovery extends AbstractNacos{

    public HerodotusNacosDiscovery(ManagementProperties managementProperties) {
        super(managementProperties);
    }
}

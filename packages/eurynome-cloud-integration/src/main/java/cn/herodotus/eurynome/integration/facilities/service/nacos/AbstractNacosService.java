/*
 * Copyright (c) 2019-2021 the original author or authors.
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
 * Module Name: eurynome-cloud-integration
 * File Name: AbstractNacosService.java
 * Author: gengwei.zheng
 * Date: 2021/4/10 下午3:35
 * LastModified: 2021/4/10 下午3:35
 */

package cn.herodotus.eurynome.integration.facilities.service.nacos;

import cn.herodotus.eurynome.integration.definition.AbstractRestApiService;
import cn.herodotus.eurynome.integration.facilities.properties.NacosProperties;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>File: AbstractNacosService </p>
 *
 * <p>Description: Nacos 基础服务类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/10 15:35
 */
public abstract class AbstractNacosService extends AbstractRestApiService {

    @Autowired
    private NacosProperties nacosProperties;

    protected NacosProperties getNacosProperties() {
        return nacosProperties;
    }

    @Override
    protected String getBaseUrl() {
        return nacosProperties.getBaseUrl();
    }
}

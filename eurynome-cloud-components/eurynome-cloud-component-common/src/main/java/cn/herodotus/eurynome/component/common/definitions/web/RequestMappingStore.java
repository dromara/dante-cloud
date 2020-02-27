/*
 * Copyright 2019-2019 the original author or authors.
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
 * Project Name: luban-cloud
 * Module Name: luban-cloud-component-common
 * File Name: ResourceStore.java
 * Author: gengwei.zheng
 * Date: 2019/11/21 上午11:40
 * LastModified: 2019/11/21 上午11:39
 */

package cn.herodotus.eurynome.component.common.definitions.web;

import java.util.List;

/**
 * <p> Description : 资源存储接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/21 11:38
 */

public interface RequestMappingStore {

    /**
     * 对接口的扫描结果进行存储
     * @param requestMappingResources controller路径映射实体
     * @return 是否存储成功
     */
    boolean storeRequestMappings(List<RequestMappingResource> requestMappingResources);
}

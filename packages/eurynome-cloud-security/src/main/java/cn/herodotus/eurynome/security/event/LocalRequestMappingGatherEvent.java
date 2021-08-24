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
 * File Name: LocalRequestMappingGatherEvent.java
 * Author: gengwei.zheng
 * Date: 2021/08/23 18:53:23
 */

package cn.herodotus.eurynome.security.event;

import cn.herodotus.eurynome.security.definition.domain.RequestMapping;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * <p>Description: 本地RequestMapping收集事件 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/23 18:53
 */
public class LocalRequestMappingGatherEvent extends ApplicationEvent {

    private final List<RequestMapping> requestMappings;

    public LocalRequestMappingGatherEvent(List<RequestMapping> requestMappings) {
        super(requestMappings);
        this.requestMappings = requestMappings;
    }

    public List<RequestMapping> getRequestMappings() {
        return requestMappings;
    }
}

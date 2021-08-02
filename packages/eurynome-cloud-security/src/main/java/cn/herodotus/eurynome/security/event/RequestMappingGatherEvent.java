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
 * File Name: RequestMappingGatherEvent.java
 * Author: gengwei.zheng
 * Date: 2021/08/01 23:24:01
 */

package cn.herodotus.eurynome.security.event;

import cn.herodotus.eurynome.security.definition.domain.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * <p>Description: RequestMapping扫描完成事件 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/1 23:24
 */
public class RequestMappingGatherEvent extends ApplicationEvent {

    private static final Logger log = LoggerFactory.getLogger(RequestMappingGatherEvent.class);

    private final List<RequestMapping> requestMappings;

    public RequestMappingGatherEvent(List<RequestMapping> requestMappings) {
        super(requestMappings);
        this.requestMappings = requestMappings;
        log.debug("[Eurynome] |- Request Mapping Gather Event, init!");
    }

    public List<RequestMapping> getRequestMappings() {
        return requestMappings;
    }
}

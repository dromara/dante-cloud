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
 * Module Name: eurynome-cloud-data
 * File Name: DestinationResolver.java
 * Author: gengwei.zheng
 * Date: 2021/10/19 21:35:19
 */

package cn.herodotus.eurynome.data.support;

import org.springframework.cloud.bus.event.Destination;
import org.springframework.cloud.bus.event.PathDestinationFactory;

/**
 * <p>Description: Spring Cloud Bus Destination辅助类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/11 20:39
 */
public class DestinationSupport {

    private static final PathDestinationFactory pathDestinationFactory = new PathDestinationFactory();

    public static Destination create(String serviceName) {
        return pathDestinationFactory.getDestination(serviceName + ":**");
    }
}

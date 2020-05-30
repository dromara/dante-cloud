/*
 * Copyright (c) 2019-2020 the original author or authors.
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
 * Module Name: eurynome-cloud-message
 * File Name: SecurityMetadataSink.java
 * Author: gengwei.zheng
 * Date: 2020/5/30 上午11:20
 * LastModified: 2020/5/30 上午11:20
 */

package cn.herodotus.eurynome.message.stream.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: SecurityMetadataSink </p>
 *
 * <p>Description: SecurityMetadata 输入通道 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/30 11:20
 */
public interface SecurityMetadataSink {

    String INPUT = "security-metadata-input";

    /**
     * 接收RequestMapping
     * @return {@link SubscribableChannel}
     */
    @Input(INPUT)
    SubscribableChannel input();
}

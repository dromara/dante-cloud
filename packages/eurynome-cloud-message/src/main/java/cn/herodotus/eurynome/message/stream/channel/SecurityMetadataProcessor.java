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
 * File Name: SecurityMetadataProcessor.java
 * Author: gengwei.zheng
 * Date: 2020/5/28 下午5:00
 * LastModified: 2020/5/28 下午4:18
 */

package cn.herodotus.eurynome.message.stream.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: RequestMappingCollection </p>
 *
 * <p>Description: RequestMapping接收和发送通道 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/28 15:30
 */
public interface SecurityMetadataProcessor {

    String INPUT = "security-metadata-input";
    String OUTPUT = "security-metadata-output";

    /**
     * 发送RequestMapping
     * @return {@link MessageChannel}
     */
    @Output(OUTPUT)
    MessageChannel output();

    /**
     * 接收RequestMapping
     * @return {@link SubscribableChannel}
     */
    @Input(INPUT)
    SubscribableChannel input();
}

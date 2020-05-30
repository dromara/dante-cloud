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
 * File Name: SecurityMetadataSource.java
 * Author: gengwei.zheng
 * Date: 2020/5/30 上午11:21
 * LastModified: 2020/5/30 上午11:21
 */

package cn.herodotus.eurynome.message.stream.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: SecurityMetadataSource </p>
 *
 * <p>Description: SecurityMetadata 输出通道 </p>
 *
 * 解决Dispatcher has no subscribers错误，所以输入和输出分开。
 * @see :https://www.jianshu.com/p/bf992c23c381
 *
 * @author : gengwei.zheng
 * @date : 2020/5/30 11:21
 */
public interface SecurityMetadataSource {

    String OUTPUT = "security-metadata-output";

    /**
     * 发送RequestMapping
     * @return {@link MessageChannel}
     */
    @Output(OUTPUT)
    MessageChannel output();
}

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
 * File Name: EnableTxcloudSms.java
 * Author: gengwei.zheng
 * Date: 2021/4/17 上午5:25
 * LastModified: 2021/4/17 上午5:25
 */

package cn.herodotus.eurynome.integration.sms.annotation;

import cn.herodotus.eurynome.integration.sms.configuration.TxcloudSmsConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>File: EnableTxcloudSms </p>
 *
 * <p>Description: 开启腾讯短信服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/17 5:25
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(TxcloudSmsConfiguration.class)
public @interface EnableTxcloudSms {
}

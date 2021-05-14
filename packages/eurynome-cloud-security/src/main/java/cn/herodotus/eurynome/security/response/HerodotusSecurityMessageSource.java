/*
 * Copyright (c) 2019-2021 Gengwei Zheng(herodotus@aliyun.com)
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
 * File Name: HerodotusSecurityMessageSource.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:46:07
 */

package cn.herodotus.eurynome.security.response;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

/**
 * <p>File: HerodotusSecurityMessageSource </p>
 *
 * <p>Description: 将错误消息指定为中文 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/30 11:06
 */
public class HerodotusSecurityMessageSource extends ResourceBundleMessageSource {

    public HerodotusSecurityMessageSource() {
        setBasename("classpath:messages/messages");
        setDefaultLocale(Locale.CHINA);;
    }

    public static MessageSourceAccessor getAccessor() {
        return new MessageSourceAccessor(new HerodotusSecurityMessageSource());
    }
}

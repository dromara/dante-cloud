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
 * Module Name: eurynome-cloud-security
 * File Name: SocialProvider.java
 * Author: gengwei.zheng
 * Date: 2021/4/3 下午4:06
 * LastModified: 2021/4/3 下午3:56
 */

package cn.herodotus.eurynome.security.definition.social;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: SocialProvider </p>
 *
 * <p>Description: 社交登录提供商 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/3 15:56
 */
public enum SocialProvider {

    /**
     * 手机验证码登录
     */
    SMS,

    /**
     * 微信小程序登录
     */
    WXAPP;
}

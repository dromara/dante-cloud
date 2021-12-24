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
 * Module Name: eurynome-cloud-oauth
 * File Name: OauthCaptchaMismatchException.java
 * Author: gengwei.zheng
 * Date: 2021/12/24 21:02:24
 */

package cn.herodotus.eurynome.oauth.exception;

import cn.herodotus.eurynome.security.exception.PlatformAuthenticationException;

/**
 * <p>Description: Oauth2 使用的验证码不匹配错误 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/24 21:02
 */
public class OauthCaptchaMismatchException extends PlatformAuthenticationException {

    public OauthCaptchaMismatchException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public OauthCaptchaMismatchException(String msg) {
        super(msg);
    }
}

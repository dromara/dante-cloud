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
 * File Name: IllegalAuthenticationArgumentException.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:46:07
 */

package cn.herodotus.eurynome.security.response.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * <p>File: IllegalAuthenticationArgumentException </p>
 *
 * <p>Description: 认证参数错误 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/30 13:21
 */
public class IllegalAuthenticationArgumentException extends AuthenticationException {

    public IllegalAuthenticationArgumentException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public IllegalAuthenticationArgumentException(String msg) {
        super(msg);
    }
}

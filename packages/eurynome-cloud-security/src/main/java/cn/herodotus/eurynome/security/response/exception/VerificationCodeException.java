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
 * Module Name: eurynome-cloud-security
 * File Name: VerificationCodeException.java
 * Author: gengwei.zheng
 * Date: 2020/6/3 上午9:56
 * LastModified: 2020/5/23 上午9:32
 */

package cn.herodotus.eurynome.security.response.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/29 13:46
 */
public class VerificationCodeException extends AuthenticationException {

    public VerificationCodeException(String message) {
        super(message);
    }
}

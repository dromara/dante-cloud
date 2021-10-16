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
 * Module Name: eurynome-cloud-security
 * File Name: IllegalSymmetricKeyException.java
 * Author: gengwei.zheng
 * Date: 2021/10/16 16:08:16
 */

package cn.herodotus.eurynome.security.exception;

/**
 * <p> Description : 非法加密Key Exception </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/28 17:32
 */
public class IllegalSymmetricKeyException extends PlatformAuthenticationException {

    public IllegalSymmetricKeyException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public IllegalSymmetricKeyException(String msg) {
        super(msg);
    }
}

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
 * File Name: AccessDeniedAuthorityLimitedException.java
 * Author: gengwei.zheng
 * Date: 2020/6/5 下午3:36
 * LastModified: 2020/6/5 下午3:36
 */

package cn.herodotus.eurynome.security.response.exception;

import org.springframework.security.access.AccessDeniedException;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: AccessDeniedAuthorityLimitedException </p>
 *
 * <p>Description: 没有权限错误 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/6/5 15:36
 */
public class AccessDeniedAuthorityLimitedException extends AccessDeniedException {

    public AccessDeniedAuthorityLimitedException() {
        super("Not authorized to access");
    }

    public AccessDeniedAuthorityLimitedException(String msg) {
        super(msg);
    }

    public AccessDeniedAuthorityLimitedException(String msg, Throwable t) {
        super(msg, t);
    }
}

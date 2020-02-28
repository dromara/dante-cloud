/*
 * Copyright 2019-2019 the original author or authors.
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
 * Project Name: luban-cloud
 * Module Name: luban-cloud-component-security
 * File Name: SessionUtil.java
 * Author: gengwei.zheng
 * Date: 2019/11/18 下午1:09
 * LastModified: 2019/11/17 下午8:55
 */

package cn.herodotus.eurynome.component.security.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 作者：Anenan
 * 来源：CSDN
 * 原文：https://blog.csdn.net/anenan/article/details/88745167
 * 版权声明：本文为博主原创文章，转载请附上博文链接！
 */
public class SessionUtils {
    /**
     * 获取当前session
     *
     * @return
     */
    public static HttpSession session() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request.getSession();
    }
}

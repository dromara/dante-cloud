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
 * File Name: FormLoginWebAuthenticationDetailsSource.java
 * Author: gengwei.zheng
 * Date: 2020/6/8 上午11:55
 * LastModified: 2020/5/23 上午9:32
 */

package cn.herodotus.eurynome.security.authentication.form;

import cn.herodotus.eurynome.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> Description : 自定义AuthenticationDetailsSource </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/29 15:49
 */
@Component
public class FormLoginWebAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest request) {
        return new FormLoginWebAuthenticationDetails(request, securityProperties);
    }
}

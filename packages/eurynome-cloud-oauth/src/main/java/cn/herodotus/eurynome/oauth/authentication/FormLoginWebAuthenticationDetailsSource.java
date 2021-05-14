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
 * Module Name: eurynome-cloud-oauth
 * File Name: FormLoginWebAuthenticationDetailsSource.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.oauth.authentication;

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
public class FormLoginWebAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

    private SecurityProperties securityProperties;

    public FormLoginWebAuthenticationDetailsSource(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest request) {
        return new FormLoginWebAuthenticationDetails(request, securityProperties);
    }
}

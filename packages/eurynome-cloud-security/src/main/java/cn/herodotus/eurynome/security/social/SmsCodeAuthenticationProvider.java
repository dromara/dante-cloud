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
 * File Name: SmsCodeAuthenticationProvider.java
 * Author: gengwei.zheng
 * Date: 2021/3/28 下午7:22
 * LastModified: 2021/3/28 下午7:22
 */

package cn.herodotus.eurynome.security.social;

import cn.herodotus.eurynome.security.definition.service.HerodotusUserDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: SmsCodeAuthenticationProvider </p>
 *
 * <p>Description: 短信验证码认证Provider </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/3/28 19:22
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private HerodotusUserDetailsService herodotusUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;

        UserDetails userDetails = herodotusUserDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());

        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        // TODO 在这里校验验证码是否正确，验证码一般存放到redis中
        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    public HerodotusUserDetailsService getHerodotusUserDetailsService() {
        return herodotusUserDetailsService;
    }

    public void setHerodotusUserDetailsService(HerodotusUserDetailsService herodotusUserDetailsService) {
        this.herodotusUserDetailsService = herodotusUserDetailsService;
    }
}

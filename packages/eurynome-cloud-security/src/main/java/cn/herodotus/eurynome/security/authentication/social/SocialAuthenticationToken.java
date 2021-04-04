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
 * File Name: SocialAuthenticationToken.java
 * Author: gengwei.zheng
 * Date: 2021/3/28 下午7:19
 * LastModified: 2021/3/28 下午7:19
 */

package cn.herodotus.eurynome.security.authentication.social;

import cn.herodotus.eurynome.security.definition.social.SocialProvider;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: SocialAuthenticationToken </p>
 *
 * <p>Description: 拓展的短信验证码Token </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/3/28 19:19
 */
public class SocialAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * 身份
     */
    private final Object principal;
    /**
     * 服务提供商类型
     */
    private SocialProvider socialProvider;

    public SocialAuthenticationToken(Object openId, SocialProvider socialProvider) {
        super(null);
        this.principal = openId;
        this.socialProvider = socialProvider;
        setAuthenticated(false);
    }

    public SocialAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        // must use super, as we override
        super.setAuthenticated(true);
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public SocialProvider getProviderType() {
        return socialProvider;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}

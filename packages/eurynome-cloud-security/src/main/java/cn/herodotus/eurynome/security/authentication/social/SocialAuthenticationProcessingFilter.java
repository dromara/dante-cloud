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
 * File Name: SocialAuthenticationProcessingFilter.java
 * Author: gengwei.zheng
 * Date: 2021/4/3 下午5:12
 * LastModified: 2021/4/3 下午5:12
 */

package cn.herodotus.eurynome.security.authentication.social;

import cn.herodotus.eurynome.security.definition.social.HerodotusSocialDetails;
import cn.herodotus.eurynome.security.definition.social.AbstractSocialHandlerFactory;
import cn.herodotus.eurynome.security.definition.social.SocialHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: SocialAuthenticationProcessingFilter </p>
 *
 * <p>Description: 社交认证处理过滤器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/3 17:12
 */
public class SocialAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private boolean postOnly = true;

    private AbstractSocialHandlerFactory abstractSocialHandlerFactory;

    protected SocialAuthenticationProcessingFilter(String defaultFilterProcessesUrl) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {

        if (postOnly && !httpServletRequest.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + httpServletRequest.getMethod());
        }

        // 封装OpenIdAuthenticationToken
        String providerId = this.obtain(httpServletRequest, "providerId");
        SocialHandler socialHandler = this.abstractSocialHandlerFactory.getSocialHandler(providerId);
        HerodotusSocialDetails herodotusSocialDetails = socialHandler.parseSocialDetails(providerId, httpServletRequest);

        SocialAuthenticationToken authRequest = new SocialAuthenticationToken(herodotusSocialDetails);
        // Allow subclasses to set the "details" property
        setDetails(httpServletRequest, authRequest);

        // 开始认证
        return this.getAuthenticationManager().authenticate(authRequest);
    }

       protected String obtainProviderId(HttpServletRequest request) {
        return this.obtain(request, "providerId");
    }

    private String obtain(HttpServletRequest request, String attribute) {
        String parameter = request.getParameter(attribute);
        return StringUtils.trim(parameter);
    }

    protected void setDetails(HttpServletRequest request, SocialAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public void setHerodotusSocialHandlerFactory(AbstractSocialHandlerFactory abstractSocialHandlerFactory) {
        this.abstractSocialHandlerFactory = abstractSocialHandlerFactory;
    }
}

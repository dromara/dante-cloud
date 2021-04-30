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
 * File Name: SocialAuthenticationProvider.java
 * Author: gengwei.zheng
 * Date: 2021/3/28 下午7:22
 * LastModified: 2021/3/28 下午7:22
 */

package cn.herodotus.eurynome.security.authentication.social;

import cn.herodotus.eurynome.security.definition.service.HerodotusUserDetailsService;
import cn.herodotus.eurynome.security.definition.service.SocialDetailsChecker;
import cn.herodotus.eurynome.security.definition.social.HerodotusSocialDetails;
import cn.herodotus.eurynome.security.response.HerodotusSecurityMessageSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: SocialAuthenticationProvider </p>
 *
 * <p>Description: 短信验证码， 社交登录认证Provider </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/3/28 19:22
 */
@Slf4j
public class SocialAuthenticationProvider implements AuthenticationProvider {

    private final MessageSourceAccessor messages = HerodotusSecurityMessageSource.getAccessor();
    private final UserDetailsChecker accountStatusUserDetailsChecker = new AccountStatusUserDetailsChecker();
    private HerodotusUserDetailsService herodotusUserDetailsService;
    private SocialDetailsChecker socialDetailsChecker;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SocialAuthenticationToken socialAuthenticationToken = (SocialAuthenticationToken) authentication;

        socialDetailsChecker.check((HerodotusSocialDetails) socialAuthenticationToken.getPrincipal());

        UserDetails userDetails = herodotusUserDetailsService.loadUserBySocial((String) socialAuthenticationToken.getPrincipal(), socialAuthenticationToken.getProviderType());

        if (userDetails == null) {
            log.debug("[Eurynome] |- Authentication failed: no credentials provided");

            throw new BadCredentialsException(messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.noopBindAccount", "Noop Bind Account"));
        }

        accountStatusUserDetailsChecker.check(userDetails);

        SocialAuthenticationToken authenticateResult = new SocialAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticateResult.setDetails(socialAuthenticationToken.getDetails());

        return authenticateResult;

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SocialAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void setHerodotusUserDetailsService(HerodotusUserDetailsService herodotusUserDetailsService) {
        this.herodotusUserDetailsService = herodotusUserDetailsService;
    }

    public void setHerodotusVerificationService(SocialDetailsChecker socialDetailsChecker) {
        this.socialDetailsChecker = socialDetailsChecker;
    }
}

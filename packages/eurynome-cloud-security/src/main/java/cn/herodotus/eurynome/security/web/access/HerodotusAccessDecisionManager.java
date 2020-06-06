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
 * File Name: HerodotusAccessDecisionManager.java
 * Author: gengwei.zheng
 * Date: 2020/6/5 下午4:03
 * LastModified: 2020/6/5 下午3:46
 */

package cn.herodotus.eurynome.security.web.access;

import cn.herodotus.eurynome.security.authentication.exception.AccessDeniedAuthorityLimitedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * <p>Description: AccessDecisionManager是访问决策器，决定某个用户具有的角色，是否有足够的权限去访问某个资源 </p>
 * <p>
 * Oauth2是为用户资源的授权（更多的只是认证）定义了一个安全、开放及简单的标准，第三方无需知道用户的账号及密码，就可获取到用户的授权信息，并且这是安全的。
 *
 * @author : gengwei.zheng
 * @date : 2020/5/20 12:27
 */
@Slf4j
public class HerodotusAccessDecisionManager implements AccessDecisionManager {

    /**
     * decide 方法是判定是否拥有权限的决策方法，
     *
     * @param authentication   是自定义UserService中循环添加到 GrantedAuthority 对象中的权限信息集合.
     * @param object           包含客户端发起的请求的requset信息，可转换为 HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
     * @param configAttributes 为InvocationSecurityMetadataSource的getAttributes(Object object)这个方法返回的结果，
     *                         此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在即（即返回值为空）权限表中则放行。
     * @throws AccessDeniedException               拒绝访问
     * @throws InsufficientAuthenticationException 认证不足
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        if (CollectionUtils.sizeIsEmpty(configAttributes)) {
            log.debug("[Herodotus] |- ConfigAttributes is null!");
            return;
        }

        for (ConfigAttribute configAttribute : configAttributes) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            Collection<? extends GrantedAuthority> result = authorities.stream().filter(grantedAuthority -> grantedAuthority.getAuthority().equals(configAttribute.getAttribute())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(result)) {
                log.debug("[Herodotus] |- Permission [{}] is Matched!", configAttribute.getAttribute());
                return;
            }
        }

        throw new AccessDeniedAuthorityLimitedException();
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return attribute instanceof SecurityConfig;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}

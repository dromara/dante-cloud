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
 * File Name: SecurityUtils.java
 * Author: gengwei.zheng
 * Date: 2019/11/18 下午1:12
 * LastModified: 2019/11/18 下午12:11
 */

package cn.herodotus.eurynome.component.security.utils;

import cn.herodotus.eurynome.component.common.utils.BeanUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.herodotus.eurynome.component.common.constants.SecurityConstants;
import cn.herodotus.eurynome.component.security.domain.HerodotusRole;
import cn.herodotus.eurynome.component.security.domain.HerodotusUserDetails;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author LIQIU
 * @date 2018-3-8
 **/
public class SecurityUtils {

    public static SecurityContext getSecurityContext() {
        return SecurityContextHolder.getContext();
    }

    public static Authentication getAuthentication() {
        return getSecurityContext().getAuthentication();
    }

    public static boolean isAuthenticated() {
        return ObjectUtils.isNotEmpty(getAuthentication()) && getAuthentication().isAuthenticated();
    }

    public static Object getDetails() {
        return getAuthentication().getDetails();
    }

    /**
     * 当用户角色发生变化，或者用户角色对应的权限发生变化，那么就从数据库中重新查询用户相关信息
     * @param newHerodotusUserDetails 从数据库中重新查询并生成的用户信息
     */
    public static void reloadAuthority(HerodotusUserDetails newHerodotusUserDetails) {
        // 重新new一个token，因为Authentication中的权限是不可变的.
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                newHerodotusUserDetails, newHerodotusUserDetails.getPassword(),
                newHerodotusUserDetails.getAuthorities());
        token.setDetails(getDetails());
        getSecurityContext().setAuthentication(token);
    }

    /**
     * 获取认证用户信息
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public static HerodotusUserDetails getPrincipal() {
        if (isAuthenticated()) {
            Authentication authentication = getAuthentication();
            if (authentication.getPrincipal() instanceof HerodotusUserDetails) {
                return (HerodotusUserDetails) authentication.getPrincipal();
            }
            if (authentication.getPrincipal() instanceof Map) {
                Map<String, Object> principal = (Map<String, Object>) authentication.getPrincipal();
                return BeanUtils.mapToBean(principal, HerodotusUserDetails.class);
            }
        }

        return null;
    }

    public static String getUsername() {
        HerodotusUserDetails user = getPrincipal();
        if (user != null) {
            return user.getUsername();
        }
        return null;
    }

    public static String getClientId() {
        HerodotusUserDetails user = getPrincipal();
        if (user != null) {
            return user.getClientId();
        }
        return null;
    }

    public static HerodotusUserDetails getPrincipals() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null) {
            if (principal instanceof HerodotusUserDetails) {
                return (HerodotusUserDetails) principal;
            } else if (principal instanceof LinkedHashMap) {
                // TODO: zhangyu 2019/7/15 感觉还可以升级一把，不吐linkedhashmap 直接就是oauth2user
                // 2019/7/20 试验过将OAuth2UserAuthenticationConverter map<string,?>中的?强制转换成oauth2user，试验失败，问题不是很急，可以先放着
                /**
                 * https://blog.csdn.net/m0_37834471/article/details/81814233
                 * cn/itcraftsman/luban/auth/oauth2/OAuth2UserAuthenticationConverter.java
                 */
                HerodotusUserDetails user = new HerodotusUserDetails();
                BeanUtil.fillBeanWithMap((LinkedHashMap) principal, user, true);
                return user;
            } else if (principal instanceof String && principal.equals("anonymousUser")) {
                return null;
            } else {
                throw new IllegalStateException("获取用户数据失败");
            }
        }
        return null;
    }

    public static boolean hasRole(String role) {

        if (!StringUtils.startsWith(role, SecurityConstants.ROLE_PREFIX)) {
            return false;
        }

        HerodotusUserDetails herodotusUserDetails = getPrincipal();
        if (ObjectUtils.isNotEmpty(herodotusUserDetails)) {
            List<HerodotusRole> roles = herodotusUserDetails.getRoles();
            if (CollectionUtils.isNotEmpty(roles)) {
                Collection filteredResult = roles.stream().filter(artisanRole -> artisanRole.getAuthority().equals(role)).collect(Collectors.toList());
                return CollectionUtils.isNotEmpty(filteredResult);
            }
        }

        return false;
    }

    public static String getUserId() {
        HerodotusUserDetails herodotusUserDetails = getPrincipal();
        if (ObjectUtils.isNotEmpty(herodotusUserDetails)) {
            return herodotusUserDetails.getUserId();
        }

        return null;
    }

    public static String getNickName() {
        HerodotusUserDetails herodotusUserDetails = getPrincipal();
        if (ObjectUtils.isNotEmpty(herodotusUserDetails)) {
            return herodotusUserDetails.getNickName();
        }

        return null;
    }

    public static String getAvatar() {
        HerodotusUserDetails herodotusUserDetails = getPrincipal();
        if (ObjectUtils.isNotEmpty(herodotusUserDetails)) {
            return herodotusUserDetails.getAvatar();
        }

        return null;
    }

    public static boolean hasAuthority(String authority) {

        if (!StringUtils.startsWith(authority, SecurityConstants.AUTHORITY_PREFIX)) {
            return false;
        }

        HerodotusUserDetails herodotusUserDetails = getPrincipal();
        if (ObjectUtils.isNotEmpty(herodotusUserDetails)) {
            Collection<? extends GrantedAuthority> authorities = herodotusUserDetails.getAuthorities();
            if (CollectionUtils.isNotEmpty(authorities)) {
                Collection filteredResult = authorities.stream().filter(grantedAuthority -> grantedAuthority.getAuthority().equals(authority)).collect(Collectors.toList());
                return CollectionUtils.isNotEmpty(filteredResult);
            }
        }

        return false;
    }

    public static String encrypt(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

}

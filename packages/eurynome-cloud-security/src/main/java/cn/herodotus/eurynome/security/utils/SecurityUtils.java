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
 * Module Name: eurynome-cloud-security
 * File Name: SecurityUtils.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.security.utils;

import cn.herodotus.eurynome.common.utils.BeanUtils;
import cn.herodotus.eurynome.security.definition.core.HerodotusUserDetails;
import cn.hutool.core.bean.BeanUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>Description: 安全工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/13 16:50
 */
public class SecurityUtils {

    private static final Logger log = LoggerFactory.getLogger(SecurityUtils.class);

    public static final String PREFIX_ROLE = "ROLE_";
    public static final String PREFIX_SCOPE = "SCOPE_";

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
     *
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

    public static String encrypt(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static String[] whitelistToAntMatchers(List<String> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            String[] array = new String[list.size()];
            log.debug("[Herodotus] |- Fetch The REST White List.");
            return list.toArray(array);
        }

        log.warn("[Herodotus] |- Can not Fetch The REST White List Configurations.");
        return new String[]{};
    }

    public static String wellFormRolePrefix(String content) {
        return wellFormPrefix(content, PREFIX_ROLE);
    }

    public static String wellFormPrefix(String content, String prefix) {
        if (StringUtils.startsWith(content, prefix)) {
            return content;
        } else {
            return prefix + content;
        }
    }

}

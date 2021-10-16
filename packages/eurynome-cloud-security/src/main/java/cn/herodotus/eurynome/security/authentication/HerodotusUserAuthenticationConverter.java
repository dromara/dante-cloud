/*
 * Copyright (c) 2019-2021 Gengwei Zheng (herodotus@aliyun.com)
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
 * File Name: HerodotusUserAuthenticationConverter.java
 * Author: gengwei.zheng
 * Date: 2021/06/29 15:58:29
 */

package cn.herodotus.eurynome.security.authentication;

import cn.herodotus.eurynome.security.definition.core.HerodotusUserDetails;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自定义用户转换器
 * 用于token解析
 *
 * @author gengwei.zheng
 */
public class HerodotusUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(USERNAME, authentication.getPrincipal());
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        return response;
    }

    /**
     * 转换为自定义信息
     *
     * @param map
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private Object converterUserDetails(Map<String, ?> map) {
        Map<String, Object> params = new HashMap<>(8);
        for (String key : map.keySet()) {
            if (USERNAME.equals(key)) {
                if (map.get(key) instanceof Map) {
                    params.putAll((Map) map.get(key));
                } else if (map.get(key) instanceof HerodotusUserDetails) {
                    return map.get(key);
                } else {
                    params.put(key, map.get(key));
                }
            } else {
                params.put(key, map.get(key));
            }
        }
        HerodotusUserDetails herodotusUserDetails = BeanUtil.mapToBean(params, HerodotusUserDetails.class, true);
        if (params.get(USERNAME) != null) {
            herodotusUserDetails.setUsername(params.get(USERNAME).toString());
        }

        herodotusUserDetails.setAuthorities(getAuthorities(map));
        return herodotusUserDetails;
    }

    /**
     * 读取认证信息
     *
     * @param map
     * @return
     */
    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        if (map.containsKey(USERNAME)) {
            Object principal = converterUserDetails(map);
            Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
            if (principal != null) {
                HerodotusUserDetails user = (HerodotusUserDetails) principal;
                authorities = user.getAuthorities();
            }
            return new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
        }
        return null;
    }

    /**
     * 获取权限
     *
     * @param map
     * @return
     */
    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        if (!map.containsKey(AUTHORITIES)) {
            return AuthorityUtils.NO_AUTHORITIES;
        }
        Object authorities = map.get(AUTHORITIES);
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                    .collectionToCommaDelimitedString((Collection<?>) authorities));
        }
        throw new IllegalArgumentException("Authorities must be either a String or a Collection");
    }
}

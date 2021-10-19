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
 * Module Name: eurynome-cloud-upms-api
 * File Name: UpmsHelper.java
 * Author: gengwei.zheng
 * Date: 2021/09/19 13:30:19
 */

package cn.herodotus.eurynome.upms.api.helper;


import cn.herodotus.eurynome.assistant.enums.StatusEnum;
import cn.herodotus.eurynome.assistant.constant.SymbolConstants;
import cn.herodotus.eurynome.security.definition.core.HerodotusClientDetails;
import cn.herodotus.eurynome.security.definition.core.HerodotusUserDetails;
import cn.herodotus.eurynome.security.definition.domain.RequestMapping;
import cn.herodotus.eurynome.security.utils.SecurityUtils;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthScopes;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthApplications;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthClientDetails;
import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;
import cn.herodotus.eurynome.upms.api.entity.system.SysRole;
import cn.herodotus.eurynome.upms.api.entity.system.SysUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Description: 实体转换帮助类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/18 11:38
 */
public class UpmsHelper {

    public static HerodotusClientDetails convertOauthClientDetailsToHerodotusClientDetails(OauthClientDetails oauthClientDetails) {
        HerodotusClientDetails herodotusClientDetails = null;

        if (ObjectUtils.isNotEmpty(oauthClientDetails)) {
            herodotusClientDetails = new HerodotusClientDetails(
                    oauthClientDetails.getClientId(),
                    oauthClientDetails.getResourceIds(),
                    oauthClientDetails.getScope(),
                    oauthClientDetails.getAuthorizedGrantTypes(),
                    oauthClientDetails.getAuthorities(),
                    oauthClientDetails.getWebServerRedirectUri());
            herodotusClientDetails.setAccessTokenValiditySeconds(oauthClientDetails.getAccessTokenValidity());
            herodotusClientDetails.setRefreshTokenValiditySeconds(oauthClientDetails.getRefreshTokenValidity());
            herodotusClientDetails.setClientSecret(oauthClientDetails.getClientSecret());

            if (StringUtils.hasText(oauthClientDetails.getAdditionalInformation())) {
                Map<String, Object> additionalInformation = JSON.parseObject(oauthClientDetails.getAdditionalInformation(), new TypeReference<Map<String, Object>>() {
                });
                herodotusClientDetails.setAdditionalInformation(additionalInformation);
            }
        }

        return herodotusClientDetails;
    }


    public static OauthClientDetails convertHerodotusClientDetailsToOauthClientDetails(HerodotusClientDetails herodotusClientDetails) {
        OauthClientDetails oauthClientDetails = new OauthClientDetails();
        oauthClientDetails.setClientId(herodotusClientDetails.getClientId());
        oauthClientDetails.setClientSecret(herodotusClientDetails.getClientSecret());
        oauthClientDetails.setResourceIds(StringUtils.collectionToCommaDelimitedString(herodotusClientDetails.getResourceIds()));
        oauthClientDetails.setScope(StringUtils.collectionToCommaDelimitedString(herodotusClientDetails.getScope()));
        oauthClientDetails.setAuthorizedGrantTypes(StringUtils.collectionToCommaDelimitedString(herodotusClientDetails.getAuthorizedGrantTypes()));
        oauthClientDetails.setWebServerRedirectUri(StringUtils.collectionToCommaDelimitedString(herodotusClientDetails.getRegisteredRedirectUri()));
        oauthClientDetails.setAuthorities(StringUtils.collectionToCommaDelimitedString(herodotusClientDetails.getAuthorities()));
        oauthClientDetails.setAccessTokenValidity(herodotusClientDetails.getAccessTokenValiditySeconds());
        oauthClientDetails.setRefreshTokenValidity(herodotusClientDetails.getRefreshTokenValiditySeconds());
        oauthClientDetails.setAdditionalInformation(JSON.toJSONString(herodotusClientDetails.getAdditionalInformation()));
        return oauthClientDetails;
    }

    public static HerodotusUserDetails convertSysUserToHerodotusUserDetails(SysUser sysUser) {
        HerodotusUserDetails herodotusUserDetails = new HerodotusUserDetails();
        herodotusUserDetails.setUserId(sysUser.getUserId());
        herodotusUserDetails.setUsername(sysUser.getUserName());
        herodotusUserDetails.setPassword(sysUser.getPassword());
        herodotusUserDetails.setNickName(sysUser.getNickName());
        herodotusUserDetails.setAccountNonExpired(sysUser.getStatus() != StatusEnum.EXPIRED);
        herodotusUserDetails.setAccountNonLocked(sysUser.getStatus() != StatusEnum.LOCKING);
        herodotusUserDetails.setEnabled(sysUser.getStatus() == StatusEnum.ENABLE);

        Collection<SimpleGrantedAuthority> authorities = new LinkedHashSet<>();

        for (SysRole sysRole : sysUser.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(SecurityUtils.wellFormRolePrefix(sysRole.getRoleCode())));
            Set<SysAuthority> sysAuthorities = sysRole.getAuthorities();
            if (CollectionUtils.isNotEmpty(sysAuthorities)) {
                sysAuthorities.forEach(sysAuthority -> authorities.add(new SimpleGrantedAuthority((sysAuthority.getAuthorityCode()))));
            }
        }

        herodotusUserDetails.setAuthorities(authorities);

        return herodotusUserDetails;
    }

    public static List<SysAuthority> convertRequestMappingsToSysAuthorities(Collection<RequestMapping> requestMappings) {
        if (CollectionUtils.isNotEmpty(requestMappings)) {
            return requestMappings.stream().map(UpmsHelper::convertRequestMappingToSysAuthority).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private static SysAuthority convertRequestMappingToSysAuthority(RequestMapping requestMapping) {
        SysAuthority sysAuthority = new SysAuthority();
        sysAuthority.setAuthorityId(requestMapping.getMetadataId());
        sysAuthority.setAuthorityName(requestMapping.getMetadataName());
        sysAuthority.setAuthorityCode(requestMapping.getMetadataCode());
        sysAuthority.setRequestMethod(requestMapping.getRequestMethod());
        sysAuthority.setServiceId(requestMapping.getServiceId());
        sysAuthority.setUrl(requestMapping.getUrl());
        sysAuthority.setParentId(requestMapping.getParentId());
        sysAuthority.setClassName(requestMapping.getClassName());
        sysAuthority.setMethodName(requestMapping.getMethodName());
        return sysAuthority;
    }

    public static OauthClientDetails convertOauthApplicationsToOauthClientDetails(OauthApplications oauthApplications, OauthClientDetails oauthClientDetails) {
        if (ObjectUtils.isEmpty(oauthClientDetails)) {
            oauthClientDetails = new OauthClientDetails();
        }

        oauthClientDetails.setClientId(oauthApplications.getAppKey());
        oauthClientDetails.setClientSecret(SecurityUtils.encrypt(oauthApplications.getAppSecret()));

        if (CollectionUtils.isNotEmpty(oauthApplications.getScopes())) {
            String scope = oauthApplications.getScopes().stream().map(OauthScopes::getScopeCode).collect(Collectors.joining(SymbolConstants.COMMA));
            oauthClientDetails.setScope(scope);
        }

        oauthClientDetails.setAdditionalInformation(JSON.toJSONString(oauthApplications));
        return oauthClientDetails;
    }

}

package cn.herodotus.eurynome.upms.api.helper;


import cn.herodotus.eurynome.common.constants.SymbolConstants;
import cn.herodotus.eurynome.common.enums.StatusEnum;
import cn.herodotus.eurynome.security.definition.RequestMapping;
import cn.herodotus.eurynome.security.definition.core.HerodotusAuthority;
import cn.herodotus.eurynome.security.definition.core.HerodotusClientDetails;
import cn.herodotus.eurynome.security.definition.core.HerodotusRole;
import cn.herodotus.eurynome.security.definition.core.HerodotusUserDetails;
import cn.herodotus.eurynome.security.utils.SecurityUtils;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthApplications;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthClientDetails;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthMicroservices;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthScopes;
import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;
import cn.herodotus.eurynome.upms.api.entity.system.SysRole;
import cn.herodotus.eurynome.upms.api.entity.system.SysUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Description: 实体转换帮助类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/18 11:38
 */
public class UpmsHelper {

    public static HerodotusRole convertSysRoleToArtisanRole(SysRole sysRole) {
        HerodotusRole herodotusRole = new HerodotusRole();
        herodotusRole.setRoleId(sysRole.getRoleId());
        herodotusRole.setRoleName(sysRole.getRoleName());
        herodotusRole.setRoleCode(sysRole.getRoleCode());
        return herodotusRole;
    }

    public static List<HerodotusAuthority> convertSysAuthoritiesToHerodotusAuthorities(Collection<SysAuthority> sysAuthorities) {
        if (CollectionUtils.isNotEmpty(sysAuthorities)) {
            return sysAuthorities.stream().map(UpmsHelper::convertSysAuthorityToArtisanAuthority).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private static HerodotusAuthority convertSysAuthorityToArtisanAuthority(SysAuthority sysAuthority) {
        HerodotusAuthority herodotusAuthority = new HerodotusAuthority();
        herodotusAuthority.setAuthorityId(sysAuthority.getAuthorityId());
        herodotusAuthority.setAuthorityName(sysAuthority.getAuthorityName());
        herodotusAuthority.setUrl(sysAuthority.getUrl());
        herodotusAuthority.setParentId(sysAuthority.getParentId());
        herodotusAuthority.setAuthorityCode(sysAuthority.getAuthorityCode());
        herodotusAuthority.setAuthorityType(sysAuthority.getAuthorityType());
        herodotusAuthority.setMenuClass(sysAuthority.getMenuClass());
        herodotusAuthority.setRanking(sysAuthority.getRanking());
        return herodotusAuthority;
    }

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
        herodotusUserDetails.setClientId(sysUser.getEmployeeId());
        herodotusUserDetails.setAccountNonExpired(sysUser.getStatus() != StatusEnum.EXPIRED);
        herodotusUserDetails.setAccountNonLocked(sysUser.getStatus() != StatusEnum.LOCKING);
        herodotusUserDetails.setEnabled(sysUser.getStatus() == StatusEnum.ENABLE);

        List<HerodotusRole> herodotusRoles = new ArrayList<>();
        List<HerodotusAuthority> herodotusAuthorities = new ArrayList<>();

        for (SysRole sysRole : sysUser.getRoles()) {
            herodotusRoles.add(convertSysRoleToArtisanRole(sysRole));
            herodotusAuthorities.addAll(convertSysAuthoritiesToHerodotusAuthorities(sysRole.getAuthorities()));
        }

        herodotusUserDetails.setRoles(herodotusRoles);
        herodotusUserDetails.setAuthorities(herodotusAuthorities);

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

    public static List<RequestMapping> convertSysAuthoritiesToRequestMappings(Collection<SysAuthority> sysAuthorities) {
        if (CollectionUtils.isNotEmpty(sysAuthorities)) {
            return sysAuthorities.stream().map(UpmsHelper::convertSysAuthorityToRequestMapping).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private static RequestMapping convertSysAuthorityToRequestMapping(SysAuthority sysAuthority) {
        RequestMapping requestMapping = new RequestMapping();
        requestMapping.setMetadataId(sysAuthority.getAuthorityId());
        requestMapping.setMetadataCode(sysAuthority.getAuthorityCode());
        requestMapping.setMetadataName(sysAuthority.getAuthorityName());
        requestMapping.setRequestMethod(sysAuthority.getRequestMethod());
        requestMapping.setServiceId(sysAuthority.getServiceId());
        requestMapping.setClassName(sysAuthority.getClassName());
        requestMapping.setMethodName(sysAuthority.getMethodName());
        requestMapping.setUrl(sysAuthority.getUrl());
        requestMapping.setParentId(sysAuthority.getParentId());
        return requestMapping;
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

    public static OauthClientDetails convertOauthMicroserviceToOauthClientDetails(OauthMicroservices oauthMicroservices, OauthClientDetails oauthClientDetails) {
        if (ObjectUtils.isEmpty(oauthClientDetails)) {
            oauthClientDetails = new OauthClientDetails();
        }

        oauthClientDetails.setClientId(oauthMicroservices.getServiceId());
        oauthClientDetails.setClientSecret(SecurityUtils.encrypt(oauthMicroservices.getAppSecret()));

        if (CollectionUtils.isNotEmpty(oauthMicroservices.getScopes())) {
            String scope = oauthMicroservices.getScopes().stream().map(OauthScopes::getScopeCode).collect(Collectors.joining(SymbolConstants.COMMA));
            oauthClientDetails.setScope(scope);
        }

        oauthClientDetails.setAdditionalInformation(JSON.toJSONString(oauthMicroservices));
        return oauthClientDetails;
    }

}

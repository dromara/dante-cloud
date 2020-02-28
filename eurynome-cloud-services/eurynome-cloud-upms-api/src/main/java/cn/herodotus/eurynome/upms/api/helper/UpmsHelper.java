package cn.herodotus.eurynome.upms.api.helper;


import cn.herodotus.eurynome.component.common.definitions.web.RequestMappingResource;
import cn.herodotus.eurynome.component.common.enums.StatusEnum;
import cn.herodotus.eurynome.component.security.domain.HerodotusAuthority;
import cn.herodotus.eurynome.component.security.domain.HerodotusClientDetails;
import cn.herodotus.eurynome.component.security.domain.HerodotusRole;
import cn.herodotus.eurynome.component.security.domain.HerodotusUserDetails;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthClientDetail;
import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;
import cn.herodotus.eurynome.upms.api.entity.system.SysRole;
import cn.herodotus.eurynome.upms.api.entity.system.SysUser;
import org.apache.commons.collections4.CollectionUtils;

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

    public static List<HerodotusAuthority> convertSysAuthoritiesToArtisanAuthorities(Collection<SysAuthority> sysAuthorities) {
        if (CollectionUtils.isNotEmpty(sysAuthorities)) {
            return sysAuthorities.stream().map(UpmsHelper::convertSysAuthorityToArtisanAuthority).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private static HerodotusAuthority convertSysAuthorityToArtisanAuthority(SysAuthority sysAuthority) {
        HerodotusAuthority artisanAuthority = new HerodotusAuthority();
        artisanAuthority.setAuthorityId(sysAuthority.getAuthorityId());
        artisanAuthority.setAuthorityName(sysAuthority.getAuthorityName());
        artisanAuthority.setUrl(sysAuthority.getUrl());
        artisanAuthority.setParentId(sysAuthority.getParentId());
        artisanAuthority.setAuthorityCode(sysAuthority.getAuthorityCode());
        artisanAuthority.setAuthorityType(sysAuthority.getAuthorityType());
        artisanAuthority.setMenuClass(sysAuthority.getMenuClass());
        artisanAuthority.setRanking(sysAuthority.getRanking());
        return artisanAuthority;
    }

    public static HerodotusClientDetails convertSysClientDetailToOAuth2ClientDetails(OauthClientDetail sysClientDetail, Map<String, Object> additionalInformation) {
        HerodotusClientDetails artisanClientDetails = new HerodotusClientDetails(
                sysClientDetail.getClientId(),
                sysClientDetail.getResourceIds(),
                sysClientDetail.getScope(),
                sysClientDetail.getAuthorizedGrantTypes(),
                sysClientDetail.getAuthorities(),
                sysClientDetail.getWebServerRedirectUri());
        artisanClientDetails.setAccessTokenValiditySeconds(sysClientDetail.getAccessTokenValidity());
        artisanClientDetails.setRefreshTokenValiditySeconds(sysClientDetail.getRefreshTokenValidity());
        artisanClientDetails.setClientSecret(sysClientDetail.getClientSecret());
        artisanClientDetails.setAdditionalInformation(additionalInformation);
        return artisanClientDetails;
    }

    public static HerodotusUserDetails convertSysUserToArtisanUserDetails(SysUser sysUser) {
        HerodotusUserDetails artisanUserDetails = new HerodotusUserDetails();
        artisanUserDetails.setUserId(sysUser.getUserId());
        artisanUserDetails.setUsername(sysUser.getUserName());
        artisanUserDetails.setPassword(sysUser.getPassword());
        artisanUserDetails.setNickName(sysUser.getNickName());
        artisanUserDetails.setClientId(sysUser.getEmployeeId());
        artisanUserDetails.setAccountNonExpired(sysUser.getStatus() != StatusEnum.EXPIRED);
        artisanUserDetails.setAccountNonLocked(sysUser.getStatus() != StatusEnum.LOCKING);
        artisanUserDetails.setEnabled(sysUser.getStatus() == StatusEnum.ENABLE);

        List<HerodotusRole> artisanRoles = new ArrayList<>();
        List<HerodotusAuthority> artisanAuthorities = new ArrayList<>();

        for (SysRole sysRole : sysUser.getRoles()) {
            artisanRoles.add(convertSysRoleToArtisanRole(sysRole));
            artisanAuthorities.addAll(convertSysAuthoritiesToArtisanAuthorities(sysRole.getAuthorities()));
        }

        artisanUserDetails.setRoles(artisanRoles);
        artisanUserDetails.setAuthorities(artisanAuthorities);

        return artisanUserDetails;
    }

    public static List<SysAuthority> convertServiceResourceToSysAuthorities(Collection<RequestMappingResource> requestMappingResources) {
        if (CollectionUtils.isNotEmpty(requestMappingResources)) {
            return requestMappingResources.stream().map(UpmsHelper::convertServiceResourceToSysAuthority).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private static SysAuthority convertServiceResourceToSysAuthority(RequestMappingResource requestMappingResource) {
        SysAuthority sysAuthority = new SysAuthority();
        sysAuthority.setAuthorityId(requestMappingResource.getId());
        sysAuthority.setAuthorityName(requestMappingResource.getName());
        sysAuthority.setAuthorityCode(requestMappingResource.getCode());
        sysAuthority.setRequestMethod(requestMappingResource.getRequestMethod());
        sysAuthority.setServiceId(requestMappingResource.getServiceId());
        sysAuthority.setUrl(requestMappingResource.getUrl());
        sysAuthority.setParentId(requestMappingResource.getParentId());
        sysAuthority.setClassName(requestMappingResource.getClassName());
        sysAuthority.setMethodName(requestMappingResource.getMethodName());
        return sysAuthority;
    }

}

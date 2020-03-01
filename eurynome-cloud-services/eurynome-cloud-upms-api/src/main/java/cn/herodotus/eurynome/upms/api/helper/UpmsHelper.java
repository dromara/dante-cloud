package cn.herodotus.eurynome.upms.api.helper;


import cn.herodotus.eurynome.component.common.domain.RequestMappingResource;
import cn.herodotus.eurynome.component.common.enums.StatusEnum;
import cn.herodotus.eurynome.component.security.domain.HerodotusAuthority;
import cn.herodotus.eurynome.component.security.domain.HerodotusClientDetails;
import cn.herodotus.eurynome.component.security.domain.HerodotusRole;
import cn.herodotus.eurynome.component.security.domain.HerodotusUserDetails;
import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;
import cn.herodotus.eurynome.upms.api.entity.system.SysClientDetail;
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

    public static HerodotusClientDetails convertSysClientDetailToOAuth2ClientDetails(SysClientDetail sysClientDetail, Map<String, Object> additionalInformation) {
        HerodotusClientDetails herodotusClientDetails = new HerodotusClientDetails(
                sysClientDetail.getClientId(),
                sysClientDetail.getResourceIds(),
                sysClientDetail.getScope(),
                sysClientDetail.getAuthorizedGrantTypes(),
                sysClientDetail.getAuthorities(),
                sysClientDetail.getWebServerRedirectUri());
        herodotusClientDetails.setAccessTokenValiditySeconds(sysClientDetail.getAccessTokenValidity());
        herodotusClientDetails.setRefreshTokenValiditySeconds(sysClientDetail.getRefreshTokenValidity());
        herodotusClientDetails.setClientSecret(sysClientDetail.getClientSecret());
        herodotusClientDetails.setAdditionalInformation(additionalInformation);
        return herodotusClientDetails;
    }

    public static HerodotusUserDetails convertSysUserToArtisanUserDetails(SysUser sysUser) {
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
        List<HerodotusAuthority> artisanAuthorities = new ArrayList<>();

        for (SysRole sysRole : sysUser.getRoles()) {
            herodotusRoles.add(convertSysRoleToArtisanRole(sysRole));
            artisanAuthorities.addAll(convertSysAuthoritiesToArtisanAuthorities(sysRole.getAuthorities()));
        }

        herodotusUserDetails.setRoles(herodotusRoles);
        herodotusUserDetails.setAuthorities(artisanAuthorities);

        return herodotusUserDetails;
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

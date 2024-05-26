/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Herodotus Cloud.
 *
 * Herodotus Cloud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Herodotus Cloud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.vip>.
 */

package cn.herodotus.cloud.module.social.processor;

import cn.herodotus.stirrup.access.all.processor.AccessHandlerStrategyFactory;
import cn.herodotus.stirrup.access.core.definition.AccessUserDetails;
import cn.herodotus.stirrup.core.identity.domain.AccessPrincipal;
import cn.herodotus.stirrup.core.identity.domain.HerodotusUser;
import cn.herodotus.stirrup.core.identity.domain.SocialUserDetails;
import cn.herodotus.stirrup.core.identity.exception.AccessIdentityVerificationFailedException;
import cn.herodotus.stirrup.core.identity.exception.UsernameAlreadyExistsException;
import cn.herodotus.stirrup.core.identity.handler.AbstractSocialAuthenticationHandler;
import cn.herodotus.stirrup.logic.upms.converter.SysUserToHerodotusUserConverter;
import cn.herodotus.stirrup.logic.upms.entity.security.SysSocialUser;
import cn.herodotus.stirrup.logic.upms.entity.security.SysUser;
import cn.herodotus.stirrup.logic.upms.service.security.SysSocialUserService;
import cn.herodotus.stirrup.logic.upms.service.security.SysUserService;
import cn.herodotus.stirrup.oauth2.core.exception.SocialCredentialsParameterBindingFailedException;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 * <p>Description: 社交登录默认处理器。 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/26 23:44
 */
public class DefaultSocialAuthenticationHandler extends AbstractSocialAuthenticationHandler {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysSocialUserService sysSocialUserService;
    @Autowired
    private AccessHandlerStrategyFactory accessHandlerStrategyFactory;

    private final Converter<SysUser, HerodotusUser> toUser;

    public DefaultSocialAuthenticationHandler() {
        this.toUser = new SysUserToHerodotusUserConverter();
    }

    @Override
    public SocialUserDetails identity(String source, AccessPrincipal accessPrincipal) throws AccessIdentityVerificationFailedException {
        AccessUserDetails accessUserDetails = accessHandlerStrategyFactory.findAccessUserDetails(source, accessPrincipal);

        if (BeanUtil.isNotEmpty(accessUserDetails)) {
            SysSocialUser sysSocialUser = new SysSocialUser();
            BeanUtil.copyProperties(accessUserDetails, sysSocialUser);
            return sysSocialUser;
        }

        throw new AccessIdentityVerificationFailedException("Access Identity Verification Failed!");
    }

    @Override
    public SocialUserDetails isUserExist(SocialUserDetails socialUserDetails) {
        String uuid = socialUserDetails.getId();
        String source = socialUserDetails.getSource();
        if (StringUtils.isNotBlank(uuid) && StringUtils.isNotBlank(source)) {
            return sysSocialUserService.findByUuidAndSource(uuid, source);
        }

        return null;
    }

    @Override
    public HerodotusUser register(SocialUserDetails socialUserDetails) throws UsernameAlreadyExistsException {
        return sysUserService.registerUserDetails(socialUserDetails);
    }

    @Override
    public void binding(String userId, SocialUserDetails socialUserDetails) throws SocialCredentialsParameterBindingFailedException {
        if (socialUserDetails instanceof SysSocialUser sysSocialUser) {
            SysUser sysUser = new SysUser();
            sysUser.setUserId(userId);
            sysSocialUser.setUsers(ImmutableSet.<SysUser>builder().add(sysUser).build());
            sysSocialUserService.saveAndFlush(sysSocialUser);
        }
    }

    @Override
    public void additionalRegisterOperation(HerodotusUser herodotusUserDetails, SocialUserDetails socialUserDetails) {

    }

    @Override
    public HerodotusUser signIn(SocialUserDetails socialUserDetails) {
        if (socialUserDetails instanceof SysSocialUser sysSocialUser) {
            SysUser sysUser = sysSocialUser.getUsers().stream().findFirst().orElse(null);
            if (ObjectUtils.isNotEmpty(sysUser)) {
                return toUser.convert(sysUser);
            } else {
                return null;
            }
        }

        return null;
    }

    @Override
    public void additionalSignInOperation(HerodotusUser herodotusUserDetails, SocialUserDetails newSocialUserDetails, SocialUserDetails oldSocialUserDetails) {
        if (newSocialUserDetails instanceof SysSocialUser newSysSocialUser && oldSocialUserDetails instanceof SysSocialUser oldSysSocialUser) {
            setSocialUserInfo(oldSysSocialUser, newSysSocialUser.getAccessToken(), newSysSocialUser.getExpireIn(), newSysSocialUser.getRefreshToken(), newSysSocialUser.getRefreshTokenExpireIn(), newSysSocialUser.getScope(), newSysSocialUser.getTokenType(), newSysSocialUser.getUid(), newSysSocialUser.getOpenId(), newSysSocialUser.getAccessCode(), newSysSocialUser.getUnionId());
            sysSocialUserService.saveAndFlush(oldSysSocialUser);
        }
    }

    protected void setSocialUserInfo(SysSocialUser sysSocialUser, String accessToken, Integer expireIn, String refreshToken, Integer refreshTokenExpireIn, String scope, String tokenType, String uid, String openId, String accessCode, String unionId) {
        sysSocialUser.setAccessToken(accessToken);
        sysSocialUser.setExpireIn(expireIn);
        sysSocialUser.setRefreshToken(refreshToken);
        sysSocialUser.setRefreshTokenExpireIn(refreshTokenExpireIn);
        sysSocialUser.setScope(scope);
        sysSocialUser.setTokenType(tokenType);
        sysSocialUser.setUid(uid);
        sysSocialUser.setOpenId(openId);
        sysSocialUser.setAccessCode(accessCode);
        sysSocialUser.setUnionId(unionId);
    }
}

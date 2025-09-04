/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Cloud 采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package org.dromara.dante.module.social.processor;

import cn.herodotus.engine.access.all.processor.AccessHandlerStrategyFactory;
import cn.herodotus.engine.access.core.definition.AccessUserDetails;
import cn.herodotus.engine.access.core.exception.AccessIdentityVerificationFailedException;
import cn.herodotus.engine.core.identity.domain.AccessPrincipal;
import cn.herodotus.engine.oauth2.core.definition.domain.HerodotusUser;
import cn.herodotus.engine.oauth2.core.definition.domain.SocialUserDetails;
import cn.herodotus.engine.oauth2.core.definition.handler.AbstractSocialAuthenticationHandler;
import cn.herodotus.engine.oauth2.core.exception.SocialCredentialsParameterBindingFailedException;
import cn.herodotus.engine.oauth2.core.exception.UsernameAlreadyExistsException;
import cn.herodotus.engine.supplier.upms.logic.converter.SysUserToHerodotusUserConverter;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysSocialUser;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysUser;
import cn.herodotus.engine.supplier.upms.logic.service.security.SysSocialUserService;
import cn.herodotus.engine.supplier.upms.logic.service.security.SysUserService;
import cn.hutool.v7.core.bean.BeanUtil;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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
        String uuid = socialUserDetails.getUuid();
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

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

package cn.herodotus.cloud.rpc.server.upms.autoconfigure.feign;

import cn.herodotus.stirrup.core.definition.domain.Result;
import cn.herodotus.stirrup.core.identity.domain.AccessPrincipal;
import cn.herodotus.stirrup.core.identity.domain.HerodotusUser;
import cn.herodotus.stirrup.core.identity.handler.AbstractSocialAuthenticationHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: 社交用户登录接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/20 18:19
 */
@RestController
@RequestMapping("/security/social")
@Tag(name = "社交用户登录接口")
public class SocialSignInController {

    private final AbstractSocialAuthenticationHandler socialAuthenticationHandler;

    @Autowired
    public SocialSignInController(AbstractSocialAuthenticationHandler socialAuthenticationHandler) {
        this.socialAuthenticationHandler = socialAuthenticationHandler;
    }

    @Operation(summary = "社交登录用户信息查询", description = "根据不同的source查询对应社交用户的信息")
    @Parameters({
            @Parameter(name = "source", required = true, description = "系统用户名", in = ParameterIn.PATH),
    })
    @RequestMapping("/sign-in/{source}")
    public Result<HerodotusUser> findUserDetailsBySocial(@PathVariable("source") String source, AccessPrincipal accessPrincipal) {
        HerodotusUser herodotusUser = this.socialAuthenticationHandler.authentication(source, accessPrincipal);
        if (ObjectUtils.isNotEmpty(herodotusUser)) {
            return Result.success("社交登录成功", herodotusUser);
        } else {
            return Result.failure("社交登录失败，未能查到用户数据");
        }
    }
}

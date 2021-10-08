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
 * Module Name: eurynome-cloud-oauth
 * File Name: IdentityController.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.oauth.controller;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.data.domain.SecretKey;
import cn.herodotus.eurynome.oauth.dto.Session;
import cn.herodotus.eurynome.oauth.dto.SessionCreate;
import cn.herodotus.eurynome.oauth.dto.SessionExchange;
import cn.herodotus.eurynome.security.definition.core.HerodotusUserDetails;
import cn.herodotus.eurynome.oauth.service.InterfaceSecurityService;
import cn.herodotus.eurynome.security.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author gengwei.zheng
 * @refer:https://conkeyn.iteye.com/blog/2296406
 */
@RestController
@Tag(name = "平台认证接口")
public class IdentityController {

    private final Logger log = LoggerFactory.getLogger(IdentityController.class);

    @Resource
    private ConsumerTokenServices consumerTokenServices;

    @Autowired
    private InterfaceSecurityService interfaceSecurityService;

    /**
     * 获取用户基础信息
     *
     * @return Result<HerodotusUserDetails>
     */
    @Operation(summary = "获取当前登录用户信息", description = "获取当前登录用户信息")
    @GetMapping("/identity/profile")
    public Result<HerodotusUserDetails> getUserProfile() {
        HerodotusUserDetails userDetails = SecurityUtils.getPrincipal();
        log.debug("[Herodotus] |- Get oauth profile！");
        return Result.content(userDetails);
    }

    @Operation(summary = "注销退出登录", description = "退出系统，注销登录")
    @Parameters({
            @Parameter(name = "access_token", required = true, description = "已分配的Token"),
    })
    @GetMapping("/open/identity/logout")
    public Result<String> userLogout(@RequestParam("access_token") String accessToken) {
        if (consumerTokenServices.revokeToken(accessToken)) {
            return Result.success("注销成功！");
        } else {
            return Result.failure("注销失败！");
        }
    }

    @Operation(summary = "获取后台加密公钥", description = "根据未登录时的身份标识，在后台创建RSA公钥和私钥。身份标识为前端的唯一标识，如果为空，则在后台创建一个",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "自定义Session", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "sessionCreate", required = true, description = "Session创建请求参数", schema = @Schema(implementation = SessionCreate.class)),
    })
    @PostMapping("/open/identity/session")
    public Result<Session> codeToSession(@Validated @RequestBody SessionCreate sessionCreate) {

        SecretKey secretKey = interfaceSecurityService.createSecretKey(sessionCreate.getClientId(), sessionCreate.getClientSecret(), sessionCreate.getSessionId());
        if (ObjectUtils.isNotEmpty(secretKey)) {
            Session session = new Session();
            session.setSessionId(secretKey.getSessionId());
            session.setPublicKey(interfaceSecurityService.appendPkcs8PublicKeyPadding(secretKey.getPublicKeyBase64()));

            return Result.content(session);
        }

        return Result.failure();
    }

    @Operation(summary = "获取AES秘钥", description = "用后台publicKey，加密前台publicKey，到后台换取AES秘钥",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "加密后的AES", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "sessionExchange", required = true, description = "秘钥交换", schema = @Schema(implementation = SessionExchange.class)),
    })
    @PostMapping("/open/identity/exchange")
    public Result<String> exchange(@Validated @RequestBody SessionExchange sessionExchange) {

        String encryptedAesKey = interfaceSecurityService.exchange(sessionExchange.getSessionId(), sessionExchange.getConfidential());
        if (StringUtils.isNotEmpty(encryptedAesKey)) {
            return Result.content(encryptedAesKey);
        }

        return Result.failure();
    }
}

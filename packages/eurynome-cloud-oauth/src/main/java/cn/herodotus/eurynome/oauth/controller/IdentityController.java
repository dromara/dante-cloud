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
import cn.herodotus.eurynome.security.definition.core.HerodotusUserDetails;
import cn.herodotus.eurynome.security.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author gengwei.zheng
 * @refer:https://conkeyn.iteye.com/blog/2296406
 */
@Slf4j
@RestController
@Tag(name = "认证接口")
public class IdentityController {

    @Resource
    private ConsumerTokenServices consumerTokenServices;

    /**
     * 获取用户基础信息
     *
     * @return Result<HerodotusUserDetails>
     */
    @Operation(summary = "获取当前登录用户信息", description = "获取当前登录用户信息")
    @GetMapping("/identity/profile")
    public Result<HerodotusUserDetails> getUserProfile() {

        HerodotusUserDetails userDetails = SecurityUtils.getPrincipal();
        Result<HerodotusUserDetails> result = new Result<>();

        log.debug("[Eurynome] |- Get oauth profile！");
        return result.ok().data(userDetails);
    }

    @GetMapping("/identity/logout")
    public Result<String> userLogout(@RequestParam("access_token") String accessToken) {
        if (consumerTokenServices.revokeToken(accessToken)) {
            return new Result<String>().ok().message("注销成功！");
        } else {
            return new Result<String>().ok().message("注销失败！");
        }
    }

}

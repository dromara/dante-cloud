/*
 * Copyright (c) 2019-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-integration
 * File Name: WxappOpenController.java
 * Author: gengwei.zheng
 * Date: 2021/4/3 下午7:46
 * LastModified: 2021/4/3 下午7:46
 */

package cn.herodotus.eurynome.integration.controller.wxapp;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.integration.domain.wxapp.WxappRequest;
import cn.herodotus.eurynome.integration.service.wxapp.WxappService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: WxappOpenController </p>
 *
 * <p>Description: 微信小程序开放接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/3 19:46
 */
@Slf4j
@RestController
@RequestMapping("/integration/open/wxapp")
@Api(tags = {"第三方集成开放接口", "微信小程序开放接口", "微信小程序", })
public class WxappOpenController {

    @Autowired
    private WxappService wxappService;

    @ApiOperation(value = "微信小程序登录", notes = "利用wx.login获取code，进行小程序登录", consumes = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", required = true, value = "微信小程序code", dataType = "String", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "appId", required = true, value = "微信小程序appId", dataType = "String", dataTypeClass = String.class, paramType = "query"),
    })
    @PostMapping("/login")
    public Result<WxMaJscode2SessionResult> login(@RequestBody WxappRequest wxappRequest) {
        WxMaJscode2SessionResult wxMaSession = wxappService.login(wxappRequest);
        if (ObjectUtils.isNotEmpty(wxMaSession)) {
            return new Result<WxMaJscode2SessionResult>().ok().data(wxMaSession);
        } else {
            return new Result<WxMaJscode2SessionResult>().failed().message("微信小程序登录失败");
        }
    }
}

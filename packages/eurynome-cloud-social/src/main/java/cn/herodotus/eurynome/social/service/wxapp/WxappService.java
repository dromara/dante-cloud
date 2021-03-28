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
 * Module Name: eurynome-cloud-social
 * File Name: WxappService.java
 * Author: gengwei.zheng
 * Date: 2021/3/28 下午5:03
 * LastModified: 2021/3/28 下午5:00
 */

package cn.herodotus.eurynome.social.service.wxapp;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.herodotus.eurynome.social.configuration.WxappConfiguration;
import cn.herodotus.eurynome.social.domain.wxapp.WxappRequest;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: WxMaService </p>
 *
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/3/28 16:21
 */
@Slf4j
@Service
public class WxappService {

    private WxMaService getWxMaService(String appid) {
        return WxappConfiguration.getWxMaService(appid);
    }

    private WxMaService checkUserInfo(WxappRequest wxappRequest) {
        WxMaService wxMaService = getWxMaService(wxappRequest.getAppId());

        // 用户信息校验
        if (!wxMaService.getUserService().checkUserInfo(wxappRequest.getSessionKey(), wxappRequest.getRawData(), wxappRequest.getSignature())) {
            log.error("[Eurynome] |- Weixin Mini App user check failed!");
            return null;
        }

        return wxMaService;
    }

    public WxMaJscode2SessionResult login(WxappRequest wxappRequest) {

        if (StringUtils.isBlank(wxappRequest.getCode())) {
            log.error("[Eurynome] |- Weixin Mini App can not get code param!");
            return null;
        }

        WxMaService wxMaService = getWxMaService(wxappRequest.getAppId());

        try {
            WxMaJscode2SessionResult sessionResult = wxMaService.getUserService().getSessionInfo(wxappRequest.getCode());
            log.debug("[Eurynome] |- Weixin Mini App login successfully!");
            return sessionResult;
        } catch (WxErrorException e) {
            log.error("[Eurynome] |- Weixin Mini App login failed!", e);
            return null;
        }
    }

    public WxMaUserInfo getUserInfo(WxappRequest wxappRequest) {

        WxMaService wxMaService = checkUserInfo(wxappRequest);
        if (wxMaService == null) {
            return null;
        };

        // 解密用户信息
        WxMaUserInfo wxMaUserInfo = wxMaService.getUserService().getUserInfo(wxappRequest.getSessionKey(), wxappRequest.getEncryptedData(), wxappRequest.getIv());
        log.debug("[Eurynome] |- Weixin Mini App get user info successfully!");
        return wxMaUserInfo;
    }

    public WxMaPhoneNumberInfo getPhoneNumberInfo(WxappRequest wxappRequest) {

        WxMaService wxMaService = checkUserInfo(wxappRequest);
        if (wxMaService == null) {
            return null;
        };

        // 解密手机号
        WxMaPhoneNumberInfo wxMaPhoneNumberInfo = wxMaService.getUserService().getPhoneNoInfo(wxappRequest.getSessionKey(), wxappRequest.getEncryptedData(), wxappRequest.getIv());
        log.debug("[Eurynome] |- Weixin Mini App get phone number successfully!");
        return wxMaPhoneNumberInfo;
    }
}

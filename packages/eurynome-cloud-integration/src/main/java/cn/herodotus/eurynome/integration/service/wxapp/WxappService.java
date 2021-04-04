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
 * File Name: WxappService.java
 * Author: gengwei.zheng
 * Date: 2021/4/3 下午7:42
 * LastModified: 2021/4/3 下午3:37
 */

package cn.herodotus.eurynome.integration.service.wxapp;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.herodotus.eurynome.integration.configuration.WxappConfiguration;
import cn.herodotus.eurynome.integration.domain.wxapp.WxappRequest;
import cn.herodotus.eurynome.integration.properties.WxappProperties;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final WxappProperties wxappProperties;

    @Autowired
    public WxappService(WxappProperties wxappProperties) {
        this.wxappProperties = wxappProperties;
    }

    private WxMaService getWxMaService() {
        return this.getWxMaService(null);
    }

    private WxMaService getWxMaService(String appid) {
        if (StringUtils.isBlank(appid)) {
            if (StringUtils.isNotBlank(wxappProperties.getDefaultAppId())) {
                return getWxMaService(wxappProperties.getDefaultAppId());
            } else {
                log.error("[Eurynome] |- Must set [herodotus.social.wx.wxapp.default-app-id] property, or use getWxMaService(String appid)!");
                return null;
            }
        } else {
            return WxappConfiguration.getWxMaService(appid);
        }
    }

    /**
     * 获取登录后的session信息.
     *
     * @param code        登录时获取的 code
     * @param wxMaService 微信小程序服务
     * @return {@link WxMaJscode2SessionResult}
     */
    private WxMaJscode2SessionResult getSessionInfo(String code, WxMaService wxMaService) {
        try {
            WxMaJscode2SessionResult sessionResult = wxMaService.getUserService().getSessionInfo(code);
            log.debug("[Eurynome] |- Weixin Mini App login successfully!");
            return sessionResult;
        } catch (WxErrorException e) {
            log.error("[Eurynome] |- Weixin Mini App login failed! For reason: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 验证用户完整性
     *
     * @param sessionKey  会话密钥
     * @param rawData     微信用户基本信息
     * @param signature   数据签名
     * @param wxMaService 微信小程序服务
     * @return true 完整， false 不完整
     */
    private boolean checkUserInfo(String sessionKey, String rawData, String signature, WxMaService wxMaService) {
        if (wxMaService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            log.debug("[Eurynome] |- Weixin Mini App user info is valid!");
            return true;
        } else {
            log.warn("[Braineex] |- Weixin Mini App user check failed!");
            return false;
        }
    }

    /**
     * 解密用户信息
     *
     * @param sessionKey    会话密钥
     * @param encryptedData 消息密文
     * @param iv            加密算法的初始向量
     * @param wxMaService   微信小程序服务
     * @return {@link WxMaUserInfo}
     */
    private WxMaUserInfo getUserInfo(String sessionKey, String encryptedData, String iv, WxMaService wxMaService) {
        WxMaUserInfo wxMaUserInfo = wxMaService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        log.debug("[Eurynome] |- Weixin Mini App get user info successfully!");
        return wxMaUserInfo;
    }

    /**
     * 解密手机号
     *
     * @param sessionKey    会话密钥
     * @param encryptedData 消息密文
     * @param iv            加密算法的初始向量
     * @param wxMaService   微信小程序服务
     * @return {@link WxMaPhoneNumberInfo}
     */
    private WxMaPhoneNumberInfo getPhoneNumberInfo(String sessionKey, String encryptedData, String iv, WxMaService wxMaService) {
        WxMaPhoneNumberInfo wxMaPhoneNumberInfo = wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
        log.debug("[Braineex] |- Weixin Mini App get phone number successfully!");
        return wxMaPhoneNumberInfo;
    }

    private boolean checkUserInfo(String rawData, String signature) {
        return StringUtils.isNotBlank(rawData) && StringUtils.isNotBlank(signature);
    }

    public WxMaJscode2SessionResult login(WxappRequest wxappRequest) {
        WxMaService wxMaService = getWxMaService(wxappRequest.getAppId());
        if (StringUtils.isNotBlank(wxappRequest.getCode()) && ObjectUtils.isNotEmpty(wxMaService)) {
            return this.getSessionInfo(wxappRequest.getCode(), wxMaService);
        } else {
            log.error("[Eurynome] |- Weixin Mini App login failed, please check code param!");
            return null;
        }
    }

    public WxMaUserInfo getUserInfo(WxappRequest wxappRequest) {

        WxMaService wxMaService = getWxMaService(wxappRequest.getAppId());

        if (ObjectUtils.isNotEmpty(wxMaService)) {
            // 用户信息校验
            if (checkUserInfo(wxappRequest.getRawData(), wxappRequest.getSignature())) {
                if (checkUserInfo(wxappRequest.getSessionKey(), wxappRequest.getRawData(), wxappRequest.getSignature(), wxMaService)) {
                    return null;
                }
            }

            return this.getUserInfo(wxappRequest.getSessionKey(), wxappRequest.getEncryptedData(), wxappRequest.getIv(), wxMaService);
        } else {
            log.error("[Eurynome] |- Weixin Mini App get user info failed!");
            return null;
        }
    }

    public WxMaPhoneNumberInfo getPhoneNumberInfo(WxappRequest wxappRequest) {

        WxMaService wxMaService = getWxMaService(wxappRequest.getAppId());

        if (ObjectUtils.isNotEmpty(wxMaService)) {
            // 用户信息校验
            if (checkUserInfo(wxappRequest.getRawData(), wxappRequest.getSignature())) {
                if (checkUserInfo(wxappRequest.getSessionKey(), wxappRequest.getRawData(), wxappRequest.getSignature(), wxMaService)) {
                    return null;
                }
            }

            return this.getPhoneNumberInfo(wxappRequest.getSessionKey(), wxappRequest.getEncryptedData(), wxappRequest.getIv(), wxMaService);
        } else {
            log.error("[Eurynome] |- Weixin Mini App get phone number info failed!");
            return null;
        }
    }
}

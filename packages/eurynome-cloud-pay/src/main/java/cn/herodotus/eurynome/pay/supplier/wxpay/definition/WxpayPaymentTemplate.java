/*
 * Copyright (c) 2019-2022 Gengwei Zheng (herodotus@aliyun.com)
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
 * Module Name: eurynome-cloud-pay
 * File Name: WxpayPaymentTemplate.java
 * Author: gengwei.zheng
 * Date: 2022/01/11 15:03:11
 */

package cn.herodotus.eurynome.pay.supplier.wxpay.definition;

import cn.herodotus.eurynome.pay.exception.PaymentProfileIdIncorrectException;
import cn.herodotus.eurynome.pay.exception.PaymentProfileNotFoundException;
import cn.herodotus.eurynome.pay.supplier.wxpay.properties.WxpayProperties;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Description: 微信支付配置属性 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/11 15:03
 */
public class WxpayPaymentTemplate {

    private static final Logger log = LoggerFactory.getLogger(WxpayPaymentTemplate.class);

    private final WxpayProfileStorage wxpayProfileStorage;
    private final WxpayProperties wxpayProperties;

    public WxpayPaymentTemplate(WxpayProfileStorage wxpayProfileStorage, WxpayProperties wxpayProperties) {
        this.wxpayProfileStorage = wxpayProfileStorage;
        this.wxpayProperties = wxpayProperties;
    }

    private WxpayProfileStorage getWxpayProfileStorage() {
        return wxpayProfileStorage;
    }

    private WxpayProperties getWxpayProperties() {
        return wxpayProperties;
    }

    private WxpayProfile getProfile(String identity) {
        WxpayProfile wxpayProfile = getWxpayProfileStorage().getProfile(identity);
        if (ObjectUtils.isNotEmpty(wxpayProfile)) {
            return wxpayProfile;
        } else {
            throw new PaymentProfileNotFoundException("Payment profile for " + identity + " not found.");
        }
    }

    private WxpayPaymentExecuter getProcessor(Boolean sandbox, WxpayProfile wxpayProfile) {

        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(wxpayProfile.getAppId());
        payConfig.setMchId(wxpayProfile.getMchId());
        payConfig.setMchKey(wxpayProfile.getMchKey());
        payConfig.setSubAppId(wxpayProfile.getSubAppId());
        payConfig.setSubMchId(wxpayProfile.getSubMchId());
        payConfig.setKeyPath(wxpayProfile.getKeyPath());

        // 可以指定是否使用沙箱环境
        payConfig.setUseSandboxEnv(sandbox);

        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        return new WxpayPaymentExecuter(wxPayService);
    }

    public WxpayPaymentExecuter getProcessor(String identity) {

        String id = StringUtils.isNotBlank(identity) ? identity : getWxpayProperties().getDefaultProfile();

        if (StringUtils.isBlank(id)) {
            throw new PaymentProfileIdIncorrectException("Payment profile incorrect, or try to set default profile id.");
        }

        WxpayProfile wxpayProfile = getProfile(identity);
        return getProcessor(getWxpayProperties().getSandbox(), wxpayProfile);
    }
}

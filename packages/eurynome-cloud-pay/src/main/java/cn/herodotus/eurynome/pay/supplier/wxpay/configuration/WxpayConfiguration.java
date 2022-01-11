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
 * File Name: WxpayConfiguration.java
 * Author: gengwei.zheng
 * Date: 2022/01/11 15:05:11
 */

package cn.herodotus.eurynome.pay.supplier.wxpay.configuration;

import cn.herodotus.eurynome.pay.supplier.wxpay.annotation.ConditionalOnWxpay;
import cn.herodotus.eurynome.pay.supplier.wxpay.definition.WxpayPaymentTemplate;
import cn.herodotus.eurynome.pay.supplier.wxpay.definition.WxpayProfileStorage;
import cn.herodotus.eurynome.pay.supplier.wxpay.properties.WxpayProperties;
import cn.herodotus.eurynome.pay.supplier.wxpay.support.WxpayDefaultProfileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * <p>Description: 微信支付 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/11 15:05
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWxpay
@EnableConfigurationProperties(WxpayProperties.class)
public class WxpayConfiguration {

    private static final Logger log = LoggerFactory.getLogger(WxpayConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Plugin [Herodotus Wxpay] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public WxpayProfileStorage wxpayDefaultProfileStorage(WxpayProperties wxpayProperties) {
        WxpayDefaultProfileStorage wxpayDefaultProfileStorage = new WxpayDefaultProfileStorage(wxpayProperties);
        log.debug("[Herodotus] |- Bean [Wxpay Default Profile Storage] Auto Configure.");
        return wxpayDefaultProfileStorage;
    }

    @Bean
    @ConditionalOnMissingBean
    public WxpayPaymentTemplate wxpayPaymentTemplate(WxpayProfileStorage wxpayProfileStorage, WxpayProperties wxpayProperties) {
        WxpayPaymentTemplate wxpayPaymentTemplate = new WxpayPaymentTemplate(wxpayProfileStorage, wxpayProperties);
        log.trace("[Herodotus] |- Bean [Wxpay Payment Template] Auto Configure.");
        return wxpayPaymentTemplate;
    }
}

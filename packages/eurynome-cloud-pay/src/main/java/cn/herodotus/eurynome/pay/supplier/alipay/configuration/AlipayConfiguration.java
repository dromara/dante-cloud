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
 * File Name: AlipayConfiguration.java
 * Author: gengwei.zheng
 * Date: 2022/01/11 14:59:11
 */

package cn.herodotus.eurynome.pay.supplier.alipay.configuration;

import cn.herodotus.eurynome.pay.supplier.alipay.annotation.ConditionalOnAlipay;
import cn.herodotus.eurynome.pay.supplier.alipay.definition.AlipayPaymentTemplate;
import cn.herodotus.eurynome.pay.supplier.alipay.definition.AlipayProfileStorage;
import cn.herodotus.eurynome.pay.supplier.alipay.properties.AlipayProperties;
import cn.herodotus.eurynome.pay.supplier.alipay.support.AlipayDefaultProfileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * <p>Description: 支付宝配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/11 14:59
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnAlipay
@EnableConfigurationProperties(AlipayProperties.class)
@ComponentScan(basePackages = {
        "cn.herodotus.eurynome.pay.supplier.alipay.controller"
})
public class AlipayConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AlipayConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Plugin [Herodotus Alipay] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public AlipayProfileStorage alipayProfileDefaultStorage(AlipayProperties alipayProperties) {
        AlipayDefaultProfileStorage alipayDefaultProfileStorage = new AlipayDefaultProfileStorage(alipayProperties);
        log.debug("[Herodotus] |- Bean [Alipay Profile Default Storage] Auto Configure.");
        return alipayDefaultProfileStorage;
    }

    @Bean
    @ConditionalOnMissingBean
    public AlipayPaymentTemplate alipayPaymentTemplate(AlipayProfileStorage alipayProfileStorage, AlipayProperties alipayProperties) {
        AlipayPaymentTemplate alipayPaymentTemplate = new AlipayPaymentTemplate(alipayProfileStorage, alipayProperties);
        log.trace("[Herodotus] |- Bean [Alipay Payment Template] Auto Configure.");
        return alipayPaymentTemplate;
    }
}

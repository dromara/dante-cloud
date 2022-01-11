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
 * File Name: WxpayDefaultProfileStorage.java
 * Author: gengwei.zheng
 * Date: 2022/01/11 15:05:11
 */

package cn.herodotus.eurynome.pay.supplier.wxpay.support;

import cn.herodotus.eurynome.pay.supplier.wxpay.definition.WxpayProfile;
import cn.herodotus.eurynome.pay.supplier.wxpay.definition.WxpayProfileStorage;
import cn.herodotus.eurynome.pay.supplier.wxpay.properties.WxpayProperties;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p>Description: 微信支付配置，配置文件文件存储 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/11 15:05
 */
@Component
public class WxpayDefaultProfileStorage extends WxpayProfileStorage {

    private final WxpayProperties wxpayProperties;

    public WxpayDefaultProfileStorage(WxpayProperties wxpayProperties) {
        this.wxpayProperties = wxpayProperties;
    }

    private WxpayProperties getWxpayProperties() {
        return wxpayProperties;
    }

    @Override
    public WxpayProfile getProfile(String identity) {
        Map<String, WxpayProfile> profiles = getWxpayProperties().getProfiles();
        if (MapUtils.isNotEmpty(profiles)) {
            return profiles.get(identity);
        }

        return null;
    }
}

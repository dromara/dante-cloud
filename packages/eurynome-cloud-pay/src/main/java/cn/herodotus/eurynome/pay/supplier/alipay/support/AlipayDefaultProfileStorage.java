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
 * File Name: AlipayDefaultProfileStorage.java
 * Author: gengwei.zheng
 * Date: 2022/01/11 14:56:11
 */

package cn.herodotus.eurynome.pay.supplier.alipay.support;

import cn.herodotus.eurynome.pay.supplier.alipay.definition.AlipayProfile;
import cn.herodotus.eurynome.pay.supplier.alipay.definition.AlipayProfileStorage;
import cn.herodotus.eurynome.pay.supplier.alipay.properties.AlipayProperties;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;

/**
 * <p>Description: 支付宝配置默认存储 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/11 14:56
 */
public class AlipayDefaultProfileStorage extends AlipayProfileStorage {

    private final AlipayProperties alipayProperties;

    public AlipayDefaultProfileStorage(AlipayProperties alipayProperties) {
        this.alipayProperties = alipayProperties;
    }

    @Override
    public AlipayProfile getProfile(String identity) {
        Map<String, AlipayProfile> profiles = alipayProperties.getProfiles();
        if (MapUtils.isNotEmpty(profiles)) {
            return profiles.get(identity);
        }

        return null;
    }
}

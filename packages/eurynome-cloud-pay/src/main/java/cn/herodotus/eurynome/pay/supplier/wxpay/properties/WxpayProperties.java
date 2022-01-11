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
 * File Name: WxpayProperties.java
 * Author: gengwei.zheng
 * Date: 2022/01/11 15:03:11
 */

package cn.herodotus.eurynome.pay.supplier.wxpay.properties;

import cn.herodotus.eurynome.assistant.constant.PropertyConstants;
import cn.herodotus.eurynome.pay.supplier.wxpay.definition.WxpayProfile;
import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * <p>Description: 微信支付配置属性 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/11 15:03
 */
@ConfigurationProperties(prefix = PropertyConstants.PROPERTY_PAY_WXIPAY)
public class WxpayProperties {

    /**
     * 是否开启微信支付使用
     */
    private Boolean enabled;
    /**
     * 是否是使用微信支付仿真环境
     */
    private Boolean sandbox = false;
    /**
     * 默认的 Profile 自定义唯一标识 Key
     */
    private String defaultProfile;
    /**
     * 支付宝支付信息配置，支持多个。以自定义唯一标识作为 Key。
     */
    private Map<String, WxpayProfile> profiles;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getSandbox() {
        return sandbox;
    }

    public void setSandbox(Boolean sandbox) {
        this.sandbox = sandbox;
    }

    public String getDefaultProfile() {
        return defaultProfile;
    }

    public void setDefaultProfile(String defaultProfile) {
        this.defaultProfile = defaultProfile;
    }

    public Map<String, WxpayProfile> getProfiles() {
        return profiles;
    }

    public void setProfiles(Map<String, WxpayProfile> profiles) {
        this.profiles = profiles;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("enabled", enabled)
                .add("sandbox", sandbox)
                .add("defaultProfile", defaultProfile)
                .toString();
    }
}

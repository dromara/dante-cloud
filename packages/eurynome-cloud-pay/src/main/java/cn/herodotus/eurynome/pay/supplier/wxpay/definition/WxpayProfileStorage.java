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
 * File Name: WxpayProfileStorage.java
 * Author: gengwei.zheng
 * Date: 2022/01/11 15:02:11
 */

package cn.herodotus.eurynome.pay.supplier.wxpay.definition;

/**
 * <p>Description: 微信支付配置信息存储定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/11 15:02
 */
public abstract class WxpayProfileStorage {

    /**
     * 获取支付配置信息
     *
     * @param identity 自定义的支付配置信息识别代码
     * @return 微信支付配置信息
     */
    public abstract WxpayProfile getProfile(String identity);
}

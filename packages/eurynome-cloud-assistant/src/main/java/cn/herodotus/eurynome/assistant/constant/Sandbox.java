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
 * Module Name: eurynome-cloud-assistant
 * File Name: Sandbox.java
 * Author: gengwei.zheng
 * Date: 2022/01/11 14:42:11
 */

package cn.herodotus.eurynome.assistant.constant;

/**
 * <p>Description: 统一的 Sandbox 管理 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/11 14:42
 */
public class Sandbox {

    /**
     * 支付宝网关地址
     */
    private static final String ALIPAY_PRODUCTION_SERVER_URL = "https://openapi.alipay.com/gateway.do";
    private static final String ALIPAY_SANDBOX_SERVER_URL = "https://openapi.alipaydev.com/gateway.do";

    public static String getAliPayServerUrl(boolean sandbox) {
        return sandbox ? ALIPAY_SANDBOX_SERVER_URL : ALIPAY_PRODUCTION_SERVER_URL;
    }
}

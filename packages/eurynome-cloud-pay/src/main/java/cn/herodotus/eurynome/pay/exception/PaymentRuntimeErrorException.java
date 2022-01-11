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
 * File Name: PaymentRuntimeErrorException.java
 * Author: gengwei.zheng
 * Date: 2022/01/11 14:31:11
 */

package cn.herodotus.eurynome.pay.exception;

import cn.herodotus.eurynome.assistant.exception.platform.PaymentException;

/**
 * <p>Description: 支持处理错误 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/11 14:31
 */
public class PaymentRuntimeErrorException extends PaymentException {

    public PaymentRuntimeErrorException() {
        super();
    }

    public PaymentRuntimeErrorException(String message) {
        super(message);
    }

    public PaymentRuntimeErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentRuntimeErrorException(Throwable cause) {
        super(cause);
    }

    public PaymentRuntimeErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

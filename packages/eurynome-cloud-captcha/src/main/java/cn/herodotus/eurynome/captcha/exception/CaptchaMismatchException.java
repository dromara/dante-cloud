/*
 * Copyright (c) 2019-2021 Gengwei Zheng (herodotus@aliyun.com)
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
 * Module Name: eurynome-cloud-captcha
 * File Name: CaptchaMismatchException.java
 * Author: gengwei.zheng
 * Date: 2021/12/17 21:39:17
 */

package cn.herodotus.eurynome.captcha.exception;

/**
 * <p>Description: 验证码不匹配错误 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/17 21:39
 */
public class CaptchaMismatchException extends CaptchaException{

    public CaptchaMismatchException() {
        super();
    }

    public CaptchaMismatchException(String message) {
        super(message);
    }

    public CaptchaMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaptchaMismatchException(Throwable cause) {
        super(cause);
    }

    public CaptchaMismatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

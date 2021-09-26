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
 * Module Name: eurynome-cloud-upms-api
 * File Name: VerificationCodeFeignService.java
 * Author: gengwei.zheng
 * Date: 2021/09/25 10:41:25
 */

package cn.herodotus.eurynome.upms.api.service.fegin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Description: VerificationCodeFeignService </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/3 16:19
 */
public interface VerificationCodeFeignService {
    /**
     * 生成图形验证码
     * @throws Exception
     */
    /**
     * 生成图形验证码
     *
     * @param httpServletRequest  请求对象
     * @param httpServletResponse 响应对象
     * @throws Exception 绘制错误
     */
    @GetMapping("/captcha")
    void getVerificationCode(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception;

    /**
     * 验证
     *
     * @param code 传输代码
     * @return 是否正确
     */
    @PostMapping("/captcha")
    Boolean checkVerificationCode(@RequestBody String code);

}

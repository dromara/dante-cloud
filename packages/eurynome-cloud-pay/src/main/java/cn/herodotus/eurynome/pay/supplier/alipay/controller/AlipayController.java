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
 * File Name: AlipayController.java
 * Author: gengwei.zheng
 * Date: 2022/01/11 14:58:11
 */

package cn.herodotus.eurynome.pay.supplier.alipay.controller;

import cn.herodotus.eurynome.assistant.domain.Result;
import cn.herodotus.eurynome.pay.supplier.alipay.definition.AlipayPaymentTemplate;
import cn.herodotus.eurynome.pay.supplier.alipay.domain.PagePayDetails;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.response.AlipayTradePagePayResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: 支付宝电脑网站支付 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/11 14:58
 */
@RestController
@RequestMapping("/pay/alipay")
@Tags({@Tag(name = "支付接口"), @Tag(name = "支付宝支付接口")})
public class AlipayController {

    @Autowired
    private AlipayPaymentTemplate alipayPaymentTemplate;

    @Operation(summary = "电脑网站支付", description = "支付宝电脑网站支付接口", responses = {@ApiResponse(description = "单位列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class)))})
    @Parameters({
            @Parameter(name = "details", required = true, description = "支付宝电脑网站支付DTO", schema = @Schema(implementation = PagePayDetails.class))
    })
    @PostMapping("/pagepay")
    public Result<String> pagePay(@Validated PagePayDetails details) {
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(details.getTradeNo());
        model.setSubject(details.getSubject());
        model.setBody(details.getBody());
        model.setTotalAmount(details.getTotalAmount());
        /*
          销售产品码，与支付宝签约的产品码名称。注：目前电脑支付场景下仅支持FAST_INSTANT_TRADE_PAY
         */
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        /*
          PC扫码支付的方式。
          支持前置模式和跳转模式。
          前置模式是将二维码前置到商户的订单确认页的模式。需要商户在自己的页面中以 iframe 方式请求支付宝页面。具体支持的枚举值有以下几种：
          0：订单码-简约前置模式，对应 iframe 宽度不能小于600px，高度不能小于300px；
          1：订单码-前置模式，对应iframe 宽度不能小于 300px，高度不能小于600px；
          3：订单码-迷你前置模式，对应 iframe 宽度不能小于 75px，高度不能小于75px；
          4：订单码-可定义宽度的嵌入式二维码，商户可根据需要设定二维码的大小。

          跳转模式下，用户的扫码界面是由支付宝生成的，不在商户的域名下。支持传入的枚举值有：
          2：订单码-跳转模式
         */
        model.setQrPayMode(details.getMode());

        AlipayTradePagePayResponse response;
        if (details.getMode().equals("2")) {
            response = alipayPaymentTemplate.getProcessor(details.getTradeNo(), details.getIdentity() ).pagePay(model, alipayPaymentTemplate.getNotifyUrl(), alipayPaymentTemplate.getReturnUrl(), details.getAppAuthToken(), HttpMethod.GET);
        } else {
            response = alipayPaymentTemplate.getProcessor(details.getTradeNo(), details.getIdentity()).pagePay(model, alipayPaymentTemplate.getNotifyUrl(), alipayPaymentTemplate.getReturnUrl(), details.getAppAuthToken());
        }

        if (ObjectUtils.isNotEmpty(response) && response.isSuccess()) {
            return Result.success("调用支付宝电脑网站支付成功！", response.getBody());
        } else {
            return Result.failure("调用支付宝电脑网站支付失败！");
        }
    }
}

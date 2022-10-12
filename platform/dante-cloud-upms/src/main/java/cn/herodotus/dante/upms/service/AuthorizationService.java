/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dante.upms.service;

import cn.herodotus.engine.assistant.core.definition.http.AbstractRest;
import cn.hutool.core.codec.Base64;
import cn.zhxu.data.TypeRef;
import cn.zhxu.okhttps.MsgConvertor;
import cn.zhxu.okhttps.OkHttps;
import cn.zhxu.okhttps.jackson.JacksonMsgConvertor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>Description: Spring Authorization Server 授权码模式回调测试接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/15 14:11
 */
@Service
public class AuthorizationService extends AbstractRest {

    @Override
    protected String getBaseUrl() {
        return "http://192.168.101.10:8847/dante-cloud-uaa";
    }

    @Override
    protected MsgConvertor getMsgConvertor() {
        return new JacksonMsgConvertor();
    }

    public Map<String, Object> authorized(String code, String state) {

         String clientDetails = "14a9cf797931430896ad13a6b1855611:a05fe1fc50ed42a4990c6c6fc4bec398";

        return http().sync("/oauth2/token")
                .bodyType(OkHttps.JSON)
                .addHeader("Authorization", "Basic " + Base64.encode(clientDetails) )
                .addUrlPara("grant_type", "authorization_code")
                .addUrlPara("code", code)
                .addUrlPara("redirect_uri", "http://192.168.101.10:8847/dante-cloud-upms/open/authorized")
                .post()
                .getBody()
                .toBean(new TypeRef<Map<String, Object>>() {
                });
    }
}

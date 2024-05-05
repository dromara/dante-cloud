/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.cloud.upms.service;

import cn.herodotus.stirrup.core.definition.support.RestApiTemplate;
import cn.zhxu.data.TypeRef;
import cn.zhxu.okhttps.OkHttps;
import org.dromara.hutool.core.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>Description: Spring Authorization Server 授权码模式回调测试接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/15 14:11
 */
@Service
public class AuthorizationService implements RestApiTemplate {

    @Override
    public String getUrl() {
        return "http://192.168.101.10:8847/herodotus-cloud-uaa";
    }

    /**
     * 在发送 /oauth2/authorize 请求时，应该动态创建 state，然后再进行state 的比对。
     *
     * @param code  /oauth2/authorize 请求返回的 code
     * @param state /oauth2/authorize 返回的 state
     * @return /oauth2/token 认证相关信息
     */
    public Map<String, Object> authorized(String code, String state) {

        String clientDetails = "14a9cf797931430896ad13a6b1855611:a05fe1fc50ed42a4990c6c6fc4bec398";

        return http().sync("/oauth2/token")
                .bodyType(OkHttps.JSON)
                .addHeader("Authorization", "Basic " + Base64.encode(clientDetails))
                .addUrlPara("grant_type", "authorization_code")
                .addUrlPara("code", code)
                .addUrlPara("redirect_uri", "http://192.168.101.10:8847/herodotus-cloud-upms/open/authorized")
                .post()
                .getBody()
                .toBean(new TypeRef<Map<String, Object>>() {
                });
    }
}

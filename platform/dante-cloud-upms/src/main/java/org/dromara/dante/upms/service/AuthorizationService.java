/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Dante Cloud.
 *
 * Dante Cloud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dante Cloud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.cn>.
 */

package org.dromara.dante.upms.service;

import cn.herodotus.engine.assistant.definition.support.RestApiTemplate;
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
    public String getBaseUrl() {
        return "http://192.168.101.10:8847/dante-cloud-uaa";
    }

    public Map<String, Object> authorized(String code, String state) {

        String clientDetails = "14a9cf797931430896ad13a6b1855611:a05fe1fc50ed42a4990c6c6fc4bec398";

        return http().sync("/oauth2/token")
                .bodyType(OkHttps.JSON)
                .addHeader("Authorization", "Basic " + Base64.encode(clientDetails))
                .addUrlPara("grant_type", "authorization_code")
                .addUrlPara("code", code)
                .addUrlPara("redirect_uri", "http://192.168.101.10:8847/dante-cloud-upms/open/authorized")
                .post()
                .getBody()
                .toBean(new TypeRef<Map<String, Object>>() {
                });
    }
}

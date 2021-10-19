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
 * Module Name: eurynome-cloud-oauth
 * File Name: HerodotusJwtTokenEnhancer.java
 * Author: gengwei.zheng
 * Date: 2021/08/18 17:48:18
 */

package cn.herodotus.eurynome.oauth.provider.token;

import cn.herodotus.eurynome.assistant.constant.SecurityConstants;
import cn.herodotus.eurynome.security.definition.core.HerodotusUserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
/**
 * <p>Description: 自定义JwtAccessToken增强 </p>
 *
 * jwt生成token 我们可以自己定义jwt里面的内容
 *
 * @author : gengwei.zheng
 * @date : 2020/3/4 16:07
 */
public class HerodotusJwtTokenEnhancer implements TokenEnhancer {

    /**
     * 生成token,添加额外信息。
     * @param accessToken accessToken
     * @param authentication authentication
     * @return OAuth2AccessToken
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken defaultOauth2AccessToken = new DefaultOAuth2AccessToken(accessToken);
        if (authentication.getPrincipal() != null && authentication.getPrincipal() instanceof HerodotusUserDetails) {
            // 设置额外用户信息
            // 与登录时候放进去的UserDetail实现类一直查看link{SecurityConfiguration}
            HerodotusUserDetails herodotusUserDetails = (HerodotusUserDetails) authentication.getPrincipal();
            final Map<String, Object> additionalInfo = new HashMap<>(8);
            additionalInfo.put(SecurityConstants.OPEN_ID, herodotusUserDetails.getUserId());
            additionalInfo.put(SecurityConstants.LICENSE, "herodotus-cloud");
            defaultOauth2AccessToken.setAdditionalInformation(additionalInfo);
        }

        return defaultOauth2AccessToken;
    }
}

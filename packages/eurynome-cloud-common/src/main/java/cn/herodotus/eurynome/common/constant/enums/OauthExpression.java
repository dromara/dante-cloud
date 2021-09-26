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
 * Module Name: eurynome-cloud-constant
 * File Name: OAuth2Expression.java
 * Author: gengwei.zheng
 * Date: 2021/08/14 06:50:14
 */

package cn.herodotus.eurynome.common.constant.enums;

import com.google.common.collect.ImmutableMap;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 安全表达式 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/14 6:50
 */
public enum OauthExpression {
    /**
     * 允许全部
     */
    PERMIT_ALL(0, "permitAll"),
    ANONYMOUS(1, "anonymous"),
    REMEMBER_ME(2, "rememberMe"),
    DENY_ALL(3, "denyAll"),
    AUTHENTICATED(4, "authenticated"),
    FULLY_AUTHENTICATED(5, "fullyAuthenticated"),
    NOT_PERMIT_ALL(6, "!permitAll"),
    NOT_ANONYMOUS(7, "!anonymous"),
    NOT_REMEMBER_ME(8, "!rememberMe"),
    NOT_DENY_ALL(9, "!denyAll"),
    NOT_AUTHENTICATED(10, "!authenticated"),
    NOT_FULLY_AUTHENTICATED(11, "!fullyAuthenticated"),
    HAS_ROLE(12, "hasRole"),
    HAS_ANY_ROLE(13, "hasAnyRole"),
    HAS_AUTHORITY(14, "hasAuthority"),
    HAS_ANY_AUTHORITY(15, "hasAnyAuthority"),
    HAS_IP_ADDRESS(16, "hasIpAddress"),
    CLIENT_HAS_ROLE(17, "#oauth2.clientHasRole"),
    CLIENT_HAS_ANY_ROLE(18, "#oauth2.clientHasAnyRole"),
    HAS_SCOPE(19, "#oauth2.hasScope"),
    HAS_ANY_SCOPE(20, "#oauth2.hasAnyScope"),
    HAS_SCOPE_MATCHING(21, "#oauth2.hasScopeMatching"),
    HAS_ANY_SCOPE_MATCHING(22, "#oauth2.hasAnyScopeMatching"),
    DENY_OAUTH_CLIENT(23, "#oauth2.denyOAuthClient()"),
    IS_OAUTH(24, "#oauth2.isOAuth()"),
    IS_USER(25, "#oauth2.isUser()"),
    IS_CLIENT(26, "#oauth2.isClient()");

    private static final Map<String, OauthExpression> INDEX_MAP = new HashMap<>();
    private static final List<Map<String, Object>> TO_JSON_STRUCT = new ArrayList<>();

    @Schema(title =  "索引")
    private final int index;
    @Schema(title =  "文字")
    private final String content;

    static {
        for (OauthExpression OauthExpression : OauthExpression.values()) {
            INDEX_MAP.put(OauthExpression.name(), OauthExpression);
            TO_JSON_STRUCT.add(OauthExpression.ordinal(),
                    ImmutableMap.<String, Object>builder()
                            .put("value", OauthExpression.name())
                            .put("key", OauthExpression.name())
                            .put("text", OauthExpression.getContent())
                            .build());
        }
    }

    OauthExpression(int index, String content) {
        this.index = index;
        this.content = content;
    }

    public int getIndex() {
        return index;
    }

    public String getContent() {
        return content;
    }

    public static OauthExpression getSecurityExpressions(int index) {
        return INDEX_MAP.get(index);
    }

    public static List<Map<String, Object>> getToJsonStruct() {
        return TO_JSON_STRUCT;
    }
}

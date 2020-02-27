/*
 * Copyright 2019-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
 *
 * Project Name: luban-cloud
 * Module Name: luban-cloud-component-common
 * File Name: OAuth2Constants.java
 * Author: gengwei.zheng
 * Date: 2019/11/8 下午4:15
 * LastModified: 2019/11/8 下午4:08
 */

package cn.herodotus.eurynome.component.common.constants;

import cn.herodotus.eurynome.component.common.enums.OAuth2GrantType;

/**
 * <p>Description: SecurityConstants </p>
 * 
 * @author : gengwei.zheng
 * @date : 2019/11/18 8:37
 */
public class SecurityConstants {

    /**
     * Oauth2 四种模式类型
     */
    public static final String AUTHORIZATION_CODE = OAuth2GrantType.AUTHORIZATION_CODE.getGrant();
    public static final String IMPLICIT = OAuth2GrantType.IMPLICIT.getGrant();
    public static final String PASSWORD = OAuth2GrantType.PASSWORD.getGrant();
    public static final String CLIENT_CREDENTIALS = OAuth2GrantType.CLIENT_CREDENTIALS.getGrant();

    /**
     * 访问 /oauth/authorize地址的两种 response_type类型值
     */

    public static final String TOKEN = "token";

    public static final String BEARER_TYPE = "Bearer";
    public static final String BEARER_TOKEN = BEARER_TYPE + SymbolConstants.SPACE;

    public static final String ROLE_PREFIX = "ROLE_";
    public static final String AUTHORITY_PREFIX = "OP_";

    public final static String OPEN_ID = "openid";

    public static final String CODE = "code";
    public static final String UNKNOWN = "unknown";
    public static final String USERNAME = "username";

    public static final String XML_HTTP_REQUEST = "XMLHttpRequest";

    public static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
    public static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
    public static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
    public static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";
    public static final String X_REAL_IP = "X-Real-IP";
}

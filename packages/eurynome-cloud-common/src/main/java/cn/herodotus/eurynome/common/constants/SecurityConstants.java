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

package cn.herodotus.eurynome.common.constants;

/**
 * <p>Description: 认证授权等安全相关常量值 </p>
 * 
 * @author : gengwei.zheng
 * @date : 2019/11/18 8:37
 */
public class SecurityConstants {

    /**
     * Oauth2 四种模式类型
     */
    public static final String AUTHORIZATION_CODE = "authorization_code";
    public static final String IMPLICIT = "implicit";
    public static final String PASSWORD = "password";
    public static final String CLIENT_CREDENTIALS = "client_credentials";

    /**
     * 访问 /oauth/authorize地址的两种 response_type类型值
     */

    public static final String TOKEN = "token";

    public static final String BEARER_TYPE = "Bearer";
    public static final String BEARER_TOKEN = BEARER_TYPE + SymbolConstants.SPACE;

    public static final String BASIC_TYPE = "Basic";
    public static final String BASIC_TOKEN = BASIC_TYPE + SymbolConstants.SPACE;

    public static final String ROLE_PREFIX = "ROLE_";
    public static final String AUTHORITY_PREFIX = "OP_";
    public static final String AUTHORITY_PREFIX_API = "API_";

    public final static String OPEN_ID = "openid";
    public final static String LICENSE = "license";

    public static final String CODE = "code";


    public static final String GATEWAY_TRACE_HEADER = "Gateway";
    public static final String GATEWAY_STORAGE_KEY = "gateway:trace";
}

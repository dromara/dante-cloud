/*
 * Copyright 2019-2020 the original author or authors.
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
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-operation
 * File Name: OauthPropertyContent.java
 * Author: gengwei.zheng
 * Date: 2020/5/22 下午5:00
 * LastModified: 2020/5/19 下午3:31
 */

package cn.herodotus.eurynome.operation.yaml.domain;

import java.io.Serializable;

/**
 * <p> Description : Security Oauth 配置模拟实体。 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/3 16:10
 */
public class OauthPropertyContent {

    private Security security = new Security();

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public static class Security implements Serializable {

        private Oauth2 oauth2 = new Oauth2();

        public Oauth2 getOauth2() {
            return oauth2;
        }

        public void setOauth2(Oauth2 oauth2) {
            this.oauth2 = oauth2;
        }

        public static class Oauth2 implements Serializable {
            private Client client = new Client();
            private Resource resource = new Resource();

            public Client getClient() {
                return client;
            }

            public void setClient(Client client) {
                this.client = client;
            }

            public Resource getResource() {
                return resource;
            }

            public void setResource(Resource resource) {
                this.resource = resource;
            }

            public static class Client implements Serializable {
                private String clientId;
                private String clientSecret;

                public String getClientId() {
                    return clientId;
                }

                public void setClientId(String clientId) {
                    this.clientId = clientId;
                }

                public String getClientSecret() {
                    return clientSecret;
                }

                public void setClientSecret(String clientSecret) {
                    this.clientSecret = clientSecret;
                }
            }

            public static class Resource implements Serializable {

                private static final String TOKEN_INFO_URI= "${herodotus.platform.token-info-uri}";
                private static final String USER_INFO_URI= "${herodotus.platform.user-info-uri}";

                private String tokenInfoUri = TOKEN_INFO_URI;
                private String userInfoUri = USER_INFO_URI;

                public String getTokenInfoUri() {
                    return tokenInfoUri;
                }

                public void setTokenInfoUri(String tokenInfoUri) {
                    this.tokenInfoUri = tokenInfoUri;
                }

                public String getUserInfoUri() {
                    return userInfoUri;
                }

                public void setUserInfoUri(String userInfoUri) {
                    this.userInfoUri = userInfoUri;
                }
            }
        }
    }
}

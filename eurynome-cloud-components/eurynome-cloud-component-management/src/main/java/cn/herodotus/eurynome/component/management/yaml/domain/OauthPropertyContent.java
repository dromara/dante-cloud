package cn.herodotus.eurynome.component.management.yaml.domain;

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

                private static final String TOKEN_INFO_URI= "${eurynome.platform.token-info-uri}";
                private static final String USER_INFO_URI= "${eurynome.platform.user-info-uri}";

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

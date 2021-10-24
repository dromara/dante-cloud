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
 * Module Name: eurynome-cloud-rest
 * File Name: PlatformProperties.java
 * Author: gengwei.zheng
 * Date: 2021/09/25 10:33:25
 */

package cn.herodotus.eurynome.rest.properties;

import cn.herodotus.eurynome.assistant.enums.ProtocolType;
import cn.herodotus.eurynome.assistant.constant.PropertyConstants;
import cn.herodotus.eurynome.assistant.constant.SecurityConstants;
import cn.herodotus.eurynome.assistant.constant.ServiceConstants;
import cn.herodotus.eurynome.assistant.constant.SymbolConstants;
import cn.herodotus.eurynome.assistant.enums.Architecture;
import com.google.common.base.MoreObjects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: 平台服务相关配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/17 15:22
 */
@Slf4j
@ConfigurationProperties(prefix = PropertyConstants.PROPERTY_HERODOTUS_PLATFORM)
public class PlatformProperties {

    /**
     * 平台架构类型，默认：DISTRIBUTED（分布式架构）
     */
    private Architecture architecture = Architecture.DISTRIBUTED;

    private Endpoint endpoint = new Endpoint();

    private Swagger swagger = new Swagger();

    public Architecture getArchitecture() {
        return architecture;
    }

    public void setArchitecture(Architecture architecture) {
        this.architecture = architecture;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public Swagger getSwagger() {
        return swagger;
    }

    public void setSwagger(Swagger swagger) {
        this.swagger = swagger;
    }

    public static class Endpoint {

        private ProtocolType protocol = ProtocolType.HTTP;
        /**
         * 网关服务地址。可以是IP+端口，可以是域名
         */
        private String gatewayAddress;
        /**
         * 统一认证中心服务地址
         */
        private String uaaServiceUri;
        /**
         * 统一权限管理服务地址
         */
        private String upmsServiceUri;
        /**
         * OAuth2 /oauth/token端点地址，可修改为自定义地址
         */
        private String accessTokenUri;
        /**
         * OAuth2 /oauth/authorize端点地址，可修改为自定义地址
         */
        private String userAuthorizationUri;
        /**
         * OAuth2 /oauth/check_token端点地址，可修改为自定义地址
         */
        private String tokenInfoUri;
        /**
         * 用户信息获取地址
         */
        private String userInfoUri;

        private String convertAddressToUri(String address) {
            String content = address;
            if (StringUtils.endsWith(address, SymbolConstants.FORWARD_SLASH)) {
                content = StringUtils.removeEnd(address, SymbolConstants.FORWARD_SLASH);
            }

            if (StringUtils.startsWith(content, ProtocolType.HTTP.getPrefix())) {
                return content;
            } else {
                return this.getProtocol().getFormat() + content;
            }
        }

        public ProtocolType getProtocol() {
            return protocol;
        }

        public void setProtocol(ProtocolType protocol) {
            this.protocol = protocol;
        }

        public String getGatewayAddress() {
            if (StringUtils.isNotBlank(this.gatewayAddress)) {
                return convertAddressToUri(this.gatewayAddress);
            }
            return gatewayAddress;
        }

        public void setGatewayAddress(String gatewayAddress) {
            this.gatewayAddress = gatewayAddress;
        }

        public String getUaaServiceUri() {
            if (StringUtils.isBlank(this.uaaServiceUri)) {
                return this.getGatewayAddress() + SymbolConstants.FORWARD_SLASH + ServiceConstants.SERVICE_NAME_UAA;
            }
            return uaaServiceUri;
        }

        public void setUaaServiceUri(String uaaServiceUri) {
            this.uaaServiceUri = uaaServiceUri;
        }

        public String getUpmsServiceUri() {
            if (StringUtils.isBlank(this.upmsServiceUri)) {
                return this.getGatewayAddress() + SymbolConstants.FORWARD_SLASH + ServiceConstants.SERVICE_NAME_UPMS;
            }
            return upmsServiceUri;
        }

        public void setUpmsServiceUri(String upmsServiceUri) {
            this.upmsServiceUri = upmsServiceUri;
        }

        public String getAccessTokenUri() {
            if (StringUtils.isBlank(this.accessTokenUri)) {
                return this.getUaaServiceUri() + SecurityConstants.ENDPOINT_OAUTH_TOKEN;
            }
            return accessTokenUri;
        }

        public void setAccessTokenUri(String accessTokenUri) {
            this.accessTokenUri = accessTokenUri;
        }

        public String getUserAuthorizationUri() {
            if (StringUtils.isBlank(this.userAuthorizationUri)) {
                return this.getUserInfoUri() + SecurityConstants.ENDPOINT_OAUTH_AUTHORIZE;
            }
            return userAuthorizationUri;
        }

        public void setUserAuthorizationUri(String userAuthorizationUri) {
            this.userAuthorizationUri = userAuthorizationUri;
        }

        public String getTokenInfoUri() {
            if (StringUtils.isBlank(this.tokenInfoUri)) {
                return this.getUaaServiceUri() + SecurityConstants.ENDPOINT_OAUTH_CHECK_TOKEN;
            }
            return tokenInfoUri;
        }

        public void setTokenInfoUri(String tokenInfoUri) {
            this.tokenInfoUri = tokenInfoUri;
        }

        public String getUserInfoUri() {
            if (StringUtils.isBlank(this.userInfoUri)) {
                return this.getUaaServiceUri() + SecurityConstants.ENDPOINT_OAUTH_IDENTITY_PROFILE;
            }
            return userInfoUri;
        }

        public void setUserInfoUri(String userInfoUri) {
            this.userInfoUri = userInfoUri;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("protocol", protocol)
                    .add("gatewayAddress", gatewayAddress)
                    .add("uaaServiceUri", uaaServiceUri)
                    .add("upmsServiceUri", upmsServiceUri)
                    .add("accessTokenUri", accessTokenUri)
                    .add("userAuthorizationUri", userAuthorizationUri)
                    .add("tokenInfoUri", tokenInfoUri)
                    .add("userInfoUri", userInfoUri)
                    .toString();
        }
    }

    public static class Swagger {

        /**
         * 是否开启Swagger
         */
        private Boolean enabled;

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("enabled", enabled)
                    .toString();
        }
    }
}

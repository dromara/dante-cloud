/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Cloud 采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package org.dromara.dante.authentication.autoconfigure.customizer;

import cn.herodotus.engine.assistant.definition.constants.DefaultConstants;
import cn.herodotus.engine.oauth2.authentication.consumer.OAuth2AuthorizationCodeAuthenticationProviderConsumer;
import cn.herodotus.engine.oauth2.authentication.consumer.OAuth2ClientCredentialsAuthenticationProviderConsumer;
import cn.herodotus.engine.oauth2.authentication.oidc.HerodotusOidcUserInfoMapper;
import cn.herodotus.engine.oauth2.authentication.provider.OAuth2ResourceOwnerPasswordAuthenticationConverter;
import cn.herodotus.engine.oauth2.authentication.provider.OAuth2SocialCredentialsAuthenticationConverter;
import cn.herodotus.engine.oauth2.authentication.response.OAuth2AuthenticationFailureResponseHandler;
import cn.herodotus.engine.oauth2.core.definition.service.ClientDetailsService;
import cn.herodotus.engine.oauth2.management.response.OAuth2AccessTokenResponseHandler;
import cn.herodotus.engine.oauth2.management.response.OAuth2DeviceVerificationResponseHandler;
import cn.herodotus.engine.oauth2.management.response.OidcClientRegistrationResponseHandler;
import cn.herodotus.engine.rest.protect.crypto.processor.HttpCryptoProcessor;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2ClientCredentialsAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2DeviceCodeAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2RefreshTokenAuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.DelegatingAuthenticationConverter;

import java.util.Arrays;

/**
 * <p>Description: AuthorizationServerConfigurer 自定义配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/2/4 12:40
 */
public class OAuth2AuthorizationServerConfigurerCustomizer implements Customizer<OAuth2AuthorizationServerConfigurer> {

    private final HttpSecurity httpSecurity;
    private final SessionRegistry sessionRegistry;
    private final ClientDetailsService clientDetailsService;
    private final HttpCryptoProcessor httpCryptoProcessor;
    private final OidcClientRegistrationResponseHandler oidcClientRegistrationResponseHandler;
    private final OAuth2AuthenticationFailureResponseHandler oauth2AuthenticationFailureResponseHandler;
    private final OAuth2DeviceVerificationResponseHandler oauth2DeviceVerificationResponseHandler;

    public OAuth2AuthorizationServerConfigurerCustomizer(HttpSecurity httpSecurity, SessionRegistry sessionRegistry, ClientDetailsService clientDetailsService, HttpCryptoProcessor httpCryptoProcessor, OidcClientRegistrationResponseHandler oidcClientRegistrationResponseHandler, OAuth2DeviceVerificationResponseHandler oauth2DeviceVerificationResponseHandler) {
        this.httpSecurity = httpSecurity;
        this.sessionRegistry = sessionRegistry;
        this.clientDetailsService = clientDetailsService;
        this.httpCryptoProcessor = httpCryptoProcessor;
        this.oidcClientRegistrationResponseHandler = oidcClientRegistrationResponseHandler;
        this.oauth2DeviceVerificationResponseHandler = oauth2DeviceVerificationResponseHandler;
        this.oauth2AuthenticationFailureResponseHandler = new OAuth2AuthenticationFailureResponseHandler();
    }

    @Override
    public void customize(OAuth2AuthorizationServerConfigurer oauth2AuthorizationServerConfigurer) {

        oauth2AuthorizationServerConfigurer
                .clientAuthentication(endpoint -> {
                    endpoint.errorResponseHandler(oauth2AuthenticationFailureResponseHandler);
                    endpoint.authenticationProviders(new OAuth2ClientCredentialsAuthenticationProviderConsumer(httpSecurity, clientDetailsService));
                })
                .authorizationEndpoint(endpoint -> {
                    endpoint.errorResponseHandler(oauth2AuthenticationFailureResponseHandler);
                    endpoint.consentPage(DefaultConstants.AUTHORIZATION_CONSENT_URI);
                })
                .deviceAuthorizationEndpoint(endpoint -> {
                    endpoint.errorResponseHandler(oauth2AuthenticationFailureResponseHandler);
                    endpoint.verificationUri(DefaultConstants.DEVICE_ACTIVATION_URI);
                })
                .deviceVerificationEndpoint(endpoint -> {
                    endpoint.errorResponseHandler(oauth2AuthenticationFailureResponseHandler);
                    endpoint.consentPage(DefaultConstants.AUTHORIZATION_CONSENT_URI);
                    endpoint.deviceVerificationResponseHandler(oauth2DeviceVerificationResponseHandler);
                })
                .tokenEndpoint(endpoint -> {
                    AuthenticationConverter authenticationConverter = new DelegatingAuthenticationConverter(
                            Arrays.asList(
                                    new OAuth2AuthorizationCodeAuthenticationConverter(),
                                    new OAuth2RefreshTokenAuthenticationConverter(),
                                    new OAuth2ClientCredentialsAuthenticationConverter(),
                                    new OAuth2DeviceCodeAuthenticationConverter(),
                                    new OAuth2ResourceOwnerPasswordAuthenticationConverter(httpCryptoProcessor),
                                    new OAuth2SocialCredentialsAuthenticationConverter(httpCryptoProcessor)
                            ));
                    endpoint.accessTokenRequestConverter(authenticationConverter);
                    endpoint.errorResponseHandler(oauth2AuthenticationFailureResponseHandler);
                    endpoint.accessTokenResponseHandler(new OAuth2AccessTokenResponseHandler(httpCryptoProcessor));
                    endpoint.authenticationProviders(new OAuth2AuthorizationCodeAuthenticationProviderConsumer(httpSecurity, sessionRegistry));
                })
                .tokenIntrospectionEndpoint(endpoint -> endpoint.errorResponseHandler(oauth2AuthenticationFailureResponseHandler))
                .tokenRevocationEndpoint(endpoint -> endpoint.errorResponseHandler(oauth2AuthenticationFailureResponseHandler))
                .oidc(oidc -> oidc.clientRegistrationEndpoint(endpoint -> {
                            endpoint.errorResponseHandler(oauth2AuthenticationFailureResponseHandler);
                            endpoint.clientRegistrationResponseHandler(oidcClientRegistrationResponseHandler);
                        })
                        .userInfoEndpoint(userInfo -> userInfo
                                .userInfoMapper(new HerodotusOidcUserInfoMapper())));
    }
}

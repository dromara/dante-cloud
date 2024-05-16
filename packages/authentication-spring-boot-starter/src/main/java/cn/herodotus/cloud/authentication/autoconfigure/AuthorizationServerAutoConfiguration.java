/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.apache.org/licenses/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.cloud.authentication.autoconfigure;

import cn.herodotus.stirrup.core.autoconfigure.oauth2.properties.OAuth2AuthorizationProperties;
import cn.herodotus.stirrup.core.definition.constants.DefaultConstants;
import cn.herodotus.stirrup.core.foundation.enums.Certificate;
import cn.herodotus.stirrup.core.foundation.utils.ResourceUtils;
import cn.herodotus.stirrup.core.identity.service.ClientDetailsService;
import cn.herodotus.stirrup.logic.identity.response.OAuth2DeviceVerificationResponseHandler;
import cn.herodotus.stirrup.oauth2.authentication.autoconfigure.response.OAuth2AccessTokenResponseHandler;
import cn.herodotus.stirrup.oauth2.authentication.autoconfigure.response.OidcClientRegistrationResponseHandler;
import cn.herodotus.stirrup.oauth2.authentication.configurer.OAuth2AuthenticationProviderConfigurer;
import cn.herodotus.stirrup.oauth2.authentication.consumer.OAuth2AuthorizationCodeAuthenticationProviderConsumer;
import cn.herodotus.stirrup.oauth2.authentication.consumer.OAuth2ClientCredentialsAuthenticationProviderConsumer;
import cn.herodotus.stirrup.oauth2.authentication.consumer.OidcClientRegistrationAuthenticationProviderConsumer;
import cn.herodotus.stirrup.oauth2.authentication.customizer.HerodotusOidcUserInfoMapper;
import cn.herodotus.stirrup.oauth2.authentication.customizer.OAuth2FormLoginConfigurerCustomizer;
import cn.herodotus.stirrup.oauth2.authentication.provider.OAuth2ResourceOwnerPasswordAuthenticationConverter;
import cn.herodotus.stirrup.oauth2.authentication.provider.OAuth2SocialCredentialsAuthenticationConverter;
import cn.herodotus.stirrup.oauth2.authentication.response.DefaultOAuth2AuthenticationEventPublisher;
import cn.herodotus.stirrup.oauth2.authentication.response.OAuth2AuthenticationFailureResponseHandler;
import cn.herodotus.stirrup.oauth2.authentication.utils.OAuth2ConfigurerUtils;
import cn.herodotus.stirrup.oauth2.authorization.servlet.OAuth2ResourceServerConfigurerCustomer;
import cn.herodotus.stirrup.oauth2.authorization.servlet.OAuth2SessionManagementConfigurerCustomer;
import cn.herodotus.stirrup.oauth2.core.properties.OAuth2AuthenticationProperties;
import cn.herodotus.stirrup.web.service.properties.EndpointProperties;
import cn.herodotus.stirrup.web.servlet.crypto.HttpCryptoProcessor;
import cn.herodotus.stirrup.web.servlet.tenant.MultiTenantFilter;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.web.authentication.*;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.UUID;

/**
 * <p>Description: 认证服务器配置 </p>
 * <p>
 * 1. 权限核心处理 {@link org.springframework.security.web.access.intercept.FilterSecurityInterceptor}
 * 2. 默认的权限判断 {@link org.springframework.security.access.vote.AffirmativeBased}
 * 3. 模式决策 {@link org.springframework.security.authentication.ProviderManager}
 * 4. Token 有效期校验 {@link org.springframework.security.oauth2.jwt.JwtTimestampValidator}
 * 5. Token 校验入口 {@link org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider}
 *
 * @author : gengwei.zheng
 * @date : 2022/2/12 20:57
 */
@AutoConfiguration
public class AuthorizationServerAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationServerAutoConfiguration.class);

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(
            HttpSecurity httpSecurity,
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService,
            ClientDetailsService clientDetailsService,
            HttpCryptoProcessor httpCryptoProcessor,
            OidcClientRegistrationResponseHandler oidcClientRegistrationResponseHandler,
            OAuth2AuthenticationProperties oauth2AuthenticationProperties,
            OAuth2DeviceVerificationResponseHandler oauth2DeviceVerificationResponseHandler,
            OAuth2FormLoginConfigurerCustomizer oauth2FormLoginConfigurerCustomizer,
            OAuth2ResourceServerConfigurerCustomer oauth2ResourceServerConfigurerCustomer,
            OAuth2SessionManagementConfigurerCustomer oauth2sessionManagementConfigurerCustomer
    ) throws Exception {

        log.debug("[Herodotus] |- Bean [Authorization Server Security Filter Chain] Auto Configure.");

        SessionRegistry sessionRegistry = OAuth2ConfigurerUtils.getOptionalBean(httpSecurity, SessionRegistry.class);

        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        httpSecurity.with(authorizationServerConfigurer, (configurer) -> {});

        OAuth2AuthenticationFailureResponseHandler errorResponseHandler = new OAuth2AuthenticationFailureResponseHandler();
        authorizationServerConfigurer.clientAuthentication(endpoint -> {
            endpoint.errorResponseHandler(errorResponseHandler);
            endpoint.authenticationProviders(new OAuth2ClientCredentialsAuthenticationProviderConsumer(httpSecurity, clientDetailsService));
        });
        authorizationServerConfigurer.authorizationEndpoint(endpoint -> {
            endpoint.errorResponseHandler(errorResponseHandler);
            endpoint.consentPage(DefaultConstants.AUTHORIZATION_CONSENT_URI);
        });
        authorizationServerConfigurer.deviceAuthorizationEndpoint(endpoint -> {
            endpoint.errorResponseHandler(errorResponseHandler);
            endpoint.verificationUri(DefaultConstants.DEVICE_ACTIVATION_URI);
        });
        authorizationServerConfigurer.deviceVerificationEndpoint(endpoint -> {
            endpoint.errorResponseHandler(errorResponseHandler);
            endpoint.consentPage(DefaultConstants.AUTHORIZATION_CONSENT_URI);
            endpoint.deviceVerificationResponseHandler(oauth2DeviceVerificationResponseHandler);
        });
        authorizationServerConfigurer.tokenEndpoint(endpoint -> {
            AuthenticationConverter authenticationConverter = new DelegatingAuthenticationConverter(
                    Arrays.asList(
                            new OAuth2AuthorizationCodeAuthenticationConverter(),
                            new OAuth2RefreshTokenAuthenticationConverter(),
                            new OAuth2ClientCredentialsAuthenticationConverter(),
                            new OAuth2DeviceCodeAuthenticationConverter(),
                            new OAuth2ResourceOwnerPasswordAuthenticationConverter(httpCryptoProcessor),
                            new OAuth2SocialCredentialsAuthenticationConverter(httpCryptoProcessor)));
            endpoint.accessTokenRequestConverter(authenticationConverter);
            endpoint.errorResponseHandler(errorResponseHandler);
            endpoint.accessTokenResponseHandler(new OAuth2AccessTokenResponseHandler(httpCryptoProcessor));
            endpoint.authenticationProviders(new OAuth2AuthorizationCodeAuthenticationProviderConsumer(httpSecurity, sessionRegistry));
        });
        authorizationServerConfigurer.tokenIntrospectionEndpoint(endpoint -> endpoint.errorResponseHandler(errorResponseHandler));
        authorizationServerConfigurer.tokenRevocationEndpoint(endpoint -> endpoint.errorResponseHandler(errorResponseHandler));
        authorizationServerConfigurer.oidc(oidc -> {
            oidc.clientRegistrationEndpoint(endpoint -> {
                endpoint.errorResponseHandler(errorResponseHandler);
                endpoint.authenticationProviders(new OidcClientRegistrationAuthenticationProviderConsumer());
                endpoint.clientRegistrationResponseHandler(oidcClientRegistrationResponseHandler);
            });
            oidc.userInfoEndpoint(userInfo -> userInfo
                    .userInfoMapper(new HerodotusOidcUserInfoMapper()));
        });

        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();
        // 仅拦截 OAuth2 Authorization Server 的相关 endpoint
        httpSecurity.securityMatcher(endpointsMatcher)
                // 开启请求认证
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                // 禁用对 OAuth2 Authorization Server 相关 endpoint 的 CSRF 防御
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .formLogin(oauth2FormLoginConfigurerCustomizer)
                .sessionManagement(oauth2sessionManagementConfigurerCustomer)
                .addFilterBefore(new MultiTenantFilter(), AuthorizationFilter.class)
                .oauth2ResourceServer(oauth2ResourceServerConfigurerCustomer)
                .with(new OAuth2AuthenticationProviderConfigurer(sessionRegistry, passwordEncoder, userDetailsService, oauth2AuthenticationProperties), (configurer) -> {});

        // 这里增加 DefaultAuthenticationEventPublisher 配置，是为了解决 ProviderManager 在初次使用时，外部定义DefaultAuthenticationEventPublisher 不会注入问题
        // 外部注入 DefaultAuthenticationEventPublisher 是标准配置方法，两处都保留是为了保险，还需要深入研究才能决定去掉哪个。
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        ApplicationContext applicationContext = httpSecurity.getSharedObject(ApplicationContext.class);
        authenticationManagerBuilder.authenticationEventPublisher(new DefaultOAuth2AuthenticationEventPublisher(applicationContext));

        return httpSecurity.build();
    }

    /**
     * The JWK Set endpoint is configured only if a JWKSource<SecurityContext> @Bean is registered.
     *
     * @param oauth2AuthorizationProperties 自定义 OAuth2 Properties {@link OAuth2AuthorizationProperties}
     * @return {@link JWKSource}
     * @throws NoSuchAlgorithmException 不支持当前算法Exception
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource(OAuth2AuthorizationProperties oauth2AuthorizationProperties) throws NoSuchAlgorithmException {

        OAuth2AuthorizationProperties.Jwk jwk = oauth2AuthorizationProperties.getJwk();

        KeyPair keyPair = null;
        if (jwk.getCertificate() == Certificate.CUSTOM) {
            try {
                Resource[] resource = ResourceUtils.getResources(jwk.getJksKeyStore());
                if (ArrayUtils.isNotEmpty(resource)) {
                    KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource[0], jwk.getJksStorePassword().toCharArray());
                    keyPair = keyStoreKeyFactory.getKeyPair(jwk.getJksKeyAlias(), jwk.getJksKeyPassword().toCharArray());
                }
            } catch (IOException e) {
                log.error("[Herodotus] |- Read custom certificate under resource folder error!", e);
            }

        } else {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        }

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    /**
     * OAuth2AuthorizationServerConfiguration.jwtDecoder(JWKSource<SecurityContext>) is a convenience (static) utility method that can be used to register a JwtDecoder @Bean,
     * which is REQUIRED for the OpenID Connect 1.0 UserInfo endpoint and the OpenID Connect 1.0 Client Registration endpoint.
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    /**
     * AuthorizationServerSettings is a REQUIRED component.
     *
     * @param endpointProperties {@link EndpointProperties}
     * @return {@link AuthorizationServerSettings}
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings(EndpointProperties endpointProperties) {
        return AuthorizationServerSettings.builder()
                .issuer(endpointProperties.getIssuerUri())
                .authorizationEndpoint(endpointProperties.getAuthorizationEndpoint())
                .deviceAuthorizationEndpoint(endpointProperties.getDeviceAuthorizationEndpoint())
                .deviceVerificationEndpoint(endpointProperties.getDeviceVerificationEndpoint())
                .tokenEndpoint(endpointProperties.getAccessTokenEndpoint())
                .tokenIntrospectionEndpoint(endpointProperties.getTokenIntrospectionEndpoint())
                .tokenRevocationEndpoint(endpointProperties.getTokenRevocationEndpoint())
                .jwkSetEndpoint(endpointProperties.getJwkSetEndpoint())
                .oidcLogoutEndpoint(endpointProperties.getOidcLogoutEndpoint())
                .oidcUserInfoEndpoint(endpointProperties.getOidcUserInfoEndpoint())
                .oidcClientRegistrationEndpoint(endpointProperties.getOidcClientRegistrationEndpoint())
                .build();
    }
}

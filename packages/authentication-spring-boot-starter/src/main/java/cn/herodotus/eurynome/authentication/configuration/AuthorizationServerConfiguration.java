/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
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
 * Eurynome Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Eurynome Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/eurynome-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/eurynome-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.eurynome.authentication.configuration;

import cn.herodotus.engine.assistant.core.utils.ResourceUtils;
import cn.herodotus.engine.oauth2.authorization.authentication.OAuth2ResourceOwnerPasswordAuthenticationConverter;
import cn.herodotus.engine.oauth2.authorization.authentication.OAuth2ResourceOwnerPasswordAuthenticationProvider;
import cn.herodotus.engine.oauth2.authorization.customizer.HerodotusTokenCustomizer;
import cn.herodotus.engine.oauth2.authorization.utils.OAuth2ConfigurerUtils;
import cn.herodotus.engine.oauth2.core.enums.Certificate;
import cn.herodotus.engine.oauth2.core.properties.OAuth2Properties;
import cn.herodotus.engine.oauth2.core.response.HerodotusAccessDeniedHandler;
import cn.herodotus.engine.oauth2.core.response.HerodotusAuthenticationEntryPoint;
import cn.herodotus.engine.oauth2.core.response.HerodotusAuthenticationFailureHandler;
import cn.herodotus.engine.protect.web.crypto.processor.HttpCryptoProcessor;
import cn.herodotus.engine.web.core.properties.EndpointProperties;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.web.authentication.DelegatingAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2ClientCredentialsAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2RefreshTokenAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.annotation.PostConstruct;
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
 *
 * @author : gengwei.zheng
 * @date : 2022/2/12 20:57
 */
@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationServerConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [OAuth2 Authorization Server] Auto Configure.");
    }


    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity httpSecurity, JwtDecoder jwtDecoder, HttpCryptoProcessor httpCryptoProcessor) throws Exception {

        OAuth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer<>();

        HerodotusAuthenticationFailureHandler failureHandler = new HerodotusAuthenticationFailureHandler();
        authorizationServerConfigurer.clientAuthentication(endpoint -> endpoint.errorResponseHandler(failureHandler));
        authorizationServerConfigurer.authorizationEndpoint(endpoint -> endpoint.errorResponseHandler(failureHandler));
        authorizationServerConfigurer.tokenRevocationEndpoint(endpoint -> endpoint.errorResponseHandler(failureHandler));
        authorizationServerConfigurer.tokenEndpoint(endpoint -> {
            AuthenticationConverter authenticationConverter = new DelegatingAuthenticationConverter(
                    Arrays.asList(
                            new OAuth2AuthorizationCodeAuthenticationConverter(),
                            new OAuth2RefreshTokenAuthenticationConverter(),
                            new OAuth2ClientCredentialsAuthenticationConverter(),
                            new OAuth2ResourceOwnerPasswordAuthenticationConverter(httpCryptoProcessor)));

            endpoint.accessTokenRequestConverter(authenticationConverter);
            endpoint.errorResponseHandler(failureHandler);
        });

        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

        httpSecurity
                .requestMatcher(endpointsMatcher)
                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .oauth2ResourceServer(configurer -> configurer
                        .jwt(jwt -> jwt.decoder(jwtDecoder))
                        .bearerTokenResolver(new DefaultBearerTokenResolver())
                        .accessDeniedHandler(new HerodotusAccessDeniedHandler())
                        .authenticationEntryPoint(new HerodotusAuthenticationEntryPoint()))
                .apply(authorizationServerConfigurer)
                .oidc(oidc -> oidc
                        .userInfoEndpoint(userInfo -> userInfo
                                .userInfoMapper(context -> {
                                    OidcUserInfoAuthenticationToken authentication = context.getAuthentication();
                                    JwtAuthenticationToken principal = (JwtAuthenticationToken) authentication.getPrincipal();
                                    return new OidcUserInfo(principal.getToken().getClaims());
                                })));

        SecurityFilterChain securityFilterChain = httpSecurity.formLogin(Customizer.withDefaults()).build();

        AuthenticationManager authenticationManager = httpSecurity.getSharedObject(AuthenticationManager.class);
        OAuth2AuthorizationService authorizationService = OAuth2ConfigurerUtils.getAuthorizationService(httpSecurity);
        OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator = OAuth2ConfigurerUtils.getTokenGenerator(httpSecurity);

        OAuth2ResourceOwnerPasswordAuthenticationProvider resourceOwnerPasswordAuthenticationProvider =
                new OAuth2ResourceOwnerPasswordAuthenticationProvider(authorizationService, tokenGenerator, authenticationManager);
        httpSecurity.authenticationProvider(resourceOwnerPasswordAuthenticationProvider);

        return securityFilterChain;
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(OAuth2Properties oAuth2Properties) throws NoSuchAlgorithmException {

        OAuth2Properties.Jwk jwk = oAuth2Properties.getJwk();

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
     * jwt 解码
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> oAuth2TokenCustomizer() {
        HerodotusTokenCustomizer herodotusTokenCustomizer = new HerodotusTokenCustomizer();
        log.trace("[Herodotus] |- Bean [OAuth2 Token Customizer] Auto Configure.");
        return herodotusTokenCustomizer;
    }

    @Bean
    public ProviderSettings providerSettings(EndpointProperties endpointProperties) {
        return ProviderSettings.builder()
                .issuer(endpointProperties.getIssuerUri())
                .authorizationEndpoint(endpointProperties.getAuthorizationEndpoint())
                .tokenEndpoint(endpointProperties.getAccessTokenEndpoint())
                .tokenRevocationEndpoint(endpointProperties.getTokenRevocationEndpoint())
                .tokenIntrospectionEndpoint(endpointProperties.getTokenIntrospectionEndpoint())
                .jwkSetEndpoint(endpointProperties.getJwkSetEndpoint())
                .oidcUserInfoEndpoint(endpointProperties.getOidcUserInfoEndpoint())
                .oidcClientRegistrationEndpoint(endpointProperties.getOidcClientRegistrationEndpoint())
                .build();
    }
}

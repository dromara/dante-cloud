/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.eurynome.module.oauth2.configuration;

import cn.herodotus.engine.oauth2.granter.customizer.HerodotusTokenCustomizer;
import cn.herodotus.engine.oauth2.granter.password.OAuth2ResourceOwnerPasswordAuthenticationConverter;
import cn.herodotus.engine.oauth2.granter.password.OAuth2ResourceOwnerPasswordAuthenticationProvider;
import cn.herodotus.engine.oauth2.granter.utils.OAuth2ConfigurerUtils;
import cn.herodotus.engine.oauth2.manager.configuration.OAuth2ManagerConfiguration;
import cn.herodotus.eurynome.module.oauth2.extend.HerodotusAuthenticationFailureHandler;
import cn.herodotus.eurynome.module.oauth2.properties.OauthProperties;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.server.authorization.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.web.authentication.DelegatingAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2ClientCredentialsAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2RefreshTokenAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.annotation.PostConstruct;
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
@EnableConfigurationProperties({OauthProperties.class})
@Import({
        OAuth2ManagerConfiguration.class
})
public class AuthorizationServerConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationServerConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [OAuth2 Authorization Server] Auto Configure.");
    }


    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {

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
                            new OAuth2ResourceOwnerPasswordAuthenticationConverter()));

            endpoint.accessTokenRequestConverter(authenticationConverter);
            endpoint.errorResponseHandler(failureHandler);
        });

        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

        httpSecurity.requestMatcher(endpointsMatcher)
                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .apply(authorizationServerConfigurer);

        SecurityFilterChain securityFilterChain = httpSecurity.formLogin(Customizer.withDefaults()).build();

        addOAuth2ResourceOwnerPasswordAuthenticationProvider(httpSecurity);

        return securityFilterChain;
    }

    @NotNull
    private OAuth2ResourceOwnerPasswordAuthenticationProvider addOAuth2ResourceOwnerPasswordAuthenticationProvider(HttpSecurity httpSecurity) {
        AuthenticationManager authenticationManager = httpSecurity.getSharedObject(AuthenticationManager.class);
        OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer = OAuth2ConfigurerUtils.getJwtCustomizer(httpSecurity);
        JwtEncoder jwtEncoder = OAuth2ConfigurerUtils.getJwtEncoder(httpSecurity);
        ProviderSettings providerSettings = OAuth2ConfigurerUtils.getProviderSettings(httpSecurity);
        OAuth2AuthorizationService authorizationService = OAuth2ConfigurerUtils.getAuthorizationService(httpSecurity);

        OAuth2ResourceOwnerPasswordAuthenticationProvider resourceOwnerPasswordAuthenticationProvider =
                new OAuth2ResourceOwnerPasswordAuthenticationProvider(authenticationManager, authorizationService, jwtEncoder);
        if (jwtCustomizer != null) {
            resourceOwnerPasswordAuthenticationProvider.setJwtCustomizer(jwtCustomizer);
        }
        resourceOwnerPasswordAuthenticationProvider.setProviderSettings(providerSettings);
        return resourceOwnerPasswordAuthenticationProvider;
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
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
    public ProviderSettings providerSettings(OauthProperties oauthProperties) {
        return ProviderSettings.builder()
                .issuer(oauthProperties.getIssuerUrl())
                .build();
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> oAuth2TokenCustomizer() {
        HerodotusTokenCustomizer herodotusTokenCustomizer = new HerodotusTokenCustomizer();
        return herodotusTokenCustomizer;
    }
}

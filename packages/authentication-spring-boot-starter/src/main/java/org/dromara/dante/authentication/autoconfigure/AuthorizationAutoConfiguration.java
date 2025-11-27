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

package org.dromara.dante.authentication.autoconfigure;

import cn.herodotus.dante.spring.utils.ResourceResolverUtils;
import cn.herodotus.engine.core.foundation.enums.Certificate;
import cn.herodotus.engine.core.identity.service.ClientDetailsService;
import cn.herodotus.engine.oauth2.authentication.autoconfigure.customizer.OAuth2AuthorizationServerConfigurerCustomizer;
import cn.herodotus.engine.oauth2.authentication.configurer.OAuth2AuthenticationConfigurerManager;
import cn.herodotus.engine.oauth2.authentication.configurer.OAuth2AuthenticationProviderConfigurer;
import cn.herodotus.engine.oauth2.authentication.utils.OAuth2ConfigurerUtils;
import cn.herodotus.engine.oauth2.authorization.servlet.ServletOAuth2AuthorizationConfigurerManager;
import cn.herodotus.engine.oauth2.core.properties.OAuth2AuthenticationProperties;
import cn.herodotus.engine.web.service.properties.EndpointProperties;
import cn.herodotus.engine.web.servlet.tenant.MultiTenantFilter;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.encrypt.KeyStoreKeyFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * <p>Description: 认证服务器配置 </p>
 * <p>
 * 1. 权限核心处理 {@link org.springframework.security.web.access.intercept.AuthorizationFilter}
 * 2. 默认的权限判断
 * 3. 模式决策 {@link org.springframework.security.authentication.ProviderManager}
 *
 * @author : gengwei.zheng
 * @date : 2022/2/12 20:57
 */
@AutoConfiguration
public class AuthorizationAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Auto [Authorization Server] Configure.");
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(
            HttpSecurity httpSecurity,
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService,
            ClientDetailsService clientDetailsService,
            OAuth2AuthenticationConfigurerManager authenticationConfigurerManager,
            ServletOAuth2AuthorizationConfigurerManager authorizationConfigurerManager
    ) throws Exception {

        log.debug("[Herodotus] |- Bean [Authorization Server Security Filter Chain] Configure.");

        SessionRegistry sessionRegistry = OAuth2ConfigurerUtils.getOptionalBean(httpSecurity, SessionRegistry.class);

        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();

        // 仅拦截 OAuth2 Authorization Server 的相关 endpoint
        httpSecurity.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                // 不配置 oauth2ResourceServer 就不会启用BearerTokenAuthenticationFilter
                // 当前的版本 SAS(1.4.1) 环境下，oauth2ResourceServer 必须在 with(authorizationServerConfigurer 前面配置，否则会导致应用无法启动
                // 主要原因是 OAuth2AuthorizationServerConfigurer 默认 jwt 配置与 Opaqua 配置冲突。see：https://stackoverflow.com/questions/79336064/oidcuserinfoauthenticationprovider-doesnt-support-for-opaque-token-bearer-autho
                .oauth2ResourceServer(authorizationConfigurerManager.getOAuth2ResourceServerConfigurerCustomer())
                .with(authorizationServerConfigurer, new OAuth2AuthorizationServerConfigurerCustomizer(httpSecurity, sessionRegistry, clientDetailsService, authenticationConfigurerManager))
                // 开启请求认证
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .formLogin(authenticationConfigurerManager.getOAuth2FormLoginConfigurerCustomizer())
                .sessionManagement(authorizationConfigurerManager.getOAuth2SessionManagementConfigurerCustomer())
                .exceptionHandling(authenticationConfigurerManager.getOAuth2ExceptionHandlingConfigurerCustomizer())
                .addFilterBefore(new MultiTenantFilter(), AuthorizationFilter.class)
                .with(new OAuth2AuthenticationProviderConfigurer(sessionRegistry, passwordEncoder, userDetailsService, authenticationConfigurerManager.getOAuth2AuthenticationProperties()), (configurer) -> {
                });

        return httpSecurity.build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(OAuth2AuthenticationProperties authenticationProperties) throws NoSuchAlgorithmException {

        OAuth2AuthenticationProperties.Jwk jwk = authenticationProperties.getJwk();

        KeyPair keyPair = null;
        if (jwk.getCertificate() == Certificate.CUSTOM) {
            try {
                Resource[] resource = ResourceResolverUtils.getResources(jwk.getJksKeyStore());
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
    public AuthorizationServerSettings authorizationServerSettings(EndpointProperties endpointProperties) {
        return AuthorizationServerSettings.builder()
                .issuer(endpointProperties.getIssuerUri())
                .authorizationEndpoint(endpointProperties.getAuthorizationEndpoint())
                .pushedAuthorizationRequestEndpoint(endpointProperties.getPushedAuthorizationRequestEndpoint())
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

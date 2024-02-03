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

package cn.herodotus.dante.authentication.autoconfigure;

import cn.herodotus.stirrup.captcha.core.processor.CaptchaRendererFactory;
import cn.herodotus.stirrup.oauth2.authentication.configurer.OAuth2FormLoginSecureConfigurer;
import cn.herodotus.stirrup.oauth2.authentication.properties.OAuth2AuthenticationProperties;
import cn.herodotus.stirrup.oauth2.authentication.response.DefaultOAuth2AuthenticationEventPublisher;
import cn.herodotus.stirrup.oauth2.authorization.customizer.session.OAuth2SessionManagementConfigurerCustomer;
import cn.herodotus.stirrup.oauth2.authorization.servlet.HerodotusAccessDeniedHandler;
import cn.herodotus.stirrup.oauth2.authorization.servlet.HerodotusAuthenticationEntryPoint;
import cn.herodotus.stirrup.oauth2.authorization.servlet.OAuth2AuthorizeHttpRequestsConfigurerCustomer;
import cn.herodotus.stirrup.oauth2.authorization.servlet.OAuth2ResourceServerConfigurerCustomer;
import cn.herodotus.stirrup.oauth2.core.definition.service.ClientDetailsService;
import cn.herodotus.stirrup.oauth2.core.definition.strategy.StrategyUserDetailsService;
import cn.herodotus.stirrup.oauth2.management.processor.HerodotusClientDetailsService;
import cn.herodotus.stirrup.oauth2.management.processor.HerodotusUserDetailsService;
import cn.herodotus.stirrup.oauth2.management.service.OAuth2ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * <p>Description: 默认安全配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/12 20:53
 */
@AutoConfiguration
@EnableWebSecurity
public class DefaultSecurityAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(DefaultSecurityAutoConfiguration.class);

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(
            HttpSecurity httpSecurity,
            UserDetailsService userDetailsService,
            OAuth2AuthenticationProperties authenticationProperties,
            CaptchaRendererFactory captchaRendererFactory,
            OAuth2SessionManagementConfigurerCustomer oauth2SessionManagementConfigurerCustomer,
            OAuth2ResourceServerConfigurerCustomer oauth2ResourceServerConfigurerCustomer,
            OAuth2AuthorizeHttpRequestsConfigurerCustomer oauth2AuthorizeHttpRequestsConfigurerCustomer
    ) throws Exception {

        log.debug("[Herodotus] |- Bean [Default Security Filter Chain] Auto Configure.");
        // 禁用CSRF 开启跨域
        httpSecurity.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable);

        // @formatter:off
        httpSecurity
                .authorizeHttpRequests(oauth2AuthorizeHttpRequestsConfigurerCustomer)
                .sessionManagement(oauth2SessionManagementConfigurerCustomer)
                .exceptionHandling(exceptions -> {
                    exceptions.authenticationEntryPoint(new HerodotusAuthenticationEntryPoint());
                    exceptions.accessDeniedHandler(new HerodotusAccessDeniedHandler());
                })
                .oauth2ResourceServer(oauth2ResourceServerConfigurerCustomer)
                .with(new OAuth2FormLoginSecureConfigurer<>(userDetailsService, authenticationProperties, captchaRendererFactory), (configurer) -> {});

        // @formatter:on
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationEventPublisher authenticationEventPublisher(ApplicationContext applicationContext) {
        log.debug("[Herodotus] |- Bean [Authentication Event Publisher] Auto Configure.");
        return new DefaultOAuth2AuthenticationEventPublisher(applicationContext);
    }

    @Bean
    @ConditionalOnMissingBean
    public UserDetailsService userDetailsService(StrategyUserDetailsService strategyUserDetailsService) {
        HerodotusUserDetailsService herodotusUserDetailsService = new HerodotusUserDetailsService(strategyUserDetailsService);
        log.debug("[Herodotus] |- Bean [Herodotus User Details Service] Auto Configure.");
        return herodotusUserDetailsService;
    }

    @Bean
    @ConditionalOnMissingBean
    public ClientDetailsService clientDetailsService(OAuth2ApplicationService applicationService) {
        HerodotusClientDetailsService herodotusClientDetailsService = new HerodotusClientDetailsService(applicationService);
        log.debug("[Herodotus] |- Bean [Herodotus Client Details Service] Auto Configure.");
        return herodotusClientDetailsService;
    }
}

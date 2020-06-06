/*
 * Copyright (c) 2019-2020 the original author or authors.
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
 * Module Name: eurynome-cloud-starter
 * File Name: ResourceServerConfiguration.java
 * Author: gengwei.zheng
 * Date: 2020/6/6 下午12:51
 * LastModified: 2020/6/6 下午12:50
 */

package cn.herodotus.eurynome.autoconfigure;

import cn.herodotus.eurynome.rest.properties.ApplicationProperties;
import cn.herodotus.eurynome.rest.properties.RestProperties;
import cn.herodotus.eurynome.security.metadata.RequestMappingScanner;
import cn.herodotus.eurynome.security.metadata.SecurityMetadataLocalStorage;
import cn.herodotus.eurynome.security.web.access.HerodotusAccessDecisionManager;
import cn.herodotus.eurynome.security.properties.SecurityProperties;
import cn.herodotus.eurynome.security.web.HerodotusAuthenticationEntryPoint;
import cn.herodotus.eurynome.security.web.access.HerodotusAccessDeniedHandler;
import cn.herodotus.eurynome.security.web.access.intercept.HerodotusSecurityMetadataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.util.List;
/**
 * <p>Description: 通用的ResourceService配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/6/6 10:49
 */
@Slf4j
@Configuration
@ConditionalOnExpression("#{!'${spring.application.name}'.equals('eurynome-cloud-uaa')}")
@EnableResourceServer
public class ResourceServerAutoConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private ResourceServerProperties resourceServerProperties;
    @Autowired
    private DefaultAccessTokenConverter defaultAccessTokenConverter;

    @Bean
    public ResourceServerTokenServices tokenServices() {
        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        remoteTokenServices.setCheckTokenEndpointUrl(resourceServerProperties.getTokenInfoUri());
        remoteTokenServices.setClientId(resourceServerProperties.getClientId());
        remoteTokenServices.setClientSecret(resourceServerProperties.getClientSecret());
        remoteTokenServices.setAccessTokenConverter(defaultAccessTokenConverter);
        return remoteTokenServices;
    }

    @Bean
    @ConditionalOnMissingBean(SecurityMetadataLocalStorage.class)
    public SecurityMetadataLocalStorage securityMetadataLocalStorage() {
        SecurityMetadataLocalStorage securityMetadataLocalStorage = new SecurityMetadataLocalStorage();
        log.debug("[Herodotus] |- Bean [Security Metadata Local Storage] Auto Configure.");
        return securityMetadataLocalStorage;
    }

    /**
     * 自定义注解扫描
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(RequestMappingScanner.class)
    @ConditionalOnClass(SecurityMetadataLocalStorage.class)
    public RequestMappingScanner requestMappingScanner(RestProperties restProperties, ApplicationProperties applicationProperties, SecurityMetadataLocalStorage securityMetadataLocalStorage) {
        RequestMappingScanner requestMappingScan = new RequestMappingScanner(restProperties, applicationProperties, securityMetadataLocalStorage, EnableResourceServer.class);
        log.debug("[Herodotus] |- Bean [Request Mapping Scan] Auto Configure.");
        return requestMappingScan;
    }

    @Bean
    @ConditionalOnMissingBean(HerodotusSecurityMetadataSource.class)
    @ConditionalOnClass(RequestMappingScanner.class)
    public HerodotusSecurityMetadataSource herodotusSecurityMetadataSource() {
        HerodotusSecurityMetadataSource herodotusSecurityMetadataSource = new HerodotusSecurityMetadataSource();
        herodotusSecurityMetadataSource.setSecurityMetadataLocalStorage(securityMetadataLocalStorage());
        herodotusSecurityMetadataSource.setSecurityProperties(securityProperties);
        log.debug("[Herodotus] |- Bean [Security Metadata Source] Auto Configure.");
        return herodotusSecurityMetadataSource;
    }

    @Bean
    public HerodotusAccessDecisionManager herodotusAccessDecisionManager() {
        HerodotusAccessDecisionManager herodotusAccessDecisionManager = new HerodotusAccessDecisionManager();
        log.debug("[Herodotus] |- Bean [Access Decision Manager] Auto Configure.");
        return herodotusAccessDecisionManager;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        log.debug("[Herodotus] |- Bean [Core Resource Server] Auto Configure.");

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        // @formatter:off
        http.authorizeRequests()
                .antMatchers(getWhitelist()).permitAll()
                // 指定监控访问权限
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
                        fsi.setAccessDecisionManager(herodotusAccessDecisionManager());
                        fsi.setSecurityMetadataSource(herodotusSecurityMetadataSource());
                        return fsi;
                    }
                })
                .and() // 认证鉴权错误处理,为了统一异常处理。每个资源服务器都应该加上。
                .exceptionHandling()
                .accessDeniedHandler(new HerodotusAccessDeniedHandler())
                .authenticationEntryPoint(new HerodotusAuthenticationEntryPoint());

        // 关闭csrf 跨站（域）攻击防控
        http.csrf().disable();
        // @formatter:on
    }

    private String[] getWhitelist() {
        if (ObjectUtils.isNotEmpty(securityProperties)) {
            List<String> whitelist = securityProperties.getInterceptor().getWhitelist();
            if (CollectionUtils.isNotEmpty(whitelist)) {
                log.info("[Herodotus] |- OAuth2 Fetch The Resource White List.");
                return whitelist.toArray(new String[]{});
            }
        }

        log.warn("[Herodotus] |- OAuth2 Can not Fetch The Resource White List Configurations.");
        return new String[]{};
    }
}

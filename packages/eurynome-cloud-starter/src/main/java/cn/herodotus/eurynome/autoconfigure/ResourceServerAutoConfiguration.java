/*
 * Copyright (c) 2019-2021 Gengwei Zheng(herodotus@aliyun.com)
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
 * Module Name: eurynome-cloud-starter
 * File Name: ResourceServerAutoConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/05/13 11:01:13
 */

package cn.herodotus.eurynome.autoconfigure;

import cn.herodotus.eurynome.security.properties.SecurityProperties;
import cn.herodotus.eurynome.security.response.HerodotusAuthenticationEntryPoint;
import cn.herodotus.eurynome.security.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import javax.annotation.PostConstruct;

/**
 * <p>Description: 通用的ResourceService配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/6/6 10:49
 */
@Configuration
@EnableResourceServer
public class ResourceServerAutoConfiguration extends ResourceServerConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ResourceServerAutoConfiguration.class);

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private ResourceServerProperties resourceServerProperties;
    @Autowired
    private DefaultAccessTokenConverter defaultAccessTokenConverter;

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Core [Herodotus Resource Server in starter] Auto Configure.");
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 认证鉴权错误处理,为了统一异常处理。每个资源服务器都应该加上。
        resources.authenticationEntryPoint(new HerodotusAuthenticationEntryPoint());
        super.configure(resources);
    }

    @Bean
    public ResourceServerTokenServices tokenServices() {
        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        remoteTokenServices.setCheckTokenEndpointUrl(resourceServerProperties.getTokenInfoUri());
        remoteTokenServices.setClientId(resourceServerProperties.getClientId());
        remoteTokenServices.setClientSecret(resourceServerProperties.getClientSecret());
        remoteTokenServices.setAccessTokenConverter(defaultAccessTokenConverter);
        return remoteTokenServices;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        log.trace("[Herodotus] |- Bean [Core Resource Server] Auto Configure.");

        // 如果不设置，那么在通过浏览器访问被保护的任何资源时，每次是不同的SessionID，并且将每次请求的历史都记录在OAuth2Authentication的details的中
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        // 禁用CSRF 开启跨域
        http.csrf().disable().cors();

        // @formatter:off
        http.authorizeRequests()
                .antMatchers(SecurityUtils.whitelistToAntMatchers(securityProperties.getInterceptor().getWhitelist())).permitAll()
                // 指定监控访问权限
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .anyRequest().authenticated();

        // 防止iframe 造成跨域
        http.headers().frameOptions().disable();
    }
}

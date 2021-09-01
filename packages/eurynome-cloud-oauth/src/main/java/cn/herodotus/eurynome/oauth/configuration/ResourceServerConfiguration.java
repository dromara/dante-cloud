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
 * Module Name: eurynome-cloud-oauth
 * File Name: ResourceServerConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/05/13 11:07:13
 */

package cn.herodotus.eurynome.oauth.configuration;

import cn.herodotus.eurynome.security.properties.SecurityProperties;
import cn.herodotus.eurynome.security.response.HerodotusAuthenticationEntryPoint;
import cn.herodotus.eurynome.security.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import javax.annotation.PostConstruct;

/**
 * <p>Description: 通用的ResourceService配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/6/6 10:49
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ResourceServerConfiguration.class);

    @Autowired
    private SecurityProperties securityProperties;

    @PostConstruct
    public void postConstruct() {
        log.debug("[Eurynome] |- Core [Herodotus Resource Server in component oauth] Auto Configure.");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        // @formatter:off
        http.authorizeRequests()
                .antMatchers("/", "/defaultKaptcha").permitAll()
                .antMatchers(SecurityUtils.whitelistToAntMatchers(securityProperties.getInterceptor().getWhitelist())).permitAll()
                // 指定监控访问权限
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .anyRequest().authenticated()
                .and().cors()
                .and() // 认证鉴权错误处理,为了统一异常处理。每个资源服务器都应该加上。
                .exceptionHandling()
                .authenticationEntryPoint(new HerodotusAuthenticationEntryPoint());

        // 关闭csrf 跨站（域）攻击防控
        http.csrf().disable();
        // @formatter:on
    }
}

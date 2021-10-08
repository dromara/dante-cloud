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
 * Module Name: eurynome-cloud-oauth-starter
 * File Name: ResourceServerConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/09/01 12:31:01
 */

package cn.herodotus.eurynome.oauth.autoconfigure;

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
public class ResourceServerAutoConfiguration extends ResourceServerConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ResourceServerAutoConfiguration.class);

    @Autowired
    private SecurityProperties securityProperties;

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Core [Herodotus Resource Server in component oauth] Auto Configure.");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        // 禁用CSRF 开启跨域
        http.csrf().disable().cors();

        // 认证鉴权错误处理,为了统一异常处理。每个资源服务器都应该加上。
        http.exceptionHandling()
                // 未登录认证异常
                .authenticationEntryPoint(new HerodotusAuthenticationEntryPoint());

        // @formatter:off
        http.authorizeRequests()
                .antMatchers("/defaultKaptcha").permitAll()
                .antMatchers(SecurityUtils.whitelistToAntMatchers(securityProperties.getInterceptor().getWhitelist())).permitAll()
                // 指定监控访问权限
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .anyRequest().authenticated();

        // 防止iframe 造成跨域
        http.headers().frameOptions().disable();
    }
}

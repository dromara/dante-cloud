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
 * Module Name: eurynome-cloud-uaa
 * File Name: ResourceServerConfiguration.java
 * Author: gengwei.zheng
 * Date: 2020/6/4 上午8:42
 * LastModified: 2020/6/4 上午8:41
 */

package cn.herodotus.eurynome.uaa.configuration;

import cn.herodotus.eurynome.security.response.HerodotusAuthenticationEntryPoint;
import cn.herodotus.eurynome.security.authentication.access.HerodotusAccessDeniedHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * <p>
 * WebSecurityConfigurerAdapter与ResourceServerConfigurerAdapter同时在的话且都配置了处理url为：/api/**，默认是后者会生效。
 * 为什么是后者生效，因为
 * · 默认的WebSecurityConfigurerAdapter里的@Order值是100（我们可以在该类上可以明确看到@Order(100)），
 * · 而在ResourceServerConfigurerAdapter上添加了@EnableResourceServer注解，他的作用之一就是定义了@Order值为3（该注解里引用了ResourceServerConfiguration，这个类里面定义了Order值）.
 * · 在spring 的体系里Order值越小优先级越高，所以ResourceServerConfigurerAdapter优先级比另外一个更高，他会优先处理，而WebSecurityConfigurerAdapter会失效。
 * <p>
 * 如果想让WebSecurityConfigurerAdapter比ResourceServerConfigurerAdapter优先级高的话，只须要让前者的@Order值比后者的@Order值更低就行了。
 * 似乎也可以在配置文件中这么配置
 * security:
 * oauth2:
 * resource:
 * filter-order: 3
 * <p>
 * 注意：每声明一个*Adapter类，都会产生一个filterChain。一个request（匹配url）只能被一个filterChain处理，这就解释了为什么二者Adapter同时在的时候，前者默认为什么会失效的原因。
 * <p>
 * {@link :https://www.jianshu.com/p/fe1194ca8ecd}
 *
 * @author gengwei.zheng
 */
@Slf4j
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {

        log.info("[Herodotus] |- Bean [Resource Server Configurer Adapter] Auto Configure.");

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        // @formatter:off
        http.requestMatchers().antMatchers("/identity/**", "/actuator/**")
                .and()
                    .authorizeRequests()
                    // 指定监控访问权限
                    .antMatchers("/actuator/**").permitAll()
                    .antMatchers("/identity/**").authenticated()
                    .anyRequest().authenticated()
                .and() // 认证鉴权错误处理,为了统一异常处理。每个资源服务器都应该加上。
                    .exceptionHandling()
                    .accessDeniedHandler(new HerodotusAccessDeniedHandler())
                    .authenticationEntryPoint(new HerodotusAuthenticationEntryPoint())
                .and()
                    .csrf().disable()
                    .httpBasic().disable();
        // @formatter:on
    }
}

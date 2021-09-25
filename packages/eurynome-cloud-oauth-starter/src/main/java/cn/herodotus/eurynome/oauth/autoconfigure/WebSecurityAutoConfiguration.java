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
 * File Name: WebSecurityConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/09/14 14:43:14
 */

package cn.herodotus.eurynome.oauth.autoconfigure;

import cn.herodotus.eurynome.oauth.authentication.FormLoginAuthenticationFailureHandler;
import cn.herodotus.eurynome.oauth.authentication.FormLoginAuthenticationProvider;
import cn.herodotus.eurynome.oauth.authentication.FormLoginDecryptParameterAuthenticationFilter;
import cn.herodotus.eurynome.oauth.authentication.FormLoginWebAuthenticationDetailsSource;
import cn.herodotus.eurynome.security.definition.service.HerodotusClientDetailsService;
import cn.herodotus.eurynome.security.definition.service.HerodotusUserDetailsService;
import cn.herodotus.eurynome.security.properties.SecurityProperties;
import cn.herodotus.eurynome.security.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * <p>Description: 说明 </p>
 *
 * <p>
 * WebSecurityConfigurerAdapter用于保护oauth相关的endpoints，同时主要作用于用户的登录(form login,Basic auth)
 * ResourceServerConfigurerAdapter用于保护oauth要开放的资源，同时主要作用于client端以及token的认证(Bearer auth)
 * {@link :https://www.cnblogs.com/white-knight/p/9711515.html}
 * <p>
 * <p>
 * WebSecurityConfigurerAdapter默认情况下是spring security的http配置。默认情况下为：@Order（100）{@link WebSecurityConfigurerAdapter}
 * ResourceServerConfigurerAdapter默认情况下是spring security oauth2的http配置。默认情况下为：@Order（3）{@link ResourceServerAutoConfiguration}
 * <p>
 * 因此二者是分工协作的
 * · 在WebSecurityConfigurerAdapter不拦截oauth要开放的资源
 * · 在ResourceServerConfigurerAdapter配置需要token验证的资源
 *
 * @author : gengwei.zheng
 * @date : 2020/3/11 19:16
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(2)
public class WebSecurityAutoConfiguration extends WebSecurityConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(WebSecurityAutoConfiguration.class);

    @Autowired
    private DataSource dataSource;
    @Autowired
    private HerodotusUserDetailsService herodotusUserDetailsService;
    @Autowired
    private HerodotusClientDetailsService herodotusClientDetailsService;
    @Autowired
    private SecurityProperties securityProperties;
    /**
     * {@link AuthorizationServerEndpointsConfiguration#defaultAuthorizationServerTokenServices()}
     */
    @Autowired
    private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

    @PostConstruct
    public void postConstruct() {
        log.debug("[Eurynome] |- Core [Herodotus Web Security  in component oauth] Auto Configure.");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationProvider
     * ProviderManager把工作委托给AuthenticationProvider集合。
     * ProviderManager将所有AuthenticationProvider进行循环，直到运行返回一个完整的Authentication，
     * 不符合条件或者不能认证当前Authentication，返回AuthenticationException异常或者null。
     * <p>
     * supports(Class<?> authentication)方法是判断是否支持当前Authentication类型的认证。
     * (JaasAuthenticationToken、OAuth2Authentication、UsernamePasswordAuthenticationToken、自己实现)等。
     * <p>
     * 这一点逻辑和Shiro类似。
     * <p>
     * 在后台，如果用户名登录错误，是有返回具体的错误异常信息的。但是在前台界面，却只看到了“Bad Credential”，而不是具体错误信息。
     * 设置其 hideUserNotFoundExceptions 为 false，就解决问题了
     *
     * @return AuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        FormLoginAuthenticationProvider provider = new FormLoginAuthenticationProvider();
        provider.setUserDetailsService(herodotusUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }

    @Bean
    public FormLoginDecryptParameterAuthenticationFilter formLoginDecryptParameterAuthenticationFilter() throws Exception {
        FormLoginDecryptParameterAuthenticationFilter filter = new FormLoginDecryptParameterAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationFailureHandler(new FormLoginAuthenticationFailureHandler(securityProperties));
        filter.setAuthenticationDetailsSource(new FormLoginWebAuthenticationDetailsSource(securityProperties));
        filter.setUsernameParameter(securityProperties.getLogin().getUsernameParameter());
        filter.setPasswordParameter(securityProperties.getLogin().getPasswordParameter());
        return filter;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //自动创建数据库表，使用一次后注释掉，不然会报错
//            jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    /**
     * 大体意思就是antMatcher()``是HttpSecurity的一个方法，他只告诉了Spring我只配置了一个我这个Adapter能处理哪个的url，它与authorizeRequests()没有任何关系。
     * <p>
     * 然后使用authorizeRequests().antMatchers()是告诉你在antMatchers()中指定的一个或多个路径,比如执行permitAll()或hasRole()。他们在第一个http.antMatcher()匹配时就会生效。
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        // 禁用CSRF 开启跨域
        http.csrf().disable().cors();

        // @formatter:off
        http.requestMatchers().antMatchers("/oauth/**", "/login**")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated()
                .antMatchers(SecurityUtils.whitelistToAntMatchers(securityProperties.getInterceptor().getWhitelist())).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(formLoginDecryptParameterAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                // 可以设置自定义的登录页面 或者 （登录）接口
                // 注意1： 一般来说设置成（登录）接口后，该接口会配置成无权限即可访问，所以会走匿名filter, 也就意味着不会走认证过程了，所以我们一般不直接设置成接口地址
                // 注意2： 这里配置的 地址一定要配置成无权限访问，否则将出现 一直重定向问题（因为无权限后又会重定向到这里配置的登录页url）
                .formLogin().loginPage(securityProperties.getLogin().getLoginUrl()).permitAll()
//                .and()
//                    .rememberMe()
//                        .tokenRepository(persistentTokenRepository())
//                        .tokenValiditySeconds(securityProperties.getRememberMe().getValiditySeconds())
//                        .key(securityProperties.getRememberMe().getCookieName())
//                        .userDetailsService(oauth2UserDetailsService)
                .and().logout().permitAll();
        // @formatter:on
    }
}

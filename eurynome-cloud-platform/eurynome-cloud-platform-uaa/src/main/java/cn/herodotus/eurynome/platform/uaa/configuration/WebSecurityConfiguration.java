package cn.herodotus.eurynome.platform.uaa.configuration;

import cn.herodotus.eurynome.component.security.filter.TenantSecurityContextFilter;
import cn.herodotus.eurynome.component.security.properties.SecurityProperties;
import cn.herodotus.eurynome.platform.uaa.service.OauthUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

/**
 * <p>Description: 说明 </p>
 *
 * <p>
 * WebSecurityConfigurerAdapter用于保护oauth相关的endpoints，同时主要作用于用户的登录(form login,Basic auth)
 * ResourceServerConfigurerAdapter用于保护oauth要开放的资源，同时主要作用于client端以及token的认证(Bearer auth)
 * {@link :https://www.cnblogs.com/white-knight/p/9711515.html}
 * <p>
 * <p>
 * WebSecurityConfigurerAdapter默认情况下是springsecurity的http配置
 * ResourceServerConfigurerAdapter默认情况下是spring security oauth2的http配置
 * <p>
 * 因此二者是分工协作的
 * · 在WebSecurityConfigurerAdapter不拦截oauth要开放的资源
 * · 在ResourceServerConfigurerAdapter配置需要token验证的资源
 *
 * @author : gengwei.zheng
 * @date : 2020/3/11 19:16
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private OauthUserDetailsService oauth2UserDetailsService;
    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
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
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(oauth2UserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }

    @Bean
    public TenantSecurityContextFilter tenantSecurityContextFilter() {
        return new TenantSecurityContextFilter();
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
        // @formatter:off
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                    .addFilterAfter(tenantSecurityContextFilter(), WebAsyncManagerIntegrationFilter.class)

                    // 指定该配置对应拦截的内容
                    .antMatcher("/**")
                        .authorizeRequests()
                        .antMatchers("/oauth/token", "/oauth/authorize", "/login**", "/error**").permitAll()
                .and()
                    .authorizeRequests()
                        .anyRequest().authenticated()
                .and()
                    .formLogin()
                // 可以设置自定义的登录页面 或者 （登录）接口
                // 注意1： 一般来说设置成（登录）接口后，该接口会配置成无权限即可访问，所以会走匿名filter, 也就意味着不会走认证过程了，所以我们一般不直接设置成接口地址
                // 注意2： 这里配置的 地址一定要配置成无权限访问，否则将出现 一直重定向问题（因为无权限后又会重定向到这里配置的登录页url）
                        .loginPage(securityProperties.getLogin().getLoginUrl()).permitAll()
                .and()
                    .logout().permitAll()
                .and()
                    .csrf().disable()
                    .httpBasic().disable();
                // @formatter:on
    }
}

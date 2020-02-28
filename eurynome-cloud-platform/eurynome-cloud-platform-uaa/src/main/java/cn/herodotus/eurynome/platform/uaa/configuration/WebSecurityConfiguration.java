package cn.herodotus.eurynome.platform.uaa.configuration;

import cn.herodotus.eurynome.platform.uaa.service.OauthUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author gengwei.zheng
 * 配置Spring Security
 * ResourceServerConfig 是比SecurityConfig 的优先级低的
 * <p>
 * User must be authenticated with Spring Security before authorization can be completed.unauthorized
 * <p>
 * ResourceServerConfiguration 和 SecurityConfiguration上配置的顺序, 
 * SecurityConfiguration一定要在ResourceServerConfiguration 之前，
 * 因为spring实现安全是通过添加过滤器(Filter)来实现的，基本的安全过滤应该在oauth过滤之前,
 * 所以在SecurityConfiguration设置@Order(2), 在ResourceServerConfiguration上设置@Order(6)
 * <p>
 * 似乎也可以在配置文件中这么配置
 * security:
 * oauth2:
 * resource:
 * filter-order: 3
 */
@Order(2)
@Slf4j
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private OauthUserDetailsService oauth2UserDetailsService;

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
     *
     * supports(Class<?> authentication)方法是判断是否支持当前Authentication类型的认证。
     * (JaasAuthenticationToken、OAuth2Authentication、UsernamePasswordAuthenticationToken、自己实现)等。
     *
     * 这一点逻辑和Shiro类似。
     *
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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 如果有允许匿名的url，填在下面
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/oauth/confirm_access")
                .and().logout().permitAll();
    }
}

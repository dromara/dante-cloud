package cn.herodotus.eurynome.platform.uaa.configuration;

import cn.herodotus.eurynome.component.security.filter.TenantSecurityContextFilter;
import cn.herodotus.eurynome.component.security.response.HerodotusAccessDeniedHandler;
import cn.herodotus.eurynome.component.security.response.HerodotusAuthenticationEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    private BearerTokenExtractor tokenExtractor = new BearerTokenExtractor();

    @Bean
    public TenantSecurityContextFilter tenantSecurityContextFilter() {
        return new TenantSecurityContextFilter();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        log.info("[Herodotus] |- ResourceServerConfigurerAdapter configuration!");
        // @formatter:off
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
                .addFilterAfter(tenantSecurityContextFilter(), WebAsyncManagerIntegrationFilter.class)
                .antMatcher("/**")
                    .authorizeRequests()
                    .antMatchers("/oauth/token", "/oauth/authorize").permitAll()
                    .anyRequest().authenticated()
                .and() // 认证鉴权错误处理,为了统一异常处理。每个资源服务器都应该加上。
                .exceptionHandling()
                .accessDeniedHandler(new HerodotusAccessDeniedHandler())
                .authenticationEntryPoint(new HerodotusAuthenticationEntryPoint());

        // 关闭csrf 跨站（域）攻击防控
        http.csrf().disable();
    }


    public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
        public LogoutSuccessHandler() {
            // 重定向到原地址
            this.setUseReferer(true);
        }

        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            try {
                // 解密请求头
                authentication = tokenExtractor.extract(request);
                if (authentication != null && authentication.getPrincipal() != null) {
                    String tokenValue = authentication.getPrincipal().toString();
                    log.debug("[Herodotus] |- RevokeToken tokenValue:{}", tokenValue);
                    // 移除token
                    tokenStore.removeAccessToken(tokenStore.readAccessToken(tokenValue));
                }
            } catch (Exception e) {
                log.error("[Herodotus] |- RevokeToken error:{}", e);
            }
            super.onLogoutSuccess(request, response, authentication);
        }
    }
}

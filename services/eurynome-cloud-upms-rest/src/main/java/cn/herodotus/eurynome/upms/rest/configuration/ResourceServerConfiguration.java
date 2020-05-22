package cn.herodotus.eurynome.upms.rest.configuration;

import cn.herodotus.eurynome.security.properties.SecurityProperties;
import cn.herodotus.eurynome.security.web.access.HerodotusAccessDeniedHandler;
import cn.herodotus.eurynome.security.web.HerodotusAuthenticationEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import javax.annotation.Resource;

@Slf4j
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Resource
    private SecurityProperties securityProperties;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        log.info("[Herodotus] |- UPMS ResourceServerConfigurerAdapter configuration!");

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        // @formatter:off
        http.authorizeRequests()
                .antMatchers(getWhiteList()).permitAll()
                // 指定监控访问权限
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .anyRequest().authenticated()
                .and() // 认证鉴权错误处理,为了统一异常处理。每个资源服务器都应该加上。
                .exceptionHandling()
                .accessDeniedHandler(new HerodotusAccessDeniedHandler())
                .authenticationEntryPoint(new HerodotusAuthenticationEntryPoint());

        // 关闭csrf 跨站（域）攻击防控
        http.csrf().disable();
        // @formatter:on
    }

    private String[] getWhiteList() {
        if (securityProperties != null) {
            log.info("[Herodotus] |- OAuth2 Fetch The Resource White List.");
            return securityProperties.getInterceptor().getWhiteList();
        } else {
            log.warn("[Herodotus] |- OAuth2 Can not Fetch The Resource White List Configurations.");
            return new String[] {};
        }
    }
}

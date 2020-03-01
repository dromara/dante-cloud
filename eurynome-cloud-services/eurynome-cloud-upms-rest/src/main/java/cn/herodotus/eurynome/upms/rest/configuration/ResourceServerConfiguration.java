package cn.herodotus.eurynome.upms.rest.configuration;

import cn.herodotus.eurynome.component.security.properties.SecurityProperities;
import cn.herodotus.eurynome.component.security.web.access.ArtisanAccessDeniedHandler;
import cn.herodotus.eurynome.component.security.web.authentication.ArtisanAuthenticationEntryPoint;
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
    private SecurityProperities securityProperities;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .antMatchers(getWhiteList()).permitAll()
                // 指定监控访问权限
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new ArtisanAccessDeniedHandler())
                .authenticationEntryPoint(new ArtisanAuthenticationEntryPoint())
                .and()
                .csrf().disable();
    }

    private String[] getWhiteList() {
        if (securityProperities != null) {
            log.info("[Herodotus] |- OAuth2 Fetch The Resource White List.");
            return securityProperities.getInterceptor().getWhiteList();
        } else {
            log.warn("[Herodotus] |- OAuth2 Can not Fetch The Resource White List Configurations.");
            return new String[] {};
        }
    }
}

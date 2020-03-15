package cn.herodotus.eurynome.platform.uaa.configuration;

import cn.herodotus.eurynome.component.security.properties.SecurityProperties;
import cn.herodotus.eurynome.component.security.response.HerodotusWebResponseExceptionTranslator;
import cn.herodotus.eurynome.platform.uaa.extend.OauthJwtTokenEnhancer;
import cn.herodotus.eurynome.platform.uaa.extend.OauthUserAuthenticationConverter;
import cn.herodotus.eurynome.platform.uaa.service.OauthClientDetailsService;
import cn.herodotus.eurynome.platform.uaa.service.OauthUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.security.Principal;
import java.util.Arrays;
import java.util.Map;

/**
 * ·AuthenticationManager: 身份验证的主要策略设置接口
 * ·ProviderManager: AuthenticationManager最常用的接口实现
 * ·AuthenticationProvider: ProviderManager的工作被委托者
 * ·Authentication: 认证用户信息主体
 * ·GrantedAuthority: 用户主体的权限
 * ·UserDetails: 用户的基本必要信息
 * ·UserDetailsService: 通过String username返回一个UserDetails
 * ·SecurityContextHolder: 提供访问 SecurityContext。
 * ·SecurityContext: 保存Authentication，和一些其它的信息
 *
 * '@EnableAuthorizationServer' 提供/oauth/authorize,/oauth/token,/oauth/check_token,/oauth/confirm_access,/oauth/error
 *
 * 为了实现OAuth 2.0授权服务器，Spring Security过滤器链中需要以下端点：
 * ·AuthorizationEndpoint用于为授权请求提供服务。默认网址：/oauth/authorize。
 * ·TokenEndpoint用于服务访问令牌的请求。默认网址：/oauth/token
 *
 * 以下过滤器是实现OAuth 2.0资源服务器所必需的：
 * ·将OAuth2AuthenticationProcessingFilter用于加载给定的认证访问令牌请求的认证。
 *
 * [/oauth/authorize]
 * [/oauth/token] {@link org.springframework.security.oauth2.provider.endpoint.TokenEndpoint#getAccessToken(Principal, Map)}
 * [/oauth/check_token] {@link org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint#checkToken(String)}
 * [/oauth/confirm_access]
 * [/oauth/token_key]
 * [/oauth/error]
 *
 * @author gengwei.zheng
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    /**
     * 注入authenticationManager
     * 来支持 password grant type
     */
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;
    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;
    @Autowired
    private OauthUserDetailsService oauthUserDetailsService;
    @Autowired
    private OauthUserAuthenticationConverter oauthUserAuthenticationConverter;
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 配置令牌端点(Token Endpoint)的安全约束
     * {@link org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerSecurityConfiguration}
     * @param security security
     * @throws Exception security
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                // 默认都是denyAll()
                // exposes public key for token verification if using JWT tokens
                .tokenKeyAccess("isAuthenticated()")
                // 开启/oauth/check_token验证端口认证权限访问
                // used by Resource Servers to decode access tokens
//                .checkTokenAccess("isAuthenticated()")
                .checkTokenAccess("permitAll()")
                // 开启表单认证
                // allowFormAuthenticationForClients是为了注册clientCredentialsTokenEndpointFilter
                // clientCredentialsTokenEndpointFilter,解析request中的client_id和client_secret
                // 构造成UsernamePasswordAuthenticationToken,然后通过UserDetailsService查询作简单的认证而已
                // 一般是针对password模式和client_credentials
                // 当然也可以使用http basic认证
                // 如果使用了http basic认证,就不用使用clientCredentialsTokenEndpointFilter
                // 因为本质是一样的
                .allowFormAuthenticationForClients();
    }




    /**
     * 配置客户端详情信息(Client Details)
     * clientId：（必须的）用来标识客户的Id。
     * secret：（需要值得信任的客户端）客户端安全码，如果有的话。
     * scope：用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。
     * authorizedGrantTypes：此客户端可以使用的授权类型，默认为空。
     * authorities：此客户端可以使用的权限（基于Spring Security authorities）。
     * <p>
     * 配置客户端详情服务（ClientDetailsService）
     * 客户端详情信息在这里进行初始化
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(oauthClientDetailsService);
    }

    /**
     * 授权store
     *
     * @return ApprovalStore
     */
    @Bean
    public ApprovalStore createApprovalStore() {
        return new JdbcApprovalStore(dataSource);
    }

    /**
     * 授权码
     *
     * @return AuthorizationCodeServices
     */
    @Bean
    public AuthorizationCodeServices createAuthorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * setAccessTokenValiditySeconds 由于我们在数据库中设置了表oauth_client_details字段 access_token_validity
     * setRefreshTokenValiditySeconds 由于我们在数据库中设置了表oauth_client_details字段 refresh_token_validity
     * 如果 数据 库定义了，则代码处配置失效
     */
    @Bean
    public DefaultTokenServices createDefaultTokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setReuseRefreshToken(true);
        defaultTokenServices.setClientDetailsService(oauthClientDetailsService);
        defaultTokenServices.setTokenEnhancer(createAccessTokenEnhancerChain());
        return defaultTokenServices;
    }

    @Bean
    public TokenEnhancerChain createAccessTokenEnhancerChain() {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(createJwtTokenEnhancer(), createJwtAccessTokenConverter()));
        return tokenEnhancerChain;
    }

    @Bean
    public JwtAccessTokenConverter createJwtAccessTokenConverter(){
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(securityProperties.getSigningKey());
        return jwtAccessTokenConverter;
    }

    @Bean
    public TokenEnhancer createJwtTokenEnhancer() {
        return new OauthJwtTokenEnhancer();
    }

    /**
     * 将所有授权信息都返回到资源服务器
     * 调用自定义用户转换器
     * 用于token解析转换
     */
    @Bean
    public DefaultAccessTokenConverter createDefaultAccessTokenConverter() {
        DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter();
        defaultAccessTokenConverter.setUserTokenConverter(oauthUserAuthenticationConverter);
        return defaultAccessTokenConverter;
    }

    /**
     * 声明 TokenStore 管理方式实现
     *
     * @return TokenStore
     */
    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(lettuceConnectionFactory);
//        return new JdbcTokenStore(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .authenticationManager(authenticationManager)
                .approvalStore(createApprovalStore())
                .authorizationCodeServices(createAuthorizationCodeServices())
                .userDetailsService(oauthUserDetailsService)
                .tokenServices(createDefaultTokenServices())
                .accessTokenConverter(createDefaultAccessTokenConverter());

        // 自定义确认授权页面
        endpoints.pathMapping("/oauth/confirm_access", "/oauth/confirm_access");
        // 自定义错误页
        endpoints.pathMapping("/oauth/error", "/oauth/error");
        // 自定义异常转换类
        endpoints.exceptionTranslator(new HerodotusWebResponseExceptionTranslator());
    }
}

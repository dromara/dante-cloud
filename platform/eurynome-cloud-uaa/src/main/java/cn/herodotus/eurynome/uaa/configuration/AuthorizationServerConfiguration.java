package cn.herodotus.eurynome.uaa.configuration;

import cn.herodotus.eurynome.security.response.HerodotusWebResponseExceptionTranslator;
import cn.herodotus.eurynome.security.authentication.token.HerodotusJwtTokenEnhancer;
import cn.herodotus.eurynome.security.properties.SecurityProperties;
import cn.herodotus.eurynome.uaa.service.OauthClientDetailsService;
import cn.herodotus.eurynome.uaa.service.OauthUserDetailsService;
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
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
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
 * <p>
 * 以下过滤器是实现OAuth 2.0资源服务器所必需的：
 * ·将OAuth2AuthenticationProcessingFilter用于加载给定的认证访问令牌请求的认证。
 * <p>
 * 配置授权端点的URL
 * '@EnableAuthorizationServer' 提供/oauth/authorize,/oauth/token,/oauth/check_token,/oauth/confirm_access,/oauth/error
 * <p>
 * [Default Security: /login] {@link org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter#doFilter(ServletRequest, ServletResponse, FilterChain)}
 * [/oauth/authorize] {@link org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint#authorize(Map, Map, SessionStatus, Principal)}
 * [/oauth/token] {@link org.springframework.security.oauth2.provider.endpoint.TokenEndpoint#getAccessToken(Principal, Map)}
 * [/oauth/check_token] {@link org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint#checkToken(String)}
 * [/oauth/confirm_access]{@link org.springframework.security.oauth2.provider.endpoint.WhitelabelApprovalEndpoint#getAccessConfirmation(Map, HttpServletRequest)}
 * [/oauth/token_key]
 * [/oauth/error]
 * <p>
 * Oauth Client Details
 * ·Scope 用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围 scope中文翻译就是作用域，用来限制客户端权限访问的范围，可以用来设置角色或者权限，也可以不设置
 * [scope] {@link org.springframework.security.oauth2.provider.endpoint.TokenEndpoint#postAccessToken(Principal, Map)}
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
    private SecurityProperties securityProperties;
    @Autowired
    private DefaultAccessTokenConverter defaultAccessTokenConverter;

    /**
     * 以下三个方法的配置是Oauth2 Authorization Server Configuration 的核心内容
     * @see :https://projects.spring.io/spring-security-oauth/docs/oauth2.html
     */

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
     * As with the Authorization Server, you can often use the DefaultTokenServices and the choices are mostly expressed through the TokenStore (backend storage or local encoding).
     * An alternative is the RemoteTokenServices which is a Spring OAuth features (not part of the spec) allowing Resource Servers to decode tokens through an HTTP resource on the Authorization Server (/oauth/check_token).
     * RemoteTokenServices are convenient if there is not a huge volume of traffic in the Resource Servers (every request has to be verified with the Authorization Server), or if you can afford to cache the results.
     * To use the /oauth/check_token endpoint you need to expose it by changing its access rule (default is "denyAll()") in the AuthorizationServerSecurityConfigurer
     * <p>
     * 配置令牌端点(Token Endpoint)的安全约束
     * {@link org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerSecurityConfiguration}
     * <p>
     * 权限添加的位置：
     * {@link org.springframework.security.web.access.expression.ExpressionBasedFilterInvocationSecurityMetadataSource}
     *
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
                .checkTokenAccess("isAuthenticated()")
//                .checkTokenAccess("permitAll()")
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
     * 之前是采用自己定一个DefaultTokenServices的方式对Token的一些内容进行设置。
     * 但是这样就存在一个问题，这会让人混淆有些属性，例如tokenStore，到底是应该设置在endpoints还是DefaultTokenServices中。
     * <p>
     * 后来查阅的oauth的源代码{@link AuthorizationServerEndpointsConfigurer#getDefaultAuthorizationServerTokenServices()}，发现：
     * 这个类会判断是否已经设置了DefaultTokenServices，如果没有就帮助创建一个。
     * 通过对比发现oauth源代码创建DefaultTokenServices内容，和自己创建的没有多大区别，因此考虑没有比较自己创建，还引起了不必要的混乱。
     * <p>
     * setAccessTokenValiditySeconds 由于我们在数据库中设置了表oauth_client_details字段 access_token_validity
     * setRefreshTokenValiditySeconds 由于我们在数据库中设置了表oauth_client_details字段 refresh_token_validity
     *
     * @param endpoints AuthorizationServerEndpointsConfigurer
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .authenticationManager(authenticationManager)
                // 授权允许存储方式
                .approvalStore(createApprovalStore())
                // 授权码模式code存储方式
                .authorizationCodeServices(createAuthorizationCodeServices())
                // token存储方式
                .tokenStore(createTokenStore())
                .userDetailsService(oauthUserDetailsService)
                .tokenEnhancer(createAccessTokenEnhancerChain())
                .accessTokenConverter(defaultAccessTokenConverter);

        // 自定义确认授权页面
        endpoints.pathMapping("/oauth/confirm_access", "/oauth/confirm_access");
        // 自定义错误页
        endpoints.pathMapping("/oauth/error", "/oauth/error");
        // 自定义异常转换类
        endpoints.exceptionTranslator(new HerodotusWebResponseExceptionTranslator());
    }

    /**
     * 声明 TokenStore 管理方式实现
     *
     * @return TokenStore
     */
    @Bean
    public TokenStore createTokenStore() {
        return new RedisTokenStore(lettuceConnectionFactory);
    }

    /**
     * 授权store。主要用于Authorization Code模式中，确认授权范围页面。
     * <p>
     * JdbcApprovalStore不是特别友好，针对Postgresql数据库，会出现大小写问题。
     *
     * @return ApprovalStore
     * @see: https://github.com/spring-projects/spring-security-oauth/issues/1419
     */
    @Bean
    public ApprovalStore createApprovalStore() {
        TokenApprovalStore tokenApprovalStore = new TokenApprovalStore();
        tokenApprovalStore.setTokenStore(createTokenStore());
        return tokenApprovalStore;
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

    @Bean
    public TokenEnhancerChain createAccessTokenEnhancerChain() {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(createJwtTokenEnhancer(), createJwtAccessTokenConverter()));
        return tokenEnhancerChain;
    }

    @Bean
    public JwtAccessTokenConverter createJwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(securityProperties.getSigningKey());
        return jwtAccessTokenConverter;
    }

    @Bean
    public TokenEnhancer createJwtTokenEnhancer() {
        return new HerodotusJwtTokenEnhancer();
    }
}

package cn.herodotus.eurynome.security.access;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

import java.util.Collection;

/** 
 * <p>Description: AccessDecisionManager是访问决策器，决定某个用户具有的角色，是否有足够的权限去访问某个资源 </p>
 *
 * Oauth2是为用户资源的授权（更多的只是认证）定义了一个安全、开放及简单的标准，第三方无需知道用户的账号及密码，就可获取到用户的授权信息，并且这是安全的。
 *
 * 
 * @author : gengwei.zheng
 * @date : 2020/5/20 12:27
 */

public class HerodotusAccessDecisionManager implements AccessDecisionManager {

    /**
     * decide 方法是判定是否拥有权限的决策方法，
     *
     * @param authentication 是自定义UserService中循环添加到 GrantedAuthority 对象中的权限信息集合.
     * @param object 包含客户端发起的请求的requset信息，可转换为 HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
     * @param configAttributes 为InvocationSecurityMetadataSource的getAttributes(Object object)这个方法返回的结果，
     *                         此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在即（即返回值为空）权限表中则放行。
     *
     * @throws AccessDeniedException 拒绝访问
     * @throws InsufficientAuthenticationException 认证不足
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}

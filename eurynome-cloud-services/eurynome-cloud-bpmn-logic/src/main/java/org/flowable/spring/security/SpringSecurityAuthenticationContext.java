package org.flowable.spring.security;

import cn.herodotus.eurynome.component.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.identity.AuthenticationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.security.Principal;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/14 19:04
 */
@Slf4j
public class SpringSecurityAuthenticationContext implements AuthenticationContext {

    @Override
    public String getAuthenticatedUserId() {
        log.debug("[Herodotus] |- Flowable get AuthenticatedUserId from LUBAN CLOUD!");
        return SecurityUtils.getClientId();
    }

    @Override
    public Principal getPrincipal() {
        log.debug("[Herodotus] |- Flowable get getPrincipal from LUBAN CLOUD!");
        return SecurityUtils.getAuthentication();
    }

    @Override
    public void setPrincipal(Principal principal) {
        if (principal == null) {
            SecurityUtils.getSecurityContext().setAuthentication(null);
        } else if (principal instanceof Authentication) {
            SecurityUtils.getSecurityContext().setAuthentication((Authentication) principal);
        } else {
            SecurityUtils.getSecurityContext().setAuthentication(new UsernamePasswordAuthenticationToken(principal, null));
            log.debug("Setting a principal that is not of type `org.springframework.security.core.Authentication`."
                            + " When using Spring Security you can just set the user through 'SecurityContextHolder.getContext().setAuthentication(..)'"
                            + " Using 'org.springframework.security.authentication.UsernamePasswordAuthenticationToken' to wrap the principal of type '{}'",
                    principal.getClass());
        }

    }
}

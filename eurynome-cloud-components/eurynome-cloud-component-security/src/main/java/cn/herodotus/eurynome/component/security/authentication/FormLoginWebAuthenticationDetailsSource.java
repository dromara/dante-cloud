package cn.herodotus.eurynome.component.security.authentication;

import cn.herodotus.eurynome.component.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/29 15:49
 */
@Component
public class FormLoginWebAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest request) {
        return new FormLoginWebAuthenticationDetails(request, securityProperties);
    }
}

package cn.herodotus.eurynome.component.security.authentication;

import cn.herodotus.eurynome.component.security.properties.SecurityProperities;
import cn.herodotus.eurynome.component.security.utils.SymmetricUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/27 17:38
 */
@Slf4j
public class FormLoginDecryptParameterAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private SecurityProperities securityProperities;

    public FormLoginDecryptParameterAuthenticationFilter(SecurityProperities securityProperities) {
        super();
        this.securityProperities = securityProperities;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        UsernamePasswordAuthenticationToken authRequest = getAuthenticationToken(request);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);


        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 重写该方法，避免在日志Debug级别会输出错误信息的问题。
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();

        getRememberMeServices().loginFail(request, response);

        getFailureHandler().onAuthenticationFailure(request, response, failed);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(
            HttpServletRequest request) {

        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String key = request.getParameter("symmetric");

        if (StringUtils.isBlank(username)) {
            username = "";
        }

        if (StringUtils.isBlank(password)) {
            password = "";
        }

        if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            byte[] byteKey = SymmetricUtils.getDecryptedSymmetricKey(key);
            username = SymmetricUtils.decrypt(username, byteKey);
            password = SymmetricUtils.decrypt(password, byteKey);
            log.debug("[Herodotus] |- Decrypt Username is : [{}], Password is : [{}]", username, password);

        }

        return new UsernamePasswordAuthenticationToken(username, password);
    }
}

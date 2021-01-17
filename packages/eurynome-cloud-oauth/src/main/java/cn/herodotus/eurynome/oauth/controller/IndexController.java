package cn.herodotus.eurynome.oauth.controller;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.oauth.utils.SymmetricUtils;
import cn.herodotus.eurynome.security.configuration.SecurityGlobalExceptionHandler;
import cn.herodotus.eurynome.security.definition.service.HerodotusClientDetailsService;
import cn.herodotus.eurynome.security.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author gengwei.zheng
 */
@Slf4j
@Controller
@SessionAttributes("authorizationRequest")
public class IndexController {

    private static final String ERROR_MESSAGE_KEY = "SPRING_SECURITY_LAST_EXCEPTION_CUSTOM_MESSAGE";

    @Autowired
    private HerodotusClientDetailsService herodotusClientDetailsService;
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 欢迎页
     *
     * @return
     */
    @RequestMapping("/")
    public String welcome() {
        return "/login";
    }

    /**
     * 登录页
     *
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(Map<String, Object> model, HttpServletRequest request) throws DecoderException {

        ModelAndView modelAndView = new ModelAndView("/login");

        HttpSession session = request.getSession();
        Object error = session.getAttribute(ERROR_MESSAGE_KEY);
        if (ObjectUtils.isNotEmpty(error)) {
            modelAndView.addObject("message", error);
        }
        session.removeAttribute(ERROR_MESSAGE_KEY);

        // AES加密key
        modelAndView.addObject("soup_spoon", SymmetricUtils.getEncryptedSymmetricKey());
        // 登录可配置用户名参数
        modelAndView.addObject("vulgar_tycoon", securityProperties.getLogin().getUsernameParameter());
        // 登录可配置密码参数
        modelAndView.addObject("beast", securityProperties.getLogin().getPasswordParameter());
        // 登录可配置验证码参数
        modelAndView.addObject("graphic", securityProperties.getVerificationCode().getVerificationCodeParameter());
        // 登录可配置是否启用验证码参数
        modelAndView.addObject("hide_verification_code", securityProperties.getVerificationCode().isClosed());

        return modelAndView;
    }

    /**
     * 确认授权页
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/oauth/confirm_access")
    public ModelAndView confirmAccess(Map<String, Object> model, HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView("/confirm_access");

        if (request.getAttribute("_csrf") != null) {
            modelAndView.addObject("_csrf", request.getAttribute("_csrf"));
        }

        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
        modelAndView.addObject("scopes", authorizationRequest.getScope());

        ClientDetails clientDetails = herodotusClientDetailsService.getOauthClientDetails(authorizationRequest.getClientId());
        modelAndView.addObject("app", clientDetails.getAdditionalInformation());

        return modelAndView;
    }

    /**
     * 自定义oauth2错误页
     *
     * @param request
     * @return
     */
    @RequestMapping("/oauth/error")
    public String handleError(Map<String, Object> model, HttpServletRequest request) {
        Object error = request.getAttribute("error");
        if (error instanceof Exception) {
            Exception exception = (Exception) error;
            Result<String> result = SecurityGlobalExceptionHandler.resolveOauthException(exception, request.getRequestURI());
            model.putAll(result.toModel());
        }
        return "/error";
    }
}

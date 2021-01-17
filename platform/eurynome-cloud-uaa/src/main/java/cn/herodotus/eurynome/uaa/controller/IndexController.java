package cn.herodotus.eurynome.uaa.controller;

import cn.herodotus.eurynome.security.properties.SecurityProperties;
import cn.herodotus.eurynome.security.utils.SecurityUtils;
import cn.herodotus.eurynome.oauth.utils.SymmetricUtils;
import cn.herodotus.eurynome.uaa.service.OauthClientDetailsService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
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
    private OauthClientDetailsService oauthClientDetailsService;
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 欢迎页
     *
     * @return
     */
    @GetMapping("/")
    public String welcome() {
        return "/login";
    }

    /**
     * 登录页
     *
     * @return
     */
    @GetMapping("/login")
    public ModelAndView login(HttpServletRequest request) throws DecoderException {
        ModelAndView modelAndView = new ModelAndView("/login");

        HttpSession session = request.getSession();
        Object error = session.getAttribute(ERROR_MESSAGE_KEY);
        if (ObjectUtils.isNotEmpty(error) ) {
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
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/oauth/confirm_access")
    public String confirm_access(HttpServletRequest request, HttpSession session, Map<String, Object> model) {
        Map<String, Object> scopeMap;
        if (model.containsKey("scopes")) {
            scopeMap = (Map<String, Object>) model.get("scopes");
        } else {
            scopeMap = (Map<String, Object>) request.getAttribute("scopes");
        }

        List<String> scopeList = new ArrayList<>();
        if (MapUtils.isNotEmpty(scopeMap)) {
            scopeList.addAll(scopeMap.keySet());
        }

        model.put("scopeList", scopeList);

        Object auth = session.getAttribute("authorizationRequest");
        if (auth != null) {
            AuthorizationRequest authorizationRequest = (AuthorizationRequest) auth;
            // TODO 这里后面需要完善一下。Client Details 信息不够完整
            ClientDetails clientDetails = oauthClientDetailsService.getOauthClientDetails(authorizationRequest.getClientId());
            model.put("app", clientDetails.getAdditionalInformation());
            model.put("user", SecurityUtils.getPrincipal());
        }

        System.out.println(JSON.toJSONString(model));

        return "/confirm_access";
    }

    /**
     * 自定义oauth2错误页
     * @param request
     * @return
     */
    @RequestMapping("/oauth/error")
    @ResponseBody
    public Object handleError(HttpServletRequest request) {
        Object error = request.getAttribute("error");
        return error;
    }
}

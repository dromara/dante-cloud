package cn.herodotus.eurynome.platform.uaa.controller;

import cn.herodotus.eurynome.security.utils.SecurityUtils;
import cn.herodotus.eurynome.platform.uaa.service.OauthClientDetailsService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

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

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    /**
     * 欢迎页
     *
     * @return
     */
    @GetMapping("/")
    public String welcome() {
        return login();
    }

    /**
     * 登录页
     *
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "/login";
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
            ClientDetails clientDetails = oauthClientDetailsService.getRemoteOauthClientDetails(authorizationRequest.getClientId());
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

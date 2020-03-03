package cn.herodotus.eurynome.upms.rest.controller;


import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.security.domain.HerodotusUserDetails;
import cn.herodotus.eurynome.component.security.utils.SecurityUtils;
import cn.herodotus.eurynome.upms.rest.configuration.Oauth2ClientConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Api(tags = "用户中心")
@Slf4j
@RestController
public class LoginController {
    @Autowired
    private VerificationCodeController verificationCodeController;


    @Autowired
    private Oauth2ClientConfig clientConfig;

    /**
     * 获取用户基础信息
     *
     * @return
     */
    @ApiOperation(value = "获取当前登录用户信息", notes = "获取当前登录用户信息")
    @GetMapping("/current/user")
    public Result<HerodotusUserDetails> getUserProfile() {

        Result<HerodotusUserDetails> result = new Result<>();
        HerodotusUserDetails userDetails = SecurityUtils.getPrincipal();

        String name123 = "";
        if (userDetails != null) {
            name123 = userDetails.getUsername();
            System.out.println(userDetails);
        }


        log.info("hi,i'am hello world service!!!" + JSON.toJSONString(userDetails));
//
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        //anonymousUser
        //    Object principal1 =  userInfoRemoteService.getUser();//SecurityUtils.getPrincipal();
        return result.data(userDetails);
    }


    /**
     * 获取用户访问令牌
     * 基于oauth2密码模式登录
     *
     * @param username
     * @param password
     * @return access_token
     */
    @ApiOperation(value = "登录获取用户访问令牌", notes = "基于oauth2密码模式登录,无需签名,返回access_token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", required = true, value = "登录名", paramType = "form"),
            @ApiImplicitParam(name = "password", required = true, value = "登录密码", paramType = "form"),
            @ApiImplicitParam(name = "code", required = true, value = "验证码", paramType = "form"),
    })
    @PostMapping("/login/token")
    public Result<Object> getLoginToken(@RequestParam String username, @RequestParam String password, @RequestParam String code, @RequestHeader HttpHeaders headers) {

        log.warn("id:{},sec:{}", clientConfig.getClient_id(), clientConfig.getClient_secret());
        if (verificationCodeController.checkVerificationCode(code)) {

            return new Result<Object>().ok().message("验证码正确").data(getToken(username, password, null, headers));
        } else {
            return new Result<Object>().failed().message("验证码错误");
        }
        //return null;
//        JSONObject result = getToken(username,password,null,headers);
//        if (result.containsKey("access_token")) {
//            return ResultBody.ok().data(result);
//        } else {
//            return result;
//        }
    }

    private JSONObject getToken(String userName, String password, String type, HttpHeaders headers) {
        RestTemplate restTemplate = new RestTemplate();
        // 使用oauth2密码模式登录.
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("username", userName);
        postParameters.add("password", password);
        postParameters.add("client_id", clientConfig.getClient_id());
        postParameters.add("client_secret", clientConfig.getClient_secret());
        postParameters.add("grant_type", "password");
        // 添加参数区分,第三方登录
        postParameters.add("login_type", type);
        // 使用客户端的请求头,发起请求
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 强制移除 原来的请求头,防止token失效
        headers.remove(HttpHeaders.AUTHORIZATION);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity(postParameters, headers);
        JSONObject result = restTemplate.postForObject(clientConfig.getAccess_token_uri(), request, JSONObject.class);
        return result;
    }
}
package cn.herodotus.eurynome.platform.uaa.controller;

import cn.herodotus.eurynome.component.security.domain.ArtisanUserDetails;
import cn.herodotus.eurynome.component.security.utils.SecurityUtils;
import cn.herodotus.eurynome.component.security.utils.VerificationCodeUtils;
import cn.herodotus.eurynome.platform.uaa.service.feign.VerifyCaptchaRemoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gengwei.zheng
 * @refer:https://conkeyn.iteye.com/blog/2296406
 */
@Slf4j
@RestController
@Api(tags = "认证接口")
public class LoginController {


    @Autowired
    private VerifyCaptchaRemoteService captchaRemoteService;


    @GetMapping("/current/user")
    @ApiOperation("获取当前用户信息")
    public ArtisanUserDetails getUserPrincipal() {
        return SecurityUtils.getPrincipal();
    }

    @GetMapping("/hello/getVerifiCode")
    public void getVerifiCode() throws Exception {
        byte[] bytes = captchaRemoteService.getVerifiCode();
        VerificationCodeUtils.buildImageResponse(bytes);
    }

    @PostMapping("/hello/checkVerifiCode")
    public Boolean checkVerifiCode(String code) {
        return captchaRemoteService.checkVerifiCode(code);
    }
}

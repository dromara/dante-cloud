package cn.herodotus.eurynome.platform.uaa.controller;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.component.security.core.userdetails.HerodotusUserDetails;
import cn.herodotus.eurynome.component.security.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author gengwei.zheng
 * @refer:https://conkeyn.iteye.com/blog/2296406
 */
@Slf4j
@RestController
@Api(tags = "认证接口")
public class IdentityController {

    @Resource
    private ConsumerTokenServices consumerTokenServices;

    /**
     * 获取用户基础信息
     * @return Result<HerodotusUserDetails>
     */
    @ApiOperation(value = "获取当前登录用户信息", notes = "获取当前登录用户信息")
    @GetMapping("/identity/profile")
    public Result<HerodotusUserDetails> getUserProfile() {

        HerodotusUserDetails userDetails = SecurityUtils.getPrincipal();
        Result<HerodotusUserDetails> result = new Result<>();

        log.debug("[Herodotus] |- Get oauth profile！");
        return result.ok().data(userDetails);
    }

    @GetMapping("/identity/logout")
    public Result<String> userLogout(@RequestParam("access_token") String accessToken) {
        if (consumerTokenServices.revokeToken(accessToken)) {
            return new Result<String>().ok().message("注销成功！");
        } else {
            return new Result<String>().ok().message("注销失败！");
        }
    }

}

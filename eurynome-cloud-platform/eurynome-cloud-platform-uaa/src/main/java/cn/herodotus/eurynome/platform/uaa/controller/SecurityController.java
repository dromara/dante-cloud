package cn.herodotus.eurynome.platform.uaa.controller;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.security.domain.HerodotusUserDetails;
import cn.herodotus.eurynome.component.security.utils.SecurityUtils;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gengwei.zheng
 * @refer:https://conkeyn.iteye.com/blog/2296406
 */
@Slf4j
@RestController
@Api(tags = "认证接口")
public class SecurityController {

    /**
     * 获取用户基础信息
     * @return Result<HerodotusUserDetails>
     */
    @ApiOperation(value = "获取当前登录用户信息", notes = "获取当前登录用户信息")
    @GetMapping("/oauth/profile")
    public Result<HerodotusUserDetails> getUserProfile() {

        HerodotusUserDetails userDetails = SecurityUtils.getPrincipal();
        Result<HerodotusUserDetails> result = new Result<>();

        log.debug("[Herodotus] |- Get oauth profile！");
        return result.ok().data(userDetails);
    }
}

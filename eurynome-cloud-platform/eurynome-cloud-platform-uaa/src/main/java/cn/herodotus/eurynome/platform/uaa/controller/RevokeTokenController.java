package cn.herodotus.eurynome.platform.uaa.controller;

import cn.herodotus.eurynome.component.common.domain.Result;
import com.alibaba.fastjson.JSON;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
//@FrameworkEndpoint //方式一 以endpoint方式，这种方式不友好，完成后始终返回json说token无效
public class RevokeTokenController {

    //@Autowired //方式一
    //@Qualifier("consumerTokenServices") //方式一
    @Resource
    private ConsumerTokenServices consumerTokenServices;

    @GetMapping("/logout/quit")
    public String wuJinLogout(@RequestParam("access_token")String accessToken){
        Result result = Result.failed().message("注销失败");
        if (consumerTokenServices.revokeToken(accessToken)) {
            result = Result.ok().message("注销成功");
            return JSON.toJSONString(result);
        }
        return JSON.toJSONString(result);
    }
}

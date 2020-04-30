package cn.herodotus.eurynome.upms.rest.controller.social;

import cn.herodotus.eurynome.component.data.base.service.BaseService;
import cn.herodotus.eurynome.component.rest.controller.BaseRestController;
import cn.herodotus.eurynome.upms.api.entity.social.WeappUser;
import cn.herodotus.eurynome.upms.logic.service.social.WeappUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weapp/user")
@Api(value = "小程序用户接口", tags = "用户中心服务")
public class WeappUserController extends BaseRestController<WeappUser, String> {

    private final WeappUserService weappUserService;

    @Autowired
    public WeappUserController(WeappUserService weappUserService) {
        this.weappUserService = weappUserService;
    }

    @Override
    public BaseService<WeappUser, String> getBaseService() {
        return this.weappUserService;
    }
}

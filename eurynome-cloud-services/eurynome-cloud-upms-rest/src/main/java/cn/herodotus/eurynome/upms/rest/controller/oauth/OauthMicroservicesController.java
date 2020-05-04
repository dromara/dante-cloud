package cn.herodotus.eurynome.upms.rest.controller.oauth;

import cn.herodotus.eurynome.component.data.base.service.BaseService;
import cn.herodotus.eurynome.component.rest.controller.BaseRestController;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthMicroservices;
import cn.herodotus.eurynome.upms.logic.service.oauth.OauthMicroservicesService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> Description : OauthMicroservicesController </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/30 15:34
 */
@RestController
@RequestMapping("/oauth/microservices")
@Api(value = "厂商管理接口", tags = "用户中心服务")
@Transactional(rollbackFor = Exception.class)
public class OauthMicroservicesController extends BaseRestController<OauthMicroservices, String> {

    private final OauthMicroservicesService oauthMicroservicesService;

    @Autowired
    public OauthMicroservicesController(OauthMicroservicesService oauthMicroservicesService) {
        this.oauthMicroservicesService = oauthMicroservicesService;
    }

    @Override
    public BaseService<OauthMicroservices, String> getBaseService() {
        return this.oauthMicroservicesService;
    }
}

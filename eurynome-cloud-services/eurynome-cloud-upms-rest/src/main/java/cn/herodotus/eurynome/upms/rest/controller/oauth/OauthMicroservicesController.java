package cn.herodotus.eurynome.upms.rest.controller.oauth;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.data.base.service.BaseService;
import cn.herodotus.eurynome.component.rest.controller.BaseRestController;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthMicroservices;
import cn.herodotus.eurynome.upms.logic.service.oauth.OauthMicroservicesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "发布或更新ClientDetails配置", notes = "如果ClientDetails中，存储数据的ApplicationType为Service，那么就可以进行配置信息的发布")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clientId", required = true, value = "clientId", paramType = "JSON")
    })
    @PostMapping("/config")
    public Result<String> publishConfig(@RequestParam(name = "clientId") String clientId) {
        boolean isPublishOk = oauthMicroservicesService.publishOrUpdateConfig(clientId);
        Result<String> result = new Result<>();
        if (isPublishOk) {
            return result.ok().message("发布配置成功！");
        } else {
            return result.failed().message("发布配置失败！");
        }
    }

    @ApiOperation(value = "删除微服务配置", notes = "根据clientId删除ClientDetails对应Service的Nacos配置。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clientId", required = true, value = "clientId", paramType = "JSON")
    })
    @DeleteMapping("/config")
    public Result<String> deleteConfig(@RequestBody String clientId) {
        boolean isDeleteOk = oauthMicroservicesService.deleteConfig(clientId);
        Result<String> result = new Result<>();
        if (isDeleteOk) {
            return result.ok().message("删除配置成功！");
        } else {
            return result.failed().message("删除配置失败！");
        }
    }
}

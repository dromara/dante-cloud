package cn.herodotus.eurynome.upms.rest.controller.oauth;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.data.base.service.BaseService;
import cn.herodotus.eurynome.rest.controller.BaseRestController;
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
@Api(tags = {"用户中心服务", "Oauth微服接口"})
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

    @ApiOperation(value = "发布或更新微服务配置", notes = "为接入平台的微服务创建OauthClientDetails相关的配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceId", required = true, value = "serviceId", paramType = "JSON")
    })
    @PostMapping("/config")
    public Result<String> publishConfig(@RequestParam(name = "serviceId") String serviceId) {
        boolean isPublishOk = oauthMicroservicesService.publishOrUpdateConfig(serviceId);
        Result<String> result = new Result<>();
        if (isPublishOk) {
            return result.ok().message("发布配置成功！");
        } else {
            return result.failed().message("发布配置失败！");
        }
    }

    @ApiOperation(value = "删除微服务配置", notes = "根据serviceId删除与该微服务对应的Oauth2的Nacos配置。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceId", required = true, value = "serviceId", paramType = "JSON")
    })
    @DeleteMapping("/config")
    public Result<String> deleteConfig(@RequestBody String serviceId) {
        boolean isDeleteOk = oauthMicroservicesService.deleteConfig(serviceId);
        Result<String> result = new Result<>();
        if (isDeleteOk) {
            return result.ok().message("删除配置成功！");
        } else {
            return result.failed().message("删除配置失败！");
        }
    }
}

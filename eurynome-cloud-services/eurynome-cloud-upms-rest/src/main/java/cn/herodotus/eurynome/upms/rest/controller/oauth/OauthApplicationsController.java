package cn.herodotus.eurynome.upms.rest.controller.oauth;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.data.base.service.BaseService;
import cn.herodotus.eurynome.component.rest.controller.BaseRestController;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthApplications;
import cn.herodotus.eurynome.upms.logic.service.oauth.OauthApplicationsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> Description : OauthApplicationsController </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/25 17:10
 */
@RestController
@RequestMapping("/oauth/applications")
@Api(value = "Oauth应用接口", tags = "用户中心服务")
public class OauthApplicationsController extends BaseRestController<OauthApplications, String> {

    private final OauthApplicationsService oauthApplicationsService;

    @Autowired
    public OauthApplicationsController(OauthApplicationsService oauthApplicationsService) {
        this.oauthApplicationsService = oauthApplicationsService;
    }

    @Override
    public BaseService<OauthApplications, String> getBaseService() {
        return this.oauthApplicationsService;
    }

    @ApiOperation(value = "给应用分配Scope", notes = "给应用分配Scope")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appKey", required = true, value = "appKey"),
            @ApiImplicitParam(name = "scopes[]", required = true, value = "Scope对象组成的数组")
    })
    @PutMapping
    public Result<OauthApplications> assign(@RequestParam(name = "appKey") String scopeId, @RequestParam(name = "scopes[]") String[] scopes) {
        OauthApplications oauthApplications = oauthApplicationsService.assign(scopeId, scopes);
        return result(oauthApplications);
    }
}

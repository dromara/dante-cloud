package cn.herodotus.eurynome.upms.rest.controller.oauth;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.rest.base.controller.BaseWriteableRestController;
import cn.herodotus.eurynome.rest.base.service.WriteableService;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthApplications;
import cn.herodotus.eurynome.upms.logic.service.oauth.OauthApplicationsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
@Api(tags = {"用户中心服务", "Oauth应用接口"})
@Transactional(rollbackFor = Exception.class)
public class OauthApplicationsController extends BaseWriteableRestController<OauthApplications, String> {

    private final OauthApplicationsService oauthApplicationsService;

    @Autowired
    public OauthApplicationsController(OauthApplicationsService oauthApplicationsService) {
        this.oauthApplicationsService = oauthApplicationsService;
    }

    @Override
    public WriteableService<OauthApplications, String> getWriteableService() {
        return this.oauthApplicationsService;
    }

    @ApiOperation(value = "给应用分配Scope", notes = "给应用分配Scope")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appKey", required = true, value = "appKey", dataType = "String", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "scopes[]", required = true, value = "Scope对象组成的数组", dataType = "String[]", dataTypeClass = String[].class, paramType = "query")
    })
    @PutMapping
    public Result<OauthApplications> assign(@RequestParam(name = "appKey") String scopeId, @RequestParam(name = "scopes[]") String[] scopes) {
        OauthApplications oauthApplications = oauthApplicationsService.assign(scopeId, scopes);
        return result(oauthApplications);
    }
}

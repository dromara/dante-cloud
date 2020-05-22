package cn.herodotus.eurynome.upms.rest.controller.oauth;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.data.base.service.BaseService;
import cn.herodotus.eurynome.component.rest.controller.BaseRestController;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthScopes;
import cn.herodotus.eurynome.upms.logic.service.oauth.OauthScopesService;
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
 * <p> Description : OauthScopesController </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/25 17:10
 */
@RestController
@RequestMapping("/oauth/scopes")
@Api(tags = {"用户中心服务", "Oauth权限范围接口"})
public class OauthScopesController extends BaseRestController<OauthScopes, String> {

    private final OauthScopesService oauthScopesService;

    @Autowired
    public OauthScopesController(OauthScopesService oauthScopesService) {
        this.oauthScopesService = oauthScopesService;
    }

    @Override
    public BaseService<OauthScopes, String> getBaseService() {
        return this.oauthScopesService;
    }

    @ApiOperation(value = "给OauthScopes授权", notes = "为OauthScopes分配接口权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "scopeId", required = true, value = "ScopeID"),
            @ApiImplicitParam(name = "authorities[]", required = true, value = "权限对象组成的数组")
    })
    @PutMapping
    public Result<OauthScopes> authorize(@RequestParam(name = "scopeId") String scopeId, @RequestParam(name = "authorities[]") String[] authorities) {
        OauthScopes oauthScopes = oauthScopesService.authorize(scopeId, authorities);
        return result(oauthScopes);
    }
}

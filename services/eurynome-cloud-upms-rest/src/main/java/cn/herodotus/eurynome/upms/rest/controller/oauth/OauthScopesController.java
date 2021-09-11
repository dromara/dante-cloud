package cn.herodotus.eurynome.upms.rest.controller.oauth;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.rest.base.controller.BaseWriteableRestController;
import cn.herodotus.eurynome.rest.base.service.WriteableService;
import cn.herodotus.eurynome.upms.api.entity.oauth.OAuth2Scopes;
import cn.herodotus.eurynome.upms.logic.service.oauth.OauthScopesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Oauth权限范围接口")
public class OauthScopesController extends BaseWriteableRestController<OAuth2Scopes, String> {

    private final OauthScopesService oauthScopesService;

    @Autowired
    public OauthScopesController(OauthScopesService oauthScopesService) {
        this.oauthScopesService = oauthScopesService;
    }

    @Override
    public WriteableService<OAuth2Scopes, String> getWriteableService() {
        return this.oauthScopesService;
    }

    @Operation(summary = "给OauthScopes授权", description = "为OauthScopes分配接口权限")
    @Parameters({
            @Parameter(name = "scopeId", required = true, description = "ScopeID"),
            @Parameter(name = "authorities[]", required = true, description = "权限对象组成的数组")
    })
    @PutMapping
    public Result<OAuth2Scopes> authorize(@RequestParam(name = "scopeId") String scopeId, @RequestParam(name = "authorities[]") String[] authorities) {
        OAuth2Scopes OAuth2Scopes = oauthScopesService.authorize(scopeId, authorities);
        return result(OAuth2Scopes);
    }
}

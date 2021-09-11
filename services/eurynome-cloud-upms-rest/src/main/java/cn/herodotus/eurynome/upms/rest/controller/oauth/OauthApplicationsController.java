package cn.herodotus.eurynome.upms.rest.controller.oauth;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.rest.base.controller.BaseWriteableRestController;
import cn.herodotus.eurynome.rest.base.service.WriteableService;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthApplications;
import cn.herodotus.eurynome.upms.logic.service.oauth.OauthApplicationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Oauth应用接口")
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

    @Operation(summary = "给应用分配Scope", description = "给应用分配Scope")
    @Parameters({
            @Parameter(name = "appKey", required = true, description = "appKey"),
            @Parameter(name = "scopes[]", required = true, description = "Scope对象组成的数组")
    })
    @PutMapping
    public Result<OauthApplications> assign(@RequestParam(name = "appKey") String scopeId, @RequestParam(name = "scopes[]") String[] scopes) {
        OauthApplications oauthApplications = oauthApplicationsService.assign(scopeId, scopes);
        return result(oauthApplications);
    }
}

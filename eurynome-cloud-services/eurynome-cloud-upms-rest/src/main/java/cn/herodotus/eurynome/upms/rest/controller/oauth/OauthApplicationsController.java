package cn.herodotus.eurynome.upms.rest.controller.oauth;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.rest.controller.BaseController;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthApplications;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthScopes;
import cn.herodotus.eurynome.upms.api.entity.system.SysRole;
import cn.herodotus.eurynome.upms.logic.service.oauth.OauthApplicationsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p> Description : OauthApplicationsController </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/25 17:10
 */
@RestController
@RequestMapping("/oauth/applications")
@Api(value = "Oauth应用接口", tags = "用户中心服务")
public class OauthApplicationsController extends BaseController {

    @Autowired
    private OauthApplicationsService oauthApplicationsService;

    @ApiOperation(value = "获取应用分页数据", notes = "通过pageNumber和pageSize获取分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", required = true, value = "当前页数"),
            @ApiImplicitParam(name = "pageSize", required = true, value = "每页显示数据条目")
    })
    @GetMapping
    public Result<Map<String, Object>> findByPage(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize) {

        Page<OauthApplications> pages = oauthApplicationsService.findByPage(pageNumber, pageSize);
        return result(pages);
    }

    @ApiOperation(value = "保存或更新应用", notes = "接收JSON数据，转换为OauthApplications实体，进行保存或更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oauthApplications", required = true, value = "可转换为OauthApplications实体的json数据", paramType = "JSON")
    })
    @PostMapping
    public Result<OauthApplications> saveOrUpdate(@RequestBody OauthApplications oauthApplications) {
        OauthApplications newOauthApplications = oauthApplicationsService.saveOrUpdate(oauthApplications);
        return result(newOauthApplications);
    }

    @ApiOperation(value = "删除应用", notes = "根据appKey删除应用，以及相关联的关联关系数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appKey", required = true, value = "应用ID", paramType = "JSON")
    })
    @DeleteMapping
    public Result<String> delete(@RequestBody String appKey) {
        Result<String> result = result(appKey);
        oauthApplicationsService.deleteById(appKey);
        return result;
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

package cn.herodotus.eurynome.upms.rest.controller.oauth;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.common.utils.JacksonUtils;
import cn.herodotus.eurynome.component.rest.controller.BaseController;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthScopes;
import cn.herodotus.eurynome.upms.logic.service.oauth.OauthScopesService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p> Description : OauthScopesController </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/25 17:10
 */
@RestController
@RequestMapping("/oauth/scopes")
@Api(value = "Oauth Scopes 接口", tags = "用户中心服务")
public class OauthScopesController extends BaseController {

    @Autowired
    private OauthScopesService oauthScopesService;

    @ApiOperation(value = "获取OauthScopes分页数据", notes = "通过pageNumber和pageSize获取OauthScopes分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", required = true, value = "当前页数"),
            @ApiImplicitParam(name = "pageSize", required = true, value = "每页显示数据条目数")
    })
    @GetMapping
    public Result<Map<String, Object>> findByPage(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize) {

        Page<OauthScopes> pages = oauthScopesService.findByPage(pageNumber, pageSize);
        Result<Map<String, Object>> result = result(pages);
        System.out.println(JSON.toJSONString(result));
        System.out.println(JacksonUtils.toJson(result));
        return result;
    }

    @ApiOperation(value = "保存或更新OauthScopes", notes = "接收JSON数据，转换为OauthScopes实体，进行保存或更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oauthScopes", required = true, value = "可转换为OauthScopes实体的json数据", paramType = "JSON")
    })
    @PostMapping
    public Result<OauthScopes> saveOrUpdate(@RequestBody OauthScopes oauthScopes) {
        OauthScopes newOauthScopes = oauthScopesService.saveOrUpdate(oauthScopes);
        return result(newOauthScopes);
    }

    @ApiOperation(value = "删除OauthScopes", notes = "根据scopeId删除OauthScopes，以及相关联的关系数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "scopeId", required = true, value = "ScopeID", paramType = "JSON")
    })
    @DeleteMapping
    public Result<String> delete(@RequestBody String scopeId) {
        Result<String> result = result(scopeId);
        oauthScopesService.deleteById(scopeId);
        return result;
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

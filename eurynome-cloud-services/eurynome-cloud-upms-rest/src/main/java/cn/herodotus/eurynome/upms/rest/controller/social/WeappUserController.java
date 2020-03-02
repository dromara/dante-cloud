package cn.herodotus.eurynome.upms.rest.controller.social;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.rest.controller.BaseCrudController;
import cn.herodotus.eurynome.upms.api.entity.social.WeappUser;
import cn.herodotus.eurynome.upms.logic.service.social.WeappUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/weapp/user")
@Api(value = "小程序用户接口", tags = "用户中心服务")
public class WeappUserController extends BaseCrudController {

    private final WeappUserService weappUserService;

    @Autowired
    public WeappUserController(WeappUserService weappUserService) {
        this.weappUserService = weappUserService;
    }

    @ApiOperation(value = "获取微信小程序用户分页数据", notes = "通过pageNumber和pageSize获取分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", required = true, value = "当前页数"),
            @ApiImplicitParam(name = "pageSize", required = true, value = "每页显示数据条目")
    })
    @GetMapping
    public Result<Map<String, Object>> findByPage(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize) {
        Page<WeappUser> pages = weappUserService.findByPage(pageNumber, pageSize);
        return result(pages);
    }

    @ApiOperation(value = "保存或更新微信用户", notes = "接收JSON数据，转换为WeappUser实体，进行保存或更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "weappUser", required = true, value = "可转换为WeappUser实体的json数据", paramType = "JSON")
    })
    @PostMapping
    public Result<WeappUser> saveOrUpdate(@RequestBody WeappUser weappUser) {
        WeappUser newWeappUser = weappUserService.saveOrUpdate(weappUser);
        return result(newWeappUser);
    }

    @ApiOperation(value = "删除微信用户", notes = "根据openId删除微信用户，以及相关联的关联关系数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", required = true, value = "微信用户ID", paramType = "JSON")
    })
    @DeleteMapping
    public Result<String> delete(@RequestBody String openId) {
        Result<String> result = result(openId);

        weappUserService.delete(openId);
        return result;
    }
}

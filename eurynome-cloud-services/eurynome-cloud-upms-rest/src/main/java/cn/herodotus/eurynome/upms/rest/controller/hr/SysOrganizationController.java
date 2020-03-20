package cn.herodotus.eurynome.upms.rest.controller.hr;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.rest.controller.BaseController;
import cn.herodotus.eurynome.upms.api.entity.hr.SysOrganization;
import cn.herodotus.eurynome.upms.logic.service.hr.SysOrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/organization")
@Api(value = "单位管理接口", tags = "用户中心服务")
public class SysOrganizationController extends BaseController {

    private final SysOrganizationService sysOrganizationService;

    @Autowired
    public SysOrganizationController(SysOrganizationService sysOrganizationService) {
        this.sysOrganizationService = sysOrganizationService;
    }

    @ApiOperation(value = "获取单位分页数据", notes = "通过pageNumber和pageSize获取分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", required = true, value = "当前页数"),
            @ApiImplicitParam(name = "pageSize", required = true, value = "每页显示数据条目")
    })
    @GetMapping
    public Result<Map<String, Object>> findByPage(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize) {

        Page<SysOrganization> pages = sysOrganizationService.findByPage(pageNumber, pageSize);
        return result(pages);
    }

    @ApiOperation(value = "保存或更新单位", notes = "接收JSON数据，转换为SysOrganization实体，进行保存或更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysOrganization", required = true, value = "可转换为SysOrganization实体的json数据", paramType = "JSON")
    })
    @PostMapping
    public Result<SysOrganization> saveOrUpdate(@RequestBody SysOrganization sysOrganization) {
        SysOrganization newSysOrganization = sysOrganizationService.saveOrUpdate(sysOrganization);
        return result(newSysOrganization);
    }

    @ApiOperation(value = "删除单位", notes = "根据organizationId删除单位，以及相关联的关联关系数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "organizationId", required = true, value = "单位ID", paramType = "JSON")
    })
    @DeleteMapping
    public Result delete(@RequestBody String organizationId) {
        Result<String> result = result(organizationId);
        sysOrganizationService.deleteById(organizationId);
        return result;
    }
}

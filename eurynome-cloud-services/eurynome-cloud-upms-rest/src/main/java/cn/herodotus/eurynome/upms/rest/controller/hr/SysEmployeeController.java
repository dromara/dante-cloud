package cn.herodotus.eurynome.upms.rest.controller.hr;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.upms.api.entity.hr.SysEmployee;
import cn.herodotus.eurynome.upms.logic.service.hr.SysEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@Api(value = "人员管理接口", tags = "用户中心服务")
public class SysEmployeeController {

    private final SysEmployeeService sysEmployeeService;

    @Autowired
    public SysEmployeeController(SysEmployeeService sysEmployeeService) {
        this.sysEmployeeService = sysEmployeeService;
    }

    @ApiOperation(value = "获取人员分页数据", notes = "通过pageNumber和pageSize获取分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", required = true, value = "当前页数"),
            @ApiImplicitParam(name = "pageSize", required = true, value = "每页显示数据条目")
    })
    @GetMapping
    public Result<Page<SysEmployee>> findByPage(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize) {
        if (pageSize != 0) {
            Page<SysEmployee> pages = sysEmployeeService.findByPage(pageNumber, pageSize);
            return Result.ok().data(pages);
        } else {
            return Result.failed();
        }
    }

    @ApiOperation(value = "保存或更新人员", notes = "接收JSON数据，转换为SysEmployee实体，进行保存或更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysEmployee", required = true, value = "可转换为SysEmployee实体的json数据", paramType = "JSON")
    })
    @PostMapping
    public Result saveOrUpdate(@RequestBody SysEmployee sysEmployee) {
        SysEmployee newSysEmployee = sysEmployeeService.saveOrUpdate(sysEmployee);
        if (null != newSysEmployee) {
            return Result.ok().data(newSysEmployee);
        } else {
            return Result.failed().message("保存失败！");
        }
    }

    @ApiOperation(value = "删除人员", notes = "根据employeeId删除人员，以及相关联的关联关系数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "employeeId", required = true, value = "人员ID", paramType = "JSON")
    })
    @DeleteMapping
    public Result delete(@RequestBody String employeeId) {
        if (StringUtils.isNotEmpty(employeeId)) {
            sysEmployeeService.deleteById(employeeId);
            return Result.ok();
        } else {
            return Result.failed().message("参数错误，删除失败！");
        }
    }
}

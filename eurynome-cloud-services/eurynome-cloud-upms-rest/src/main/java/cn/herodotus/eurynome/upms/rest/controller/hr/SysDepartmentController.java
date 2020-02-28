package cn.herodotus.eurynome.upms.rest.controller.hr;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.upms.api.entity.hr.SysDepartment;
import cn.herodotus.eurynome.upms.logic.service.hr.SysDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
@Api(value = "部门管理接口", tags = "用户中心服务")
public class SysDepartmentController {

    private final SysDepartmentService sysDepartmentService;

    @Autowired
    public SysDepartmentController(SysDepartmentService sysDepartmentService) {
        this.sysDepartmentService = sysDepartmentService;
    }

    @ApiOperation(value = "获取部门分页数据", notes = "通过pageNumber和pageSize获取分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", required = true, value = "当前页数"),
            @ApiImplicitParam(name = "pageSize", required = true, value = "每页显示数据条目")
    })
    @GetMapping
    public Result findByPage(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize) {
        if (pageSize != 0) {
            Page<SysDepartment> pages = sysDepartmentService.findByPage(pageNumber, pageSize);
            return Result.ok().data(pages);
        } else {
            return Result.failed();
        }
    }

    @ApiOperation(value = "保存或更新部门", notes = "接收JSON数据，转换为SysDepartment实体，进行保存或更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysDepartment", required = true, value = "可转换为SysDepartment实体的json数据", paramType = "JSON")
    })
    @PostMapping
    public Result<SysDepartment> saveOrUpdate(@RequestBody SysDepartment sysDepartment) {
        SysDepartment newSysDepartment = sysDepartmentService.saveOrUpdate(sysDepartment);
        if (null != newSysDepartment) {
            return Result.ok().data(newSysDepartment);
        } else {
            return Result.failed().message("保存失败！");
        }
    }

    @ApiOperation(value = "删除部门", notes = "根据departmentId删除部门，以及相关联的关联关系数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "departmentId", required = true, value = "部门ID", paramType = "JSON")
    })
    @DeleteMapping
    public Result delete(@RequestBody String departmentId) {
        if (StringUtils.isNotEmpty(departmentId)) {
            sysDepartmentService.deleteById(departmentId);
            return Result.ok();
        } else {
            return Result.failed().message("参数错误，删除失败！");
        }
    }
}

package cn.herodotus.eurynome.upms.rest.controller.hr;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.upms.api.entity.hr.SysPosition;
import cn.herodotus.eurynome.upms.logic.service.hr.SysPositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/position")
@Api(value = "岗位管理接口", tags = "用户中心服务")
public class SysPositionController {

    private final SysPositionService sysPositionService;

    @Autowired
    public SysPositionController(SysPositionService sysPositionService) {
        this.sysPositionService = sysPositionService;
    }

    @ApiOperation(value = "获取岗位分页数据", notes = "通过pageNumber和pageSize获取分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", required = true, value = "当前页数"),
            @ApiImplicitParam(name = "pageSize", required = true, value = "每页显示数据条目")
    })
    @GetMapping
    public Result findByPage(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize) {
        if (pageSize != 0) {
            Page<SysPosition> pages = sysPositionService.findByPage(pageNumber, pageSize);
            return Result.ok().data(pages);
        } else {
            return Result.failed();
        }
    }

    @ApiOperation(value = "保存或更新岗位", notes = "接收JSON数据，转换为SysPosition实体，进行保存或更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPosition", required = true, value = "可转换为SysPosition实体的json数据", paramType = "JSON")
    })
    @PostMapping
    public Result saveOrUpdate(@RequestBody SysPosition sysPosition) {
        SysPosition newSysPosition = sysPositionService.saveOrUpdate(sysPosition);
        if (null != newSysPosition) {
            return Result.ok().data(newSysPosition);
        } else {
            return Result.failed().message("保存失败！");
        }
    }

    @ApiOperation(value = "删除岗位", notes = "根据positionId删除岗位，以及相关联的关联关系数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "positionId", required = true, value = "岗位ID", paramType = "JSON")
    })
    @DeleteMapping
    public Result delete(@RequestBody String positionId) {
        if (StringUtils.isNotEmpty(positionId)) {
            sysPositionService.delete(positionId);
            return Result.ok();
        } else {
            return Result.failed().message("参数错误，删除失败！");
        }
    }
}

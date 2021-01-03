package cn.herodotus.eurynome.upms.logic.controller.hr;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.crud.service.BaseService;
import cn.herodotus.eurynome.crud.controller.BaseRestController;
import cn.herodotus.eurynome.upms.api.entity.hr.SysEmployee;
import cn.herodotus.eurynome.upms.logic.service.hr.SysEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>Description: SysEmployeeController </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/19 15:19
 */
@RestController
@RequestMapping("/employee")
@Api(tags = {"用户中心服务", "人员管理接口"})
public class SysEmployeeController extends BaseRestController<SysEmployee, String> {

    private final SysEmployeeService sysEmployeeService;

    @Autowired
    public SysEmployeeController(SysEmployeeService sysEmployeeService) {
        this.sysEmployeeService = sysEmployeeService;
    }

    @Override
    public BaseService<SysEmployee, String> getBaseService() {
        return this.sysEmployeeService;
    }

    @ApiOperation(value = "获取部门人员", notes = "根据部门ID获取部门下的所有人员信息", consumes = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", required = true, value = "当前页码", dataType = "Integer", dataTypeClass = Integer.class, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", required = true, value = "每页显示数量", dataType = "Integer", dataTypeClass = Integer.class, paramType = "query"),
            @ApiImplicitParam(name = "departmentId", required = true, value = "单位ID", dataType = "String", dataTypeClass = String.class, paramType = "query"),
    })
    @GetMapping("/list")
    public Result<Map<String, Object>> findByPage(@RequestParam("pageNumber") Integer pageNumber,
                                                  @RequestParam("pageSize") Integer pageSize,
                                                  @RequestParam("departmentId") String departmentId) {
        Page<SysEmployee> pages = sysEmployeeService.findAllByPageWithDepartmentId(pageNumber, pageSize, departmentId);
        return result(pages);
    }
}

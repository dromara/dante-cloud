package cn.herodotus.eurynome.upms.logic.controller.hr;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.common.domain.TreeNode;
import cn.herodotus.eurynome.common.utils.TreeUtils;
import cn.herodotus.eurynome.crud.service.BaseService;
import cn.herodotus.eurynome.crud.controller.BaseRestController;
import cn.herodotus.eurynome.upms.api.entity.hr.SysDepartment;
import cn.herodotus.eurynome.upms.logic.service.hr.SysDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/department")
@Api(tags = {"用户中心服务", "部门管理接口"})
public class SysDepartmentController extends BaseRestController<SysDepartment, String> {

    private final SysDepartmentService sysDepartmentService;

    @Autowired
    public SysDepartmentController(SysDepartmentService sysDepartmentService) {
        this.sysDepartmentService = sysDepartmentService;
    }

    @Override
    public BaseService<SysDepartment, String> getBaseService() {
        return this.sysDepartmentService;
    }

    @ApiOperation(value = "获取部门列表", notes = "根据单位ID获取部门信息列表", consumes = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "organizationId", required = true, value = "单位ID", dataType = "String", dataTypeClass = String.class, paramType = "query"),
    })
    @GetMapping("/list")
    public Result<List<SysDepartment>> findAllByOrganizationId(@RequestParam("organizationId") String organizationId) {
        List<SysDepartment> sysDepartments = sysDepartmentService.findAllByOrganizationId(organizationId);
        return result(sysDepartments);
    }

    @ApiOperation(value = "获取部门树", notes = "根据单位ID获取部门数据，转换为树形结构", consumes = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "organizationId", required = true, value = "单位ID", dataType = "String", dataTypeClass = String.class, paramType = "query"),
    })
    @GetMapping("/tree")
    public Result<List<TreeNode>> findTree(@RequestParam("organizationId") String organizationId) {
        Result<List<TreeNode>> result = new Result<>();

        List<SysDepartment> sysDepartments = sysDepartmentService.findAllByOrganizationId(organizationId);
        if (CollectionUtils.isNotEmpty(sysDepartments)) {
            List<TreeNode> treeNodes = sysDepartments.stream().map(sysDepartment -> {
                TreeNode treeNode = new TreeNode();
                treeNode.setId(sysDepartment.getDepartmentId());
                treeNode.setName(sysDepartment.getDepartmentName());
                treeNode.setParentId(sysDepartment.getParentId());
                return treeNode;
            }).collect(Collectors.toList());
            return result.data(TreeUtils.build(treeNodes));
        } else {
            return result.message("获取数据失败");
        }
    }
}

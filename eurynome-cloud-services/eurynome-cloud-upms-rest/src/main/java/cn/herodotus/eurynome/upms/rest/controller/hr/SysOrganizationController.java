package cn.herodotus.eurynome.upms.rest.controller.hr;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.common.domain.TreeNode;
import cn.herodotus.eurynome.component.common.utils.TreeUtils;
import cn.herodotus.eurynome.component.data.base.service.BaseService;
import cn.herodotus.eurynome.component.rest.controller.BaseRestController;
import cn.herodotus.eurynome.upms.api.entity.hr.SysOrganization;
import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;
import cn.herodotus.eurynome.upms.logic.service.hr.SysOrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/organization")
@Api(tags = {"用户中心服务", "单位管理接口"})
public class SysOrganizationController extends BaseRestController<SysOrganization, String> {

    private final SysOrganizationService sysOrganizationService;

    @Autowired
    public SysOrganizationController(SysOrganizationService sysOrganizationService) {
        this.sysOrganizationService = sysOrganizationService;
    }

    @Override
    public BaseService<SysOrganization, String> getBaseService() {
        return this.sysOrganizationService;
    }

    @ApiOperation(value = "获取全部单位", notes = "获取全部单位数据", consumes = "application/json")
    @GetMapping("/list")
    @Override
    public Result<List<SysOrganization>> findAll() {
        return super.findAll();
    }

    @ApiOperation(value = "获取单位树", notes = "获取全部单位数据，转换为树形结构", consumes = "application/json")
    @GetMapping("/tree")
    public Result<List<TreeNode>> findTree() {
        Result<List<TreeNode>> result = new Result<>();

        List<SysOrganization> sysOrganizations = sysOrganizationService.findAll();
        if (CollectionUtils.isNotEmpty(sysOrganizations)) {
            List<TreeNode> treeNodes = sysOrganizations.stream().map(sysOrganization -> {
                TreeNode treeNode = new TreeNode();
                treeNode.setId(sysOrganization.getOrganizationId());
                treeNode.setName(sysOrganization.getOrganizationName());
                treeNode.setParentId(sysOrganization.getParentId());
                return treeNode;
            }).collect(Collectors.toList());
            return result.data(TreeUtils.build(treeNodes));
        } else {
            return result.message("获取数据失败");
        }
    }
}

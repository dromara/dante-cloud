/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dante.module.upms.rest.controller.hr;

import cn.herodotus.dante.module.upms.logic.entity.hr.SysDepartment;
import cn.herodotus.dante.module.upms.logic.service.hr.SysDepartmentService;
import cn.herodotus.engine.assistant.core.definition.constants.BaseConstants;
import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.data.core.service.WriteableService;
import cn.herodotus.engine.rest.core.controller.BaseWriteableRestController;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Description: SysDepartmentController </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/9/23 23:13
 */
@RestController
@RequestMapping("/department")
@Tag(name = "部门管理接口")
@Validated
public class SysDepartmentController extends BaseWriteableRestController<SysDepartment, String> {

    private final SysDepartmentService sysDepartmentService;

    @Autowired
    public SysDepartmentController(SysDepartmentService sysDepartmentService) {
        this.sysDepartmentService = sysDepartmentService;
    }

    @Override
    public WriteableService<SysDepartment, String> getWriteableService() {
        return this.sysDepartmentService;
    }

    private List<SysDepartment> getSysDepartments(String organizationId) {
        return sysDepartmentService.findAll(organizationId);
    }

    private String convertParentId(String parentId) {
        if (StringUtils.isBlank(parentId)) {
            return BaseConstants.DEFAULT_TREE_ROOT_ID;
        } else {
            return parentId;
        }
    }

    @Operation(summary = "条件查询部门分页数据", description = "根据输入的字段条件查询部门信息",
            responses = {@ApiResponse(description = "单位列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SysDepartment.class)))})
    @Parameters({
            @Parameter(name = "pageNumber", required = true, description = "当前页码"),
            @Parameter(name = "pageSize", required = true, description = "每页显示数量"),
            @Parameter(name = "organizationId", description = "单位ID"),
    })
    @GetMapping("/condition")
    public Result<Map<String, Object>> findByCondition(@NotNull @RequestParam("pageNumber") Integer pageNumber,
                                                       @NotNull @RequestParam("pageSize") Integer pageSize,
                                                       @RequestParam(value = "organizationId", required = false) String organizationId) {
        Page<SysDepartment> pages = sysDepartmentService.findByCondition(pageNumber, pageSize, organizationId);
        return result(pages);
    }

    @Operation(summary = "获取部门列表", description = "根据单位ID获取部门信息列表",
            responses = {@ApiResponse(description = "单位列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SysDepartment.class)))})
    @Parameters({
            @Parameter(name = "organizationId", required = true, description = "单位ID"),
    })
    @GetMapping("/list")
    public Result<List<SysDepartment>> findAllByOrganizationId(@RequestParam(value = "organizationId", required = false) String organizationId) {
        List<SysDepartment> sysDepartments = getSysDepartments(organizationId);
        return result(sysDepartments);
    }

    @Operation(summary = "获取部门树", description = "根据单位ID获取部门数据，转换为树形结构",
            responses = {@ApiResponse(description = "单位列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SysDepartment.class)))})
    @Parameters({
            @Parameter(name = "organizationId", required = true, description = "单位ID"),
    })
    @GetMapping("/tree")
    public Result<List<Tree<String>>> findTree(@RequestParam(value = "organizationId", required = false) String organizationId) {
        List<SysDepartment> sysDepartments = getSysDepartments(organizationId);
        if (ObjectUtils.isNotEmpty(sysDepartments)) {
            List<TreeNode<String>> treeNodes = sysDepartments.stream().map(sysDepartment -> {
                TreeNode<String> treeNode = new TreeNode<>();
                treeNode.setId(sysDepartment.getDepartmentId());
                treeNode.setName(sysDepartment.getDepartmentName());
                treeNode.setParentId(convertParentId(sysDepartment.getParentId()));
                return treeNode;
            }).collect(Collectors.toList());
            return Result.success("查询数据成功", TreeUtil.build(treeNodes, BaseConstants.DEFAULT_TREE_ROOT_ID));
        } else {
            return Result.failure("查询数据失败");
        }
    }
}

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

import cn.herodotus.dante.module.upms.logic.dto.AllocatableDeploy;
import cn.herodotus.dante.module.upms.logic.dto.AllocatableRemove;
import cn.herodotus.dante.module.upms.logic.entity.hr.SysEmployee;
import cn.herodotus.dante.module.upms.logic.enums.Gender;
import cn.herodotus.dante.module.upms.logic.enums.Identity;
import cn.herodotus.dante.module.upms.logic.service.hr.SysEmployeeService;
import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.data.core.service.WriteableService;
import cn.herodotus.engine.rest.core.controller.BaseWriteableRestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * <p>Description: 人员管理Controller </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/19 15:19
 */
@RestController
@RequestMapping("/employee")
@Tag(name = "人员管理接口")
public class SysEmployeeController extends BaseWriteableRestController<SysEmployee, String> {

    private final SysEmployeeService sysEmployeeService;

    @Autowired
    public SysEmployeeController(SysEmployeeService sysEmployeeService) {
        this.sysEmployeeService = sysEmployeeService;
    }

    private Gender parseGender(Integer gender) {
        if (ObjectUtils.isNotEmpty(gender)) {
            return Gender.get(gender);
        }
        return null;
    }

    private Identity parseIdentity(Integer identity) {
        if (ObjectUtils.isNotEmpty(identity)) {
            return Identity.get(identity);
        }
        return null;
    }

    @Override
    public WriteableService<SysEmployee, String> getWriteableService() {
        return this.sysEmployeeService;
    }

    @Operation(summary = "模糊条件查询人员", description = "根据动态输入的字段模糊查询人员信息",
            responses = {@ApiResponse(description = "人员分页列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)))})
    @Parameters({
            @Parameter(name = "pageNumber", required = true, description = "当前页码"),
            @Parameter(name = "pageSize", required = true, description = "每页显示数量"),
            @Parameter(name = "employeeName", description = "人员姓名"),
            @Parameter(name = "mobilePhoneNumber", description = "手机号码"),
            @Parameter(name = "officePhoneNumber", description = "办公电话"),
            @Parameter(name = "email", description = "电子邮件"),
            @Parameter(name = "pkiEmail", description = "证书标识"),
            @Parameter(name = "gender", description = "性别 （索引数字值）"),
            @Parameter(name = "identity", description = "身份（索引数字值）"),
    })
    @GetMapping("/condition")
    public Result<Map<String, Object>> findByCondition(@NotBlank @RequestParam("pageNumber") Integer pageNumber,
                                                       @NotBlank @RequestParam("pageSize") Integer pageSize,
                                                       @RequestParam(value = "employeeName", required = false) String employeeName,
                                                       @RequestParam(value = "mobilePhoneNumber", required = false) String mobilePhoneNumber,
                                                       @RequestParam(value = "officePhoneNumber", required = false) String officePhoneNumber,
                                                       @RequestParam(value = "email", required = false) String email,
                                                       @RequestParam(value = "pkiEmail", required = false) String pkiEmail,
                                                       @RequestParam(value = "gender", required = false) Integer gender,
                                                       @RequestParam(value = "identity", required = false) Integer identity) {
        Page<SysEmployee> pages = sysEmployeeService.findByCondition(pageNumber, pageSize, employeeName, mobilePhoneNumber, officePhoneNumber, email, pkiEmail, parseGender(gender), parseIdentity(identity));
        return result(pages);
    }

    @Operation(summary = "给人员分配用户", description = "为人员创建用户，生成默认用户信息，让人员可以进入系统",
            responses = {@ApiResponse(description = "已分配用户的人员信息", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SysEmployee.class)))})
    @Parameters({
            @Parameter(name = "employeeId", description = "人员ID")
    })
    @PutMapping
    public Result<SysEmployee> authorize(@RequestParam("employeeId") String employeeId) {
        SysEmployee sysEmployee = sysEmployeeService.authorize(employeeId);
        return result(sysEmployee);
    }

    @Operation(summary = "查询可设置人事归属的人员", description = "根据输入的单位和部门，分页查询当前部门下未设置人事归属的人员信息，排除了已经设置的人员信息",
            responses = {@ApiResponse(description = "可配置人员分页列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)))})
    @Parameters({
            @Parameter(name = "pageNumber", required = true, description = "当前页码"),
            @Parameter(name = "pageSize", required = true, description = "每页显示数量"),
            @Parameter(name = "organizationId", required = true, description = "单位ID"),
            @Parameter(name = "departmentId", required = true, description = "部门ID"),
            @Parameter(name = "employeeName", description = "人员姓名"),
            @Parameter(name = "mobilePhoneNumber", description = "手机号码"),
            @Parameter(name = "email", description = "电子邮件"),
            @Parameter(name = "gender", description = "性别 （索引数字值）"),
            @Parameter(name = "identity", description = "身份（索引数字值）"),
    })
    @GetMapping("/allocatable")
    public Result<Map<String, Object>> findAllocatable(@NotBlank @RequestParam("pageNumber") Integer pageNumber,
                                                       @NotBlank @RequestParam("pageSize") Integer pageSize,
                                                       @NotBlank @RequestParam("organizationId") String organizationId,
                                                       @NotBlank @RequestParam("departmentId") String departmentId,
                                                       @RequestParam(value = "employeeName", required = false) String employeeName,
                                                       @RequestParam(value = "mobilePhoneNumber", required = false) String mobilePhoneNumber,
                                                       @RequestParam(value = "email", required = false) String email,
                                                       @RequestParam(value = "gender", required = false) Integer gender,
                                                       @RequestParam(value = "identity", required = false) Integer identity) {
        Page<SysEmployee> pages = sysEmployeeService.findAllocatable(pageNumber, pageSize, organizationId, departmentId, employeeName, mobilePhoneNumber, email, parseGender(gender), parseIdentity(identity));
        return result(pages);
    }

    @Operation(summary = "查询已设置归属关系的人员", description = "根据输入的部门，分页查询当前部门下已设置人事归属的人员信息",
            responses = {@ApiResponse(description = "可配置人员分页列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)))})
    @Parameters({
            @Parameter(name = "pageNumber", required = true, description = "当前页码"),
            @Parameter(name = "pageSize", required = true, description = "每页显示数量"),
            @Parameter(name = "departmentId", required = true, description = "部门ID"),
    })
    @GetMapping("/assigned")
    public Result<Map<String, Object>> findAssigned(@NotBlank @RequestParam("pageNumber") Integer pageNumber,
                                                    @NotBlank @RequestParam("pageSize") Integer pageSize,
                                                    @NotBlank @RequestParam("departmentId") String departmentId) {
        Page<SysEmployee> pages = sysEmployeeService.findByDepartmentId(pageNumber, pageSize, departmentId);
        return result(pages);
    }

    @Operation(summary = "设置人事归属", description = "根据输入的单位和部门，设置当前部门下未设置人事归属的人员信息，排除了已经设置的人员信息",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "是否设置成功", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "allocatableDeploy", required = true, description = "当前页码", schema = @Schema(implementation = AllocatableDeploy.class)),
    })
    @PostMapping("/allocatable")
    public Result<String> saveAllocatable(@RequestBody AllocatableDeploy allocatableDeploy) {
        boolean isSuccess;
        if (ObjectUtils.isNotEmpty(allocatableDeploy)) {
            isSuccess = sysEmployeeService.deployAllocatable(allocatableDeploy);
        } else {
            isSuccess = false;
        }

        return result(isSuccess);
    }

    @Operation(summary = "删除人员归属关系", description = "根据归属关系、部门和人员的ID，删除归属关系以及人员与部门之间的关联关系",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "是否删除成功", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "allocatableRemove", required = true, description = "增加人员归属参数BO对象", schema = @Schema(implementation = AllocatableRemove.class)),
    })
    @DeleteMapping("/allocatable")
    public Result<String> deleteAllocatable(@RequestBody AllocatableRemove allocatableRemove) {
        boolean isSuccess;
        if (ObjectUtils.isNotEmpty(allocatableRemove)) {
            isSuccess = sysEmployeeService.removeAllocatable(allocatableRemove);
        } else {
            isSuccess = false;
        }

        return result(isSuccess);
    }

    @Operation(summary = "根据姓名查询人员", description = "根据输入的人员姓名，分页查询当前部门下已设置人事归属的人员信息",
            responses = {
                    @ApiResponse(description = "查询到的人员", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SysEmployee.class))),
                    @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
                    @ApiResponse(responseCode = "500", description = "查询失败")
            }
    )
    @Parameters({
            @Parameter(name = "employeeName", in = ParameterIn.PATH, required = true, description = "当前页码"),
    })
    @GetMapping("/{employeeName}")
    public Result<SysEmployee> findByEmployeeName(@PathVariable("employeeName") String employeeName) {
        SysEmployee sysEmployee = sysEmployeeService.findByEmployeeName(employeeName);
        return result(sysEmployee);
    }
}

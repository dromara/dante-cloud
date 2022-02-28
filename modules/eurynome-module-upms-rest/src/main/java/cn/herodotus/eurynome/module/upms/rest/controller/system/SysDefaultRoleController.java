/*
 * Copyright (c) 2019-2021 Gengwei Zheng (herodotus@aliyun.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-upms-rest
 * File Name: SysDefaultRoleController.java
 * Author: gengwei.zheng
 * Date: 2021/10/12 22:44:12
 */

package cn.herodotus.eurynome.module.upms.rest.controller.system;

import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.data.core.service.WriteableService;
import cn.herodotus.engine.rest.core.controller.BaseWriteableRestController;
import cn.herodotus.eurynome.module.upms.logic.entity.system.SysDefaultRole;
import cn.herodotus.eurynome.module.upms.logic.service.system.SysDefaultRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * <p>Description: 系统默认角色管理接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/12 22:44
 */
@RestController
@RequestMapping("/default-role")
@Tag(name = "系统默认角色管理")
public class SysDefaultRoleController extends BaseWriteableRestController<SysDefaultRole, String> {

    private final SysDefaultRoleService sysDefaultRoleService;

    @Autowired
    public SysDefaultRoleController(SysDefaultRoleService sysDefaultRoleService) {
        this.sysDefaultRoleService = sysDefaultRoleService;
    }

    @Override
    public WriteableService<SysDefaultRole, String> getWriteableService() {
        return this.sysDefaultRoleService;
    }

    @Operation(summary = "设置默认角色", description = "给不同的登录场景设置不同的默认橘色",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/x-www-form-urlencoded")),
            responses = {@ApiResponse(description = "已保存数据", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "defaultId", required = true, description = "默认角色类型ID"),
            @Parameter(name = "roleId", required = true, description = "设置的角色ID")
    })
    @PutMapping
    public Result<SysDefaultRole> assign(@RequestParam(name = "defaultId") @NotBlank String defaultId, @RequestParam(name = "roleId") @NotBlank String roleId) {
        SysDefaultRole sysDefaultRole = sysDefaultRoleService.assign(defaultId, roleId);
        return result(sysDefaultRole);
    }
}

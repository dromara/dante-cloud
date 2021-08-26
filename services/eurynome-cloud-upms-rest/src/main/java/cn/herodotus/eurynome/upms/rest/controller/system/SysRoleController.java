/*
 * Copyright 2019-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
 *
 * Project Name: luban-cloud
 * Module Name: luban-cloud-upms-ability
 * File Name: SysRoleController.java
 * Author: gengwei.zheng
 * Date: 2019/11/25 上午11:05
 * LastModified: 2019/11/25 上午11:02
 */

package cn.herodotus.eurynome.upms.rest.controller.system;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.rest.base.controller.BaseWriteableRestController;
import cn.herodotus.eurynome.rest.base.service.WriteableService;
import cn.herodotus.eurynome.upms.api.entity.system.SysRole;
import cn.herodotus.eurynome.upms.logic.service.system.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
@Api(tags = {"用户中心服务", "系统角色接口"})
public class SysRoleController extends BaseWriteableRestController<SysRole, String> {

    private final SysRoleService sysRoleService;

    @Autowired
    public SysRoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @Override
    public WriteableService<SysRole, String> getWriteableService() {
        return this.sysRoleService;
    }

    @ApiOperation(value = "给角色授权", notes = "为角色赋予权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", required = true, value = "角色ID", dataType = "String", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "authorities[]", required = true, value = "权限对象组成的数组", dataType = "String[]", dataTypeClass = String[].class, paramType = "query")
    })
    @PutMapping
    public Result<SysRole> authorize(@RequestParam(name = "roleId") String roleId, @RequestParam(name = "authorities[]") String[] authorities) {
        SysRole sysRole = sysRoleService.authorize(roleId, authorities);
        return result(sysRole);
    }
}

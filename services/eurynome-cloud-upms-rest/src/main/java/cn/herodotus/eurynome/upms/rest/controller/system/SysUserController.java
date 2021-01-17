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
 * File Name: SysUserController.java
 * Author: gengwei.zheng
 * Date: 2019/11/25 上午10:55
 * LastModified: 2019/11/25 上午10:55
 */

package cn.herodotus.eurynome.upms.rest.controller.system;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.crud.service.BaseService;
import cn.herodotus.eurynome.crud.controller.BaseRestController;
import cn.herodotus.eurynome.upms.api.entity.system.SysUser;
import cn.herodotus.eurynome.upms.logic.service.system.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: SysUserController </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/25 10:55
 */
@RestController
@RequestMapping("/user")
@Api(tags = {"用户中心服务", "系统用户接口"})
public class SysUserController extends BaseRestController<SysUser, String> {

    private final SysUserService sysUserService;

    @Autowired
    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    public BaseService<SysUser, String> getBaseService() {
        return this.sysUserService;
    }

    @ApiOperation(value = "给用户分配角色", notes = "给用户分配角色", produces = "application/x-www-form-urlencoded", consumes = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", required = true, value = "userId", dataType = "String", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "roles[]", required = true, value = "角色对象组成的数组", dataType = "String[]", dataTypeClass = String[].class, paramType = "query")
    })
    @PutMapping
    public Result<SysUser> assign(@RequestParam(name = "userId") String userId, @RequestParam(name = "roles[]") String[] roles) {
        SysUser sysUser = sysUserService.assign(userId, roles);
        return result(sysUser);
    }
}

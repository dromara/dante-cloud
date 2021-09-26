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
 * File Name: SysUserController.java
 * Author: gengwei.zheng
 * Date: 2021/09/25 10:52:25
 */

package cn.herodotus.eurynome.upms.rest.controller.system;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.rest.base.controller.BaseWriteableRestController;
import cn.herodotus.eurynome.rest.base.service.WriteableService;
import cn.herodotus.eurynome.upms.api.entity.system.SysUser;
import cn.herodotus.eurynome.upms.logic.service.system.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "系统用户接口")
public class SysUserController extends BaseWriteableRestController<SysUser, String> {

    private final SysUserService sysUserService;

    @Autowired
    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    public WriteableService<SysUser, String> getWriteableService() {
        return this.sysUserService;
    }

    @Operation(summary = "给用户分配角色", description = "给用户分配角色")
    @Parameters({
            @Parameter(name = "userId", required = true, description = "userId"),
            @Parameter(name = "roles[]", required = true, description = "角色对象组成的数组")
    })
    @PutMapping
    public Result<SysUser> assign(@RequestParam(name = "userId") String userId, @RequestParam(name = "roles[]") String[] roles) {
        SysUser sysUser = sysUserService.assign(userId, roles);
        return result(sysUser);
    }
}

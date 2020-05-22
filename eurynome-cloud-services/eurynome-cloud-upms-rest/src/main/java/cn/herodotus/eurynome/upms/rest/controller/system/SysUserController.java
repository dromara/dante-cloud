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
import cn.herodotus.eurynome.data.base.service.BaseService;
import cn.herodotus.eurynome.component.rest.controller.BaseController;
import cn.herodotus.eurynome.upms.api.entity.system.SysUser;
import cn.herodotus.eurynome.upms.api.service.fegin.SysUserFeginService;
import cn.herodotus.eurynome.upms.logic.service.system.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>Description: SysUserController </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/25 10:55
 */
@RestController
@Api(tags = {"用户中心服务", "系统用户接口"})
public class SysUserController extends BaseController<SysUser, String> implements SysUserFeginService {

    private final SysUserService sysUserService;

    @Autowired
    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    public BaseService<SysUser, String> getBaseService() {
        return this.sysUserService;
    }

    @ApiOperation(value = "根据用户名查询用户", notes = "根据用户名查询用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", required = true, value = "用户名称")
    })
    @Override
    public Result<SysUser> findByUsername(String username) {
        SysUser sysUser = sysUserService.findSysUserByUserName(username);
        return result(sysUser);
    }

    @ApiOperation(value = "根据用户ID查询用户", notes = "根据用户ID查询用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", required = true, value = "用户ID")
    })
    @Override
    public Result<SysUser> findById(String userId) {
        SysUser sysUser = sysUserService.findById(userId);
        return result(sysUser);
    }

    @ApiOperation(value = "获取用户分页数据", notes = "通过pageNumber和pageSize获取分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", required = true, value = "当前页数"),
            @ApiImplicitParam(name = "pageSize", required = true, value = "每页显示数据条目")
    })
    @GetMapping("/user")
    @Override
    public Result<Map<String, Object>> findByPage(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize) {
        return super.findByPage(pageNumber, pageSize);
    }

    @ApiOperation(value = "保存或更新用户", notes = "接收JSON数据，转换为SysUser实体，进行保存或更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUser", required = true, value = "可转换为SysUser实体的json数据")
    })
    @PostMapping("/user")
    @Override
    public Result<SysUser> saveOrUpdate(@RequestBody SysUser sysUser) {
        return super.saveOrUpdate(sysUser);
    }

    @ApiOperation(value = "删除用户", notes = "根据userId删除用户，以及相关联的关联关系数据", produces = "application/json", consumes = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", required = true, value = "用户ID")
    })
    @DeleteMapping("/user")
    @Override
    public Result<String> delete(@RequestBody String userId) {
        return super.delete(userId);
    }

    @ApiOperation(value = "给用户分配角色", notes = "给用户分配角色", produces = "application/x-www-form-urlencoded", consumes = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", required = true, value = "userId"),
            @ApiImplicitParam(name = "roles[]", required = true, value = "角色对象组成的数组")
    })
    @PutMapping("/user")
    public Result<SysUser> assign(@RequestParam(name = "userId") String userId, @RequestParam(name = "roles[]") String[] roles) {
        SysUser sysUser = sysUserService.assign(userId, roles);
        return result(sysUser);
    }
}

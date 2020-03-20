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
 * File Name: SysApplicationController.java
 * Author: gengwei.zheng
 * Date: 2019/11/25 上午11:19
 * LastModified: 2019/11/25 上午11:19
 */

package cn.herodotus.eurynome.upms.rest.controller.system;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.rest.controller.BaseController;
import cn.herodotus.eurynome.upms.api.entity.system.SysApplication;
import cn.herodotus.eurynome.upms.logic.service.system.SysApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@Api(value = "平台终端接口", tags = "用户中心服务")
public class SysApplicationController extends BaseController {

    private final SysApplicationService sysApplicationService;

    @Autowired
    public SysApplicationController(SysApplicationService sysApplicationService) {
        this.sysApplicationService = sysApplicationService;
    }

    @ApiOperation(value = "获取终端分页数据", notes = "通过pageNumber和pageSize获取分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", required = true, value = "当前页数"),
            @ApiImplicitParam(name = "pageSize", required = true, value = "每页显示数据条目")
    })
    @GetMapping("/application")
    public Result<Map<String, Object>> findByPage(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize) {
        Page<SysApplication> pages = sysApplicationService.findByPage(pageNumber, pageSize);
        return result(pages);
    }

    @ApiOperation(value = "保存或更新终端", notes = "接收JSON数据，转换为SysApplication实体，进行保存或更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysApplication", required = true, value = "可转换为SysApplication实体的json数据", paramType = "JSON")
    })
    @PostMapping("/application")
    public Result<SysApplication> saveOrUpdate(@RequestBody SysApplication sysApplication) {
        SysApplication newSysApplication = sysApplicationService.saveOrUpdate(sysApplication);
        return result(newSysApplication);
    }

    @ApiOperation(value = "删除终端", notes = "根据applicationId删除终端，以及相关联的关联关系数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "applicationId", required = true, value = "终端ID", paramType = "JSON")
    })
    @DeleteMapping("/application")
    public Result<String> delete(@RequestBody String applicationId) {
        Result<String> result = result(applicationId);

        sysApplicationService.deleteById(applicationId);
        return result;

    }

    @ApiOperation(value = "给终端授权", notes = "为终端赋予权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "applicationId", required = true, value = "终端ID"),
            @ApiImplicitParam(name = "authorities[]", required = true, value = "权限对象组成的数组")
    })
    @PutMapping("/application")
    public Result<SysApplication> authorize(@RequestParam(name = "applicationId") String applicationId, @RequestParam(name = "authorities[]") String[] authorities) {
        SysApplication sysApplication = sysApplicationService.authorize(applicationId, authorities);
        return result(sysApplication);
    }
}

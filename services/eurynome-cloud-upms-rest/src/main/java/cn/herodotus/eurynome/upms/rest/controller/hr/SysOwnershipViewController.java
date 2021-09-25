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
 * File Name: SysOwnershipViewController.java
 * Author: gengwei.zheng
 * Date: 2021/09/25 10:48:25
 */

package cn.herodotus.eurynome.upms.rest.controller.hr;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.rest.base.controller.BaseReadableRestController;
import cn.herodotus.eurynome.rest.base.service.ReadableService;
import cn.herodotus.eurynome.upms.api.entity.hr.SysOwnershipView;
import cn.herodotus.eurynome.upms.logic.service.hr.SysOwnershipViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>Description: 人事归属视图Controller </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/15 16:36
 */
@RestController
@RequestMapping("/ownership-view")
@Tag(name = "人事归属视图接口")
public class SysOwnershipViewController extends BaseReadableRestController<SysOwnershipView, String> {

    @Autowired
    private SysOwnershipViewService sysOwnershipViewService;

    @Override
    public ReadableService<SysOwnershipView, String> getReadableService() {
        return this.sysOwnershipViewService;
    }

    @Operation(summary = "人事归属条件查询", description = "根据单位ID和部门ID，查询人事归属分页信息")
    @Parameters({
            @Parameter(name = "pageNumber", required = true, description = "当前页码"),
            @Parameter(name = "pageSize", required = true, description = "每页显示数量"),
            @Parameter(name = "organizationId", description = "单位ID"),
            @Parameter(name = "departmentId", required = true, description = "部门ID"),
    })
    @GetMapping("/condition")
    public Result<Map<String, Object>> findByCondition(@RequestParam("pageNumber") int pageNumber,
                                                       @RequestParam("pageSize") int pageSize,
                                                       @RequestParam("organizationId") String organizationId,
                                                       @RequestParam("departmentId") String departmentId) {
        Page<SysOwnershipView> pages = sysOwnershipViewService.findByCondition(pageNumber, pageSize, organizationId, departmentId);
        return result(pages);
    }
}

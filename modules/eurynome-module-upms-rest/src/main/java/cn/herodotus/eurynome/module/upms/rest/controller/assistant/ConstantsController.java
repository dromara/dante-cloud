/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.eurynome.module.upms.rest.controller.assistant;

import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.eurynome.module.upms.logic.service.assistant.ConstantsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/constants")
@Tag(name = "系统常量接口")
public class ConstantsController {

    private final ConstantsService constantsService;

    @Autowired
    public ConstantsController(ConstantsService constantsService) {
        this.constantsService = constantsService;
    }

    @Operation(summary = "获取服务使用常量", description = "获取服务涉及的常量以及信息")
    @GetMapping(value = "/enums")
    public Result<Map<String, Object>> findAllEnums() {
        Result<Map<String, Object>> result = new Result<>();
        Map<String, Object> allEnums = constantsService.getAllEnums();
        if (MapUtils.isNotEmpty(allEnums)) {
            return Result.success("获取服务常量成功", allEnums);
        } else {
            return Result.failure("获取服务常量失败");
        }
    }
}

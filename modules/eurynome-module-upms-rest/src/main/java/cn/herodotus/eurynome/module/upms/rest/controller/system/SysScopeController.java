/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2019-2022 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.eurynome.module.upms.rest.controller.system;

import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.data.core.service.WriteableService;
import cn.herodotus.engine.rest.core.controller.BaseWriteableRestController;
import cn.herodotus.eurynome.module.upms.logic.entity.system.SysScope;
import cn.herodotus.eurynome.module.upms.logic.service.system.SysScopeService;
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
 * <p> Description : OauthScopesController </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/25 17:10
 */
@RestController
@RequestMapping("/scope")
@Tag(name = "权限范围管理接口")
public class SysScopeController extends BaseWriteableRestController<SysScope, String> {

    private final SysScopeService sysScopeService;

    @Autowired
    public SysScopeController(SysScopeService sysScopeService) {
        this.sysScopeService = sysScopeService;
    }

    @Override
    public WriteableService<SysScope, String> getWriteableService() {
        return this.sysScopeService;
    }

    @Operation(summary = "给OauthScopes授权", description = "为OauthScopes分配接口权限")
    @Parameters({
            @Parameter(name = "scopeId", required = true, description = "ScopeID"),
            @Parameter(name = "authorities[]", required = true, description = "权限对象组成的数组")
    })
    @PutMapping
    public Result<SysScope> authorize(@RequestParam(name = "scopeId") String scopeId, @RequestParam(name = "authorities[]") String[] authorities) {
        SysScope sysScope = sysScopeService.authorize(scopeId, authorities);
        return result(sysScope);
    }
}

/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Cloud Licensed under the Apache License, Version 2.0 (the "License");
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

package cn.herodotus.dante.module.upms.rest.controller.system;

import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.data.core.service.WriteableService;
import cn.herodotus.engine.rest.core.controller.BaseWriteableRestController;
import cn.herodotus.dante.module.upms.logic.entity.system.SysScope;
import cn.herodotus.dante.module.upms.logic.service.system.SysScopeService;
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

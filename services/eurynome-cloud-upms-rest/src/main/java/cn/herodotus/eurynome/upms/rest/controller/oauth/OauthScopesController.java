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
 * File Name: OauthScopesController.java
 * Author: gengwei.zheng
 * Date: 2021/09/25 10:52:25
 */

package cn.herodotus.eurynome.upms.rest.controller.oauth;

import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.rest.core.controller.BaseWriteableRestController;
import cn.herodotus.engine.data.core.service.WriteableService;
import cn.herodotus.eurynome.upms.logic.entity.oauth.OauthScopes;
import cn.herodotus.eurynome.upms.logic.service.oauth.OauthScopesService;
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
@RequestMapping("/authorize/scopes")
@Tag(name = "Oauth权限范围接口")
public class OauthScopesController extends BaseWriteableRestController<OauthScopes, String> {

    private final OauthScopesService oauthScopesService;

    @Autowired
    public OauthScopesController(OauthScopesService oauthScopesService) {
        this.oauthScopesService = oauthScopesService;
    }

    @Override
    public WriteableService<OauthScopes, String> getWriteableService() {
        return this.oauthScopesService;
    }

    @Operation(summary = "给OauthScopes授权", description = "为OauthScopes分配接口权限")
    @Parameters({
            @Parameter(name = "scopeId", required = true, description = "ScopeID"),
            @Parameter(name = "authorities[]", required = true, description = "权限对象组成的数组")
    })
    @PutMapping
    public Result<OauthScopes> authorize(@RequestParam(name = "scopeId") String scopeId, @RequestParam(name = "authorities[]") String[] authorities) {
        OauthScopes OAuth2Scopes = oauthScopesService.authorize(scopeId, authorities);
        return result(OAuth2Scopes);
    }
}

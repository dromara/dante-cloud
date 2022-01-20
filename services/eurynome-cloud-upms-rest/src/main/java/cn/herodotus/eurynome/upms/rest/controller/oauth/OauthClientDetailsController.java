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
 * File Name: OauthClientDetailsController.java
 * Author: gengwei.zheng
 * Date: 2021/09/25 10:52:25
 */

package cn.herodotus.eurynome.upms.rest.controller.oauth;

import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.rest.core.controller.BaseController;
import cn.herodotus.engine.data.core.service.WriteableService;
import cn.herodotus.eurynome.security.definition.core.HerodotusClientDetails;
import cn.herodotus.eurynome.upms.logic.entity.oauth.OauthClientDetails;
import cn.herodotus.eurynome.upms.logic.helper.UpmsHelper;
import cn.herodotus.eurynome.upms.logic.service.oauth.OauthClientDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> Description : OauthClientDetailsController </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/20 11:48
 */
@Tag(name = "Oauth客户端详情接口")
@RestController
@RequestMapping("/authorize/client-details")
@Transactional(rollbackFor = Exception.class)
public class OauthClientDetailsController extends BaseController<OauthClientDetails, String> {

    private final OauthClientDetailsService oauthClientDetailsService;

    @Autowired
    public OauthClientDetailsController(OauthClientDetailsService oauthClientDetailsService) {
        this.oauthClientDetailsService = oauthClientDetailsService;
    }

    @Override
    public WriteableService<OauthClientDetails, String> getWriteableService() {
        return oauthClientDetailsService;
    }

    @Operation(summary = "获取ClientDetails分页数据", description = "通过pageNumber和pageSize获取分页数据")
    @Parameters({
            @Parameter(name = "pageNumber", required = true, description = "当前页数"),
            @Parameter(name = "pageSize", required = true, description = "每页显示数据条目")
    })
    @GetMapping
    @Override
    public Result<Map<String, Object>> findByPage(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize) {

        Page<OauthClientDetails> pages = oauthClientDetailsService.findByPage(pageNumber, pageSize);
        if (ObjectUtils.isNotEmpty(pages) && CollectionUtils.isNotEmpty(pages.getContent())) {
            List<HerodotusClientDetails> herodotusClientDetails = pages.getContent().stream().map(UpmsHelper::convertOauthClientDetailsToHerodotusClientDetails).collect(Collectors.toList());
            return result(getPageInfoMap(herodotusClientDetails, pages.getTotalPages(), pages.getTotalElements()));
        }

        return new Result<Map<String, Object>>().failed().message("查询数据失败！");
    }

    @Operation(summary = "更新ClientDetails", description = "接收JSON数据，转换为OauthClientDetails实体，进行更新")
    @Parameters({
            @Parameter(name = "oauthClientDetails", required = true, description = "可转换为OauthClientDetails实体的json数据")
    })
    @PostMapping
    public Result<OauthClientDetails> saveOrUpdate(@RequestBody HerodotusClientDetails domain) {
        OauthClientDetails oauthClientDetails = oauthClientDetailsService.saveOrUpdate(UpmsHelper.convertHerodotusClientDetailsToOauthClientDetails(domain));
        return result(oauthClientDetails);
    }

    @Operation(summary = "删除ClientDetails", description = "根据clientId删除ClientDetails，以及相关联的关系数据")
    @Parameters({
            @Parameter(name = "clientId", required = true, description = "clientId")
    })
    @DeleteMapping
    @Override
    public Result<String> delete(@RequestBody String clientId) {
        return super.delete(clientId);
    }
}

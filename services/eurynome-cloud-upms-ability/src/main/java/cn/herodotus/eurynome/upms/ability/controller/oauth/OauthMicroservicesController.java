/*
 * Copyright (c) 2019-2021 the original author or authors.
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
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-upms-ability
 * File Name: OauthMicroservicesController.java
 * Author: gengwei.zheng
 * Date: 2021/1/19 下午3:25
 * LastModified: 2021/1/19 下午3:21
 */

package cn.herodotus.eurynome.upms.ability.controller.oauth;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.crud.service.BaseService;
import cn.herodotus.eurynome.crud.controller.BaseRestController;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthMicroservices;
import cn.herodotus.eurynome.upms.ability.service.oauth.OauthMicroservicesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * <p> Description : OauthMicroservicesController </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/30 15:34
 */
@RestController
@RequestMapping("/oauth/microservices")
@Api(tags = {"用户中心服务", "Oauth微服接口"})
@Transactional(rollbackFor = Exception.class)
public class OauthMicroservicesController extends BaseRestController<OauthMicroservices, String> {

    @Autowired
    private OauthMicroservicesService oauthMicroservicesService;

    @Override
    public BaseService<OauthMicroservices, String> getBaseService() {
        return this.oauthMicroservicesService;
    }

    /**
     * paramType：表示参数放在哪个地方
     * header-->请求参数的获取：@RequestHeader(代码中接收注解)
     * query-->请求参数的获取：@RequestParam(代码中接收注解)
     * path（用于restful接口）-->请求参数的获取：@PathVariable(代码中接收注解)
     * body-->请求参数的获取：@RequestBody(代码中接收注解)
     * form（不常用）
     *
     * @param serviceId
     * @return
     */
    @ApiOperation(value = "发布或更新微服务配置", notes = "为接入平台的微服务创建OauthClientDetails相关的配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceId", required = true, value = "serviceId", dataType = "String", dataTypeClass = String.class, paramType = "query")
    })
    @PostMapping("/config")
    public Result<String> publishConfig(@RequestParam(name = "serviceId") String serviceId) {
        boolean isPublishOk = oauthMicroservicesService.publishOrUpdateConfig(serviceId);
        Result<String> result = new Result<>();
        if (isPublishOk) {
            return result.ok().message("发布配置成功！");
        } else {
            return result.failed().message("发布配置失败！");
        }
    }

    @ApiOperation(value = "删除微服务配置", notes = "根据serviceId删除与该微服务对应的Oauth2的Nacos配置。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceId", required = true, value = "serviceId", dataType = "String", dataTypeClass = String.class, paramType = "body")
    })
    @DeleteMapping("/config")
    public Result<String> deleteConfig(@RequestBody String serviceId) {
        boolean isDeleteOk = oauthMicroservicesService.deleteConfig(serviceId);
        Result<String> result = new Result<>();
        if (isDeleteOk) {
            return result.ok().message("删除配置成功！");
        } else {
            return result.failed().message("删除配置失败！");
        }
    }

    @ApiOperation(value = "给服务分配Scope", notes = "给服务分配Scope")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceId", required = true, value = "appKey", dataType = "String", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "scopes[]", required = true, value = "Scope对象组成的数组", dataType = "String[]", dataTypeClass = String[].class, paramType = "query")
    })
    @PutMapping
    public Result<OauthMicroservices> assign(@RequestParam(name = "serviceId") String serviceId, @RequestParam(name = "scopes[]") String[] scopes) {
        OauthMicroservices oauthMicroservices = oauthMicroservicesService.assign(serviceId, scopes);
        return result(oauthMicroservices);
    }
}

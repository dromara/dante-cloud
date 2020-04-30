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
 * File Name: SysAuthorityController.java
 * Author: gengwei.zheng
 * Date: 2019/11/25 上午11:19
 * LastModified: 2019/11/25 上午11:13
 */

package cn.herodotus.eurynome.upms.rest.controller.system;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.common.domain.TreeNode;
import cn.herodotus.eurynome.component.common.enums.AuthorityType;
import cn.herodotus.eurynome.component.common.utils.TreeUtils;
import cn.herodotus.eurynome.component.data.base.service.BaseService;
import cn.herodotus.eurynome.component.rest.controller.BaseRestController;
import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;
import cn.herodotus.eurynome.upms.logic.service.system.SysAuthorityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Description: SysAuthorityController </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/10 13:21
 */
@RestController
@RequestMapping("/authority")
@Api(value = "Upms权限接口", tags = "用户中心服务")
public class SysAuthorityController extends BaseRestController<SysAuthority, String> {

    private final SysAuthorityService sysAuthorityService;

    @Autowired
    public SysAuthorityController(SysAuthorityService sysAuthorityService) {
        this.sysAuthorityService = sysAuthorityService;
    }

    @Override
    public BaseService<SysAuthority, String> getBaseService() {
        return this.sysAuthorityService;
    }

    @ApiOperation(value = "获取权限树", notes = "获取权限树形数据")
    @GetMapping("/tree")
    public Result<List<TreeNode>> findTree() {
        Result<List<TreeNode>> result = new Result<>();

        List<SysAuthority> sysAuthorities = sysAuthorityService.findAll();
        if (CollectionUtils.isNotEmpty(sysAuthorities)) {
            List<TreeNode> treeNodes = sysAuthorities.stream().map(sysAuthority -> {
                TreeNode treeNode = new TreeNode();
                treeNode.setId(sysAuthority.getAuthorityId());
                treeNode.setName(sysAuthority.getAuthorityName());
                treeNode.setParentId(sysAuthority.getParentId());
                return treeNode;
            }).collect(Collectors.toList());
            return result.data(TreeUtils.build(treeNodes));
        } else {
            return result.message("获取数据失败");
        }
    }

    @ApiOperation(value = "获取全部API接口", notes = "获取全部API接口")
    @GetMapping("/apis")
    public Result<List<SysAuthority>> findAllApis() {
        List<SysAuthority> sysAuthorities = sysAuthorityService.findAllByAuthorityType(AuthorityType.API);
        return result(sysAuthorities);
    }
}

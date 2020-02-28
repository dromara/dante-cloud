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
 * File Name: SysAuthorityServiceTests.java
 * Author: gengwei.zheng
 * Date: 2019/11/19 上午11:27
 * LastModified: 2019/11/19 上午11:14
 */

package cn.herodotus.eurynome.upms.rest.service.system;

import cn.herodotus.eurynome.component.common.domain.TreeNode;
import cn.herodotus.eurynome.upms.rest.UpmsApplication;
import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;
import cn.herodotus.eurynome.upms.logic.service.system.SysAuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { UpmsApplication.class })
@Slf4j
public class SysAuthorityServiceTests {

    @Autowired
    private SysAuthorityService sysAuthorityService;

    @Test
    public void findAuthorityTree() {
        List<SysAuthority> sysAuthorities = sysAuthorityService.findAll();
        List<TreeNode> treeNodes = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(sysAuthorities)) {
            treeNodes = sysAuthorities.stream().map(sysAuthority -> {
                TreeNode treeNode = new TreeNode();
                treeNode.setId(sysAuthority.getAuthorityId());
                treeNode.setName(sysAuthority.getAuthorityName());
                treeNode.setParentId(sysAuthority.getParentId());
                return treeNode;
            }).collect(Collectors.toList());
        }

        Assert.notEmpty(treeNodes, "findAuthorityTree Fetch Values!");
    }
}

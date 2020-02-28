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
 * File Name: SysApplicationServiceTests.java
 * Author: gengwei.zheng
 * Date: 2019/11/19 上午11:27
 * LastModified: 2019/11/19 上午11:14
 */

package cn.herodotus.eurynome.upms.rest.service.system;

import cn.herodotus.eurynome.upms.rest.UpmsApplication;
import cn.herodotus.eurynome.upms.api.constants.enums.ApplicationType;
import cn.herodotus.eurynome.upms.api.entity.system.SysApplication;
import cn.herodotus.eurynome.upms.api.entity.system.SysClientDetail;
import cn.herodotus.eurynome.upms.logic.service.system.SysApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { UpmsApplication.class })
@Slf4j
public class SysApplicationServiceTests {

    @Autowired
    private SysApplicationService sysApplicationService;
    private SysApplication sysApplication;

    @Before
    public void before() {
        sysApplication = new SysApplication();
        sysApplication.setApplicationName("测试应用");
        sysApplication.setApplicationNameEn("Test Application");
        sysApplication.setApplicationIcon("");
        sysApplication.setApplicationType(ApplicationType.WEB);
        sysApplication.setApplicationLanguage("Java");
        sysApplication.setWebsite("http://localhost:9999");

        SysClientDetail sysClientDetail = new SysClientDetail();
        sysClientDetail.setClientSecret("123456");
        sysClientDetail.setScope("ALL");
        sysClientDetail.setAuthorizedGrantTypes("password");
        sysClientDetail.setWebServerRedirectUri("http://localhost:7070");
        sysClientDetail.setAccessTokenValidity(4096);
        sysClientDetail.setRefreshTokenValidity(2587);

        sysApplication.setSysClientDetail(sysClientDetail);
    }

    @Test
    public void save() {

        SysApplication savedSysApplication = sysApplicationService.saveOrUpdate(sysApplication);

        Assert.notNull(savedSysApplication, "Save Action Fetch Result!");

        SysApplication tempSysApplication = sysApplicationService.findByClientId(savedSysApplication.getSysClientDetail().getClientId());

        Assert.notNull(savedSysApplication, "findByClientId Service Failed!");


        System.out.println(savedSysApplication.toString());
        System.out.println(savedSysApplication.getSysClientDetail().toString());

        System.out.println(tempSysApplication.toString());

        sysApplicationService.deleteById(savedSysApplication.getApplicationId());
    }

}

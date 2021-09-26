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
 * File Name: SysOwnershipController.java
 * Author: gengwei.zheng
 * Date: 2021/09/25 10:52:25
 */

package cn.herodotus.eurynome.upms.rest.controller.hr;

import cn.herodotus.eurynome.rest.base.controller.BaseWriteableRestController;
import cn.herodotus.eurynome.rest.base.service.WriteableService;
import cn.herodotus.eurynome.upms.api.entity.hr.SysOwnership;
import cn.herodotus.eurynome.upms.logic.service.hr.SysOwnershipService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: 人事归属Controller </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/15 16:36
 */
@RestController
@RequestMapping("/ownership")
@Tag(name = "人事归属管理接口")
public class SysOwnershipController extends BaseWriteableRestController<SysOwnership, String> {

    private final SysOwnershipService sysOwnershipService;

    @Autowired
    public SysOwnershipController(SysOwnershipService sysOwnershipService) {
        this.sysOwnershipService = sysOwnershipService;
    }

    @Override
    public WriteableService<SysOwnership, String> getWriteableService() {
        return this.sysOwnershipService;
    }
}

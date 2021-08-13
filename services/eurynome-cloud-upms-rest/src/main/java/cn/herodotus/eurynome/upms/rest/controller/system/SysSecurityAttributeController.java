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
 * File Name: SysSecurityAttributeController.java
 * Author: gengwei.zheng
 * Date: 2021/08/14 06:54:14
 */

package cn.herodotus.eurynome.upms.rest.controller.system;

import cn.herodotus.eurynome.crud.controller.BaseWriteableRestController;
import cn.herodotus.eurynome.crud.service.WriteableService;
import cn.herodotus.eurynome.upms.api.entity.system.SysSecurityAttribute;
import cn.herodotus.eurynome.upms.logic.service.system.SysSecurityAttributeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: SysSecurityAttributeController </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/14 6:54
 */
@RestController
@RequestMapping("/security-attribute")
@Api(tags = {"用户中心服务", "系统元数据接口", "权限管理"})
public class SysSecurityAttributeController extends BaseWriteableRestController<SysSecurityAttribute, String> {

    private final SysSecurityAttributeService sysSecurityAttributeService;

    @Autowired
    public SysSecurityAttributeController(SysSecurityAttributeService sysSecurityAttributeService) {
        this.sysSecurityAttributeService = sysSecurityAttributeService;
    }

    @Override
    public WriteableService<SysSecurityAttribute, String> getWriteableService() {
        return this.sysSecurityAttributeService;
    }
}

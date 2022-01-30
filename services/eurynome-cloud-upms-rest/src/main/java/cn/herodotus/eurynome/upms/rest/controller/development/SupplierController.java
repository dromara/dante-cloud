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
 * File Name: SupplierController.java
 * Author: gengwei.zheng
 * Date: 2021/09/25 10:52:25
 */

package cn.herodotus.eurynome.upms.rest.controller.development;

import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.data.core.service.WriteableService;
import cn.herodotus.engine.rest.core.controller.BaseWriteableRestController;
import cn.herodotus.eurynome.upms.logic.entity.development.Supplier;
import cn.herodotus.eurynome.upms.logic.service.development.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p> Description : 微服务开发厂商及团队管理 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/30 15:35
 */
@RestController
@RequestMapping("/microservice/supplier")
@Tag(name = "开发团队/厂商管理接口")
public class SupplierController extends BaseWriteableRestController<Supplier, String> {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @Override
    public WriteableService<Supplier, String> getWriteableService() {
        return this.supplierService;
    }

    @Operation(summary = "获取全部厂商数据", description = "查询全部的厂商数据，用作列表选择根据目前观测该类数据不会太多，如果过多就需要修改查询方法和方式")
    @GetMapping("/list")
    @Override
    public Result<List<Supplier>> findAll() {
        return super.findAll();
    }
}

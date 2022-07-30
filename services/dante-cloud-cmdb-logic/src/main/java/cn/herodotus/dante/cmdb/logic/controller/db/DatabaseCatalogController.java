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

package cn.herodotus.dante.cmdb.logic.controller.db;

import cn.herodotus.dante.cmdb.logic.service.db.DatabaseCatalogService;
import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.data.core.service.WriteableService;
import cn.herodotus.engine.rest.core.controller.BaseWriteableRestController;
import cn.herodotus.dante.cmdb.logic.entity.db.DatabaseCatalog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: DatabaseCatalogController </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/21 22:13
 */
@RestController
@RequestMapping("/db/catalog")
@Tags({
        @Tag(name = "CMDB 管理接口"),
        @Tag(name = "CMDB 数据库管理接口"),
})
public class DatabaseCatalogController extends BaseWriteableRestController<DatabaseCatalog, String> {

    private final DatabaseCatalogService databaseCatalogService;

    @Autowired
    public DatabaseCatalogController(DatabaseCatalogService databaseCatalogService) {
        this.databaseCatalogService = databaseCatalogService;
    }

    @Override
    public WriteableService<DatabaseCatalog, String> getWriteableService() {
        return databaseCatalogService;
    }

    /**
     * 给数据库分配账号
     *
     * @param catalogId 数据库ID
     * @param accounts  数据库对象组成的数组
     * @return {@link Result}
     */
    @Operation(summary = "给数据库分配账号", description = "给数据库分配账号",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/x-www-form-urlencoded")),
            responses = {@ApiResponse(description = "已保存数据", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "catalogId", required = true, description = "数据库ID"),
            @Parameter(name = "accounts[]", required = true, description = "数据库对象组成的数组")
    })
    @PutMapping
    public Result<DatabaseCatalog> assign(@RequestParam(name = "catalogId") String catalogId, @RequestParam(name = "accounts[]") String[] accounts) {
        DatabaseCatalog databaseCatalog = databaseCatalogService.assign(catalogId, accounts);
        return result(databaseCatalog);
    }
}

/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Cloud 采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package org.dromara.dante.upms.controller;

import cn.herodotus.engine.core.definition.domain.Result;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.collections4.MapUtils;
import org.dromara.dante.upms.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>Description: Spring Authorization Server 授权码模式回调测试接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/15 14:13
 */
@RestController
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @Autowired
    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @GetMapping("/open/authorized")
    public void authorized(@NotNull @RequestParam("code") String code, @RequestParam("state") String state) {
        System.out.println(code);
        System.out.println(state);

        Map<String, Object> map = authorizationService.authorized(code, state);
        if (MapUtils.isNotEmpty(map)) {
            map.forEach((key, value) -> {
                System.out.println("Key : " + key + " Value : " + value);
            });
        }
    }

    @GetMapping("/resource-demo")
    public Result<String> resource() {
        return Result.success("Get resource-demo");
    }
}

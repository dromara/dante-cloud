/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.cloud.upms.controller;

import cn.herodotus.cloud.upms.service.AuthorizationService;
import cn.herodotus.stirrup.core.definition.domain.Result;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.collections4.MapUtils;
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

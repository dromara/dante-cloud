/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 *    Author: ZHENGGENGWEI<码匠君>
 *    Contact: <herodotus@aliyun.com>
 *    Blog and source code availability: <https://gitee.com/herodotus/herodotus-cloud>
 */

package cn.herodotus.cloud.reactive.ability;

import cn.herodotus.stirrup.rest.reactive.nacos.annotation.EnableHerodotusRestReactiveNacos;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/1/26 16:29
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableHerodotusRestReactiveNacos
public class ManageAbilityApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageAbilityApplication.class, args);
    }
}

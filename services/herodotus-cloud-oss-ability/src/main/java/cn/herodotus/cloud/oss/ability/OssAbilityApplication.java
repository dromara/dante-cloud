/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 *    Author: ZHENGGENGWEI<码匠君>
 *    Contact: <herodotus@aliyun.com>
 *    Blog and source code availability: <https://gitee.com/herodotus/herodotus-cloud>
 */

package cn.herodotus.cloud.oss.ability;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <p>Description: 对象存储服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/5 16:16
 */
@EnableDiscoveryClient
@SpringBootApplication
public class OssAbilityApplication {

    public static void main(String[] args) {
        SpringApplication.run(OssAbilityApplication.class, args);
    }
}

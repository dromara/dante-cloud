/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.eurynome.module.oauth2.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/12 21:01
 */
@ConfigurationProperties(prefix = "herodotus.oauth")
public class OauthProperties {

    private String issuerUrl;

    public String getIssuerUrl() {
        return issuerUrl;
    }

    public void setIssuerUrl(String issuerUrl) {
        this.issuerUrl = issuerUrl;
    }
}

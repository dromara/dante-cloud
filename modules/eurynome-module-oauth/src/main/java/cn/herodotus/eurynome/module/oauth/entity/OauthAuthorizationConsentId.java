/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.eurynome.module.oauth.entity;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * <p>Description: TODO </p>
 * <p>
 * 复合主键类必须满足：
 * 1. 实现Serializable接口;
 * 2. 有默认的public无参数的构造方法;
 * 3. 重写equals和hashCode方法。equals方法用于判断两个对象是否相同，
 *
 * @author : gengwei.zheng
 * @date : 2022/1/22 17:58
 */
public class OauthAuthorizationConsentId implements Serializable {

    /**
     * 这里一定要注意：
     * <p>
     * 属性名：这里的属性名，一定要与中间表中，ManyToOne的属性名称一致
     * 类型：虽然名称一致，但是类型可以不一致，此处的String类型，说明属性对应数据库字段的类型为String
     */
    private String registeredClientId;

    private String principalName;

    public OauthAuthorizationConsentId() {
    }

    public OauthAuthorizationConsentId(String registeredClientId, String principalName) {
        this.registeredClientId = registeredClientId;
        this.principalName = principalName;
    }

    public String getRegisteredClientId() {
        return registeredClientId;
    }

    public void setRegisteredClientId(String registeredClientId) {
        this.registeredClientId = registeredClientId;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OauthAuthorizationConsentId that = (OauthAuthorizationConsentId) o;
        return Objects.equal(registeredClientId, that.registeredClientId) && Objects.equal(principalName, that.principalName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(registeredClientId, principalName);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("registeredClientId", registeredClientId)
                .add("principalName", principalName)
                .toString();
    }
}

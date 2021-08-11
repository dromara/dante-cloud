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
 * Module Name: eurynome-cloud-security
 * File Name: SecurityAttribute.java
 * Author: gengwei.zheng
 * Date: 2021/08/11 20:52:11
 */

package cn.herodotus.eurynome.security.definition.domain;

import cn.herodotus.eurynome.security.definition.core.HerodotusAuthority;
import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.Set;

/**
 * <p>Description: Security Metadata 传输数据实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/11 20:52
 */
public class SecurityAttribute implements Serializable {

    private String metadataId;

    private String defaultExpression;

    private String staticExpression;

    private String dynamicExpression;

    private String scopeExpression;

    private String ipExpression;

    private String url;

    private String requestMethod;

    private String serviceId;

    private Set<HerodotusAuthority> roles;

    private Set<HerodotusAuthority> scopes;

    public String getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(String metadataId) {
        this.metadataId = metadataId;
    }

    public String getDefaultExpression() {
        return defaultExpression;
    }

    public void setDefaultExpression(String defaultExpression) {
        this.defaultExpression = defaultExpression;
    }

    public String getStaticExpression() {
        return staticExpression;
    }

    public void setStaticExpression(String staticExpression) {
        this.staticExpression = staticExpression;
    }

    public String getDynamicExpression() {
        return dynamicExpression;
    }

    public void setDynamicExpression(String dynamicExpression) {
        this.dynamicExpression = dynamicExpression;
    }

    public String getScopeExpression() {
        return scopeExpression;
    }

    public void setScopeExpression(String scopeExpression) {
        this.scopeExpression = scopeExpression;
    }

    public String getIpExpression() {
        return ipExpression;
    }

    public void setIpExpression(String ipExpression) {
        this.ipExpression = ipExpression;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Set<HerodotusAuthority> getRoles() {
        return roles;
    }

    public void setRoles(Set<HerodotusAuthority> roles) {
        this.roles = roles;
    }

    public Set<HerodotusAuthority> getScopes() {
        return scopes;
    }

    public void setScopes(Set<HerodotusAuthority> scopes) {
        this.scopes = scopes;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("metadataId", metadataId)
                .add("defaultExpression", defaultExpression)
                .add("staticExpression", staticExpression)
                .add("dynamicExpression", dynamicExpression)
                .add("scopeExpression", scopeExpression)
                .add("ipExpression", ipExpression)
                .add("url", url)
                .add("requestMethod", requestMethod)
                .add("serviceId", serviceId)
                .toString();
    }
}

/*
 * Copyright 2019-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
 *
 * Project Name: luban-cloud
 * Module Name: luban-cloud-component-security
 * File Name: ArtisanAuthority.java
 * Author: gengwei.zheng
 * Date: 2019/11/18 上午11:40
 * LastModified: 2019/11/18 上午11:36
 */

package cn.herodotus.eurynome.component.security.core;

import cn.herodotus.eurynome.component.common.enums.AuthorityType;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author gengwei.zheng
 */
public final class HerodotusAuthority implements GrantedAuthority {

    private String authorityId;

    private String authorityName;

    private String authorityCode;

    private String parentId;

    private String url;

    private String menuClass;

    private Integer ranking;

    private AuthorityType authorityType;

    public HerodotusAuthority() {
    }

    public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public String getAuthorityCode() {
        return authorityCode;
    }

    public void setAuthorityCode(String authorityCode) {
        this.authorityCode = authorityCode;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getAuthority() {
        return getAuthorityCode();
    }

    public AuthorityType getAuthorityType() {
        return authorityType;
    }

    public void setAuthorityType(AuthorityType authorityType) {
        this.authorityType = authorityType;
    }

    public String getMenuClass() {
        return menuClass;
    }

    public void setMenuClass(String menuClass) {
        this.menuClass = menuClass;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    @Override
    public String toString() {
        return "ArtisanAuthority{" +
                "authorityId='" + authorityId + '\'' +
                ", authorityName='" + authorityName + '\'' +
                ", authorityCode='" + authorityCode + '\'' +
                ", parentId='" + parentId + '\'' +
                ", url='" + url + '\'' +
                ", menuClass='" + menuClass + '\'' +
                ", ranking=" + ranking +
                ", authorityType=" + authorityType +
                '}';
    }
}

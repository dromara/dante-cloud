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
 * Module Name: eurynome-cloud-upms-logic
 * File Name: OauthApplications.java
 * Author: gengwei.zheng
 * Date: 2021/12/17 21:32:17
 */

package cn.herodotus.eurynome.upms.logic.entity.oauth;

import cn.herodotus.engine.data.core.entity.BaseAppEntity;
import cn.herodotus.eurynome.upms.logic.constants.UpmsConstants;
import cn.herodotus.eurynome.upms.logic.constants.enums.TechnologyType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * <p> Description : Oauth Application </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/19 16:13
 */
@Entity
@Table(name = "oauth_applications", indexes = {@Index(name = "oauth_applications_id_idx", columnList = "app_key")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_OAUTH_APPLICATIONS)
public class OauthApplications extends BaseAppEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "app_key", length = 64)
    private String appKey;

    @Column(name = "app_name_en", length = 128)
    private String appNameEn;

    @Column(name = "app_icon", length = 1024)
    private String appIcon;

    @Column(name = "app_tech")
    @Enumerated(EnumType.ORDINAL)
    private TechnologyType technologyType = TechnologyType.JAVA;

    @Column(name = "website", length = 1024)
    private String website;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_OAUTH_SCOPES)
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "oauth_applications_scopes",
            joinColumns = {@JoinColumn(name = "app_key")},
            inverseJoinColumns = {@JoinColumn(name = "scope_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"app_key", "scope_id"})},
            indexes = {@Index(name = "oauth_applications_scopes_aid_idx", columnList = "app_key"), @Index(name = "oauth_applications_scopes_sid_idx", columnList = "scope_id")})
    private Set<OauthScopes> scopes = new HashSet<>();

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppNameEn() {
        return appNameEn;
    }

    public void setAppNameEn(String appNameEn) {
        this.appNameEn = appNameEn;
    }

    public TechnologyType getTechnologyType() {
        return technologyType;
    }

    public void setTechnologyType(TechnologyType technologyType) {
        this.technologyType = technologyType;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Set<OauthScopes> getScopes() {
        return scopes;
    }

    public void setScopes(Set<OauthScopes> scopes) {
        this.scopes = scopes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OauthApplications that = (OauthApplications) o;

        return new EqualsBuilder()
                .append(getAppKey(), that.getAppKey())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getAppKey())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "OauthApplications{" +
                "appKey='" + appKey + '\'' +
                ", appNameEn='" + appNameEn + '\'' +
                ", appIcon='" + appIcon + '\'' +
                ", technologyType=" + technologyType +
                ", website='" + website + '\'' +
                '}';
    }
}

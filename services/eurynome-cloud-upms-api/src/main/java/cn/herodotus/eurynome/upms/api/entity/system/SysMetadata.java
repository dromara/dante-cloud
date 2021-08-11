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
 * Module Name: eurynome-cloud-upms-api
 * File Name: SysMetadata.java
 * Author: gengwei.zheng
 * Date: 2021/08/05 17:06:05
 */

package cn.herodotus.eurynome.upms.api.entity.system;

import cn.herodotus.eurynome.data.base.entity.BaseSysEntity;
import cn.herodotus.eurynome.upms.api.constants.UpmsConstants;
import cn.herodotus.eurynome.upms.api.entity.oauth.OAuth2DynamicExpressions;
import cn.herodotus.eurynome.upms.api.entity.oauth.OAuth2IpAddresses;
import cn.herodotus.eurynome.upms.api.entity.oauth.OAuth2Scopes;
import cn.herodotus.eurynome.upms.api.entity.oauth.OAuth2StaticExpressions;
import cn.herodotus.eurynome.upms.api.listener.entity.SysMetadataEntityListener;
import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: 系统权限元数据 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/5 17:06
 */
@ApiModel(description = "系统权限元数据")
@Entity
@Table(name = "sys_metadata", indexes = {@Index(name = "sys_metadata_id_idx", columnList = "metadata_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_SYS_METADATA)
@EntityListeners(value = {SysMetadataEntityListener.class})
public class SysMetadata extends BaseSysEntity {

    @ApiModelProperty(value = "元数据ID")
    @Id
    @GeneratedValue(generator = "metadata-uuid")
    @GenericGenerator(name = "metadata-uuid", strategy = "cn.herodotus.cloud.upms.api.generator.SysMetadataUUIDGenerator")
    @Column(name = "metadata_id", length = 64)
    private String metadataId;

    @ApiModelProperty(value = "URL")
    @Column(name = "url", length = 2048)
    private String url;

    @ApiModelProperty(value = "请求类型")
    @Column(name = "request_method", length = 20)
    private String requestMethod;

    @ApiModelProperty(value = "服务ID", notes = "如果是单体式架构，该值为空")
    @Column(name = "service_id", length = 128)
    private String serviceId;

    @ApiModelProperty(value = "默认表达式", notes = "该值即authority_code值，会被封装成hasAuthority('XX'), 是自动生成的默认权限")
    @Column(name = "default_expression", length = 128)
    private String defaultExpression;

    @ApiModelProperty(value = "Scope权限表达式", notes = "该表达式要符合ScopeVoter规则")
    @Column(name = "scope_expression", length = 128)
    private String scopeExpression;

    @ApiModelProperty(value = "静态字符串表达式", notes = "Security和OAuth2涉及的表达式字符串")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_OAUTH_STATIC_EXPRESSIONS)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "static_expression_id", referencedColumnName = "expression_id")
    private OAuth2StaticExpressions staticExpression;

    @ApiModelProperty(value = "动态字符串表达式", notes = "通过UI界面等配置生成的合规的表达式")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_OAUTH_DYNAMIC_EXPRESSIONS)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dynamic_expression_id", referencedColumnName = "expression_id")
    private OAuth2DynamicExpressions dynamicExpression;

    @ApiModelProperty(value = "IP地址表达式", notes = "该表达式要符合WebExpressionVoter规则,根据配置的IP地址动态生成")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_OAUTH_IP_ADDRESSES)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ip_expression_id", referencedColumnName = "ip_id")
    private OAuth2IpAddresses ipExpression;

    @ApiModelProperty(value = "动态权限表达式", notes = "该表达式要符合WebExpressionVoter规则,根据配置动态生成")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_SYS_ROLE)
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "sys_role_authority",
            joinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "metadata_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")})
    private Set<SysRole> roles = new HashSet<>();

    @ApiModelProperty(value = "动态权限表达式", notes = "该表达式要符合WebExpressionVoter规则,根据配置动态生成")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_OAUTH_SCOPES)
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "oauth_scopes_authority",
            joinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "metadata_id")},
            inverseJoinColumns = {@JoinColumn(name = "scope_id", referencedColumnName = "scope_id")})
    private Set<OAuth2Scopes> scopes = new HashSet<>();

    @Override
    public String getLinkedProperty() {
        return null;
    }

    @Override
    public String getId() {
        return this.getMetadataId();
    }

    public String getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(String metadataId) {
        this.metadataId = metadataId;
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

    public String getDefaultExpression() {
        return defaultExpression;
    }

    public void setDefaultExpression(String defaultExpression) {
        this.defaultExpression = defaultExpression;
    }

    public String getScopeExpression() {
        return scopeExpression;
    }

    public void setScopeExpression(String scopeExpression) {
        this.scopeExpression = scopeExpression;
    }

    public OAuth2StaticExpressions getStaticExpression() {
        return staticExpression;
    }

    public void setStaticExpression(OAuth2StaticExpressions staticExpression) {
        this.staticExpression = staticExpression;
    }

    public OAuth2DynamicExpressions getDynamicExpression() {
        return dynamicExpression;
    }

    public void setDynamicExpression(OAuth2DynamicExpressions dynamicExpression) {
        this.dynamicExpression = dynamicExpression;
    }

    public OAuth2IpAddresses getIpExpression() {
        return ipExpression;
    }

    public void setIpExpression(OAuth2IpAddresses ipExpression) {
        this.ipExpression = ipExpression;
    }

    public Set<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<SysRole> roles) {
        this.roles = roles;
    }

    public Set<OAuth2Scopes> getScopes() {
        return scopes;
    }

    public void setScopes(Set<OAuth2Scopes> scopes) {
        this.scopes = scopes;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("metadataId", metadataId)
                .add("url", url)
                .add("requestMethod", requestMethod)
                .add("serviceId", serviceId)
                .add("defaultExpression", defaultExpression)
                .add("scopeExpression", scopeExpression)
                .add("staticExpression", staticExpression)
                .add("dynamicExpression", dynamicExpression)
                .add("ipExpression", ipExpression)
                .toString();
    }
}

/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
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
 * Dante Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dante.module.upms.logic.entity.system;

import cn.herodotus.dante.module.upms.logic.assistant.generator.SysSecurityAttributeUuid;
import cn.herodotus.dante.module.upms.logic.assistant.listener.SysSecurityAttributeEntityListener;
import cn.herodotus.dante.module.upms.logic.constants.UpmsConstants;
import cn.herodotus.engine.data.core.entity.BaseSysEntity;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: 系统权限元数据 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/4 3:18
 */
@Schema(title = "系统权限元数据")
@Entity
@Table(name = "sys_security_attribute", indexes = {@Index(name = "sys_security_attribute_id_idx", columnList = "attribute_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_SYS_SECURITY_ATTRIBUTE)
@EntityListeners(value = {SysSecurityAttributeEntityListener.class})
public class SysSecurityAttribute extends BaseSysEntity {

    @Schema(title = "元数据ID")
    @Id
    @SysSecurityAttributeUuid
    @Column(name = "attribute_id", length = 64)
    private String attributeId;

    @Schema(title = "URL")
    @Column(name = "url", length = 2048)
    private String url;

    @Schema(title = "请求类型")
    @Column(name = "request_method", length = 20)
    private String requestMethod;

    @Schema(title = "服务ID", description = "如果是单体式架构，该值为空")
    @Column(name = "service_id", length = 128)
    private String serviceId;

    @Schema(title = "默认权限代码", description = "该值即authority_code值，如果没有设置其它权限，该值会被封装成hasAuthority('XX')作为默认权限, 是自动生成的默认权限")
    @Column(name = "attribute_code", length = 128)
    private String attributeCode;

    @Schema(title = "表达式", description = "Security和OAuth2涉及的表达式字符串，通过该值设置不同的权限")
    @Column(name = "expression", length = 128)
    private String expression;

    @Schema(title = "IP地址", description = "该表达式要符合WebExpressionVoter规则,根据配置的IP地址动态生成")
    @Column(name = "ip_address", length = 64)
    private String ipAddress;

    @Schema(title = "指定表达式", description = "预留字段，该值可手动设置具体的权限表达式，而不是通过Role、Scope等关联数据自动生成")
    @Column(name = "manual_setting", length = 256)
    private String manualSetting;

    @Schema(title = "动态权限表达式", description = "该表达式要符合WebExpressionVoter规则,根据配置动态生成")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_SYS_ROLE)
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "sys_role_authority",
            joinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "attribute_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")})
    private Set<SysRole> roles = new HashSet<>();

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
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

    public String getAttributeCode() {
        return attributeCode;
    }

    public void setAttributeCode(String attributeCode) {
        this.attributeCode = attributeCode;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getManualSetting() {
        return manualSetting;
    }

    public void setManualSetting(String manualExpression) {
        this.manualSetting = manualExpression;
    }

    public Set<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<SysRole> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("attributeId", attributeId)
                .add("url", url)
                .add("requestMethod", requestMethod)
                .add("serviceId", serviceId)
                .add("attributeCode", attributeCode)
                .add("expression", expression)
                .add("ipAddress", ipAddress)
                .add("manualSetting", manualSetting)
                .toString();
    }
}

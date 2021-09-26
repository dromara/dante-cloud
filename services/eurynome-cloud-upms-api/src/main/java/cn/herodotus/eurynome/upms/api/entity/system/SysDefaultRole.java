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
 * File Name: SysDefaultRole.java
 * Author: gengwei.zheng
 * Date: 2021/09/25 10:41:25
 */

package cn.herodotus.eurynome.upms.api.entity.system;

import cn.herodotus.eurynome.common.constant.enums.AccountType;
import cn.herodotus.eurynome.data.base.entity.BaseSysEntity;
import cn.herodotus.eurynome.upms.api.constants.UpmsConstants;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <p>Description: 系统默认角色 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/5 17:32
 */
@Entity
@Table(name = "sys_default_role", uniqueConstraints = {@UniqueConstraint(columnNames = {"default_id", "scene"})},
        indexes = {
                @Index(name = "sys_default_role_did_idx", columnList = "default_id"),
                @Index(name = "sys_default_role_rid_idx", columnList = "role_id")}
)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_SYS_DEFAULT_ROLE)
public class SysDefaultRole extends BaseSysEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "default_id", length = 64)
    private String defaultId;

    @Schema(title = "场景")
    @Column(name = "scene", unique = true)
    @Enumerated(EnumType.STRING)
    private AccountType scene = AccountType.INSTITUTION;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_SYS_ROLE)
    @Schema(title = "角色ID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private SysRole role;

    public String getDefaultId() {
        return defaultId;
    }

    public void setDefaultId(String defaultId) {
        this.defaultId = defaultId;
    }

    public AccountType getScene() {
        return scene;
    }

    public void setScene(AccountType scene) {
        this.scene = scene;
    }

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }

    @Override
    public String getLinkedProperty() {
        return this.getScene().getKey();
    }

    @Override
    public String getId() {
        return this.getDefaultId();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("defaultId", defaultId)
                .add("supplierType", scene)
                .add("role", role)
                .toString();
    }
}

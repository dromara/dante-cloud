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

import cn.herodotus.dante.module.upms.logic.constants.UpmsConstants;
import cn.herodotus.engine.assistant.core.enums.AccountType;
import cn.herodotus.engine.data.core.entity.BaseSysEntity;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <p>Description: 系统默认角色 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/16 17:01
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

    @Schema(title =  "场景")
    @Column(name = "scene", unique = true)
    @Enumerated(EnumType.STRING)
    private AccountType scene = AccountType.INSTITUTION;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_SYS_ROLE)
    @Schema(title =  "角色ID")
    @ManyToOne
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
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("defaultId", defaultId)
                .add("supplierType", scene)
                .add("role", role)
                .toString();
    }
}

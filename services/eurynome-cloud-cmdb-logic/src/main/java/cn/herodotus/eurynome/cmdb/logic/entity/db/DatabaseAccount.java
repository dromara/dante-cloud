/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
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
 * Eurynome Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Eurynome Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/eurynome-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/eurynome-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.eurynome.cmdb.logic.entity.db;

import cn.herodotus.eurynome.cmdb.logic.base.BaseCmdbEntity;
import cn.herodotus.eurynome.cmdb.logic.constants.CmdbConstants;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * <p>Description: 数据库账号 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/21 18:21
 */
@Schema(name = "数据库", title = "这里指代的是数据库软件中已经创建数据库")
@Entity
@Table(name = "db_account", indexes = {
        @Index(name = "db_account_id_idx", columnList = "account_id"),
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = {"account_id"})
})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CmdbConstants.REGION_DATABASE_ACCOUNT)
public class DatabaseAccount extends BaseCmdbEntity {

    @Schema(title = "帐户ID")
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "account_id", length = 64)
    private String accountId;

    @Schema(title = "用户名")
    @Column(name = "username", length = 50)
    private String username;

    @Schema(title = "密码")
    @Column(name = "credentials", length = 512)
    private String credentials;

    @Schema(title = "密码过期日期")
    @Column(name = "credentials_expire_at")
    private LocalDateTime credentialsExpireAt;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public LocalDateTime getCredentialsExpireAt() {
        return credentialsExpireAt;
    }

    public void setCredentialsExpireAt(LocalDateTime credentialsExpireAt) {
        this.credentialsExpireAt = credentialsExpireAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DatabaseAccount that = (DatabaseAccount) o;
        return Objects.equal(accountId, that.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(accountId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("accountId", accountId)
                .add("username", username)
                .add("credentials", credentials)
                .toString();
    }
}

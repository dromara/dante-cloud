/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Cloud Licensed under the Apache License, Version 2.0 (the "License");
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

package cn.herodotus.dante.cmdb.logic.entity.db;

import cn.herodotus.dante.cmdb.logic.constants.CmdbConstants;
import cn.herodotus.dante.cmdb.logic.base.BaseCmdbEntity;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: 数据库 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/21 18:30
 */
@Schema(name = "数据库", title = "这里指代的是数据库软件中已经创建数据库")
@Entity
@Table(name = "db_catalog", indexes = {
        @Index(name = "db_catalog_id_idx", columnList = "catalog_id"),
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = {"catalog_id"})
})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CmdbConstants.REGION_DATABASE_CATALOG)
public class DatabaseCatalog extends BaseCmdbEntity {

    @Schema(title = "数据库ID")
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "catalog_id", length = 64)
    private String catalogId;

    @Schema(title = "数据库名称")
    @Column(name = "catalog_name", length = 64)
    private String catalogName;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CmdbConstants.REGION_DATABASE_CATALOG)
    @Schema(title = "使用该数据库的账号")
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "db_catalog_account",
            joinColumns = {@JoinColumn(name = "catalog_id")},
            inverseJoinColumns = {@JoinColumn(name = "account_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"catalog_id", "account_id"})},
            indexes = {@Index(name = "db_catalog_account_cid_idx", columnList = "catalog_id"), @Index(name = "db_catalog_account_aid_idx", columnList = "account_id")})
    private Set<DatabaseAccount> accounts = new HashSet<>();

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public Set<DatabaseAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<DatabaseAccount> accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DatabaseCatalog that = (DatabaseCatalog) o;
        return Objects.equal(catalogId, that.catalogId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(catalogId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("catalogId", catalogId)
                .add("catalogName", catalogName)
                .toString();
    }
}

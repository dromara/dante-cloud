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

import cn.herodotus.dante.cmdb.logic.base.BaseCmdbEntity;
import cn.herodotus.dante.cmdb.logic.constants.CmdbConstants;
import cn.herodotus.dante.cmdb.logic.entity.asset.AssetServer;
import cn.herodotus.engine.assistant.core.enums.Database;
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
 * @date : 2022/7/21 16:56
 */
@Schema(name = "数据库实例", title = "这里指代的是安装的数据库应用软件")
@Entity
@Table(name = "db_instance", indexes = {
        @Index(name = "db_instance_id_idx", columnList = "instance_id"),
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = {"instance_id"})
})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CmdbConstants.REGION_DATABASE_INSTANCE)
public class DatabaseInstance extends BaseCmdbEntity {

    @Schema(title = "服务器ID")
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "instance_id", length = 64)
    private String instanceId;

    @Schema(title = "数据库类型")
    @Column(name = "db_type")
    @Enumerated(EnumType.ORDINAL)
    private Database dbType = Database.ORACLE;

    @Schema(title = "数据库版本")
    @Column(name = "db_version", length = 50)
    private String dbVersion;

    @Schema(title = "数据库端口")
    @Column(name = "db_ports", length = 20)
    private String dbPorts;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CmdbConstants.REGION_DATABASE_CATALOG)
    @Schema(title = "已创建数据库")
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "db_instance_catalog",
            joinColumns = {@JoinColumn(name = "instance_id")},
            inverseJoinColumns = {@JoinColumn(name = "catalog_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"instance_id", "catalog_id"})},
            indexes = {@Index(name = "db_instance_catalog_iid_idx", columnList = "instance_id"), @Index(name = "db_instance_catalog_cid_idx", columnList = "catalog_id")})
    private Set<DatabaseCatalog> catalogs = new HashSet<>();

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CmdbConstants.REGION_DATABASE_CATALOG)
    @Schema(title = "部署所在服务器")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "server_id")
    private AssetServer assetServer;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public Database getDbType() {
        return dbType;
    }

    public void setDbType(Database dbType) {
        this.dbType = dbType;
    }

    public String getDbVersion() {
        return dbVersion;
    }

    public void setDbVersion(String dbVersion) {
        this.dbVersion = dbVersion;
    }

    public String getDbPorts() {
        return dbPorts;
    }

    public void setDbPorts(String dbPorts) {
        this.dbPorts = dbPorts;
    }

    public Set<DatabaseCatalog> getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(Set<DatabaseCatalog> catalogs) {
        this.catalogs = catalogs;
    }

    public AssetServer getAssetServer() {
        return assetServer;
    }

    public void setAssetServer(AssetServer assetServer) {
        this.assetServer = assetServer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DatabaseInstance that = (DatabaseInstance) o;
        return Objects.equal(instanceId, that.instanceId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(instanceId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("instanceId", instanceId)
                .add("dbType", dbType)
                .add("dbVersion", dbVersion)
                .add("dbPorts", dbPorts)
                .toString();
    }
}

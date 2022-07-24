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

package cn.herodotus.eurynome.cmdb.logic.entity.asset;

import cn.herodotus.engine.assistant.core.enums.ServerDevice;
import cn.herodotus.eurynome.cmdb.logic.base.BaseCmdbEntity;
import cn.herodotus.eurynome.cmdb.logic.constants.CmdbConstants;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <p>Description: 服务器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/21 14:29
 */
@Schema(title = "服务器")
@Entity
@Table(name = "asset_server", uniqueConstraints = {@UniqueConstraint(columnNames = {"server_id"})},
        indexes = {@Index(name = "asset_server_id_idx", columnList = "server_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CmdbConstants.REGION_ASSET_SERVER)
public class AssetServer extends BaseCmdbEntity {

    @Schema(title = "服务器ID")
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "server_id", length = 64)
    private String serverId;

    @Schema(name = "服务器类型")
    @Column(name = "device_type")
    @Enumerated(EnumType.ORDINAL)
    private ServerDevice deviceType = ServerDevice.PHYSICAL_MACHINE;

    @Schema(name = "资产编号")
    @Column(name = "asset_id", length = 100)
    private String assetId;

    @Schema(name = "机柜号")
    @Column(name = "cabinet_number", length = 50)
    private String cabinetNumber;

    @Schema(name = "机柜中序号")
    @Column(name = "cabinet_order", length = 20)
    private String cabinetOrder;

    @Schema(name = "主机名")
    @Column(name = "host_name", length = 50)
    private String hostName;

    @Schema(name = "序列号")
    @Column(name = "serial_number", length = 100)
    private String serialNumber;

    @Schema(name = "制造商")
    @Column(name = "manufacturer", length = 100)
    private String manufacturer;

    @Schema(name = "型号")
    @Column(name = "model", length = 100)
    private String model;

    @Schema(name = "使用IP")
    @Column(name = "actual_ip", length = 20)
    private String actualIp;

    @Schema(name = "管理IP")
    @Column(name = "manage_ip", length = 20)
    private String manageIp;

    @Schema(name = "操作系统")
    @Column(name = "os_platform", length = 100)
    private String osPlatform;

    @Schema(name = "系统版本")
    @Column(name = "os_version", length = 100)
    private String osVersion;

    @Schema(name = "CPU个数")
    @Column(name = "cup_count")
    private Integer cupCount = 1;

    @Schema(name = "CPU物理个数")
    @Column(name = "cup_physical_count")
    private Integer cupPhysicalCount = 1;

    @Schema(name = "CPU型号")
    @Column(name = "cup_model", length = 100)
    private String cupModel;

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public ServerDevice getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(ServerDevice deviceType) {
        this.deviceType = deviceType;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getCabinetNumber() {
        return cabinetNumber;
    }

    public void setCabinetNumber(String cabinetNumber) {
        this.cabinetNumber = cabinetNumber;
    }

    public String getCabinetOrder() {
        return cabinetOrder;
    }

    public void setCabinetOrder(String cabinetOrder) {
        this.cabinetOrder = cabinetOrder;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getActualIp() {
        return actualIp;
    }

    public void setActualIp(String actualIp) {
        this.actualIp = actualIp;
    }

    public String getManageIp() {
        return manageIp;
    }

    public void setManageIp(String manageIp) {
        this.manageIp = manageIp;
    }

    public String getOsPlatform() {
        return osPlatform;
    }

    public void setOsPlatform(String osPlatform) {
        this.osPlatform = osPlatform;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public Integer getCupCount() {
        return cupCount;
    }

    public void setCupCount(Integer cupCount) {
        this.cupCount = cupCount;
    }

    public Integer getCupPhysicalCount() {
        return cupPhysicalCount;
    }

    public void setCupPhysicalCount(Integer cupPhysicalCount) {
        this.cupPhysicalCount = cupPhysicalCount;
    }

    public String getCupModel() {
        return cupModel;
    }

    public void setCupModel(String cupModel) {
        this.cupModel = cupModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AssetServer that = (AssetServer) o;
        return Objects.equal(serverId, that.serverId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(serverId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("serverId", serverId)
                .add("deviceType", deviceType)
                .add("assetId", assetId)
                .add("cabinetNumber", cabinetNumber)
                .add("cabinetOrder", cabinetOrder)
                .add("hostName", hostName)
                .add("serialNumber", serialNumber)
                .add("manufacturer", manufacturer)
                .add("model", model)
                .add("actualIp", actualIp)
                .add("manageIp", manageIp)
                .add("osPlatform", osPlatform)
                .add("osVersion", osVersion)
                .add("cupCount", cupCount)
                .add("cupPhysicalCount", cupPhysicalCount)
                .add("cupModel", cupModel)
                .toString();
    }
}

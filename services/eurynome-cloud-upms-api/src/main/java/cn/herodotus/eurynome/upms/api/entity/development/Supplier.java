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
 * File Name: Supplier.java
 * Author: gengwei.zheng
 * Date: 2021/07/14 21:19:14
 */

package cn.herodotus.eurynome.upms.api.entity.development;

import cn.herodotus.eurynome.data.base.entity.BaseSysEntity;
import cn.herodotus.eurynome.upms.api.constants.UpmsConstants;
import cn.herodotus.eurynome.upms.api.constants.enums.SupplierType;
import com.google.common.base.MoreObjects;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <p> Description : 服务开发厂商或团队 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/29 11:13
 */
@Entity
@Table(name = "dev_supplier", uniqueConstraints = {@UniqueConstraint(columnNames = {"supplier_code"})},
        indexes = {@Index(name = "dev_supplier_id_idx", columnList = "supplier_id"), @Index(name = "dev_supplier_code_idx", columnList = "supplier_code")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_DEVELOPMENT_SUPPLIER)
public class Supplier extends BaseSysEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "supplier_id", length = 64)
    private String supplierId;

    @Column(name = "supplier_name", length = 512)
    private String supplierName;

    @Column(name = "supplier_code", length = 128, unique = true)
    private String supplierCode;

    @Column(name = "supplier_type")
    @Enumerated(EnumType.STRING)
    private SupplierType supplierType = SupplierType.CORE;

    @Column(name = "parent_id", length = 64)
    private String parentId;

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public SupplierType getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(SupplierType supplierType) {
        this.supplierType = supplierType;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String getId() {
        return this.getSupplierId();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("supplierId", supplierId)
                .add("supplierName", supplierName)
                .add("supplierCode", supplierCode)
                .add("supplierType", supplierType)
                .add("parentId", parentId)
                .toString();
    }
}

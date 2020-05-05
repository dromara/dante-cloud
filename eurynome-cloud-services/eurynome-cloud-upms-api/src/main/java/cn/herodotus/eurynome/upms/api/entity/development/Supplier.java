package cn.herodotus.eurynome.upms.api.entity.development;

import cn.herodotus.eurynome.component.data.base.entity.BaseSysEntity;
import cn.herodotus.eurynome.upms.api.constants.enums.SupplierType;
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
    public String getLinkedProperty() {
        return this.getSupplierCode();
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierId='" + supplierId + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", supplierCode='" + supplierCode + '\'' +
                ", supplierType=" + supplierType +
                ", parentId='" + parentId + '\'' +
                '}';
    }
}

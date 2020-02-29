package cn.herodotus.eurynome.upms.api.entity.hr;

import cn.herodotus.eurynome.component.data.base.entity.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <p>Description: 单位信息 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/19 16:41
 */
@Entity
@Table(name = "sys_organization", indexes = {@Index(name = "sys_organization_id_idx", columnList = "organization_id")})
public class SysOrganization extends BaseEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "organization_id", length = 64)
    private String organizationId;

    @Column(name = "organization_name", length = 1000)
    private String organizationName;

    @Column(name = "a4_biz_org_id", length = 64)
    private String a4BizOrgId;

    @Column(name = "biz_org_code", length = 60)
    private String bizOrgCode;

    @Column(name = "biz_org_desc", length = 256)
    private String bizOrgDesc;

    @Column(name = "biz_org_id", length = 64)
    private String bizOrgId;

    @Column(name = "biz_org_name", length = 200)
    private String bizOrgName;

    @Column(name = "biz_org_type", length = 30)
    private String bizOrgType;

    @Column(name = "partition_code", length = 256)
    private String partitionCode;

    @Column(name = "short_name", length = 200)
    private String shortName;

    @Column(name = "parent_id", length = 64)
    private String parentId;

    @Override
    public String getId() {
        return getOrganizationId();
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getA4BizOrgId() {
        return a4BizOrgId;
    }

    public void setA4BizOrgId(String a4BizOrgId) {
        this.a4BizOrgId = a4BizOrgId;
    }

    public String getBizOrgCode() {
        return bizOrgCode;
    }

    public void setBizOrgCode(String bizOrgCode) {
        this.bizOrgCode = bizOrgCode;
    }

    public String getBizOrgDesc() {
        return bizOrgDesc;
    }

    public void setBizOrgDesc(String bizOrgDesc) {
        this.bizOrgDesc = bizOrgDesc;
    }

    public String getBizOrgId() {
        return bizOrgId;
    }

    public void setBizOrgId(String bizOrgId) {
        this.bizOrgId = bizOrgId;
    }

    public String getBizOrgName() {
        return bizOrgName;
    }

    public void setBizOrgName(String bizOrgName) {
        this.bizOrgName = bizOrgName;
    }

    public String getBizOrgType() {
        return bizOrgType;
    }

    public void setBizOrgType(String bizOrgType) {
        this.bizOrgType = bizOrgType;
    }

    public String getPartitionCode() {
        return partitionCode;
    }

    public void setPartitionCode(String partitionCode) {
        this.partitionCode = partitionCode;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "SysOrganization{" +
                "organizationId='" + organizationId + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", a4BizOrgId='" + a4BizOrgId + '\'' +
                ", bizOrgCode='" + bizOrgCode + '\'' +
                ", bizOrgDesc='" + bizOrgDesc + '\'' +
                ", bizOrgId='" + bizOrgId + '\'' +
                ", bizOrgName='" + bizOrgName + '\'' +
                ", bizOrgType='" + bizOrgType + '\'' +
                ", partitionCode='" + partitionCode + '\'' +
                ", shortName='" + shortName + '\'' +
                ", parentId='" + parentId + '\'' +
                '}';
    }
}

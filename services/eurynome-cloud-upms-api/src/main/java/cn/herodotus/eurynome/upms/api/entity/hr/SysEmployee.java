/*
 * Copyright (c) 2018. All Rights Reserved
 * ProjectName: hades-multi-module
 * FileName: Employee
 * Author: gengwei.zheng
 * Date: 18-12-29 上午8:41
 * LastModified: 18-12-25 下午5:10
 */

package cn.herodotus.eurynome.upms.api.entity.hr;

import cn.herodotus.eurynome.data.base.entity.BaseSysEntity;
import cn.herodotus.eurynome.upms.api.constants.enums.Gender;
import cn.herodotus.eurynome.upms.api.constants.enums.Identity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: 人员信息 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/19 16:41
 */
@ApiModel(description = "人员")
@Entity
@Table(name = "sys_employee", indexes = {@Index(name = "sys_employee_id_idx", columnList = "employee_id")})
public class SysEmployee extends BaseSysEntity {

    @ApiModelProperty(value = "人员ID")
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "employee_id", length = 64)
    private String employeeId;

    @ApiModelProperty(value = "姓名")
    @Column(name = "employee_name", length = 50)
    private String employeeName;

    @ApiModelProperty(value = "工号")
    @Column(name = "employee_no", length = 50)
    private String employeeNo;

    @ApiModelProperty(value = "手机号码")
    @Column(name = "mobile_phone_number", length = 50)
    private String mobilePhoneNumber;

    @ApiModelProperty(value = "办公电话")
    @Column(name = "office_phone_number", length = 50)
    private String officePhoneNumber;

    @ApiModelProperty(value = "电子邮箱")
    @Column(name = "email", length = 100)
    private String email;

    @ApiModelProperty(value = "PKI电子邮箱")
    @Column(name = "pki_email", length = 100)
    private String pkiEmail;

    @ApiModelProperty(value = "所属单位")
    @ManyToOne
    @JoinColumn(name = "organization_id")
    private SysOrganization organization;

    @ApiModelProperty(value = "所属部门")
    @ManyToOne
    @JoinColumn(name = "department_id")
    private SysDepartment department;

    @ApiModelProperty(value = "4A标准人员ID")
    @Column(name = "a4_biz_emp_id", length = 256)
    private String a4BizEmpId;

    @ApiModelProperty(value = "头像")
    @Column(name = "avatar", length = 1000)
    private String avatar;

    @ApiModelProperty(value = "生日")
    @Column(name = "birth_day")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @ApiModelProperty(value = "性别")
    @Column(name = "gender")
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    @ApiModelProperty(value = "身份")
    @Column(name = "identity")
    @Enumerated(EnumType.ORDINAL)
    private Identity identity = Identity.STAFF;

    /**
     * 一个员工有多个岗位
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_employee_position",
            joinColumns = {@JoinColumn(name = "employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "position_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"employee_id", "position_id"})},
            indexes = {@Index(name = "sys_employee_position_eid_idx", columnList = "employee_id"), @Index(name = "sys_employee_position_pid_idx", columnList = "position_id")})
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<SysPosition> positions = new HashSet<>();

    @Override
    public String getId() {
        return getEmployeeId();
    }

    @Override
    public String getLinkedProperty() {
        return null;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getOfficePhoneNumber() {
        return officePhoneNumber;
    }

    public void setOfficePhoneNumber(String officePhoneNumber) {
        this.officePhoneNumber = officePhoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPkiEmail() {
        return pkiEmail;
    }

    public void setPkiEmail(String pkiEmail) {
        this.pkiEmail = pkiEmail;
    }

    public SysOrganization getOrganization() {
        return organization;
    }

    public void setOrganization(SysOrganization sysOrganization) {
        this.organization = sysOrganization;
    }

    public SysDepartment getDepartment() {
        return department;
    }

    public void setDepartment(SysDepartment sysDepartment) {
        this.department = sysDepartment;
    }

    public String getA4BizEmpId() {
        return a4BizEmpId;
    }

    public void setA4BizEmpId(String a4BizEmpId) {
        this.a4BizEmpId = a4BizEmpId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public Set<SysPosition> getPositions() {
        return positions;
    }

    public void setPositions(Set<SysPosition> positions) {
        this.positions = positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysEmployee that = (SysEmployee) o;

        return new EqualsBuilder()
                .append(getEmployeeId(), that.getEmployeeId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getEmployeeId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "SysEmployee{" +
                "employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", employeeNo='" + employeeNo + '\'' +
                ", mobilePhoneNumber='" + mobilePhoneNumber + '\'' +
                ", officePhoneNumber='" + officePhoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", pkiEmail='" + pkiEmail + '\'' +
                ", a4BizEmpId='" + a4BizEmpId + '\'' +
                ", avatar='" + avatar + '\'' +
                ", birthday=" + birthday +
                ", gender=" + gender +
                ", identity=" + identity +
                '}';
    }
}

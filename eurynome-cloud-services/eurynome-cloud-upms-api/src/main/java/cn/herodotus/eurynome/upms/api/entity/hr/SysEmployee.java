/*
 * Copyright (c) 2018. All Rights Reserved
 * ProjectName: hades-multi-module
 * FileName: Employee
 * Author: gengwei.zheng
 * Date: 18-12-29 上午8:41
 * LastModified: 18-12-25 下午5:10
 */

package cn.herodotus.eurynome.upms.api.entity.hr;

import cn.herodotus.eurynome.upms.api.constants.enums.Gender;
import cn.herodotus.eurynome.upms.api.constants.enums.Identity;
import cn.herodotus.eurynome.component.data.base.entity.BaseEntity;
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
@Entity
@Table(name = "sys_employee", indexes = {@Index(name = "sys_employee_id_idx", columnList = "employee_id")})
public class SysEmployee extends BaseEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "employee_id", length = 64)
    private String employeeId;

    @Column(name = "employee_name", length = 512)
    private String employeeName;

    @Column(name = "address", length = 512)
    private String address;

    @Column(name = "avatar", length = 1000)
    private String avatar;

    @Column(name = "birth_day")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Lob
    @Column(name = "comments")
    private String comments;

    @Column(name = "degree_code", length = 30)
    private String degreeCode;

    @Column(name = "duty", length = 30)
    private String duty;

    @Column(name = "email", length = 40)
    private String email;

    @Column(name = "pki_email", length = 100)
    private String pkiEmail;

    @Column(name = "employee_no", length = 50)
    private String employeeNo;

    @Column(name = "a4_biz_emp_id", length = 256)
    private String a4BizEmpId;

    @Column(name = "gender")
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    @Column(name = "identity")
    @Enumerated(EnumType.ORDINAL)
    private Identity identity = Identity.STAFF;

    @Column(name = "job", length = 80)
    private String job;

    @Column(name = "mobile_phone_number", length = 512)
    private String mobilePhoneNumber;

    @Column(name = "office_phone_number", length = 512)
    private String officePhoneNumber;

    @Column(name = "refresh_work_date")
    @Temporal(TemporalType.DATE)
    private Date refreshWorkDate;

    @Column(name = "organization_id", length = 64)
    private String organizationId;

    @Column(name = "department_id", length = 64)
    private String departmentId;

    @Column(name = "sap_hr_user_id", length = 64)
    private String sapHrUserId;

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
    public String getDomainCacheKey() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDegreeCode() {
        return degreeCode;
    }

    public void setDegreeCode(String degreeCode) {
        this.degreeCode = degreeCode;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
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

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getA4BizEmpId() {
        return a4BizEmpId;
    }

    public void setA4BizEmpId(String a4BizEmpId) {
        this.a4BizEmpId = a4BizEmpId;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
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

    public Date getRefreshWorkDate() {
        return refreshWorkDate;
    }

    public void setRefreshWorkDate(Date refreshWorkDate) {
        this.refreshWorkDate = refreshWorkDate;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getSapHrUserId() {
        return sapHrUserId;
    }

    public void setSapHrUserId(String sapHrUserId) {
        this.sapHrUserId = sapHrUserId;
    }

    public Set<SysPosition> getPositions() {
        return positions;
    }

    public void setPositions(Set<SysPosition> positions) {
        this.positions = positions;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
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
                ", address='" + address + '\'' +
                ", avatar='" + avatar + '\'' +
                ", birthday=" + birthday +
                ", comments='" + comments + '\'' +
                ", degreeCode='" + degreeCode + '\'' +
                ", duty='" + duty + '\'' +
                ", email='" + email + '\'' +
                ", pkiEmail='" + pkiEmail + '\'' +
                ", employeeNo='" + employeeNo + '\'' +
                ", a4BizEmpId='" + a4BizEmpId + '\'' +
                ", gender=" + gender +
                ", identity=" + identity +
                ", job='" + job + '\'' +
                ", mobilePhoneNumber='" + mobilePhoneNumber + '\'' +
                ", officePhoneNumber='" + officePhoneNumber + '\'' +
                ", refreshWorkDate=" + refreshWorkDate +
                ", organizationId='" + organizationId + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", sapHrUserId='" + sapHrUserId + '\'' +
                ", positions=" + positions +
                '}';
    }
}

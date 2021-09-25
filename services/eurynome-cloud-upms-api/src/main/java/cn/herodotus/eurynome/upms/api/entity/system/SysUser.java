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
 * File Name: SysUser.java
 * Author: gengwei.zheng
 * Date: 2021/09/25 10:39:25
 */

/**
 * 2019.09.14总结：
 * 1、今天增加了Redis与Caffeine多级缓存并验证成功
 * 2、存在的主要问题就是Jackson的序列化问题。通过该问题的处理总结一下内容：
 * （1）Spring Data Jpa @ManyToMany等注解在没有特殊使用需求的时候尽量不要用双向，这会引起对象引用的环，导致json序列化出问题。
 * （2）使用Fastjson在序列化过程中，各种问题会少于Jackson。
 * （3）Spring Date Jpa 分页对象Page，在多级缓存中，由于找不到响应的Page对象，所以在缓存序列化过程中会出错，而且返回前台的内容过多。所以目前是采用返回Map的方式解决。
 */
package cn.herodotus.eurynome.upms.api.entity.system;

import cn.herodotus.eurynome.data.base.entity.BaseSysEntity;
import cn.herodotus.eurynome.upms.api.constants.UpmsConstants;
import cn.herodotus.eurynome.upms.api.entity.hr.SysEmployee;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: 系统用户 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/10 11:06
 */
@Schema(title = "系统用户")
@Entity
@Table(name = "sys_user", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_name"})},
        indexes = {@Index(name = "sys_user_id_idx", columnList = "user_id"), @Index(name = "sys_user_unm_idx", columnList = "user_name")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_SYS_USER)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
public class SysUser extends BaseSysEntity {

    @Schema(title = "用户ID")
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "user_id", length = 64)
    private String userId;

    @Schema(title = "用户名")
    @Column(name = "user_name", length = 128, unique = true)
    private String userName;

    @Schema(title = "密码", description = "BCryptPasswordEncoder")
    @Column(name = "password", length = 256)
    private String password;

    @Schema(title = "昵称")
    @Column(name = "nick_name", length = 64)
    private String nickName;

    @Schema(title = "手机号码")
    @Column(name = "phone_number", length = 256)
    private String phoneNumber;

    @Schema(title = "手机号码")
    @Column(name = "avatar", length = 1024)
    private String avatar;

    @Schema(title = "EMAIL")
    @Column(name = "email", length = 100)
    private String email;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_SYS_ROLE)
    @Schema(title = "用户角色")
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "sys_user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})},
            indexes = {@Index(name = "sys_user_role_uid_idx", columnList = "user_id"), @Index(name = "sys_user_role_rid_idx", columnList = "role_id")})
    private Set<SysRole> roles = new HashSet<>();

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_SYS_EMPLOYEE)
    @Schema(title = "人员")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_user_employee",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "employee_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "employee_id"})},
            indexes = {@Index(name = "sys_user_employee_sid_idx", columnList = "user_id"), @Index(name = "sys_user_employee_eid_idx", columnList = "employee_id")})
    private SysEmployee employee;

    @Override
    public String getId() {
        return getUserId();
    }

    @Override
    public String getLinkedProperty() {
        return getUserName();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Set<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<SysRole> roles) {
        this.roles = roles;
    }

    public SysEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(SysEmployee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("userName", userName)
                .add("password", password)
                .add("nickName", nickName)
                .add("phoneNumber", phoneNumber)
                .add("avatar", avatar)
                .add("email", email)
                .toString();
    }
}

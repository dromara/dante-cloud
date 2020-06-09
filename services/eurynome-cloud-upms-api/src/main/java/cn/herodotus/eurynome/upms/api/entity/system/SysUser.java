/**
 * 2019.09.14总结：
 * 1、今天增加了Redis与Caffeine多级缓存并验证成功
 * 2、存在的主要问题就是Jackson的序列化问题。通过该问题的处理总结一下内容：
 *   （1）Spring Data Jpa @ManyToMany等注解在没有特殊使用需求的时候尽量不要用双向，这会引起对象引用的环，导致json序列化出问题。
 *   （2）使用Fastjson在序列化过程中，各种问题会少于Jackson。
 *   （3）Spring Date Jpa 分页对象Page，在多级缓存中，由于找不到响应的Page对象，所以在缓存序列化过程中会出错，而且返回前台的内容过多。所以目前是采用返回Map的方式解决。
 */
package cn.herodotus.eurynome.upms.api.entity.system;

import cn.herodotus.eurynome.data.base.entity.BaseSysEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "系统用户")
@Entity
@Table(name = "sys_user", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_name"})},
        indexes = {@Index(name = "sys_user_id_idx", columnList = "user_id"), @Index(name = "sys_user_unm_idx", columnList = "user_name")})
public class SysUser extends BaseSysEntity {

    @ApiModelProperty(value = "用户ID")
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "user_id", length = 64)
    private String userId;

    @ApiModelProperty(value = "用户名")
    @Column(name = "user_name", length = 128, unique = true)
    private String userName;

    @ApiModelProperty(value = "密码", notes = "BCryptPasswordEncoder")
    @Column(name = "password", length = 256)
    private String password;

    @ApiModelProperty(value = "昵称")
    @Column(name = "nick_name", length = 64)
    private String nickName;

    @ApiModelProperty(value = "人员ID")
    @Column(name = "employee_id", length = 256)
    private String employeeId;

    @ApiModelProperty(value = "用户角色")
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "sys_user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})},
            indexes = {@Index(name = "sys_user_role_uid_idx", columnList = "user_id"), @Index(name = "sys_user_role_rid_idx", columnList = "role_id")})
    private Set<SysRole> roles = new HashSet<>();

    @Override
    public String getId() {
        return getUserId();
    }

    @Override
    public String getLinkedProperty() {
        return getUserName();
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

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Set<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<SysRole> roles) {
        this.roles = roles;
    }


    @Override
    public String toString() {
        return "SysUser{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", employeeId='" + employeeId + '\'' +
                '}';
    }
}

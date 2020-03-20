package cn.herodotus.eurynome.upms.api.entity.oauth;

import cn.herodotus.eurynome.component.data.base.entity.BaseSysEntity;
import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * <p> Description : Oauth Scope </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/19 14:15
 */
@Entity
@Table(name = "oauth_scopes", indexes = {@Index(name = "oauth_scope_id_idx", columnList = "scope_id")})
public class OauthScopes extends BaseSysEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "scope_id", length = 64)
    private String scopeId;

    @Column(name = "scope_code", length = 128, unique = true)
    private String scopeCode;

    @Column(name = "scope_name", length = 128)
    private String scopeName;

    /**
     * · @Fetch(FetchMode.JOIN) 会使用left join查询  只产生一条sql语句
     * · @Fetch(FetchMode.SELECT) 会产生N+1条sql语句
     * · @Fetch(FetchMode.SUBSELECT) 产生两条sql语句 第二条语句使用id in (.....)查询出所有关联的数据
     * <p>
     * 关系定义:
     * (1) 加上fetch=FetchType.LAZY  或 @Fetch(FetchMode.SELECT), 输出结果与上面相同，说明默认设置是fetch=FetchType.LAZY 和 @Fetch(FetchMode.SELECT) 下面四种配置等效，都是N+1条sql的懒加载
     * (2) 加上fetch=FetchType.Eager 和 @Fetch(FetchMode.SELECT), 同样是N+1条sql，不过和上面情况不同的是，N条sql会在criteria.list()时执行
     * (3) 加上@Fetch(FetchMode.JOIN), 那么Hibernate将强行设置为fetch=FetchType.EAGER, 用户设置fetch=FetchType.LAZY将不会生效
     * 从输出可看出，在执行criteria.list()时通过一条sql 获取了所有的City和Hotel。
     * 使用@Fetch(FetchMode.JOIN)需要注意的是：它在Join查询时是Full Join, 所以会有重复City出现
     * (4) 加上@Fetch(FetchMode.SUBSELECT), 那么Hibernate将强行设置为fetch=FetchType.EAGER, 用户设置fetch=FetchType.LAZY将不会生效 从输出可看出，在执行criteria.list()时通过两条sql分别获取City和Hotel
     * <p>
     * {@link :https://www.jianshu.com/p/23bd82a7b96e}
     */

    @ManyToMany
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "oauth_scope_authority",
            joinColumns = {@JoinColumn(name = "scope_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"scope_id", "authority_id"})},
            indexes = {@Index(name = "oauth_scope_authority_sid_idx", columnList = "scope_id"), @Index(name = "oauth_scope_authority_aid_idx", columnList = "authority_id")})
    private Set<SysAuthority> authorities = new HashSet<>();

    public String getScopeId() {
        return scopeId;
    }

    public void setScopeId(String scopeId) {
        this.scopeId = scopeId;
    }

    public String getScopeCode() {
        return scopeCode;
    }

    public void setScopeCode(String scopeCode) {
        this.scopeCode = scopeCode;
    }

    public String getScopeName() {
        return scopeName;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }

    public Set<SysAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<SysAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getDomainCacheKey() {
        return this.getScopeId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OauthScopes that = (OauthScopes) o;

        return new EqualsBuilder()
                .append(getScopeId(), that.getScopeId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getScopeId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "OauthScope{" +
                "scopeId='" + scopeId + '\'' +
                ", scopeCode='" + scopeCode + '\'' +
                ", scopeName='" + scopeName + '\'' +
                '}';
    }
}

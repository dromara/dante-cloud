package cn.herodotus.eurynome.upms.api.entity.hr;

import cn.herodotus.eurynome.component.data.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: 岗位信息 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/19 16:40
 */
@Entity
@Table(name = "sys_position", indexes = {@Index(name = "sys_position_id_idx", columnList = "position_id")})
public class SysPosition extends BaseEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "position_id", length = 64)
    private String positionId;

    @Column(name = "position_name", length = 200)
    private String positionName;

    @ManyToMany(mappedBy = "positions")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    private Set<SysEmployee> employees = new HashSet<>();

    @Override
    public String getDomainCacheKey() {
        return getPositionId();
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Set<SysEmployee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<SysEmployee> employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysPosition that = (SysPosition) o;

        return new EqualsBuilder()
                .append(getPositionId(), that.getPositionId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getPositionId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("positionId", positionId)
                .append("positionName", positionName)
                .toString();
    }
}

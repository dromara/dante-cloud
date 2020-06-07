package cn.herodotus.eurynome.upms.api.entity.oauth;

import cn.herodotus.eurynome.data.base.entity.BaseAppEntity;
import cn.herodotus.eurynome.upms.api.entity.development.Supplier;
import com.google.common.base.MoreObjects;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * <p> Description : 微服务管理 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/29 11:12
 */
@Entity
@Table(name = "oauth_microservices", indexes = {@Index(name = "oauth_microservices_id_idx", columnList = "service_id")})
public class OauthMicroservices extends BaseAppEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "service_id", length = 64)
    private String serviceId;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "oauth_microservices_scopes",
            joinColumns = {@JoinColumn(name = "service_id")},
            inverseJoinColumns = {@JoinColumn(name = "scope_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"service_id", "scope_id"})},
            indexes = {@Index(name = "oauth_microservices_scopes_mid_idx", columnList = "service_id"), @Index(name = "oauth_microservices_scopes_sid_idx", columnList = "scope_id")})
    private Set<OauthScopes> scopes = new HashSet<>();

    @Override
    public String getId() {
        return this.getServiceId();
    }

    @Override
    public String getLinkedProperty() {
        return null;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Set<OauthScopes> getScopes() {
        return scopes;
    }

    public void setScopes(Set<OauthScopes> scopes) {
        this.scopes = scopes;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("serviceId", serviceId)
                .add("supplier", supplier)
                .toString();
    }
}

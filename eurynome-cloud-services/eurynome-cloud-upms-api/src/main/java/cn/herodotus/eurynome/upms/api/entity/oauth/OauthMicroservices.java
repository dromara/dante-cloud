package cn.herodotus.eurynome.upms.api.entity.oauth;

import cn.herodotus.eurynome.component.common.enums.ApplicationType;
import cn.herodotus.eurynome.component.data.base.entity.BaseAppEntity;
import cn.herodotus.eurynome.upms.api.entity.microservice.Supplier;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <p> Description : 微服务管理 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/29 11:12
 */
@Entity
@Table(name = "oauth_microservices", indexes = {@Index(name = "oauth_microservices_id_idx", columnList = "service_id")})
public class OauthMicroservices extends BaseAppEntity {

    public OauthMicroservices() {
        super.setApplicationType(ApplicationType.SERVICE);
    }

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "service_id", length = 64)
    private String serviceId;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

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

    @Override
    public String toString() {
        return "OauthMicroservices{" +
                "serviceId='" + serviceId + '\'' +
                ", supplier=" + supplier +
                '}';
    }
}

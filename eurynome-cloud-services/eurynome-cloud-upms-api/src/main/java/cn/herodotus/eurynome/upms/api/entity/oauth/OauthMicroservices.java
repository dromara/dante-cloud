package cn.herodotus.eurynome.upms.api.entity.oauth;

import cn.herodotus.eurynome.component.data.base.entity.BaseSysEntity;
import cn.hutool.core.util.IdUtil;
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
public class OauthMicroservices extends BaseSysEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "service_id", length = 64)
    private String serviceId;

    @Column(name = "service_secret", length = 128)
    private String serviceSecret = IdUtil.randomUUID();

    @Column(name = "supplier_id", length = 64)
    private String supplierId;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceSecret() {
        return serviceSecret;
    }

    public void setServiceSecret(String serviceSecret) {
        this.serviceSecret = serviceSecret;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public String getId() {
        return this.getServiceId();
    }

    @Override
    public String getLinkedProperty() {
        return null;
    }

    @Override
    public String toString() {
        return "OauthMicroservices{" +
                "serviceId='" + serviceId + '\'' +
                ", serviceSecret='" + serviceSecret + '\'' +
                ", supplierId='" + supplierId + '\'' +
                '}';
    }
}

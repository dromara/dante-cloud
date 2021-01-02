package cn.herodotus.eurynome.upms.api.entity.oauth;

import cn.herodotus.eurynome.data.base.entity.BaseAppEntity;
import cn.herodotus.eurynome.upms.api.constants.enums.TechnologyType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * <p> Description : Oauth Application </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/19 16:13
 */
@Entity
@Table(name = "oauth_applications", indexes = {@Index(name = "oauth_applications_id_idx", columnList = "app_key")})
public class OauthApplications extends BaseAppEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "app_key", length = 64)
    private String appKey;

    @Column(name = "app_name_en", length = 128)
    private String appNameEn;

    @Column(name = "app_icon", length = 1024)
    private String appIcon;

    @Column(name = "app_tech")
    @Enumerated(EnumType.ORDINAL)
    private TechnologyType technologyType = TechnologyType.JAVA;

    @Column(name = "website", length = 1024)
    private String website;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "oauth_applications_scopes",
            joinColumns = {@JoinColumn(name = "app_key")},
            inverseJoinColumns = {@JoinColumn(name = "scope_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"app_key", "scope_id"})},
            indexes = {@Index(name = "oauth_applications_scopes_aid_idx", columnList = "app_key"), @Index(name = "oauth_applications_scopes_sid_idx", columnList = "scope_id")})
    private Set<OauthScopes> scopes = new HashSet<>();

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppNameEn() {
        return appNameEn;
    }

    public void setAppNameEn(String appNameEn) {
        this.appNameEn = appNameEn;
    }

    public TechnologyType getTechnologyType() {
        return technologyType;
    }

    public void setTechnologyType(TechnologyType technologyType) {
        this.technologyType = technologyType;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Set<OauthScopes> getScopes() {
        return scopes;
    }

    public void setScopes(Set<OauthScopes> scopes) {
        this.scopes = scopes;
    }

    @Override
    public String getId() {
        return this.getAppKey();
    }

    @Override
    public String getLinkedProperty() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OauthApplications that = (OauthApplications) o;

        return new EqualsBuilder()
                .append(getAppKey(), that.getAppKey())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getAppKey())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "OauthApplications{" +
                "appKey='" + appKey + '\'' +
                ", appNameEn='" + appNameEn + '\'' +
                ", appIcon='" + appIcon + '\'' +
                ", technologyType=" + technologyType +
                ", website='" + website + '\'' +
                '}';
    }
}

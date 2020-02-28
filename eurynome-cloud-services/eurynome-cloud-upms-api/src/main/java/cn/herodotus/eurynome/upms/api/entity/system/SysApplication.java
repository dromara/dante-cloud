package cn.herodotus.eurynome.upms.api.entity.system;

import cn.herodotus.eurynome.component.data.base.entity.BaseSysEntity;
import cn.herodotus.eurynome.upms.api.enums.ApplicationType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sys_application", indexes = {@Index(name = "sys_application_aid_idx", columnList = "application_id")})
@NamedEntityGraphs({
        @NamedEntityGraph(name = "SysApplicationWithAuthority", attributeNodes = {@NamedAttributeNode(value = "authorities")})
})
public class SysApplication extends BaseSysEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "application_id", length = 64)
    private String applicationId;

    @Column(name = "application_name", length = 128)
    private String applicationName;

    @Column(name = "application_name_en", length = 128)
    private String applicationNameEn;

    @Column(name = "application_icon", length = 1024)
    private String applicationIcon;

    @Column(name = "application_type")
    @Enumerated(EnumType.STRING)
    private ApplicationType applicationType = ApplicationType.WEB;

    @Column(name = "application_language", length = 64)
    private String applicationLanguage;

    @Column(name = "website", length = 1024)
    private String website;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "sys_application_authority",
            joinColumns = {@JoinColumn(name = "application_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"application_id", "authority_id"})},
            indexes = {@Index(name = "sys_application_authority_aid_idx", columnList = "application_id"), @Index(name = "sys_application_authority_pid_idx", columnList = "authority_id")})
    private Set<SysAuthority> authorities = new HashSet<>();


    @Override
    public String getDomainCacheKey() {
        return getApplicationId();
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationNameEn() {
        return applicationNameEn;
    }

    public void setApplicationNameEn(String applicationNameEn) {
        this.applicationNameEn = applicationNameEn;
    }

    public String getApplicationIcon() {
        return applicationIcon;
    }

    public void setApplicationIcon(String applicationIcon) {
        this.applicationIcon = applicationIcon;
    }

    public ApplicationType getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(ApplicationType applicationType) {
        this.applicationType = applicationType;
    }

    public String getApplicationLanguage() {
        return applicationLanguage;
    }

    public void setApplicationLanguage(String applicationLanguage) {
        this.applicationLanguage = applicationLanguage;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Set<SysAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<SysAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "SysApplication{" +
                "applicationId='" + applicationId + '\'' +
                ", applicationName='" + applicationName + '\'' +
                ", applicationNameEn='" + applicationNameEn + '\'' +
                ", applicationIcon='" + applicationIcon + '\'' +
                ", applicationType=" + applicationType +
                ", applicationLanguage='" + applicationLanguage + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}

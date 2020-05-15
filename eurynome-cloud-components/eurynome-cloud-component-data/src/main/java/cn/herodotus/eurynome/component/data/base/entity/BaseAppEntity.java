package cn.herodotus.eurynome.component.data.base.entity;

import cn.herodotus.eurynome.component.common.enums.ApplicationType;
import cn.hutool.core.util.IdUtil;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

/**
 * <p> Description : BaseAppEntity </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/4 12:03
 */
@MappedSuperclass
public abstract class BaseAppEntity extends BaseSysEntity{

    @Column(name = "app_secret", length = 128)
    private String appSecret = IdUtil.randomUUID();

    @Column(name = "app_name", length = 128)
    private String appName;

    @Column(name = "app_code", length = 128)
    private String appCode;

    @Column(name = "app_type")
    @Enumerated(EnumType.ORDINAL)
    private ApplicationType applicationType = ApplicationType.WEB;

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public ApplicationType getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(ApplicationType applicationType) {
        this.applicationType = applicationType;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }
}

/*
 * Copyright (c) 2019. All Rights Reserved
 * ProjectName: hades-multi-module
 * FileName: QCloudSmsProperties
 * Author: hades
 * Date: 19-3-18 下午1:58
 * LastModified: 19-3-18 下午1:58
 */

package cn.herodotus.eurynome.integration.sms.properties;

import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p>Description: </p>
 *
 * @author hades
 * @date 2019/3/18
 */

@ConfigurationProperties(prefix = "herodotus.integration.sms.txcloud")
public class TxcloudSmsProperties {
    private String appId;
    private String appKey;

    private String defaultTemplateId;

    /**
     * 短信模板ID，需要在短信应用中申请
     */
    private Map<String, String> templates;
    /**
     * 签名，如果填空，系统会使用默认签名.签名，使用的是`签名内容`，而不是`签名ID`
     */
    private String signName = "";

    private String regionId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getDefaultTemplateId() {
        return defaultTemplateId;
    }

    public void setDefaultTemplateId(String defaultTemplateId) {
        this.defaultTemplateId = defaultTemplateId;
    }

    public Map<String, String> getTemplates() {
        return templates;
    }

    public void setTemplates(Map<String, String> templates) {
        this.templates = templates;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }
}

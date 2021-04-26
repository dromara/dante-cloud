package cn.herodotus.eurynome.integration.social.domain.easemob.messages;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * <p>Description: 扩展属性 </p>
 * <p>
 * 由APP自己定义。可以没有这个字段，但是如果有，值不能是“ext:null”这种形式，否则出错
 *
 * 增加Jackson和Fastjson字段映射，由于使用时需要去掉无关的属性，建议使用okhttps的fastjson版本
 *
 * @author : gengwei.zheng
 * @date : 2021/4/2 11:49
 */
public class Extend implements Serializable {

    private String avatarUrl;
    private String username;

    /**
     * iOS扩展消息
     */
    @JsonProperty("em_apns_ext")
    @JSONField(name = "em_apns_ext")
    private ApnsExtend apnsExtend = new ApnsExtend();
    /**
     * 发送静默消息
     */
    @JsonProperty("em_ignore_notification")
    @JSONField(name = "em_ignore_notification")
    private Boolean ignoreNotification;

    /**
     * 设置强制推送型 APNs
     */
    @JsonProperty("em_force_notification")
    @JSONField(name = "em_force_notification")
    private Boolean forceNotification;

    public Extend() {
    }

    public Extend(String avatarUrl, String username) {
        this.avatarUrl = avatarUrl;
        this.username = username;
    }

    public Extend(String avatarUrl, String username, ApnsExtend apnsExtend, Boolean ignoreNotification, Boolean forceNotification) {
        this.avatarUrl = avatarUrl;
        this.username = username;
        this.apnsExtend = apnsExtend;
        this.ignoreNotification = ignoreNotification;
        this.forceNotification = forceNotification;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ApnsExtend getApnsExtend() {
        return apnsExtend;
    }

    public void setApnsExtend(ApnsExtend apnsExtend) {
        this.apnsExtend = apnsExtend;
    }

    public Boolean getIgnoreNotification() {
        return ignoreNotification;
    }

    public void setIgnoreNotification(Boolean ignoreNotification) {
        this.ignoreNotification = ignoreNotification;
    }

    public Boolean getForceNotification() {
        return forceNotification;
    }

    public void setForceNotification(Boolean forceNotification) {
        this.forceNotification = forceNotification;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("avatarUrl", avatarUrl)
                .add("username", username)
                .add("apnsExtend", apnsExtend)
                .add("ignoreNotification", ignoreNotification)
                .add("forceNotification", forceNotification)
                .toString();
    }
}

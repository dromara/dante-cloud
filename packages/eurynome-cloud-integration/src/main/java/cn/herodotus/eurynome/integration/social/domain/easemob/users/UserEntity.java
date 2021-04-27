package cn.herodotus.eurynome.integration.social.domain.easemob.users;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Description: 注册单个用户返回实体对象中entities对应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/25 16:43
 */
public class UserEntity implements Serializable {

    private String uuid;
    private String type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created = new Date();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modified = new Date();

    private String username;

    private String nickname;

    private Boolean activated;

    @JsonProperty("device_token")
    @JSONField(name = "device_token")
    private String deviceToken;

    @JsonProperty("notification_display_style")
    @JSONField(name = "notification_display_style")
    private String notificationDisplayStyle;

    @JsonProperty("notification_no_disturbing")
    @JSONField(name = "notification_no_disturbing")
    private Boolean notificationNoDisturbing;

    @JsonProperty("notification_no_disturbing_end")
    @JSONField(name = "notification_no_disturbing_end")
    private Integer notificationNoDisturbingEnd;

    @JsonProperty("notification_no_disturbing_start")
    @JSONField(name = "notification_no_disturbing_start")
    private Integer notificationNoDisturbingStart;

    @JsonProperty("notifier_name")
    @JSONField(name = "notifier_name")
    private String notifierName;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getNotificationDisplayStyle() {
        return notificationDisplayStyle;
    }

    public void setNotificationDisplayStyle(String notificationDisplayStyle) {
        this.notificationDisplayStyle = notificationDisplayStyle;
    }

    public Boolean getNotificationNoDisturbing() {
        return notificationNoDisturbing;
    }

    public void setNotificationNoDisturbing(Boolean notificationNoDisturbing) {
        this.notificationNoDisturbing = notificationNoDisturbing;
    }

    public Integer getNotificationNoDisturbingEnd() {
        return notificationNoDisturbingEnd;
    }

    public void setNotificationNoDisturbingEnd(Integer notificationNoDisturbingEnd) {
        this.notificationNoDisturbingEnd = notificationNoDisturbingEnd;
    }

    public Integer getNotificationNoDisturbingStart() {
        return notificationNoDisturbingStart;
    }

    public void setNotificationNoDisturbingStart(Integer notificationNoDisturbingStart) {
        this.notificationNoDisturbingStart = notificationNoDisturbingStart;
    }

    public String getNotifierName() {
        return notifierName;
    }

    public void setNotifierName(String notifierName) {
        this.notifierName = notifierName;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("uuid", uuid)
                .add("type", type)
                .add("created", created)
                .add("modified", modified)
                .add("username", username)
                .add("nickname", nickname)
                .add("activated", activated)
                .add("deviceToken", deviceToken)
                .add("notificationDisplayStyle", notificationDisplayStyle)
                .add("notificationNoDisturbing", notificationNoDisturbing)
                .add("notificationNoDisturbingEnd", notificationNoDisturbingEnd)
                .add("notificationNoDisturbingStart", notificationNoDisturbingStart)
                .add("notifierName", notifierName)
                .toString();
    }
}

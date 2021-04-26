package cn.herodotus.eurynome.integration.social.domain.easemob.groups;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/26 13:55
 */
public class Group implements Serializable {

    /**
     * 群组 ID，群组唯一标识符，由环信服务器生成，等同于单个用户的环信 ID
     */
    private String id;
    /**
     * 群组名称，根据用户输入创建，字符串类型
     */
    private String name;
    /**
     * 群组描述，根据用户输入创建，字符串类型。
     */
    private String description;

    /**
     * 群组类型：true：公开群，false：私有群。
     */
    @JsonProperty("public")
    @JSONField(name = "public")
    private Boolean isPublic;
    /**
     * 加入群组是否需要群主或者群管理员审批。true：是，false：否。
     */
    private Boolean membersonly;
    /**
     * 是否允许群成员邀请别人加入此群。 true：允许群成员邀请人加入此群，false：只有群主才可以往群里加人。由于只有私有群才允许群成员邀请人加入此群，所以当群组为私有群时，该参数设置为true才有效。默认为false
     */
    private Boolean allowinvites;
    /**
     * 群成员上限，创建群组的时候设置，可修改。
     */
    private Integer maxusers;
    /**
     * 现有成员总数
     */
    @JsonProperty("affiliations_count")
    @JSONField(name = "affiliations_count")
    private Integer affiliationsCount;
    /**
     * 群主的环信 ID。例如：{“owner”: “13800138001”}。
     */
    private String owner;
    /**
     * 群成员的环信 ID。例如：{“member”:“xc6xrnbzci”}。
     */
    private String member;
    /**
     * 邀请加群，被邀请人是否需要确认。如果是true，表示邀请加群需要被邀请人确认；如果是false，表示不需要被邀请人确认，直接将被邀请人加入群。 该字段的默认值为true。
     */
    @JsonProperty("invite_need_confirm")
    @JSONField(name = "invite_need_confirm")
    private String inviteNeedConfirm;
    /**
     * 群组扩展信息。
     */
    private String custom;
    /**
     * 是否全员禁言。true：是，false：否
     */
    private Boolean mute;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public Boolean getMembersonly() {
        return membersonly;
    }

    public void setMembersonly(Boolean membersonly) {
        this.membersonly = membersonly;
    }

    public Boolean getAllowinvites() {
        return allowinvites;
    }

    public void setAllowinvites(Boolean allowinvites) {
        this.allowinvites = allowinvites;
    }

    public Integer getMaxusers() {
        return maxusers;
    }

    public void setMaxusers(Integer maxusers) {
        this.maxusers = maxusers;
    }

    public Integer getAffiliationsCount() {
        return affiliationsCount;
    }

    public void setAffiliationsCount(Integer affiliationsCount) {
        this.affiliationsCount = affiliationsCount;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getInviteNeedConfirm() {
        return inviteNeedConfirm;
    }

    public void setInviteNeedConfirm(String inviteNeedConfirm) {
        this.inviteNeedConfirm = inviteNeedConfirm;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public Boolean getMute() {
        return mute;
    }

    public void setMute(Boolean mute) {
        this.mute = mute;
    }
}

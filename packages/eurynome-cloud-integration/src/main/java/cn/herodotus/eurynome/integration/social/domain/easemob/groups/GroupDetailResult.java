package cn.herodotus.eurynome.integration.social.domain.easemob.groups;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/26 13:55
 */
public class GroupDetailResult implements Serializable {

    /**
     * 群组 ID，群组唯一标识符，由环信服务器生成，等同于单个用户的环信 ID
     */
    @JSONField(name = "id")
    @JsonProperty("id")
    private String groupId;
    /**
     * 群组名称，根据用户输入创建，字符串类型
     */
    @JSONField(name = "name")
    @JsonProperty("name")
    private String groupName;
    /**
     * 群组描述，根据用户输入创建，字符串类型。
     */
    private String description;
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
     * 群主的环信 ID。例如：{“owner”: “13800138001”}。
     */
    private String owner;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created = new Date();

    /**
     * 现有成员总数
     */
    @JsonProperty("affiliations_count")
    @JSONField(name = "affiliations_count")
    private Integer affiliationsCount;

    /**
     * 群组类型：true：公开群，false：私有群。
     */
    @JsonProperty("public")
    @JSONField(name = "public")
    private Boolean isPublic;

    private List<Map<String, String>> affiliations;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getAffiliationsCount() {
        return affiliationsCount;
    }

    public void setAffiliationsCount(Integer affiliationsCount) {
        this.affiliationsCount = affiliationsCount;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public List<Map<String, String>> getAffiliations() {
        return affiliations;
    }

    public void setAffiliations(List<Map<String, String>> affiliations) {
        this.affiliations = affiliations;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("groupId", groupId)
                .add("groupName", groupName)
                .add("description", description)
                .add("membersonly", membersonly)
                .add("allowinvites", allowinvites)
                .add("maxusers", maxusers)
                .add("owner", owner)
                .add("created", created)
                .add("affiliationsCount", affiliationsCount)
                .add("isPublic", isPublic)
                .toString();
    }
}

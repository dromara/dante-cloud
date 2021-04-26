package cn.herodotus.eurynome.integration.social.domain.easemob.groups;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Description: Group返回对象，data中实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/26 14:45
 */
public class GroupResult implements Serializable {

    @JSONField(name = "groupid")
    @JsonProperty("groupid")
    private String groupId;

    @JSONField(name = "groupname")
    @JsonProperty("groupname")
    private String groupName;

    private String owner;

    private Integer affiliations;

    private String type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastModified = new Date();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created = new Date();

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getAffiliations() {
        return affiliations;
    }

    public void setAffiliations(Integer affiliations) {
        this.affiliations = affiliations;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("groupId", groupId)
                .add("groupName", groupName)
                .add("owner", owner)
                .add("affiliations", affiliations)
                .add("type", type)
                .add("lastModified", lastModified)
                .add("created", created)
                .toString();
    }
}

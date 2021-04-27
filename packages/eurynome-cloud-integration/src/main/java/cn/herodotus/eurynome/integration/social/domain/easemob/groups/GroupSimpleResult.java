package cn.herodotus.eurynome.integration.social.domain.easemob.groups;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.Date;

/**
 * <p>Description: 获取App中所有的群组返回对象对应的data实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/27 8:54
 */
public class GroupSimpleResult extends GroupUserResult {

    private String owner;

    private Integer affiliations;

    private String type;

    @JSONField(name = "last_modified")
    @JsonProperty("last_modified")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastModified = new Date();

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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("owner", owner)
                .add("affiliations", affiliations)
                .add("type", type)
                .add("lastModified", lastModified)
                .toString();
    }
}

package cn.herodotus.eurynome.integration.social.domain.easemob.groups;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

/**
 * <p>Description: 获取一个用户参与的所有群组返回对象中data对应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/26 14:45
 */
public class GroupUserResult extends GroupCreateResult {

    @JSONField(name = "groupname")
    @JsonProperty("groupname")
    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("groupName", groupName)
                .toString();
    }
}

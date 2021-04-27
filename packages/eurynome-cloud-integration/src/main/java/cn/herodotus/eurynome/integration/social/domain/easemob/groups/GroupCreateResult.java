package cn.herodotus.eurynome.integration.social.domain.easemob.groups;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * <p>Description: 环信创建群组返回数据中Data对应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/27 10:59
 */
public class GroupCreateResult implements Serializable {

    @JSONField(name = "groupid")
    @JsonProperty("groupid")
    private String groupId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}

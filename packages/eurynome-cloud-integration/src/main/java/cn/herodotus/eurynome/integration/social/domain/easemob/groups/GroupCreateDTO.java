/**
 *
 */
package cn.herodotus.eurynome.integration.social.domain.easemob.groups;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Description: Group请求实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/27 8:44
 */
public class GroupCreateDTO implements Serializable {

	/**
	 * 是否是公开群，此属性为必须的
	 */
	@JsonProperty("public")
	@JSONField(name = "public")
	private boolean isPublic = true;

	/**
	 *  群组名称，此属性为必须的
	 */
	@JsonProperty("groupname")
	@JSONField(name = "groupname")
	private String groupName;
	/**
	 * 群组描述，此属性为必须的
	 */
	@JsonProperty("desc")
	@JSONField(name = "desc")
	private String description;
	/**
	 * 群组的管理员，此属性为必须的
	 */
	private String owner;

	/**
	 * 加入群是否需要批准，默认值是false（加入公开群不需要群主批准），此属性为必选的，私有群必须为true
	 */
	private boolean approval = false;
	/**
	 * 加入群是否需要群主或者群管理员审批，默认是false
	 */
	@JsonProperty("members_only")
	@JSONField(name = "members_only")
	private boolean membersOnly = false;

	/**
	 * 是否允许群成员入群。 true：允许(公开群)，false：只有群主或者管理员才可以。
	 */
	private boolean allowinvites = true;
	/**
	 * 群组成员最大数（包括群主），值为数值类型，默认值200，最大值2000，此属性为可选的
	 */
	private Integer maxusers;

	/**
	 * 群组成员，此属性为可选的，但是如果加了此项，数组元素至少一个，不能超过100个（注：群主user1不需要写入到members里面）
	 */
	private List<String> members;

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean aPublic) {
		isPublic = aPublic;
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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public boolean isApproval() {
		return approval;
	}

	public void setApproval(boolean approval) {
		this.approval = approval;
	}

	public boolean isMembersOnly() {
		return membersOnly;
	}

	public void setMembersOnly(boolean membersOnly) {
		this.membersOnly = membersOnly;
	}

	public boolean isAllowinvites() {
		return allowinvites;
	}

	public void setAllowinvites(boolean allowinvites) {
		this.allowinvites = allowinvites;
	}

	public Integer getMaxusers() {
		return maxusers;
	}

	public void setMaxusers(Integer maxusers) {
		this.maxusers = maxusers;
	}

	public List<String> getMembers() {
		return members;
	}

	public void setMembers(List<String> members) {
		this.members = members;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("isPublic", isPublic)
				.add("groupName", groupName)
				.add("description", description)
				.add("owner", owner)
				.add("approval", approval)
				.add("membersOnly", membersOnly)
				.add("allowinvites", allowinvites)
				.add("maxusers", maxusers)
				.toString();
	}
}
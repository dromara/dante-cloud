/**
 *
 */
package cn.herodotus.eurynome.integration.social.domain.easemob.groups;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * <p>Description: 环信跟新群组信息请求参数对应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/27 11:09
 */
public class GroupUpdateDTO implements Serializable {
	/**
	 * 群组名称，修改时值不能包含斜杠
	 */
	@JsonProperty("groupname")
	private String groupName;

	/**
	 * 群组描述，修改时值不能包含斜杠
	 */
	private String description;

	/**
	 * 群组成员最大数（包括群主），值为数值类型默认值200，最大值2000
	 */
	private Integer maxusers;

	/**
	 * 加入群组是否需要群主或者群管理员审批。true：是，false：否
	 */
	private boolean membersonly;

	public GroupUpdateDTO() {
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

	public Integer getMaxusers() {
		return maxusers;
	}

	public void setMaxusers(Integer maxusers) {
		this.maxusers = maxusers;
	}

	public boolean isMembersonly() {
		return membersonly;
	}

	public void setMembersonly(boolean membersonly) {
		this.membersonly = membersonly;
	}
}

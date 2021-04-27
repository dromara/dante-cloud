package cn.herodotus.eurynome.integration.social.domain.easemob.messages;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * <p>Description: 环信Easemob通用参数实体 </p>
 * <p>
 * 增加Jackson和Fastjson字段映射，由于使用时需要去掉无关的属性，建议使用okhttps的fastjson版本
 *
 * @author : gengwei.zheng
 * @date : 2021/4/2 11:31
 */
public class MessageDTO implements Serializable {

	/**
	 * 发送的目标类型；users：给用户发消息，chatgroups：给群发消息，chatrooms：给聊天室发消息
	 */
	@JsonProperty("target_type")
	@JSONField(name = "target_type")
	private TargetType targetType;

	/**
	 * 发送的目标
	 */
	private String[] target;

	/**
	 * 表示消息发送者;无此字段Server会默认设置为"from":"admin"，有from字段但值为空串("")时请求失败
	 */
	private String from;

	/**
	 * 封装信息数据
	 */
	private Message msg;

	@JsonProperty("ext")
	@JSONField(name = "ext")
	private Extend extend;

	public TargetType getTargetType() {
		return targetType;
	}

	public void setTargetType(TargetType targetType) {
		this.targetType = targetType;
	}

	public String[] getTarget() {
		return target;
	}

	public void setTarget(String[] target) {
		this.target = target;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Message getMsg() {
		return msg;
	}

	public void setMsg(Message msg) {
		this.msg = msg;
	}

	public Extend getExtend() {
		return extend;
	}

	public void setExtend(Extend extend) {
		this.extend = extend;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("targetType", targetType)
				.add("target", target)
				.add("from", from)
				.add("msg", msg)
				.add("extend", extend)
				.toString();
	}
}

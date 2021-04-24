package cn.herodotus.eurynome.integration.social.domain.easemob;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

/**
 * <p>Description: iOS扩展消息
 * 环信提供扩展字段： </p>
 * <p>
 * 增加Jackson和Fastjson字段映射，由于使用时需要去掉无关的属性，建议使用okhttps的fastjson版本
 *
 * @author : gengwei.zheng
 * @date : 2021/4/2 11:51
 */
public class ApnsExtend {

	/**
	 * 自定义推送显示
	 */
	@JsonProperty("em_push_content")
	@JSONField(name = "em_push_content")
	private String pushContent;

	/**
	 * 向 APNs Payload 中添加 category 字段
	 */
	@JsonProperty("em_push_category")
	@JSONField(name = "em_push_category")
	private String pushCategory;

	/**
	 * 自定义推送提示音
	 */
	@JsonProperty("em_push_sound")
	@JSONField(name = "em_push_sound")
	private String pushSound;

	/**
	 * 开启 APNs 通知扩展
	 */
	@JsonProperty("em_push_mutable_content")
	@JSONField(name = "em_push_mutable_content")
	private Boolean pushMutableContent;

	public ApnsExtend() {
	}

	public ApnsExtend(String pushContent, String pushCategory, String pushSound, Boolean pushMutableContent) {
		this.pushContent = pushContent;
		this.pushCategory = pushCategory;
		this.pushSound = pushSound;
		this.pushMutableContent = pushMutableContent;
	}

	public String getPushContent() {
		return pushContent;
	}

	public void setPushContent(String pushContent) {
		this.pushContent = pushContent;
	}

	public String getPushCategory() {
		return pushCategory;
	}

	public void setPushCategory(String pushCategory) {
		this.pushCategory = pushCategory;
	}

	public String getPushSound() {
		return pushSound;
	}

	public void setPushSound(String pushSound) {
		this.pushSound = pushSound;
	}

	public Boolean getPushMutableContent() {
		return pushMutableContent;
	}

	public void setPushMutableContent(Boolean pushMutableContent) {
		this.pushMutableContent = pushMutableContent;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("pushContent", pushContent)
				.add("pushCategory", pushCategory)
				.add("pushSound", pushSound)
				.add("pushMutableContent", pushMutableContent)
				.toString();
	}
}

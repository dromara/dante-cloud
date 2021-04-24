package cn.herodotus.eurynome.integration.content.properties;

import com.google.common.base.MoreObjects;

/**
 * <p>Description: 阿里云内容管理配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 10:29
 */
public class AliyunScanProperties {

	private AliyunTimeoutProperties image = new AliyunTimeoutProperties();
	private AliyunTimeoutProperties video = new AliyunTimeoutProperties();
	private AliyunTimeoutProperties text = new AliyunTimeoutProperties();
	private AliyunTimeoutProperties voice = new AliyunTimeoutProperties();

	public AliyunTimeoutProperties getImage() {
		return image;
	}

	public void setImage(AliyunTimeoutProperties image) {
		this.image = image;
	}

	public AliyunTimeoutProperties getVideo() {
		return video;
	}

	public void setVideo(AliyunTimeoutProperties video) {
		this.video = video;
	}

	public AliyunTimeoutProperties getText() {
		return text;
	}

	public void setText(AliyunTimeoutProperties text) {
		this.text = text;
	}

	public AliyunTimeoutProperties getVoice() {
		return voice;
	}

	public void setVoice(AliyunTimeoutProperties voice) {
		this.voice = voice;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("image", image)
				.add("video", video)
				.add("text", text)
				.add("voice", voice)
				.toString();
	}
}

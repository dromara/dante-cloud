package cn.herodotus.eurynome.integration.compliance.properties;

import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: 阿里云内容管理配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 10:29
 */
@ConfigurationProperties(prefix = "herodotus.integration.compliance.aliyun")
public class AliyunScanProperties {

	private String regionId;
	private String accessKeyId;
	private String accessKeySecret;

	private Request image = new Request();
	private Request video = new Request();
	private Request text = new Request();
	private Request voice = new Request();

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public Request getImage() {
		return image;
	}

	public void setImage(Request image) {
		this.image = image;
	}

	public Request getVideo() {
		return video;
	}

	public void setVideo(Request video) {
		this.video = video;
	}

	public Request getText() {
		return text;
	}

	public void setText(Request text) {
		this.text = text;
	}

	public Request getVoice() {
		return voice;
	}

	public void setVoice(Request voice) {
		this.voice = voice;
	}

	public static class Request {
		private Integer connectTimeout = 3000;
		private Integer readTimeout = 6000;

		public Integer getConnectTimeout() {
			return connectTimeout;
		}

		public void setConnectTimeout(Integer connectTimeout) {
			this.connectTimeout = connectTimeout;
		}

		public Integer getReadTimeout() {
			return readTimeout;
		}

		public void setReadTimeout(Integer readTimeout) {
			this.readTimeout = readTimeout;
		}

		@Override
		public String toString() {
			return MoreObjects.toStringHelper(this)
					.add("connectTimeout", connectTimeout)
					.add("readTimeout", readTimeout)
					.toString();
		}
	}
}

package cn.herodotus.eurynome.integration.content.properties;

import com.google.common.base.MoreObjects;

/**
 * <p>Description: 通用的Timeout属性 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/16 16:09
 */
public class AliyunTimeoutProperties {

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

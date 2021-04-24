package cn.herodotus.eurynome.integration.content.properties;

import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: 天眼查配置信息 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/15 16:45
 */
@ConfigurationProperties(prefix = "herodotus.integration.content.tianyan")
public class TianyanProperties {

	/**
	 * 天眼查token信息
	 */
	private String token;
	/**
	 * 天眼查开放API接口基础地址
	 */
	private String baseUrl;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("token", token)
				.add("baseUrl", baseUrl)
				.toString();
	}
}

package cn.herodotus.eurynome.integration.compliance.properties;

import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: 百度文字识别OCR配置属性 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 13:13
 */
@ConfigurationProperties(prefix = "herodotus.integration.compliance.baidu")
public class BaiduOcrProperties {

	private String appId;
	private String appKey;
	private String secretKey;
	/**
	 * 服务器建立连接的超时时间（单位：毫秒）
	 */
	private Integer connectionTimeout = 5000;
	/**
	 * 通过打开的连接传输数据的超时时间（单位：毫秒）
	 */
	private Integer socketTimeout = 60000;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public Integer getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(Integer connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public Integer getSocketTimeout() {
		return socketTimeout;
	}

	public void setSocketTimeout(Integer socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("appId", appId)
				.add("appKey", appKey)
				.add("secretKey", secretKey)
				.add("connectionTimeout", connectionTimeout)
				.add("socketTimeout", socketTimeout)
				.toString();
	}
}

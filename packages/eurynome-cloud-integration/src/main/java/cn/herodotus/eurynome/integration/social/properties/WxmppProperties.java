package cn.herodotus.eurynome.integration.social.properties;

import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * <p>Description: 微信公众号属性配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/7 13:17
 */
@ConfigurationProperties(prefix = "herodotus.integration.social.wxmpp")
public class WxmppProperties {

	/**
	 * 是否使用redis存储access token
	 */
	private boolean useRedis;

	/**
	 * redis 配置
	 */
	private RedisConfig redis;

	public boolean isUseRedis() {
		return useRedis;
	}

	public void setUseRedis(boolean useRedis) {
		this.useRedis = useRedis;
	}

	public RedisConfig getRedis() {
		return redis;
	}

	public void setRedis(RedisConfig redis) {
		this.redis = redis;
	}

	public static class RedisConfig {
		/**
		 * redis服务器 主机地址
		 */
		private String host;

		/**
		 * redis服务器 端口号
		 */
		private Integer port;

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public Integer getPort() {
			return port;
		}

		public void setPort(Integer port) {
			this.port = port;
		}

		@Override
		public String toString() {
			return MoreObjects.toStringHelper(this)
					.add("host", host)
					.add("port", port)
					.toString();
		}
	}

	/**
	 * 多个公众号配置信息
	 */
	private List<MpConfig> configs;

	public List<MpConfig> getConfigs() {
		return configs;
	}

	public void setConfigs(List<MpConfig> configs) {
		this.configs = configs;
	}

	public static class MpConfig {
		/**
		 * 设置微信公众号的appid
		 */
		private String appId;

		/**
		 * 设置微信公众号的app secret
		 */
		private String secret;

		/**
		 * 设置微信公众号的token
		 */
		private String token;

		/**
		 * 设置微信公众号的EncodingAESKey
		 */
		private String aesKey;

		public String getAppId() {
			return appId;
		}

		public void setAppId(String appId) {
			this.appId = appId;
		}

		public String getSecret() {
			return secret;
		}

		public void setSecret(String secret) {
			this.secret = secret;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getAesKey() {
			return aesKey;
		}

		public void setAesKey(String aesKey) {
			this.aesKey = aesKey;
		}

		@Override
		public String toString() {
			return MoreObjects.toStringHelper(this)
					.add("appId", appId)
					.add("secret", secret)
					.add("token", token)
					.add("aesKey", aesKey)
					.toString();
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("useRedis", useRedis)
				.add("redis", redis)
				.add("configs", configs)
				.toString();
	}
}

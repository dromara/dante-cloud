package cn.herodotus.eurynome.integration.social.domain.easemob.common;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * <p>Description: 环信Token返回值类型 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/1 14:06
 */
public class Token implements Serializable {

	/**
	 * 有效的token字符串
	 */
	@JsonProperty("access_token")
	@JSONField(name = "access_token")
	private String accessToken;

	/**
	 * token 有效时间，以秒为单位，在有效期内不需要重复获取
	 */
	@JsonProperty("expires_in")
	@JSONField(name = "expires_in")
	private BigInteger expiresIn;

	/**
	 * 当前 App 的 UUID 值
	 */
	private String application;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public BigInteger getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(BigInteger expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("accessToken", accessToken)
				.add("expiresIn", expiresIn)
				.add("application", application)
				.toString();
	}
}

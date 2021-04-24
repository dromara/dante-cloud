package cn.herodotus.eurynome.integration.social.domain.wxapp;

import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>Description: 微信小程序与后端交互统一的参数实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/3/29 9:23
 */
@ApiModel(description = "小程序请求参数实体")
public class WxappRequest implements Serializable {

	private String code;

	@ApiModelProperty(value = "小程序appId")
	private String appId;

	@ApiModelProperty(value = "消息密文")
	private String encryptedData;

	@ApiModelProperty(value = "加密算法的初始向量")
	private String iv;

	@ApiModelProperty(value = "小程序用户openId")
	private String openId;

	@ApiModelProperty(value = "会话密钥")
	private String sessionKey;

	@ApiModelProperty(value = "唯一ID")
	private String unionId;

	@ApiModelProperty(value = "用户非敏感信息")
	private String rawData;

	@ApiModelProperty(value = "签名")
	private String signature;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRawData() {
		return rawData;
	}

	public void setRawData(String rawData) {
		this.rawData = rawData;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getEncryptedData() {
		return encryptedData;
	}

	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	/**
	 * pig记录日志时，会从Principal中，通过getName()的方式，来获取用户信息，并存入title字段中
	 * {@link :PigxAuthenticationFailureEventHandler}
	 * 本质上就是获取Principal的toString。
	 *
	 * 由于title数据库字段只有255，新增的微信小程序登录WxappRequest原有的toString，返回值太长，导致无法存入。
	 * 因此，这里改写为使用openId
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("code", code)
				.add("appId", appId)
				.add("encryptedData", encryptedData)
				.add("iv", iv)
				.add("openId", openId)
				.add("sessionKey", sessionKey)
				.add("unionId", unionId)
				.add("rawData", rawData)
				.add("signature", signature)
				.toString();
	}
}

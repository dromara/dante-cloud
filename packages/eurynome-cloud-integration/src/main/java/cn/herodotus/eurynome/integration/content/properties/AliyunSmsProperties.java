package cn.herodotus.eurynome.integration.content.properties;

import com.google.common.base.MoreObjects;

/**
 * <p>Description: 短信配置参数 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/16 14:44
 */
public class AliyunSmsProperties {

	/**
	 * 短信API产品域名（接口地址固定，无需修改）
	 */
	private String domain = "dysmsapi.aliyuncs.com";
	/**
	 * 短信签名-可在短信控制台中找到
	 */
	private String signName;

	private String version;

	private String templateCode;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("domain", domain)
				.add("signName", signName)
				.add("version", version)
				.add("templateCode", templateCode)
				.toString();
	}
}

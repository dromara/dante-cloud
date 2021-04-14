package cn.herodotus.eurynome.integration.compliance.domain.aliyun.common;

import com.google.common.base.MoreObjects;

/**
 * <p>Description: 阿里云公共请求参数ClientInfo </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 17:25
 */
public class ClientInfo {

	/**
	 * SDK版本号。
	 * 通过SDK调用时需要提供该字段。更多信息，请参见SDK概览。
	 */
	private String sdkVersion;
	/**
	 * 配置信息版本。
	 * 通过SDK调用时需要提供该字段。更多信息，请参见SDK概览。
	 */
	private String cfgVersion;

	/**
	 * 用户账号的类型。取值：
	 * taobao：表示淘宝账号。
	 * others：表示其他账号体系的账号。
	 */
	private String userType;
	/**
	 * 您业务系统中用户的唯一标识。
	 * 强烈建议您填写该值用于分析用户违规行为。
	 */
	private String userId;
	/**
	 * 用户昵称。
	 */
	private String userNick;
	/**
	 * 用户头像的公网可访问URL地址。传递该值可用于分析用户的行为。
	 */
	private String avatar;
	/**
	 * 硬件设备码。
	 */
	private String imei;
	/**
	 * 运营商设备码。
	 */
	private String imsi;
	/**
	 * 设备指纹。
	 */
	private String umid;
	/**
	 * 用于标识您业务系统中用户的公网IP地址。
	 * 如果请求是从设备端发起的，该字段通常不填写。如果是从后台发起的，该IP为用户的登录IP或者设备的公网IP。
	 *
	 * 建议您填写该IP用于分析用户违规行为。如果未在请求中填写，服务端会尝试从链接或者HTTP请求头中获取该IP。
	 */
	private String ip;
	/**
	 * 设备的操作系统类型，例如Android 6.0。
	 */
	private String os;
	/**
	 * 渠道号。
	 */
	private String channel;
	/**
	 * 宿主应用名称。
	 */
	private String hostAppName;
	/**
	 * 宿主应用包名。
	 */
	private String hostPackage;
	/**
	 * 宿主应用版本。
	 */
	private String hostVersion;

	public String getSdkVersion() {
		return sdkVersion;
	}

	public void setSdkVersion(String sdkVersion) {
		this.sdkVersion = sdkVersion;
	}

	public String getCfgVersion() {
		return cfgVersion;
	}

	public void setCfgVersion(String cfgVersion) {
		this.cfgVersion = cfgVersion;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getUmid() {
		return umid;
	}

	public void setUmid(String umid) {
		this.umid = umid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getHostAppName() {
		return hostAppName;
	}

	public void setHostAppName(String hostAppName) {
		this.hostAppName = hostAppName;
	}

	public String getHostPackage() {
		return hostPackage;
	}

	public void setHostPackage(String hostPackage) {
		this.hostPackage = hostPackage;
	}

	public String getHostVersion() {
		return hostVersion;
	}

	public void setHostVersion(String hostVersion) {
		this.hostVersion = hostVersion;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("sdkVersion", sdkVersion)
				.add("cfgVersion", cfgVersion)
				.add("userType", userType)
				.add("userId", userId)
				.add("userNick", userNick)
				.add("avatar", avatar)
				.add("imei", imei)
				.add("imsi", imsi)
				.add("umid", umid)
				.add("ip", ip)
				.add("os", os)
				.add("channel", channel)
				.add("hostAppName", hostAppName)
				.add("hostPackage", hostPackage)
				.add("hostVersion", hostVersion)
				.toString();
	}
}

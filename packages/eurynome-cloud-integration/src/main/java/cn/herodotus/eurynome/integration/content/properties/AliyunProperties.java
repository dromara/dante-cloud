package cn.herodotus.eurynome.integration.content.properties;

import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: 阿里云通用属性 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/15 11:37
 */
@ConfigurationProperties(prefix = "herodotus.integration.aliyun")
public class AliyunProperties {

    private String accessKeyId;
    private String accessKeySecret;
	private String regionId;
	/**
	 * 用户UID必须是阿里云账号的UID，而不是RAM用户的UID
	 * 使用内容检测异步callback方式会用到
	 */
	private String uid;

    private AliyunOssProperties oss = new AliyunOssProperties();
    private AliyunScanProperties scan = new AliyunScanProperties();
    private AliyunSmsProperties sms = new AliyunSmsProperties();

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

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

	public AliyunOssProperties getOss() {
		return oss;
	}

	public void setOss(AliyunOssProperties oss) {
		this.oss = oss;
	}

	public AliyunScanProperties getScan() {
		return scan;
	}

	public void setScan(AliyunScanProperties scan) {
		this.scan = scan;
	}

	public AliyunSmsProperties getSms() {
		return sms;
	}

	public void setSms(AliyunSmsProperties sms) {
		this.sms = sms;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("accessKeyId", accessKeyId)
				.add("accessKeySecret", accessKeySecret)
				.add("regionId", regionId)
				.add("uid", uid)
				.add("oss", oss)
				.add("scan", scan)
				.add("sms", sms)
				.toString();
	}
}

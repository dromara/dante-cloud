package cn.herodotus.eurynome.integration.content.domain.aliyun.image;

import cn.herodotus.eurynome.integration.content.domain.aliyun.common.Coordinate;
import com.google.common.base.MoreObjects;

/**
 * <p>Description: 阿里图片审核返回值：QrodeLocation对应实体</p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 17:00
 */
public class QrcodeLocation extends Coordinate {

	/**
	 * 识别到的二维码链接。
	 */
	private String qrcode;

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("qrcode", qrcode)
				.toString();
	}
}

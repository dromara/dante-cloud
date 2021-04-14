package cn.herodotus.eurynome.integration.compliance.domain.aliyun.common;

import cn.herodotus.eurynome.integration.compliance.domain.aliyun.common.Coordinate;
import com.google.common.base.MoreObjects;

/**
 * <p>Description: 阿里图片审核返回值：LogoData对应实体</p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 17:08
 */
public class LogoData extends Coordinate {

	/**
	 * 识别出的logo类型，取值为TV （台标）。
	 */
	private String type;
	/**
	 * 识别出的logo名称。
	 */
	private String name;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("type", type)
				.add("name", name)
				.toString();
	}
}

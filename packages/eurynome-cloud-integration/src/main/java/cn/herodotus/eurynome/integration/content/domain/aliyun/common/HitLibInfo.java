package cn.herodotus.eurynome.integration.content.domain.aliyun.common;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * <p>Description: 阿里图片审核请求：hibLibInfo对应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 17:19
 */
public class HitLibInfo implements Serializable {

	/**
	 * 文字命中的自定义文本内容。
	 */
	private String context;
	/**
	 * 文字命中的自定义文本内容对应的库code。
	 */
	private String libCode;
	/**
	 * 文字命中的自定义文本内容对应的库名称。
	 */
	private String libName;

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getLibCode() {
		return libCode;
	}

	public void setLibCode(String libCode) {
		this.libCode = libCode;
	}

	public String getLibName() {
		return libName;
	}

	public void setLibName(String libName) {
		this.libName = libName;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("context", context)
				.add("libCode", libCode)
				.add("libName", libName)
				.toString();
	}
}

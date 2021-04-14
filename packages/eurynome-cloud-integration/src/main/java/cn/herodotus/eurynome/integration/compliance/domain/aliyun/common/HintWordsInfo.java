package cn.herodotus.eurynome.integration.compliance.domain.aliyun.common;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * <p>Description: 阿里图片审核返回值：HintWordsInfo对应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 16:58
 */
public class HintWordsInfo implements Serializable {

	/**
	 * 文字命中的风险关键词内容。
	 */
	private String context;

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("context", context)
				.toString();
	}
}

package cn.herodotus.eurynome.integration.content.domain.aliyun.image;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * <p>Description: 阿里图片审核返回值：frame对应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 16:54
 */
public class Frame implements Serializable {
	/**
	 * 置信度分数，取值范围：0~100，置信度越高表示检测结果的可信度越高。建议您不要在业务中使用该分数。
	 */
	private Float rate;

	/**
	 * 被截断的图片的临时访问URL，地址有效期是5分钟。
	 */
	private String url;

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("rate", rate)
				.add("url", url)
				.toString();
	}
}

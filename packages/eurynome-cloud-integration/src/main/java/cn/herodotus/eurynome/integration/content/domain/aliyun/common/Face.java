package cn.herodotus.eurynome.integration.content.domain.aliyun.common;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * <p>Description: 阿里图片审核返回值：Face对应实体</p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 17:11
 */
public class Face implements Serializable {

	/**
	 * 相似人物的名称
	 */
	private String name;
	/**
	 * 置信度分数，取值范围：0（表示置信度最低）~100（表示置信度最高）。置信度越高表示人物识别结果的可信度越高。
	 */
	private Float rate;
	/**
	 * 人脸ID
	 */
	private String id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("name", name)
				.add("rate", rate)
				.add("id", id)
				.toString();
	}
}

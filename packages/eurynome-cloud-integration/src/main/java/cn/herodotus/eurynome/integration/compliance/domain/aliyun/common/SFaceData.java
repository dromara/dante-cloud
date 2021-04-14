package cn.herodotus.eurynome.integration.compliance.domain.aliyun.common;

import com.google.common.base.MoreObjects;

import java.util.List;

/**
 * <p>Description: 阿里图片审核返回值：SFaceData对应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 17:10
 */
public class SFaceData extends Coordinate {

	/**
	 * 识别出的人脸信息
	 */
	private List<Face> faces;

	public List<Face> getFaces() {
		return faces;
	}

	public void setFaces(List<Face> faces) {
		this.faces = faces;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("faces", faces)
				.toString();
	}
}

package cn.herodotus.eurynome.integration.compliance.domain.aliyun.image;

import cn.herodotus.eurynome.integration.compliance.domain.aliyun.common.Coordinate;
import com.google.common.base.MoreObjects;

/**
 * <p>Description: 阿里图片审核返回值：ProgramCodeData对应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 17:03
 */
public class ProgramCodeData extends Coordinate {

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.toString();
	}
}

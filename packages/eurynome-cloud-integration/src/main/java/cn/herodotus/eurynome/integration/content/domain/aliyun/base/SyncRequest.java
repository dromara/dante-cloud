package cn.herodotus.eurynome.integration.content.domain.aliyun.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p>Description: 阿里同步审核请求 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 16:44
 */
@ApiModel(value = "阿里内容检测通用基础同步请求参数实体")
public class SyncRequest<T extends AbstractTask> extends AbstractRequest<T> {

	@ApiModelProperty(name = "指定检测场景", required = true)
	private List<String> scenes;

	public List<String> getScenes() {
		return scenes;
	}

	public void setScenes(List<String> scenes) {
		this.scenes = scenes;
	}
}

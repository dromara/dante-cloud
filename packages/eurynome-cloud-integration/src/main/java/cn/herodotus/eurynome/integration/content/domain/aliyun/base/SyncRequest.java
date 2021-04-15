package cn.herodotus.eurynome.integration.content.domain.aliyun.base;

import java.util.List;

/**
 * <p>Description: 阿里同步审核请求 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 16:44
 */
public class SyncRequest<T extends AbstractTask> extends AbstractRequest<T> {

	private List<String> scenes;

	public List<String> getScenes() {
		return scenes;
	}

	public void setScenes(List<String> scenes) {
		this.scenes = scenes;
	}
}

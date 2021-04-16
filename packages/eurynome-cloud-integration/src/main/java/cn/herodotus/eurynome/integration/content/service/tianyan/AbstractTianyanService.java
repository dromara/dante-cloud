package cn.herodotus.eurynome.integration.content.service.tianyan;

import cn.herodotus.eurynome.integration.content.properties.TianyanProperties;
import cn.herodotus.eurynome.integration.definition.AbstractRestApiService;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: 天眼查基础服务类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/15 16:54
 */
public abstract class AbstractTianyanService extends AbstractRestApiService {

	@Autowired
	private TianyanProperties tianyanProperties;

	@Override
	protected String getBaseUrl() {
		return tianyanProperties.getBaseUrl();
	}

	protected Map<String, String> getTokenHeader() {
		Map<String, String> header = new HashMap<>();
		header.put(HttpHeaders.AUTHORIZATION, tianyanProperties.getToken());
		return header;
	}
}

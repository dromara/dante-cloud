package cn.herodotus.eurynome.integration.compliance.domain.aliyun.webpage;

import cn.herodotus.eurynome.integration.compliance.domain.aliyun.base.AbstractResultResponse;
import cn.herodotus.eurynome.integration.compliance.domain.aliyun.base.BaseResponse;

import java.util.List;
import java.util.Map;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 14:32
 */
public class WebpageSyncResponse extends BaseResponse {

    private String suggestion;

    private Map<String, Integer> riskFrequency;

            private List<TextResult> textResults;
                    private List<ImageResult> imageResults;

    private String highlightHtml;
}

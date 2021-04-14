package cn.herodotus.eurynome.integration.compliance.domain.aliyun.webpage;

import cn.herodotus.eurynome.integration.compliance.domain.aliyun.base.BaseResponse;

/**
 * <p>Description: 阿里音频审核异步步返回值对应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 14:31
 */
public class WebpageAsyncResponse extends BaseResponse {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
